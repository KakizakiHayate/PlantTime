package jp.ac.jec.cm0110.plantime.database;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "ptdatabase")
public class PlantTimeEntity {
    @PrimaryKey(autoGenerate = true)
    public int id;
    public String firstPeriod;
    public String twoPeriod;
    public String threePeriod;
    public String fourPeriod;

    public PlantTimeEntity(String firstPeriod, String twoPeriod, String threePeriod, String fourPeriod) {
        this.firstPeriod = firstPeriod;
        this.twoPeriod = twoPeriod;
        this.threePeriod = threePeriod;
        this.fourPeriod = fourPeriod;
    }

    public int getId() {
        return id;
    }

    public String getFirstPeriod() {
        return firstPeriod;
    }

    public String getTwoPeriod() {
        return this.twoPeriod;
    }

    public String getThreePeriod() {
        return threePeriod;
    }

    public String getFourPeriod() {
        return fourPeriod;
    }
}
