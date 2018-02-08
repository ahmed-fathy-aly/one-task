package wayne.enterprises.onetask;

import android.arch.core.executor.testing.InstantTaskExecutorRule;
import android.arch.persistence.room.Room;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.test.InstrumentationRegistry;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.TimeUnit;

import io.reactivex.observers.BaseTestConsumer;
import io.reactivex.observers.TestObserver;

public class TasksRepoTest {

	@Rule
	public InstantTaskExecutorRule instantTaskExecutorRule = new InstantTaskExecutorRule();

	private TasksRepo repo;
	private TestObserver<Object> observer;

	@Before
	public void setup() {
		Context context = InstrumentationRegistry.getTargetContext();
		Database db = Room
				.inMemoryDatabaseBuilder(context, Database.class)
				.allowMainThreadQueries()
				.build();
		TasksDao dao = db.tasksDao();
		repo = new TasksRepo(dao);
		observer = TestObserver.create();
		repo.getTasks().subscribe(observer);
	}

	@Test
	public void testCreateUpdateClear() throws InterruptedException {

		// create
		assertLastValue(observer, Collections.emptyList());
		repo.createTask("a");
		assertLastValue(observer, Collections.singletonList(
				new TaskDataModel(1, "a", TaskDataModel.Status.PAUSED)));
		repo.createTask("b");
		assertLastValue(observer, Arrays.asList(
				new TaskDataModel(1, "a", TaskDataModel.Status.PAUSED),
				new TaskDataModel(2, "b", TaskDataModel.Status.PAUSED)));
		repo.createTask("c");
		assertLastValue(observer, Arrays.asList(
				new TaskDataModel(1, "a", TaskDataModel.Status.PAUSED),
				new TaskDataModel(2, "b", TaskDataModel.Status.PAUSED),
				new TaskDataModel(3, "c", TaskDataModel.Status.PAUSED)));

		// set current task
		repo.setCurrentTask(1);
		assertLastValue(observer, Arrays.asList(
				new TaskDataModel(1, "a", TaskDataModel.Status.CURRENT),
				new TaskDataModel(2, "b", TaskDataModel.Status.PAUSED),
				new TaskDataModel(3, "c", TaskDataModel.Status.PAUSED)));

		repo.setCurrentTask(2);
		assertLastValue(observer, Arrays.asList(
				new TaskDataModel(1, "a", TaskDataModel.Status.PAUSED),
				new TaskDataModel(2, "b", TaskDataModel.Status.CURRENT),
				new TaskDataModel(3, "c", TaskDataModel.Status.PAUSED)));

		// mark finished
		repo.markDone(1);
		assertLastValue(observer, Arrays.asList(
				new TaskDataModel(1, "a", TaskDataModel.Status.DONE),
				new TaskDataModel(2, "b", TaskDataModel.Status.CURRENT),
				new TaskDataModel(3, "c", TaskDataModel.Status.PAUSED)));

		repo.markDone(2);
		assertLastValue(observer, Arrays.asList(
				new TaskDataModel(1, "a", TaskDataModel.Status.DONE),
				new TaskDataModel(2, "b", TaskDataModel.Status.DONE),
				new TaskDataModel(3, "c", TaskDataModel.Status.PAUSED)));

		// clear
		repo.clear();
		assertLastValue(observer, Collections.emptyList());

	}

	private void assertLastValue(TestObserver<Object> observer, List<Object> objects) throws InterruptedException {
		observer.assertValueAt(observer.valueCount() - 1, objects);
	}
}
