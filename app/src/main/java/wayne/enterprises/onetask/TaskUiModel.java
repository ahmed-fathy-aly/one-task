package wayne.enterprises.onetask;

import android.support.annotation.NonNull;

import java.util.Objects;

public class TaskUiModel {

	@NonNull
	private final int id;
	@NonNull
	private final String title;
	private final boolean showStart;


	public TaskUiModel(int id, @NonNull String title, boolean showStart) {
		this.id = id;
		this.title = title;
		this.showStart = showStart;
	}

	public boolean isShowStart() {
		return showStart;
	}

	@NonNull
	public String getTitle() {
		return title;
	}

	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof TaskUiModel)) {
			return false;
		}
		TaskUiModel other = (TaskUiModel) obj;
		return other.title.equals(title)
				&& other.showStart == showStart;
	}

	@NonNull
	public int getId() {
		return id;
	}

	@Override
	public int hashCode() {
		return Objects.hash(title, showStart);
	}

	@Override
	public String toString() {
		return String.format("title : %s ,showStart? : %b", title, showStart);
	}
}
