package jp.ac.jec.cm0110.plantime.detabaseSat;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import jp.ac.jec.cm0110.plantime.databaseThu.PlantTimeThuDao;
import jp.ac.jec.cm0110.plantime.databaseThu.PlantTimeThuEntity;

@Database(entities = {PlantTimeSatEntity.class}, version = 1, exportSchema = false)
public abstract class PlantTimeSatDatabase extends RoomDatabase {
    public abstract PlantTimeSatDao plantTimeSatDao();
}
