package jp.ac.jec.cm0110.plantime.databaseTue;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import jp.ac.jec.cm0110.plantime.database.PlantTimeDao;

@Database(entities = {PlantTimeEntityTue.class}, version = 1, exportSchema = false)
public abstract class PlantTimeTueDatabase extends RoomDatabase {
    public abstract PlantTimeTueDao plantTimeTueDao();
}
