package jp.ac.jec.cm0110.plantime.databaseWed;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "ptdatabaseWed")
public class PlantTimeWedEntity {
    @PrimaryKey(autoGenerate = true)
    public int id;
    public String firstPeriod;
    public String twoPeriod;
    public String threePeriod;
    public String fourPeriod;

    public PlantTimeWedEntity(String firstPeriod, String twoPeriod, String threePeriod, String fourPeriod) {
        this.firstPeriod = firstPeriod;
        this.twoPeriod = twoPeriod;
        this.threePeriod = threePeriod;
        this.fourPeriod = fourPeriod;
    }

    public int getIdWed() {
        return id;
    }

    public String getFirstPeriodWed() {
        return firstPeriod;
    }

    public String getTwoPeriodWed() {
        return twoPeriod;
    }

    public String getThreePeriodWed() {
        return threePeriod;
    }

    public String getFourPeriodWed() {
        return fourPeriod;
    }
}
