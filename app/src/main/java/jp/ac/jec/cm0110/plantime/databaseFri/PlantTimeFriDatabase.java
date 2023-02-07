package jp.ac.jec.cm0110.plantime.databaseFri;

import androidx.room.Database;
import androidx.room.RoomDatabase;

@Database(entities = {PlantTimeFriEntity.class}, version = 1, exportSchema = false)

public abstract class PlantTimeFriDatabase extends RoomDatabase {
    public abstract PlantTimeFriDao plantTimeFriDao();
}
