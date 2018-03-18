package wayne.enterprises.onetask;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

public class LazyValue<T> {

	@Nullable
	private volatile T data;
	@NonNull
	private final Initializer<T> initializer;

	public LazyValue(@NonNull Initializer<T> initializer) {
		this.initializer = initializer;
	}

	@NonNull
	public synchronized T get() {
		if (data ==  null) {
			data = initializer.initialize();
		}
		return data;
	}


	public interface Initializer<T> {
		@NonNull
		T initialize();
	}

}
