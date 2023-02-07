package jp.ac.jec.cm0110.plantime.database;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface PlantTimeDao {
    @Query("SELECT * FROM ptdatabase")
    List<PlantTimeEntity> getAll();

    @Query("DELETE FROM ptdatabase")
    void deleteAll();

    @Query("DELETE FROM ptdatabase WHERE id = :i")
    void delete(int i);

    @Insert
    void insert(PlantTimeEntity plantTimeEntity);
}
