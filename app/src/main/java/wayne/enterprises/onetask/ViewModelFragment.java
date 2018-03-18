package wayne.enterprises.onetask;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class ViewModelFragment<T extends ViewModel> extends Fragment {

	@Nullable
	ViewModelProviderActivity<T> parentActivity;

	@NonNull
	CompositeDisposable disposable = new CompositeDisposable();

	@Override
	public void onAttach(Context context) {
		super.onAttach(context);
		if (context instanceof ViewModelProviderActivity) {
			//noinspection unchecked
			parentActivity = (ViewModelProviderActivity) context;
		}

	}

	@Override
	public void onDetach() {
		super.onDetach();
		parentActivity = null;
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		disposable.dispose();
	}

	protected <V> void bind(@NonNull Observable<V> observable, @NonNull Consumer<V> consumer) {
		disposable.add(
				observable
						.subscribeOn(Schedulers.io())
						.observeOn(AndroidSchedulers.mainThread())
						.subscribe(consumer)
		);
	}

	@Nullable
	protected T getViewModel() {
		if (parentActivity != null) {
			return parentActivity.getViewModel();
		}
		return null;
	}

}
