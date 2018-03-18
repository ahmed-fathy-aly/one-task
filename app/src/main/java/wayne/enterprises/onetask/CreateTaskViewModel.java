package wayne.enterprises.onetask;

import android.support.annotation.NonNull;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.functions.Action;
import io.reactivex.schedulers.Schedulers;
import io.reactivex.subjects.BehaviorSubject;

public class CreateTaskViewModel extends ViewModel {

	@NonNull
	private final TasksRepo tasksRepo;

	@NonNull
	private final BehaviorSubject<Boolean> exit;

	public CreateTaskViewModel(@NonNull TasksRepo tasksRepo) {
		this.tasksRepo = tasksRepo;
		this.exit = BehaviorSubject.createDefault(false);
	}

	@NonNull
	Observable<Boolean> exit() {
		return exit;
	}

	public void createTask(@NonNull String title) {
		if (!title.isEmpty()) {
			onAnotherThread(() -> tasksRepo.createTask(title), () -> exit.onNext(true));
		}
	}

}
