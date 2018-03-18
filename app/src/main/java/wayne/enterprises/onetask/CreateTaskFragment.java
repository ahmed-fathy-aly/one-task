package wayne.enterprises.onetask;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;


public class CreateTaskFragment extends ViewModelFragment<CreateTaskViewModel> {

	@BindView(R.id.create_task_et_title)
	TextView editTextTitle;
	@BindView(R.id.create_task_btn_create)
	Button buttonCreate;

	@Nullable
	private Unbinder binding;

	@NonNull
	public static CreateTaskFragment newInstance() {
		return new CreateTaskFragment();
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
							 Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_create_task, container, false);
		binding = ButterKnife.bind(this, view);

		CreateTaskViewModel viewModel = getViewModel();
		if (viewModel != null) {
			bind(viewModel.exit().filter(isTrue -> isTrue), finish -> getActivity().finish());
			buttonCreate.setOnClickListener(unused -> viewModel.createTask(editTextTitle.getText().toString()));
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
