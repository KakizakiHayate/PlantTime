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

import jp.ac.jec.cm0110.plantime.databaseSun.PlantTimeSunDao;
import jp.ac.jec.cm0110.plantime.databaseSun.PlantTimeSunDatabase;
import jp.ac.jec.cm0110.plantime.databaseSun.PlantTimeSunEntity;

public class SunActivity extends AppCompatActivity {
    private EditText edtFirstPeriodSun;
    private EditText edtTwoPeriodSun;
    private EditText edtThreePeriodSun;
    private EditText edtFourPeriodSun;
    private List<PlantTimeSunEntity> listSun;
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
    private TextView txtLessonSun;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sun);

        edtFirstPeriodSun = findViewById(R.id.edtFirstPeriodSun);
        edtTwoPeriodSun = findViewById(R.id.edtTwoPeriodSun);
        edtThreePeriodSun = findViewById(R.id.edtThreePeriodSun);
        edtFourPeriodSun = findViewById(R.id.edtFourPeriodSun);
        txtLessonSun = findViewById(R.id.txtLessonSun);

        Button btnBack = findViewById(R.id.btnBackSun);
        btnBack.setOnClickListener(new clickEventBack());

        Button btnAllDelete = findViewById(R.id.btnAllDeleteSun);
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

            firstPeriodStr = edtFirstPeriodSun.getText().toString();
            twoPeriodStr = edtTwoPeriodSun.getText().toString();
            threePeriodStr = edtThreePeriodSun.getText().toString();
            fourPeriodStr = edtFourPeriodSun.getText().toString();
            if (firstPeriodStr.equals("") && twoPeriodStr.equals("") && threePeriodStr.equals("") && fourPeriodStr.equals("")) {
                Toast.makeText(SunActivity.this, "教科を入力してください", Toast.LENGTH_SHORT).show();
            }else if ( firstPeriodStr.equals(firstPeriodEt) && twoPeriodStr.equals(twoPeriodEt) && threePeriodStr.equals(threePeriodEt) && fourPeriodStr.equals(fourPeriodEt) ) {
                Toast.makeText(SunActivity.this, "入力内容が同じです", Toast.LENGTH_SHORT).show();

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
                        PlantTimeSunDatabase plantTimeSunDatabase = Room.databaseBuilder(getApplicationContext(),
                                PlantTimeSunDatabase.class, "ptdatabaseSun").build();
                        PlantTimeSunDao plantTimeSunDao = plantTimeSunDatabase.plantTimeSunDao();
                        plantTimeSunDao.deleteAll();
                        loadDatabase();
                        txtLessonSun.setText(new StringBuilder()
                                .append("１限目：").append("" + "\n")
                                .append("２限目：").append("" + "\n")
                                .append("３限目：").append("" + "\n")
                                .append("４限目：").append("" + "\n")
                        );


                    } catch (Exception e) {

                    }
                }
            });
            edtFirstPeriodSun.setText("");
            edtTwoPeriodSun.setText("");
            edtThreePeriodSun.setText("");
            edtFourPeriodSun.setText("");
        }
    }

    private void loadDatabase() {
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        executorService.execute(new Runnable() {
            @Override
            public void run() {
                try {
                    PlantTimeSunDatabase plantTimeSunDatabase = Room.databaseBuilder(getApplicationContext(),
                            PlantTimeSunDatabase.class, "ptdatabaseSun").build();
                    PlantTimeSunDao plantTimeSunDao = plantTimeSunDatabase.plantTimeSunDao();
                    listSun = plantTimeSunDao.getAll();
                    listMax = listSun.size();//リストの最後の番号
                    Log.i("ERROR", "ERROR_MESSAGE_LOAD++" + listMax + "+");
                    PlantTimeSunEntity plantTimeSunEntity = listSun.get(listMax - 1);

                    firstPeriodEt = plantTimeSunEntity.getFirstPeriodSun();
                    Log.i("NullWhat", "whydelete" + firstPeriodEt);
                    twoPeriodEt = plantTimeSunEntity.getTwoPeriodSun();
                    Log.i("NullWhat", "whydelete2" + twoPeriodEt);
                    threePeriodEt = plantTimeSunEntity.getThreePeriodSun();
                    fourPeriodEt = plantTimeSunEntity.getFourPeriodSun();

                    listId = plantTimeSunEntity.getIdSun();
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
                    PlantTimeSunDatabase plantTimeSunDatabase = Room.databaseBuilder(getApplicationContext(),
                            PlantTimeSunDatabase.class, "ptdatabaseSun").build();
                    PlantTimeSunDao plantTimeSunDao = plantTimeSunDatabase.plantTimeSunDao();
                    PlantTimeSunEntity plantTimeSunEntity = new PlantTimeSunEntity(firstPeriodStr, twoPeriodStr, threePeriodStr, fourPeriodStr);

                    plantTimeSunDao.insert(plantTimeSunEntity);
                    Toast.makeText(SunActivity.this, "書き込みが完了しました", Toast.LENGTH_SHORT).show();

                    loadDatabase();

                }catch (Exception e) {
                    Log.i("ERROR", "ERROR_MESSAGE_WRITE");

                }
            }
        }

        );
    }

    private void textResult() {
        txtLessonSun.setText(new StringBuilder()
                .append("１限目：").append(firstPeriodEt + "\n")
                .append("２限目：").append(twoPeriodEt + "\n")
                .append("３限目：").append(threePeriodEt + "\n")
                .append("４限目：").append(fourPeriodEt + "\n")
        );
    }
}