package wayne.enterprises.onetask;

import android.content.Context;
import android.support.annotation.NonNull;

public class StringHelper {

	@NonNull
	private final Context context;

	public StringHelper(@NonNull Context context) {
		this.context = context;
	}

	@NonNull
	public String getString(int resId) {
		return context.getString(resId);
	}

	@SuppressWarnings("ConfusingArgumentToVarargsMethod")
	@NonNull
	public String getCombinedString(int stringsId, String... strs) {
		return context.getString(stringsId, strs);
	}

	@NonNull
	public String getNetworkErrorString() {
		return context.getString(R.string.network_error);
	}

	@NonNull
	public String getGenericErrorString() {
		return context.getString(R.string.something_went_wrong);
	}

}
