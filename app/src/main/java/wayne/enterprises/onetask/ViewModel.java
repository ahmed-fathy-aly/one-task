package wayne.enterprises.onetask;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import io.reactivex.Observable;
import io.reactivex.functions.Action;
import io.reactivex.schedulers.Schedulers;

public class ViewModel {

	protected void onAnotherThread(@NonNull Runnable runnable, @Nullable Action afterTerminate) {
		Observable observable = Observable
				.create((subscriber) -> {
					runnable.run();
					subscriber.onComplete();
				})
				.subscribeOn(Schedulers.computation());
		if (afterTerminate != null) {
			observable = observable.doAfterTerminate(afterTerminate);
		}
		observable.subscribe();
	}
}
