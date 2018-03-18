package wayne.enterprises.onetask;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import timber.log.Timber;


public class TaskTabsFragment extends ViewModelFragment<TasksViewModel> {

	@BindView(R.id.tasks_tabs_fab_add)
	FloatingActionButton fabAdd;
	@BindView(R.id.recycler_view_tasks)
	RecyclerView recyclerViewTasks;

	@Nullable
	private Unbinder binding;

	@NonNull
	public static TaskTabsFragment newInstance() {
		return new TaskTabsFragment();
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
							 Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_task_tabs, container, false);
		binding = ButterKnife.bind(this, view);

		TasksViewModel viewModel = getViewModel();
		if (viewModel != null) {
			Map<Class, GenericRecyclerViewDelegate> delegates = new HashMap<>();
			delegates.put(TaskUiModel.class, new TaskRecyclerViewDelegate(new TaskRecyclerViewDelegate.Listener() {
				@Override
				public void onStartStopClicked(@NonNull TaskUiModel task) {
					viewModel.onToggleTask(task);
				}

				@Override
				public void onDeleteClicked(@NonNull TaskUiModel task) {
					viewModel.onPressDone(task);
				}
			}));
			GenericRecyclerViewAdapter adapter = new GenericRecyclerViewAdapter(delegates);
			recyclerViewTasks.setAdapter(adapter);
			recyclerViewTasks.setLayoutManager(new LinearLayoutManager(getContext().getApplicationContext()));
			bind(viewModel.getCurrentTasks(), adapter::changeData);
			bind(viewModel.showCreateTaskWindow().filter(show -> show), show -> startActivity(CreateTaskActivity.newIntent(getContext())));
			fabAdd.setOnClickListener(v -> viewModel.onClickCreateNewTask());
		}
		return view;
	}

	@Override
	public void onDestroyView() {
		super.onDestroyView();
		if (binding != null) {
			binding.unbind();
		}
	}
}
