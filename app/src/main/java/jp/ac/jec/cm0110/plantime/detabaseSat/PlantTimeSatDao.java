package jp.ac.jec.cm0110.plantime.detabaseSat;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

import jp.ac.jec.cm0110.plantime.databaseWed.PlantTimeWedEntity;

@Dao
public interface PlantTimeSatDao {
    @Query("SELECT * FROM ptdatabaseSat")
    List<PlantTimeSatEntity> getAll();

    @Query("DELETE FROM ptdatabaseSat")
    void deleteAll();

    @Query("DELETE FROM ptdatabaseSat WHERE id = :i")
    void delete(int i);

    @Insert
    void insert(PlantTimeSatEntity plantTimeSatEntity);
}
