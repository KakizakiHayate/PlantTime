package jp.ac.jec.cm0110.plantime.databaseSun;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;
@Dao
public interface PlantTimeSunDao {
    @Query("SELECT * FROM ptdatabaseSun")
    List<PlantTimeSunEntity> getAll();

    @Query("DELETE FROM ptdatabaseSun")
    void deleteAll();

    @Query("DELETE FROM ptdatabaseSun WHERE id = :i")
    void delete(int i);

    @Insert
    void insert(PlantTimeSunEntity plantTimeSunEntity);
}
