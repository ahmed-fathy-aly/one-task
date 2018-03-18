package wayne.enterprises.onetask;

import android.support.annotation.NonNull;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;

import static wayne.enterprises.onetask.TaskDataModel.Status.CURRENT;
import static wayne.enterprises.onetask.TaskDataModel.Status.DONE;
import static wayne.enterprises.onetask.TaskDataModel.Status.PAUSED;

public class TasksRepo {

	@NonNull
	private final TasksDao databaseDao;

	public TasksRepo(@NonNull TasksDao databaseDao) {
		this.databaseDao = databaseDao;
	}

	@NonNull
	Observable<List<TaskDataModel>> getTasks() {
		return databaseDao.getAll().toObservable();
	}

	void createTask(@NonNull String title) {
		TaskDataModel task = new TaskDataModel(0, title, TaskDataModel.Status.PAUSED);
		databaseDao.insert(task);
	}

	public void clear() {
		databaseDao.clear();
	}

	/**
	 * mantains that there's only 1 task running
	 */
	public void changeStatus(int taskId, @NonNull Function<TaskDataModel.Status, TaskDataModel.Status> newStatusFunction) {
		databaseDao
				.getAllSingle()
				.subscribe(tasks -> {
					List<TaskDataModel> changed = new ArrayList<>();
					for (TaskDataModel task : tasks) {
						if (task.getId() == taskId) {
							changed.add(task.setStatus(newStatusFunction.apply(task.getStatus())));
						} else if (task.getStatus() == CURRENT) {
							changed.add(task.setStatus(PAUSED));
						} else {
							changed.add(task);
						}
					}
					databaseDao.insertAll(changed);
				});
	}
}
