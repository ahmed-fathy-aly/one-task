package wayne.enterprises.onetask;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;

public abstract class ViewModelProviderActivity<T extends ViewModel> extends AppCompatActivity {

	private static final String TAG_HOLDER_FRAGMENT = "tag_holder_fragment";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_base_fragment);

		if (getSupportFragmentManager()
				.findFragmentById(R.id.fragment_container) == null) {
			getSupportFragmentManager()
					.beginTransaction()
					.replace(R.id.fragment_container, createFragment())
					.commit();
		}
	}

	@NonNull
	protected T getViewModel() {
		// check if the holder fragment is already added
		T viewModel = null;
		Fragment holderFragment = getSupportFragmentManager().findFragmentByTag(TAG_HOLDER_FRAGMENT);
		if (holderFragment != null) {
			Object fragmentData = ((DataHolderFragment) holderFragment).getData();
			if (fragmentData != null) {
				// noinspection unchecked
				viewModel = (T) fragmentData;
			}
		}

		// holder fragment is not created/destroyed, then create a new view model and its holder fragment
		if (viewModel == null) {
			viewModel = createViewModel();
			DataHolderFragment<T> newHolderFragment = DataHolderFragment.newInstance();
			newHolderFragment.setData(viewModel);
			getSupportFragmentManager()
					.beginTransaction()
					.add(newHolderFragment, TAG_HOLDER_FRAGMENT)
					.commit();
		}

		return viewModel;
	}

	protected abstract ViewModelFragment createFragment();

	@NonNull
	protected abstract T createViewModel();
}
