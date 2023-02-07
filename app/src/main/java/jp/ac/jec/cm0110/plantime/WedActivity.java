package jp.ac.jec.cm0110.plantime;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import jp.ac.jec.cm0110.plantime.databaseWed.PlantTimeWedDao;
import jp.ac.jec.cm0110.plantime.databaseWed.PlantTimeWedDatabase;
import jp.ac.jec.cm0110.plantime.databaseWed.PlantTimeWedEntity;

public class WedActivity extends AppCompatActivity {
    private EditText edtFirstPeriodWed;
    private EditText edtTwoPeriodWed;
    private EditText edtThreePeriodWed;
    private EditText edtFourPeriodWed;
    private List<PlantTimeWedEntity> listWed;
    private String firstPeriodStr = "";
    private String twoPeriodStr = "";
    private String threePeriodStr = "";
    private String fourPeriodStr = "";
    private int listId = 0;
    private int listIdGet = 1;
    private int listMaxWed = 0;
    private String firstPeriodEt = "";
    private String twoPeriodEt = "";
    private String threePeriodEt = "";
    private String fourPeriodEt = "";
    private TextView txtLessonWed;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wed);

        edtFirstPeriodWed = findViewById(R.id.edtFirstPeriodMon);
        edtTwoPeriodWed = findViewById(R.id.edtTwoPeriodThu);
        edtThreePeriodWed = findViewById(R.id.edtThreePeriodThu);
        edtFourPeriodWed = findViewById(R.id.edtFourPeriodThu);
        txtLessonWed = findViewById(R.id.txtLessonThu);

        Button btnBack = findViewById(R.id.btnBackThu);
        btnBack.setOnClickListener(new clickEventBack());

        Button btnAllDelete = findViewById(R.id.btnAllDeleteThu);
        btnAllDelete.setOnClickListener(new clickEventAllDelete());

        Button btnSave = findViewById(R.id.btnSave);
        btnSave.setOnClickListener(new clickEventSave());
        loadDatabase();
        textResult();
    }

    class clickEventBack implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            Intent intentWed = new Intent();


            intentWed.putExtra(MainActivity.EXTRA_MESSAGE_FIRST_PERIOD, firstPeriodStr);
            intentWed.putExtra(MainActivity.EXTRA_MESSAGE_TWO_PERIOD, twoPeriodStr);
            intentWed.putExtra(MainActivity.EXTRA_MESSAGE_THREE_PERIOD, threePeriodStr);
            intentWed.putExtra(MainActivity.EXTRA_MESSAGE_FOUR_PERIOD, fourPeriodStr);

            setResult(RESULT_OK, intentWed);
            finish();
        }
    }

    class clickEventSave implements View.OnClickListener {
        @Override
        public void onClick(View view) {

            firstPeriodStr = edtFirstPeriodWed.getText().toString();
            twoPeriodStr = edtTwoPeriodWed.getText().toString();
            threePeriodStr = edtThreePeriodWed.getText().toString();
            fourPeriodStr = edtFourPeriodWed.getText().toString();
            if (firstPeriodStr.equals("") && twoPeriodStr.equals("") && threePeriodStr.equals("") && fourPeriodStr.equals("")) {
                Toast.makeText(WedActivity.this, "教科を入力してください", Toast.LENGTH_SHORT).show();
            }else if ( firstPeriodStr.equals(firstPeriodEt) && twoPeriodStr.equals(twoPeriodEt) && threePeriodStr.equals(threePeriodEt) && fourPeriodStr.equals(fourPeriodEt) ) {
                Toast.makeText(WedActivity.this, "入力内容が同じです", Toast.LENGTH_SHORT).show();

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
                        PlantTimeWedDatabase plantTimeWedDatabase = Room.databaseBuilder(getApplicationContext(),
                                PlantTimeWedDatabase.class, "ptdatabaseWed").build();
                        PlantTimeWedDao plantTimeWedDao = plantTimeWedDatabase.plantTimeWedDao();
                        plantTimeWedDao.deleteAll();
                        loadDatabase();
                        txtLessonWed.setText(new StringBuilder()
                                .append("１限目：").append("" + "\n")
                                .append("２限目：").append("" + "\n")
                                .append("３限目：").append("" + "\n")
                                .append("４限目：").append("" + "\n")
                        );


                    } catch (Exception e) {

                    }
                }
            });
            edtFirstPeriodWed.setText("");
            edtTwoPeriodWed.setText("");
            edtThreePeriodWed.setText("");
            edtFourPeriodWed.setText("");
        }
    }

    private void loadDatabase() {
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        executorService.execute(new Runnable() {
            @Override
            public void run() {
                try {
                    PlantTimeWedDatabase plantTimeWedDatabase = Room.databaseBuilder(getApplicationContext(),
                            PlantTimeWedDatabase.class, "ptdatabaseWed").build();
                    PlantTimeWedDao plantTimeWedDao = plantTimeWedDatabase.plantTimeWedDao();
                    listWed = plantTimeWedDao.getAll();
                    listMaxWed = listWed.size();//リストの最後の番号
                    PlantTimeWedEntity plantTimeWedEntity = listWed.get(listMaxWed - 1);

                    firstPeriodEt = plantTimeWedEntity.getFirstPeriodWed();
                    Log.i("NullWhat", "whydelete" + firstPeriodEt);
                    twoPeriodEt = plantTimeWedEntity.getTwoPeriodWed();
                    Log.i("NullWhat", "whydelete2" + twoPeriodEt);
                    threePeriodEt = plantTimeWedEntity.getThreePeriodWed();
                    fourPeriodEt = plantTimeWedEntity.getFourPeriodWed();

                    listId = plantTimeWedEntity.getIdWed();


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
                    PlantTimeWedDatabase plantTimeWedDatabase = Room.databaseBuilder(getApplicationContext(),
                            PlantTimeWedDatabase.class, "ptdatabaseWed").build();
                    PlantTimeWedDao plantTimeWedDao = plantTimeWedDatabase.plantTimeWedDao();
                    PlantTimeWedEntity plantTimeWedEntity = new PlantTimeWedEntity(firstPeriodStr, twoPeriodStr, threePeriodStr, fourPeriodStr);

                    plantTimeWedDao.insert(plantTimeWedEntity);
                    Toast.makeText(WedActivity.this, "書き込みが完了しました", Toast.LENGTH_SHORT).show();

                    loadDatabase();

                    }catch (Exception e) {
                    Log.i("ERROR", "ERROR_MESSAGE_WRITE");

                }
            }
        }

        );
    }

    private void textResult() {
        txtLessonWed.setText(new StringBuilder()
                .append("１限目：").append(firstPeriodEt + "\n")
                .append("２限目：").append(twoPeriodEt + "\n")
                .append("３限目：").append(threePeriodEt + "\n")
                .append("４限目：").append(fourPeriodEt + "\n")
        );
    }
}