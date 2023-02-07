package jp.ac.jec.cm0110.plantime.databaseTue;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;


@Dao
public interface PlantTimeTueDao {
    @Query("SELECT * FROM ptdatabaseTue")
    List<PlantTimeEntityTue> getAll();

    @Query("DELETE FROM ptdatabaseTue")
    void deleteAll();

    @Query("DELETE FROM ptdatabaseTue WHERE id = :i")
    void delete(int i);

    @Insert
    void insert(PlantTimeEntityTue plantTimeEntityTue);

}
