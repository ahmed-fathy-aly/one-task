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

	public void setCurrentTask(int id) {
		databaseDao
				.getAllSingle()
				.subscribe(tasks -> {
					List<TaskDataModel> changed = new ArrayList<>();
					for (TaskDataModel task : tasks) {
						if (task.getId() == id && task.getStatus() != CURRENT) {
							changed.add(task.setStatus(CURRENT));
						} else if (task.getStatus() == CURRENT) {
							changed.add(task.setStatus(PAUSED));
						}
					}
					databaseDao.insertAll(changed);
				});

	}

	public void markDone(int id) {
		databaseDao.update(id, DONE);
	}
}
