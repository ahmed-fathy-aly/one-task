package wayne.enterprises.onetask;

import android.support.annotation.NonNull;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.functions.Predicate;
import io.reactivex.subjects.BehaviorSubject;

import static wayne.enterprises.onetask.TaskDataModel.Status.CURRENT;
import static wayne.enterprises.onetask.TaskDataModel.Status.DONE;
import static wayne.enterprises.onetask.TaskDataModel.Status.PAUSED;

public class TasksViewModel extends ViewModel {

	@NonNull
	private final TasksRepo tasksRepo;

	private final BehaviorSubject<Boolean> showCreateTaskWindow;


	public TasksViewModel(@NonNull TasksRepo tasksRepo) {
		this.tasksRepo = tasksRepo;
		this.showCreateTaskWindow = BehaviorSubject.createDefault(false);
	}

	@NonNull
	public Observable<List<TaskUiModel>> getCurrentTasks() {
		return tasksRepo
				.getTasks()
				.map(tasks -> filterAndConvertTasks(tasks, task -> task.getStatus() != DONE));
	}

	@NonNull
	public Observable<List<TaskUiModel>> getDoneTasks() {
		return tasksRepo
				.getTasks()
				.map(tasks -> filterAndConvertTasks(tasks, task -> task.getStatus() == DONE));
	}

	@NonNull
	public Observable<Boolean> showCreateTaskWindow() {
		return showCreateTaskWindow;
	}

	public void onClickCreateNewTask() {
		if (Boolean.FALSE.equals(showCreateTaskWindow.getValue())) {
			showCreateTaskWindow.onNext(true);
			showCreateTaskWindow.onNext(false);
		}
	}

	@NonNull
	private List<TaskUiModel> filterAndConvertTasks(@NonNull List<TaskDataModel> tasks, Predicate<? super TaskDataModel> predicate) {
		return Observable
				.fromIterable(tasks)
				.filter(predicate)
				.map(this::convertToUiModel)
				.toList()
				.blockingGet();
	}

	@NonNull
	private TaskUiModel convertToUiModel(@NonNull TaskDataModel task) {
		return new TaskUiModel(task.getId(), task.getDescription(), task.getStatus() == PAUSED);
	}

	public void onBackPressed() {
		if (Boolean.TRUE.equals(showCreateTaskWindow.getValue())) {
			showCreateTaskWindow.onNext(false);
		}
	}

	public void onToggleTask(@NonNull TaskUiModel task) {
		onAnotherThread(() -> tasksRepo.changeStatus(task.getId(), (oldStatus) -> {
			switch (oldStatus) {
				case DONE:
					return DONE;
				case PAUSED:
					return CURRENT;
				case CURRENT:
					return PAUSED;
				default:
					return null;
			}
		}), null);
	}

	public void onPressDone(@NonNull TaskUiModel task) {
		onAnotherThread(() -> tasksRepo.changeStatus(task.getId(), unused -> DONE), null);

	}
}
