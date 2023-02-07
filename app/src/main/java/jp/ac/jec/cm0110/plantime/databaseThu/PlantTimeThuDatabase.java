package jp.ac.jec.cm0110.plantime.databaseThu;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import jp.ac.jec.cm0110.plantime.databaseTue.PlantTimeEntityTue;
import jp.ac.jec.cm0110.plantime.databaseTue.PlantTimeTueDao;

@Database(entities = {PlantTimeThuEntity.class}, version = 1, exportSchema = false)

public abstract class PlantTimeThuDatabase extends RoomDatabase {
        public abstract PlantTimeThuDao plantTimeThuDao();
}

