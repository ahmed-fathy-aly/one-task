package wayne.enterprises.onetask;

import timber.log.Timber;

public class Application extends android.app.Application {

	@Override
	public void onCreate() {
		super.onCreate();
		ViewModelFactory.getInstance().init(this);

		Timber.plant(new Timber.DebugTree());
	}
}
