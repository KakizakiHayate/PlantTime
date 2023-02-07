package jp.ac.jec.cm0110.plantime.databaseTue;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "ptdatabaseTue")
public class PlantTimeEntityTue {
        @PrimaryKey(autoGenerate = true)
        public int id;
        public String firstPeriod;
        public String twoPeriod;
        public String threePeriod;
        public String fourPeriod;

        public PlantTimeEntityTue(String firstPeriod, String twoPeriod, String threePeriod, String fourPeriod) {
            this.firstPeriod = firstPeriod;
            this.twoPeriod = twoPeriod;
            this.threePeriod = threePeriod;
            this.fourPeriod = fourPeriod;
        }

        public int getIdTue() {
            return id;
        }

        public String getFirstPeriodTue() {
            return firstPeriod;
        }

        public String getTwoPeriodTue() {
            return twoPeriod;
        }

        public String getThreePeriodTue() {
            return threePeriod;
        }

        public String getFourPeriodTue() {
            return fourPeriod;
        }
}
