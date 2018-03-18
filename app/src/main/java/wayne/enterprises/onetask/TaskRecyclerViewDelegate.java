package wayne.enterprises.onetask;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;

public class TaskRecyclerViewDelegate implements GenericRecyclerViewDelegate<TaskRecyclerViewDelegate.ViewHolder, TaskUiModel> {

	@NonNull
	private Listener listener;

	public TaskRecyclerViewDelegate(@NonNull Listener listener) {
		this.listener = listener;
	}

	@Override
	public ViewHolder createViewHolder(ViewGroup parent) {
		View view = LayoutInflater
				.from(parent.getContext())
				.inflate(R.layout.row_task, parent, false);
		return new ViewHolder(view);
	}

	@Override
	public void onBindViewHolder(ViewHolder holder, TaskUiModel data) {
		holder.textViewTitle.setText(data.getTitle());
		holder.buttonStartPause.setText(data.isShowStart() ? R.string.Start : R.string.Stop);
		holder.buttonStartPause.setOnClickListener(v -> listener.onStartStopClicked(data));
		holder.buttonDelete.setOnClickListener(v -> listener.onDeleteClicked(data));
	}

	public static class ViewHolder extends RecyclerView.ViewHolder {

		@BindView(R.id.row_task_tv_title)
		TextView textViewTitle;
		@BindView(R.id.row_task_btn_start_pause)
		Button buttonStartPause;
		@BindView(R.id.row_task_btn_delete)
		Button buttonDelete;

		public ViewHolder(View itemView) {
			super(itemView);
			ButterKnife.bind(this, itemView);
		}
	}

	public interface Listener {
		void onStartStopClicked(@NonNull TaskUiModel task);

		void onDeleteClicked(@NonNull TaskUiModel task);
	}
}
