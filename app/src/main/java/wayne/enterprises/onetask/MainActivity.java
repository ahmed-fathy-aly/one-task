package wayne.enterprises.onetask;

import android.arch.lifecycle.GenericLifecycleObserver;
import android.arch.lifecycle.Lifecycle;
import android.arch.lifecycle.LifecycleOwner;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

public class MainActivity extends ViewModelProviderActivity<TasksViewModel> {

	@Override
	protected ViewModelFragment createFragment() {
		return TaskTabsFragment.newInstance();
	}

	@NonNull
	@Override
	protected TasksViewModel createViewModel() {
		return ViewModelFactory.getInstance().tasksViewModel();
	}
}
