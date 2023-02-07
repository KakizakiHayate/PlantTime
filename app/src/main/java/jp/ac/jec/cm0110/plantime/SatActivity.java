package jp.ac.jec.cm0110.plantime;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import jp.ac.jec.cm0110.plantime.detabaseSat.PlantTimeSatDao;
import jp.ac.jec.cm0110.plantime.detabaseSat.PlantTimeSatDatabase;
import jp.ac.jec.cm0110.plantime.detabaseSat.PlantTimeSatEntity;

public class SatActivity extends AppCompatActivity {
    private EditText edtFirstPeriodSat;
    private EditText edtTwoPeriodSat;
    private EditText edtThreePeriodSat;
    private EditText edtFourPeriodSat;
    private List<PlantTimeSatEntity> listSat;
    private String firstPeriodStr = "";
    private String twoPeriodStr = "";
    private String threePeriodStr = "";
    private String fourPeriodStr = "";
    private int listId = 0;
    private int listIdGet = 1;
    private int listMax = 0;
    private String firstPeriodEt = "";
    private String twoPeriodEt = "";
    private String threePeriodEt = "";
    private String fourPeriodEt = "";
    private TextView txtLessonSat;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sat);

        edtFirstPeriodSat = findViewById(R.id.edtFirstPeriodSt);
        edtTwoPeriodSat = findViewById(R.id.edtTwoPeriodSat);
        edtThreePeriodSat = findViewById(R.id.edtThreePeriodSat);
        edtFourPeriodSat = findViewById(R.id.edtFourPeriodSat);
        txtLessonSat = findViewById(R.id.txtLessonSat);

        Button btnBack = findViewById(R.id.btnBackSat);
        btnBack.setOnClickListener(new clickEventBack());

        Button btnAllDelete = findViewById(R.id.btnAllDeleteSat);
        btnAllDelete.setOnClickListener(new clickEventAllDelete());

        Button btnSave = findViewById(R.id.btnSave);
        btnSave.setOnClickListener(new clickEventSave());
        loadDatabase();
        textResult();
    }

    class clickEventBack implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            Intent intentTue = new Intent();


            intentTue.putExtra(MainActivity.EXTRA_MESSAGE_FIRST_PERIOD, firstPeriodStr);
            intentTue.putExtra(MainActivity.EXTRA_MESSAGE_TWO_PERIOD, twoPeriodStr);
            intentTue.putExtra(MainActivity.EXTRA_MESSAGE_THREE_PERIOD, threePeriodStr);
            intentTue.putExtra(MainActivity.EXTRA_MESSAGE_FOUR_PERIOD, fourPeriodStr);

            setResult(RESULT_OK, intentTue);
            finish();
        }
    }

    class clickEventSave implements View.OnClickListener {
        @Override
        public void onClick(View view) {

            firstPeriodStr = edtFirstPeriodSat.getText().toString();
            twoPeriodStr = edtTwoPeriodSat.getText().toString();
            threePeriodStr = edtThreePeriodSat.getText().toString();
            fourPeriodStr = edtFourPeriodSat.getText().toString();
            if (firstPeriodStr.equals("") && twoPeriodStr.equals("") && threePeriodStr.equals("") && fourPeriodStr.equals("")) {
                Toast.makeText(SatActivity.this, "教科を入力してください", Toast.LENGTH_SHORT).show();
            }else if ( firstPeriodStr.equals(firstPeriodEt) && twoPeriodStr.equals(twoPeriodEt) && threePeriodStr.equals(threePeriodEt) && fourPeriodStr.equals(fourPeriodEt) ) {
                Toast.makeText(SatActivity.this, "入力内容が同じです", Toast.LENGTH_SHORT).show();

            } else {
                writeDatabase();
                loadDatabase();
            }
        }
    }

    class clickEventAllDelete implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            ExecutorService executorService = Executors.newSingleThreadExecutor();
            executorService.execute(new Runnable() {
                @Override
                public void run() {
                    try {
                        PlantTimeSatDatabase plantTimeSatDatabase = Room.databaseBuilder(getApplicationContext(),
                                PlantTimeSatDatabase.class, "ptdatabaseSat").build();
                        PlantTimeSatDao plantTimeSatDao = plantTimeSatDatabase.plantTimeSatDao();
                        plantTimeSatDao.deleteAll();
                        loadDatabase();
                        txtLessonSat.setText(new StringBuilder()
                                .append("１限目：").append("" + "\n")
                                .append("２限目：").append("" + "\n")
                                .append("３限目：").append("" + "\n")
                                .append("４限目：").append("" + "\n")
                        );


                    } catch (Exception e) {

                    }
                }
            });
            edtFirstPeriodSat.setText("");
            edtTwoPeriodSat.setText("");
            edtThreePeriodSat.setText("");
            edtFourPeriodSat.setText("");
        }
    }

    private void loadDatabase() {
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        executorService.execute(new Runnable() {
            @Override
            public void run() {
                try {
                    PlantTimeSatDatabase plantTimeSatDatabase = Room.databaseBuilder(getApplicationContext(),
                            PlantTimeSatDatabase.class, "ptdatabaseSat").build();
                    PlantTimeSatDao plantTimeSatDao = plantTimeSatDatabase.plantTimeSatDao();
                    listSat = plantTimeSatDao.getAll();
                    listMax = listSat.size();//リストの最後の番号
                    Log.i("ERROR", "ERROR_MESSAGE_LOAD++" + listMax + "+");
                    PlantTimeSatEntity plantTimeSatEntity = listSat.get(listMax - 1);

                    firstPeriodEt = plantTimeSatEntity.getFirstPeriodSat();
                    Log.i("NullWhat", "whydelete" + firstPeriodEt);
                    twoPeriodEt = plantTimeSatEntity.getTwoPeriodSat();
                    Log.i("NullWhat", "whydelete2" + twoPeriodEt);
                    threePeriodEt = plantTimeSatEntity.getThreePeriodSat();
                    fourPeriodEt = plantTimeSatEntity.getFourPeriodSat();

                    listId = plantTimeSatEntity.getIdSat();
                    Log.i("went","ifWent**");

                    textResult();
                }catch (Exception e) {
                    Log.i("ERROR", "ERROR_MESSAGE_LOAD");

                }
            }
        }

        );
    }
    private void writeDatabase() {
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        executorService.execute(new Runnable() {
            @Override
            public void run() {
                try {
                    PlantTimeSatDatabase plantTimeSatDatabase = Room.databaseBuilder(getApplicationContext(),
                            PlantTimeSatDatabase.class, "ptdatabaseSat").build();
                    PlantTimeSatDao plantTimeSatDao = plantTimeSatDatabase.plantTimeSatDao();
                    PlantTimeSatEntity plantTimeSatEntity = new PlantTimeSatEntity(firstPeriodStr, twoPeriodStr, threePeriodStr, fourPeriodStr);

                    plantTimeSatDao.insert(plantTimeSatEntity);
                    Toast.makeText(SatActivity.this, "書き込みが完了しました", Toast.LENGTH_SHORT).show();

                    loadDatabase();

                }catch (Exception e) {
                    Log.i("ERROR", "ERROR_MESSAGE_WRITE");

                }
            }
        }

        );
    }

    private void textResult() {
        txtLessonSat.setText(new StringBuilder()
                .append("１限目：").append(firstPeriodEt + "\n")
                .append("２限目：").append(twoPeriodEt + "\n")
                .append("３限目：").append(threePeriodEt + "\n")
                .append("４限目：").append(fourPeriodEt + "\n")
        );
    }
}