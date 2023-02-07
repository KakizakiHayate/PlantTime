package jp.ac.jec.cm0110.plantime.detabaseSat;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "ptdatabaseSat")
public class PlantTimeSatEntity {
    @PrimaryKey(autoGenerate = true)
    public int id;
    public String firstPeriod;
    public String twoPeriod;
    public String threePeriod;
    public String fourPeriod;

    public PlantTimeSatEntity(String firstPeriod, String twoPeriod, String threePeriod, String fourPeriod) {
        this.firstPeriod = firstPeriod;
        this.twoPeriod = twoPeriod;
        this.threePeriod = threePeriod;
        this.fourPeriod = fourPeriod;
    }

    public int getIdSat() {
        return id;
    }

    public String getFirstPeriodSat() {
        return firstPeriod;
    }

    public String getTwoPeriodSat() {
        return twoPeriod;
    }

    public String getThreePeriodSat() {
        return threePeriod;
    }

    public String getFourPeriodSat() {
        return fourPeriod;
    }
}
