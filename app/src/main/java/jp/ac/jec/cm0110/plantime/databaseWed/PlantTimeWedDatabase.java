package jp.ac.jec.cm0110.plantime.databaseWed;

import androidx.room.Database;
import androidx.room.RoomDatabase;

@Database(entities = {PlantTimeWedEntity.class}, version = 1, exportSchema = false)
public abstract class PlantTimeWedDatabase extends RoomDatabase {
    public abstract PlantTimeWedDao plantTimeWedDao();
}
