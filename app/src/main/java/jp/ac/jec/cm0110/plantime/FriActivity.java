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

import jp.ac.jec.cm0110.plantime.databaseFri.PlantTimeFriDao;
import jp.ac.jec.cm0110.plantime.databaseFri.PlantTimeFriDatabase;
import jp.ac.jec.cm0110.plantime.databaseFri.PlantTimeFriEntity;

public class FriActivity extends AppCompatActivity {
    private EditText edtFirstPeriodFri;
    private EditText edtTwoPeriodFri;
    private EditText edtThreePeriodFri;
    private EditText edtFourPeriodFri;
    private List<PlantTimeFriEntity> listFri;
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
    private TextView txtLessonFri;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fri);

        edtFirstPeriodFri = findViewById(R.id.edtFirstPeriodFri);
        edtTwoPeriodFri = findViewById(R.id.edtTwoPeriodFri);
        edtThreePeriodFri = findViewById(R.id.edtThreePeriodFri);
        edtFourPeriodFri = findViewById(R.id.edtFourPeriodFri);
        txtLessonFri = findViewById(R.id.txtLessonFri);

        Button btnBack = findViewById(R.id.btnBackFri);
        btnBack.setOnClickListener(new clickEventBack());

        Button btnAllDelete = findViewById(R.id.btnAllDeleteFri);
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

            firstPeriodStr = edtFirstPeriodFri.getText().toString();
            twoPeriodStr = edtTwoPeriodFri.getText().toString();
            threePeriodStr = edtThreePeriodFri.getText().toString();
            fourPeriodStr = edtFourPeriodFri.getText().toString();
            if (firstPeriodStr.equals("") && twoPeriodStr.equals("") && threePeriodStr.equals("") && fourPeriodStr.equals("")) {
                Toast.makeText(FriActivity.this, "教科を入力してください", Toast.LENGTH_SHORT).show();
            }else if ( firstPeriodStr.equals(firstPeriodEt) && twoPeriodStr.equals(twoPeriodEt) && threePeriodStr.equals(threePeriodEt) && fourPeriodStr.equals(fourPeriodEt) ) {
                Toast.makeText(FriActivity.this, "入力内容が同じです", Toast.LENGTH_SHORT).show();

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
                        PlantTimeFriDatabase plantTimeFriDatabase = Room.databaseBuilder(getApplicationContext(),
                                PlantTimeFriDatabase.class, "ptdatabaseFri").build();
                        PlantTimeFriDao plantTimeFriDao = plantTimeFriDatabase.plantTimeFriDao();
                        plantTimeFriDao.deleteAll();
                        loadDatabase();
                        txtLessonFri.setText(new StringBuilder()
                                .append("１限目：").append("" + "\n")
                                .append("２限目：").append("" + "\n")
                                .append("３限目：").append("" + "\n")
                                .append("４限目：").append("" + "\n")
                        );


                    } catch (Exception e) {

                    }
                }
            });
            edtFirstPeriodFri.setText("");
            edtTwoPeriodFri.setText("");
            edtThreePeriodFri.setText("");
            edtFourPeriodFri.setText("");
        }
    }

    private void loadDatabase() {
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        executorService.execute(new Runnable() {
            @Override
            public void run() {
                try {
                    PlantTimeFriDatabase plantTimeFriDatabase = Room.databaseBuilder(getApplicationContext(),
                            PlantTimeFriDatabase.class, "ptdatabaseFri").build();
                    PlantTimeFriDao plantTimeFriDao = plantTimeFriDatabase.plantTimeFriDao();
                    listFri = plantTimeFriDao.getAll();
                    listMax = listFri.size();//リストの最後の番号
                    Log.i("ERROR", "ERROR_MESSAGE_LOAD++" + listMax + "+");
                    PlantTimeFriEntity plantTimeFriEntity = listFri.get(listMax - 1);

                    firstPeriodEt = plantTimeFriEntity.getFirstPeriodFri();
                    Log.i("NullWhat", "whydelete" + firstPeriodEt);
                    twoPeriodEt = plantTimeFriEntity.getTwoPeriodFri();
                    Log.i("NullWhat", "whydelete2" + twoPeriodEt);
                    threePeriodEt = plantTimeFriEntity.getThreePeriodFri();
                    fourPeriodEt =plantTimeFriEntity.getFourPeriodFri();

                    listId = plantTimeFriEntity.getIdFri();
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
                    PlantTimeFriDatabase plantTimeFriDatabase = Room.databaseBuilder(getApplicationContext(),
                            PlantTimeFriDatabase.class, "ptdatabaseFri").build();
                    PlantTimeFriDao plantTimeFriDao = plantTimeFriDatabase.plantTimeFriDao();
                    PlantTimeFriEntity plantTimeFriEntity = new PlantTimeFriEntity(firstPeriodStr, twoPeriodStr, threePeriodStr, fourPeriodStr);

                    plantTimeFriDao.insert(plantTimeFriEntity);
                    Toast.makeText(FriActivity.this, "書き込みが完了しました", Toast.LENGTH_SHORT).show();

                    loadDatabase();

                }catch (Exception e) {
                    Log.i("ERROR", "ERROR_MESSAGE_WRITE");

                }
            }
        }

        );
    }

    private void textResult() {
        txtLessonFri.setText(new StringBuilder()
                .append("１限目：").append(firstPeriodEt + "\n")
                .append("２限目：").append(twoPeriodEt + "\n")
                .append("３限目：").append(threePeriodEt + "\n")
                .append("４限目：").append(fourPeriodEt + "\n")
        );
    }
}