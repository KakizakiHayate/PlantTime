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

import jp.ac.jec.cm0110.plantime.database.PlantTimeDao;
import jp.ac.jec.cm0110.plantime.database.PlantTimeDatabase;
import jp.ac.jec.cm0110.plantime.database.PlantTimeEntity;

public class MonActivity extends AppCompatActivity {
    private EditText edtFirstPeriod;
    private EditText edtTwoPeriod;
    private EditText edtThreePeriod;
    private EditText edtFourPeriod;
    public List<PlantTimeEntity> list;
    private String firstPeriodStr;
    private String twoPeriodStr;
    private String threePeriodStr;
    private String fourPeriodStr;
    private int listId = 0;
    private int listIdGet = 1;
    private int listMax = 0;
    private String firstPeriodEt;
    private String twoPeriodEt;
    private String threePeriodEt;
    private String fourPeriodEt;
    private TextView txtLessonMon;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mon);

        edtFirstPeriod = findViewById(R.id.edtFirstPeriodMon);
        edtTwoPeriod = findViewById(R.id.edtTwoPeriodMon);
        edtThreePeriod = findViewById(R.id.edtThreePeriodMon);
        edtFourPeriod = findViewById(R.id.edtFourPeriodMon);
        txtLessonMon = findViewById(R.id.txtLessonMon);

        Button btnBack = findViewById(R.id.btnBackMon);
        btnBack.setOnClickListener(new clickEventBack());

        Button btnAllDelete = findViewById(R.id.btnAllDelete);
        btnAllDelete.setOnClickListener(new clickEventAllDelete());

        Button btnSave = findViewById(R.id.btnSave);
        btnSave.setOnClickListener(new clickEventSave());

        loadDatabase();
        textResult();

    }

    class clickEventBack implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            Intent intentMon = new Intent();
                firstPeriodStr = edtFirstPeriod.getText().toString();
                twoPeriodStr = edtTwoPeriod.getText().toString();
                threePeriodStr = edtThreePeriod.getText().toString();
                fourPeriodStr = edtFourPeriod.getText().toString();

                intentMon.putExtra(MainActivity.EXTRA_MESSAGE_FIRST_PERIOD, firstPeriodStr);
                intentMon.putExtra(MainActivity.EXTRA_MESSAGE_TWO_PERIOD, twoPeriodStr);
                intentMon.putExtra(MainActivity.EXTRA_MESSAGE_THREE_PERIOD, threePeriodStr);
                intentMon.putExtra(MainActivity.EXTRA_MESSAGE_FOUR_PERIOD, fourPeriodStr);

            setResult(RESULT_OK, intentMon);
            finish();
        }
    }

    class clickEventSave implements View.OnClickListener {
        @Override
        public void onClick(View view) {

            firstPeriodStr = edtFirstPeriod.getText().toString();
            twoPeriodStr = edtTwoPeriod.getText().toString();
            threePeriodStr = edtThreePeriod.getText().toString();
            fourPeriodStr = edtFourPeriod.getText().toString();
            if (firstPeriodStr.equals("") && twoPeriodStr.equals("") && threePeriodStr.equals("") && fourPeriodStr.equals("")) {
                Toast.makeText(MonActivity.this, "教科を入力してください", Toast.LENGTH_SHORT).show();
            }else if ( firstPeriodStr.equals(firstPeriodEt) && twoPeriodStr.equals(twoPeriodEt) && threePeriodStr.equals(threePeriodEt) && fourPeriodStr.equals(fourPeriodEt) ) {
                Toast.makeText(MonActivity.this, "入力内容が同じです", Toast.LENGTH_SHORT).show();

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
                        PlantTimeDatabase plantTimeDatabase = Room.databaseBuilder(getApplicationContext(),
                                PlantTimeDatabase.class, "ptdatabase").build();
                        PlantTimeDao plantTimeDao = plantTimeDatabase.plantTimeDao();
                        plantTimeDao.deleteAll();
                        loadDatabase();
                        txtLessonMon.setText(new StringBuilder()
                                .append("１限目：").append("" + "\n")
                                .append("２限目：").append("" + "\n")
                                .append("３限目：").append("" + "\n")
                                .append("４限目：").append("" + "\n")
                        );
                    } catch (Exception e) {

                    }
                }
            });
            edtFirstPeriod.setText("");
            edtTwoPeriod.setText("");
            edtThreePeriod.setText("");
            edtFourPeriod.setText("");
        }
    }

    private void loadDatabase() {
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        executorService.execute(new Runnable() {
            @Override
            public void run() {
                try {
                    PlantTimeDatabase plantTimeDatabase = Room.databaseBuilder(getApplicationContext(),
                            PlantTimeDatabase.class, "ptdatabase").build();
                    PlantTimeDao plantTimeDao = plantTimeDatabase.plantTimeDao();
                    list = plantTimeDao.getAll();
                    listMax = list.size();//リストの最後の番号
                    Log.i("ERROR", "ERROR_MESSAGE_LOAD++" + listMax + "+");

                    PlantTimeEntity plantTimeEntity = list.get(listMax - 1);

                    firstPeriodEt = plantTimeEntity.getFirstPeriod();
                    twoPeriodEt = plantTimeEntity.getTwoPeriod();
                    threePeriodEt = plantTimeEntity.getThreePeriod();
                    fourPeriodEt = plantTimeEntity.getFourPeriod();
                    Log.i("ERROR", "ERROR_MESSAGE_LOAD++" + plantTimeEntity.getTwoPeriod() + "+");

                    listId = plantTimeEntity.getId();
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
                    PlantTimeDatabase plantTimeDatabase = Room.databaseBuilder(getApplicationContext(),
                            PlantTimeDatabase.class, "ptdatabase").build();
                    PlantTimeDao plantTimeDao = plantTimeDatabase.plantTimeDao();
                    PlantTimeEntity plantTimeEntity = new PlantTimeEntity(firstPeriodStr, twoPeriodStr, threePeriodStr, fourPeriodStr);

                    plantTimeDao.insert(plantTimeEntity);
                    Toast.makeText(MonActivity.this, "書き込みが完了しました", Toast.LENGTH_SHORT).show();

                    loadDatabase();

                }catch (Exception e) {
                    Log.i("ERROR", "ERROR_MESSAGE_WRITE");

                }
            }
        }

        );
    }
    private void textResult() {
        txtLessonMon.setText(new StringBuilder()
                .append("１限目：").append(firstPeriodEt + "\n")
                .append("２限目：").append(twoPeriodEt + "\n")
                .append("３限目：").append(threePeriodEt + "\n")
                .append("４限目：").append(fourPeriodEt + "\n")
        );
    }
}