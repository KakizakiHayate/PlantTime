package jp.ac.jec.cm0110.plantime.databaseSun;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import jp.ac.jec.cm0110.plantime.databaseThu.PlantTimeThuDao;
import jp.ac.jec.cm0110.plantime.databaseThu.PlantTimeThuEntity;

@Database(entities = {PlantTimeSunEntity.class}, version = 1, exportSchema = false)
public abstract class PlantTimeSunDatabase extends RoomDatabase {
    public abstract PlantTimeSunDao plantTimeSunDao();
}