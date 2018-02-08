package wayne.enterprises.onetask;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import io.reactivex.observers.TestObserver;
import io.reactivex.subjects.BehaviorSubject;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class TasksViewModelTest {

	@Test
	public void testScenario() {
		TasksRepo repo = mock(TasksRepo.class);
		TasksViewModel viewModel = new TasksViewModel(repo);
		final BehaviorSubject<List<TaskDataModel>> tasks = BehaviorSubject.create();
		when(repo.getTasks())
				.thenReturn(tasks);
		doAnswer(invocation -> {
			List<TaskDataModel> currentTasks = new ArrayList<>(tasks.getValue());
			currentTasks.add(new TaskDataModel(currentTasks.size() + 1, invocation.getArgument(0), TaskDataModel.Status.PAUSED));
			tasks.onNext(currentTasks);
			return null;
		}).when(repo).createTask(anyString());

		TestObserver<List<TaskUiModel>> currentTasksObserver = TestObserver.create();
		viewModel.getCurrentTasks().subscribe(currentTasksObserver);
		TestObserver<List<TaskUiModel>> doneTasksObserver = TestObserver.create();
		viewModel.getDoneTasks().subscribe(doneTasksObserver);
		TestObserver<Boolean> showCreateTaskWindowObserver = TestObserver.create();
		viewModel.showCreateTaskWindow().subscribe(showCreateTaskWindowObserver);

		tasks.onNext(Arrays.asList(
				new TaskDataModel(1, "a", TaskDataModel.Status.PAUSED),
				new TaskDataModel(2, "b", TaskDataModel.Status.DONE),
				new TaskDataModel(3, "c", TaskDataModel.Status.PAUSED),
				new TaskDataModel(4, "d", TaskDataModel.Status.PAUSED),
				new TaskDataModel(5, "e", TaskDataModel.Status.DONE)));

		currentTasksObserver
				.assertValue(Arrays.asList(
						new TaskUiModel(1, "a", true),
						new TaskUiModel(3, "c", true),
						new TaskUiModel(4, "d", true)));
		doneTasksObserver
				.assertValue(Arrays.asList(
						new TaskUiModel(2, "b", false),
						new TaskUiModel(5, "e", false)));

		showCreateTaskWindowObserver.assertValue(false);

		// click the create task button, press back and create again
		viewModel.onClickCreateNewTask();
		showCreateTaskWindowObserver.assertValueAt(1, true);
		viewModel.onBackPressed();
		showCreateTaskWindowObserver.assertValueAt(2, false);
		viewModel.onClickCreateNewTask();
		showCreateTaskWindowObserver.assertValueAt(3, true);

		// create a new tasks
		viewModel.onCreateNewTask("f");

		showCreateTaskWindowObserver.assertValueAt(4, false);
		currentTasksObserver
				.assertValueAt(1, Arrays.asList(
						new TaskUiModel(1, "a", true),
						new TaskUiModel(3, "c", true),
						new TaskUiModel(4, "d", true),
						new TaskUiModel(6, "f", true)));
	}

}
