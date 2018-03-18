package wayne.enterprises.onetask;

import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

public interface GenericRecyclerViewDelegate<VH extends RecyclerView.ViewHolder, O> {
	VH createViewHolder(ViewGroup parent);

	void onBindViewHolder(VH holder, O data);
}
