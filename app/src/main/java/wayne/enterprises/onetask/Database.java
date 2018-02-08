package wayne.enterprises.onetask;

import android.arch.persistence.room.RoomDatabase;
import android.arch.persistence.room.TypeConverters;
import android.support.annotation.NonNull;

@android.arch.persistence.room.Database(entities = TaskDataModel.class, version = 1)
@TypeConverters(DatabaseConverters.class)
public abstract class Database extends RoomDatabase{
	@NonNull
	public abstract TasksDao tasksDao();
}
