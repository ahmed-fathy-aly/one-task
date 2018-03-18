package wayne.enterprises.onetask;

import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * to use it, wrap view holders in {@link GenericRecyclerViewDelegate}
 */
public class GenericRecyclerViewAdapter extends RecyclerView.Adapter {
	private final List<Class> mDataTypes;
	private final Map<Class, GenericRecyclerViewDelegate> mDelegates;
	private List<Object> mData;

	public GenericRecyclerViewAdapter(Map<Class, GenericRecyclerViewDelegate> delegates) {
		mDelegates = delegates;
		mDataTypes = new ArrayList<>(mDelegates.keySet());
		mData = new ArrayList<>();
	}

	public <T> void changeData(List<T> newData) {
		mData.clear();
		mData.addAll(newData);
		notifyDataSetChanged();
	}

	@Override
	public RecyclerView.ViewHolder onCreateViewHolder(
			ViewGroup parent, int viewType) {
		return mDelegates.get(mDataTypes.get(viewType)).createViewHolder(parent);
	}

	@Override
	public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
		Object item = mData.get(position);
		mDelegates.get(item.getClass()).onBindViewHolder(holder, item);
	}

	@Override
	public int getItemCount() {
		return mData.size();
	}

	@Override
	public int getItemViewType(int position) {
		return mDataTypes.indexOf(mData.get(position).getClass());
	}

}
