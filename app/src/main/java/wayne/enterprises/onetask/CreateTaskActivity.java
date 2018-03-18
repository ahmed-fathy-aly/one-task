package wayne.enterprises.onetask;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;

public class CreateTaskActivity extends ViewModelProviderActivity<CreateTaskViewModel> {

	@NonNull
	public static Intent newIntent(@NonNull Context context) {
		return new Intent(context, CreateTaskActivity.class);
	}

	@Override
	protected ViewModelFragment createFragment() {
		return CreateTaskFragment.newInstance();
	}

	@NonNull
	@Override
	protected CreateTaskViewModel createViewModel() {
		return ViewModelFactory.getInstance().createTaskViewModel();
	}
}
