package wayne.enterprises.onetask;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.support.annotation.NonNull;

import java.util.List;

import io.reactivex.Flowable;
import io.reactivex.Single;

@Dao
public interface TasksDao {


	@Query("SELECT * FROM TaskDataModel")
	Flowable<List<TaskDataModel>> getAll();

	@Query("SELECT * FROM TaskDataModel")
	Single<List<TaskDataModel>> getAllSingle();

	@Insert(onConflict = OnConflictStrategy.REPLACE)
	void insert(TaskDataModel task);

	@Query("UPDATE TaskDataModel SET status = :newStatus WHERE id = :taskId")
	void update(int taskId, TaskDataModel.Status newStatus);

	@Query("SELECT * FROM TaskDataModel WHERE id = :taskId")
	Single<TaskDataModel> getTask(int taskId);

	@Query("DELETE from TaskDataModel")
	void clear();

	@Insert(onConflict = OnConflictStrategy.REPLACE)
	void insertAll(List<TaskDataModel> newTasks);
}
