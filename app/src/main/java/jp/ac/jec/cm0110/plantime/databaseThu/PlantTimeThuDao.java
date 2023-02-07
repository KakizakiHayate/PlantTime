package jp.ac.jec.cm0110.plantime.databaseThu;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;
@Dao
public interface PlantTimeThuDao {
    @Query("SELECT * FROM ptdatabaseThu")
    List<PlantTimeThuEntity> getAll();

    @Query("DELETE FROM ptdatabaseThu")
    void deleteAll();

    @Query("DELETE FROM ptdatabaseThu WHERE id = :i")
    void delete(int i);

    @Insert
    void insert(PlantTimeThuEntity plantTimeThuEntity);
}
