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

import jp.ac.jec.cm0110.plantime.databaseTue.PlantTimeEntityTue;
import jp.ac.jec.cm0110.plantime.databaseTue.PlantTimeTueDao;
import jp.ac.jec.cm0110.plantime.databaseTue.PlantTimeTueDatabase;

public class TueActivity extends AppCompatActivity {
    private EditText edtFirstPeriodTue;
    private EditText edtTwoPeriodTue;
    private EditText edtThreePeriodTue;
    private EditText edtFourPeriodTue;
    private List<PlantTimeEntityTue> listTue;
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
    private TextView txtLessonTue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tue);

        edtFirstPeriodTue = findViewById(R.id.edtFirstPeriodMon);
        edtTwoPeriodTue = findViewById(R.id.edtTwoPeriodThu);
        edtThreePeriodTue = findViewById(R.id.edtThreePeriodThu);
        edtFourPeriodTue = findViewById(R.id.edtFourPeriodThu);
        txtLessonTue = findViewById(R.id.txtLessonThu);

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

            firstPeriodStr = edtFirstPeriodTue.getText().toString();
            twoPeriodStr = edtTwoPeriodTue.getText().toString();
            threePeriodStr = edtThreePeriodTue.getText().toString();
            fourPeriodStr = edtFourPeriodTue.getText().toString();
            if (firstPeriodStr.equals("") && twoPeriodStr.equals("") && threePeriodStr.equals("") && fourPeriodStr.equals("")) {
                Toast.makeText(TueActivity.this, "教科を入力してください", Toast.LENGTH_SHORT).show();
            }else if ( firstPeriodStr.equals(firstPeriodEt) && twoPeriodStr.equals(twoPeriodEt) && threePeriodStr.equals(threePeriodEt) && fourPeriodStr.equals(fourPeriodEt) ) {
                Toast.makeText(TueActivity.this, "入力内容が同じです", Toast.LENGTH_SHORT).show();

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
                        PlantTimeTueDatabase plantTimeTueDatabase = Room.databaseBuilder(getApplicationContext(),
                                PlantTimeTueDatabase.class, "ptdatabaseTue").build();
                        PlantTimeTueDao plantTimeTueDao = plantTimeTueDatabase.plantTimeTueDao();
                        plantTimeTueDao.deleteAll();
                        loadDatabase();
                        txtLessonTue.setText(new StringBuilder()
                                .append("１限目：").append("" + "\n")
                                .append("２限目：").append("" + "\n")
                                .append("３限目：").append("" + "\n")
                                .append("４限目：").append("" + "\n")
                        );


                    } catch (Exception e) {

                    }
                }
            });
            edtFirstPeriodTue.setText("");
            edtTwoPeriodTue.setText("");
            edtThreePeriodTue.setText("");
            edtFourPeriodTue.setText("");
        }
    }

    private void loadDatabase() {
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        executorService.execute(new Runnable() {
            @Override
            public void run() {
                try {
                    PlantTimeTueDatabase plantTimeTueDatabase = Room.databaseBuilder(getApplicationContext(),
                            PlantTimeTueDatabase.class, "ptdatabaseTue"
                    ).build();
                    PlantTimeTueDao plantTimeTueDao = plantTimeTueDatabase.plantTimeTueDao();
                    listTue = plantTimeTueDao.getAll();
                    listMax = listTue.size();//リストの最後の番号
                    Log.i("ERROR", "ERROR_MESSAGE_LOAD++" + listMax + "+");
                    PlantTimeEntityTue plantTimeEntityTue = listTue.get(listMax - 1);

                    firstPeriodEt = plantTimeEntityTue.getFirstPeriodTue();
                    Log.i("NullWhat", "whydelete" + firstPeriodEt);
                    twoPeriodEt = plantTimeEntityTue.getTwoPeriodTue();
                    Log.i("NullWhat", "whydelete2" + twoPeriodEt);
                    threePeriodEt = plantTimeEntityTue.getThreePeriodTue();
                    fourPeriodEt = plantTimeEntityTue.getFourPeriodTue();

                    listId = plantTimeEntityTue.getIdTue();
                    //中身がないときに
//                    if (!Objects.equals(firstPeriodEt, "")) {
                        Log.i("went","ifWent**");

                    textResult();
//                    }
//                    if (twoPeriodEt != "") {
//                        Log.i("went","ifWent**Two");
//                        edtTwoPeriodTue.setText(twoPeriodEt);
//                    }
//                    if (threePeriodEt != "") {
//                        Log.i("went","ifWent**thhree");
//                        edtThreePeriodTue.setText(threePeriodEt);
//                    }
//                    if (fourPeriodEt != "") {
//                        Log.i("went","ifWent**four");
//                        edtFourPeriodTue.setText(fourPeriodEt);
//                    }
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
                    PlantTimeTueDatabase plantTimeTueDatabase = Room.databaseBuilder(getApplicationContext(),
                            PlantTimeTueDatabase.class, "ptdatabaseTue").build();
                    PlantTimeTueDao plantTimeTueDao = plantTimeTueDatabase.plantTimeTueDao();
                    PlantTimeEntityTue plantTimeEntityTue = new PlantTimeEntityTue(firstPeriodStr, twoPeriodStr, threePeriodStr, fourPeriodStr);

                    plantTimeTueDao.insert(plantTimeEntityTue);
                    Toast.makeText(TueActivity.this, "書き込みが完了しました", Toast.LENGTH_SHORT).show();

                    loadDatabase();

                }catch (Exception e) {
                    Log.i("ERROR", "ERROR_MESSAGE_WRITE");

                }
            }
        }

        );
    }

    private void textResult() {
        txtLessonTue.setText(new StringBuilder()
                .append("１限目：").append(firstPeriodEt + "\n")
                .append("２限目：").append(twoPeriodEt + "\n")
                .append("３限目：").append(threePeriodEt + "\n")
                .append("４限目：").append(fourPeriodEt + "\n")
        );
    }
}