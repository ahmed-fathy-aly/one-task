package wayne.enterprises.onetask;

import android.arch.persistence.room.Room;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

public class ViewModelFactory {

	@Nullable
	private LazyValue<Database> database;


	private static class InstanceHolder {
		private static ViewModelFactory INSTANCE = new ViewModelFactory();
	}

	private ViewModelFactory() {
	}

	public void init(@NonNull Context context) {
		database = new LazyValue<>(() ->
				Room
						.databaseBuilder(context, Database.class, Config.DATA_BASE_NAME)
						.build());
	}
	@NonNull
	public static ViewModelFactory getInstance() {
		return InstanceHolder.INSTANCE;
	}


	public TasksViewModel tasksViewModel() {
	return new TasksViewModel(tasksRepo());
	}

	public CreateTaskViewModel createTaskViewModel() {
		return new CreateTaskViewModel(tasksRepo());
	}

	private TasksRepo tasksRepo() {
		return new TasksRepo(database.get().tasksDao());
	}
}
