package wayne.enterprises.onetask;

import android.arch.persistence.room.TypeConverter;
import android.support.annotation.NonNull;

public class DatabaseConverters {
	@TypeConverter
	public static TaskDataModel.Status toStatus(int ordinal) {
		return TaskDataModel.Status.values()[ordinal];
	}

	@TypeConverter
	public static int toOrdinal(@NonNull TaskDataModel.Status status) {
		return status.ordinal();
	}

}
