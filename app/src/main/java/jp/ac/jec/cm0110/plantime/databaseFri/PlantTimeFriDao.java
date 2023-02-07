package jp.ac.jec.cm0110.plantime.databaseFri;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

import jp.ac.jec.cm0110.plantime.databaseThu.PlantTimeThuEntity;

@Dao
public interface PlantTimeFriDao {
    @Query("SELECT * FROM ptdatabaseFri")
    List<PlantTimeFriEntity> getAll();

    @Query("DELETE FROM ptdatabaseFri")
    void deleteAll();

    @Query("DELETE FROM ptdatabaseFri WHERE id = :i")
    void delete(int i);

    @Insert
    void insert(PlantTimeFriEntity plantTimeFriEntity);
}
