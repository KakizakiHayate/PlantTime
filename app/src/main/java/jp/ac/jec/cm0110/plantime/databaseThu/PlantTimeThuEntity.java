package jp.ac.jec.cm0110.plantime.databaseThu;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "ptdatabaseThu")
public class PlantTimeThuEntity {
        @PrimaryKey(autoGenerate = true)
        public int id;
        public String firstPeriod;
        public String twoPeriod;
        public String threePeriod;
        public String fourPeriod;

        public PlantTimeThuEntity(String firstPeriod, String twoPeriod, String threePeriod, String fourPeriod) {
            this.firstPeriod = firstPeriod;
            this.twoPeriod = twoPeriod;
            this.threePeriod = threePeriod;
            this.fourPeriod = fourPeriod;
        }

        public int getIdThu() {
            return id;
        }

        public String getFirstPeriodThu() {
            return firstPeriod;
        }

        public String getTwoPeriodThu() {
            return twoPeriod;
        }

        public String getThreePeriodThu() {
            return threePeriod;
        }

        public String getFourPeriodThu() {
            return fourPeriod;
        }
    }


