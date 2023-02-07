package jp.ac.jec.cm0110.plantime.database;

import androidx.room.Database;
import androidx.room.RoomDatabase;

@Database(entities = {PlantTimeEntity.class}, version = 1, exportSchema = false)
public abstract class PlantTimeDatabase extends RoomDatabase {
    public abstract PlantTimeDao plantTimeDao();
}
