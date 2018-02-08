package wayne.enterprises.onetask;

import android.annotation.SuppressLint;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.arch.persistence.room.TypeConverter;
import android.arch.persistence.room.TypeConverters;
import android.support.annotation.NonNull;

import java.util.Objects;

@Entity
public class TaskDataModel {

	@NonNull
	@PrimaryKey(autoGenerate = true)
	private final int id;

	@NonNull
	private final String description;

	@NonNull
	private final Status status;

	public TaskDataModel(int id, @NonNull String description, @NonNull Status status) {
		this.id = id;
		this.description = description;
		this.status = status;
	}

	@NonNull
	public int getId() {
		return id;
	}

	@NonNull
	public String getDescription() {
		return description;
	}

	@NonNull
	public Status getStatus() {
		return status;
	}

	@NonNull
	public TaskDataModel setStatus(@NonNull Status newStatus) {
		return new TaskDataModel(id, description, newStatus);
	}

	public enum Status {
		DONE, PAUSED, CURRENT
	}

	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof TaskDataModel)) {
			return false;
		}
		TaskDataModel other = (TaskDataModel) obj;

		return id == other.id
				&& Objects.equals(description, other.description)
				&& status == other.status;
	}

	@Override
	public int hashCode() {
		return Objects.hash(id, description, status);
	}

	@SuppressLint("DefaultLocale")
	@Override
	public String toString() {
		return String.format("id: %d description : %s status: %s", id, description, status.name());
	}
}
