package jp.ac.jec.cm0110.plantime.databaseSun;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "ptdatabaseSun")
public class PlantTimeSunEntity {
    @PrimaryKey(autoGenerate = true)
    public int id;
    public String firstPeriod;
    public String twoPeriod;
    public String threePeriod;
    public String fourPeriod;

    public PlantTimeSunEntity(String firstPeriod, String twoPeriod, String threePeriod, String fourPeriod) {
        this.firstPeriod = firstPeriod;
        this.twoPeriod = twoPeriod;
        this.threePeriod = threePeriod;
        this.fourPeriod = fourPeriod;
    }

    public int getIdSun() {
        return id;
    }

    public String getFirstPeriodSun() {
        return firstPeriod;
    }

    public String getTwoPeriodSun() {
        return twoPeriod;
    }

    public String getThreePeriodSun() {
        return threePeriod;
    }

    public String getFourPeriodSun() {
        return fourPeriod;
    }
}
