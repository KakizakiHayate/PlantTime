package jp.ac.jec.cm0110.plantime.databaseFri;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "ptdatabaseFri")
public class PlantTimeFriEntity {
    @PrimaryKey(autoGenerate = true)
    public int id;
    public String firstPeriod;
    public String twoPeriod;
    public String threePeriod;
    public String fourPeriod;

    public PlantTimeFriEntity(String firstPeriod, String twoPeriod, String threePeriod, String fourPeriod) {
        this.firstPeriod = firstPeriod;
        this.twoPeriod = twoPeriod;
        this.threePeriod = threePeriod;
        this.fourPeriod = fourPeriod;
    }

    public int getIdFri() {
        return id;
    }

    public String getFirstPeriodFri() {
        return firstPeriod;
    }

    public String getTwoPeriodFri() {
        return twoPeriod;
    }

    public String getThreePeriodFri() {
        return threePeriod;
    }

    public String getFourPeriodFri() {
        return fourPeriod;
    }
}
