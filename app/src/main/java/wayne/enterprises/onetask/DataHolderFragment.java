package wayne.enterprises.onetask;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;

public class DataHolderFragment<T> extends Fragment {
	@Nullable
	private T data;

	public static <T> DataHolderFragment<T> newInstance() {
		DataHolderFragment<T> fragment = new DataHolderFragment<>();
		fragment.setRetainInstance(true);
		return fragment;
	}

	@Nullable
	public T getData() {
		return data;
	}

	public void setData(@Nullable T data) {
		this.data = data;
	}
}
