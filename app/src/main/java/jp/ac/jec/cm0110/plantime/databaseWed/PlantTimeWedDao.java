package jp.ac.jec.cm0110.plantime.databaseWed;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface PlantTimeWedDao {
    @Query("SELECT * FROM ptdatabaseWed")
    List<PlantTimeWedEntity> getAll();

    @Query("DELETE FROM ptdatabaseWed")
    void deleteAll();

    @Query("DELETE FROM ptdatabaseWed WHERE id = :i")
    void delete(int i);

    @Insert
    void insert(PlantTimeWedEntity plantTimeWedEntity);
}
