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

import jp.ac.jec.cm0110.plantime.databaseThu.PlantTimeThuDao;
import jp.ac.jec.cm0110.plantime.databaseThu.PlantTimeThuDatabase;
import jp.ac.jec.cm0110.plantime.databaseThu.PlantTimeThuEntity;

public class ThuActivity extends AppCompatActivity {
    private EditText edtFirstPeriodThu;
    private EditText edtTwoPeriodThu;
    private EditText edtThreePeriodThu;
    private EditText edtFourPeriodThu;
    private List<PlantTimeThuEntity> listThu;
    private String firstPeriodStr = "";
    private String twoPeriodStr = "";
    private String threePeriodStr = "";
    private String fourPeriodStr = "";
    private int listId = 0;
    private int listIdGet = 1;
    private int listMaxThu = 0;
    private String firstPeriodEt = "";
    private String twoPeriodEt = "";
    private String threePeriodEt = "";
    private String fourPeriodEt = "";
    private TextView txtLessonThu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thu);

        edtFirstPeriodThu = findViewById(R.id.edtFirstPeriodMon);
        edtTwoPeriodThu = findViewById(R.id.edtTwoPeriodThu);
        edtThreePeriodThu = findViewById(R.id.edtThreePeriodThu);
        edtFourPeriodThu = findViewById(R.id.edtFourPeriodThu);
        txtLessonThu = findViewById(R.id.txtLessonThu);

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

            firstPeriodStr = edtFirstPeriodThu.getText().toString();
            twoPeriodStr = edtTwoPeriodThu.getText().toString();
            threePeriodStr = edtThreePeriodThu.getText().toString();
            fourPeriodStr = edtFourPeriodThu.getText().toString();
            if (firstPeriodStr.equals("") && twoPeriodStr.equals("") && threePeriodStr.equals("") && fourPeriodStr.equals("")) {
                Toast.makeText(ThuActivity.this, "教科を入力してください", Toast.LENGTH_SHORT).show();
            }else if ( firstPeriodStr.equals(firstPeriodEt) && twoPeriodStr.equals(twoPeriodEt) && threePeriodStr.equals(threePeriodEt) && fourPeriodStr.equals(fourPeriodEt) ) {
                Toast.makeText(ThuActivity.this, "入力内容が同じです", Toast.LENGTH_SHORT).show();

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
                        PlantTimeThuDatabase plantTimeThuDatabase = Room.databaseBuilder(getApplicationContext(),
                                PlantTimeThuDatabase.class, "ptdatabaseThu").build();
                        PlantTimeThuDao plantTimeThuDao = plantTimeThuDatabase.plantTimeThuDao();
                        plantTimeThuDao.deleteAll();
                        loadDatabase();
                        txtLessonThu.setText(new StringBuilder()
                                .append("１限目：").append("" + "\n")
                                .append("２限目：").append("" + "\n")
                                .append("３限目：").append("" + "\n")
                                .append("４限目：").append("" + "\n")
                        );


                    } catch (Exception e) {

                    }
                }
            });
            edtFirstPeriodThu.setText("");
            edtTwoPeriodThu.setText("");
            edtThreePeriodThu.setText("");
            edtFourPeriodThu.setText("");
        }
    }

    private void loadDatabase() {
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        executorService.execute(new Runnable() {
            @Override
            public void run() {
                try {
                    PlantTimeThuDatabase plantTimeThuDatabase = Room.databaseBuilder(getApplicationContext(),
                            PlantTimeThuDatabase.class, "ptdatabaseThu").build();
                    PlantTimeThuDao plantTimeThuDao = plantTimeThuDatabase.plantTimeThuDao();
                    listThu = plantTimeThuDao.getAll();
                    listMaxThu = listThu.size();//リストの最後の番号
                    Log.i("ERROR", "ERROR_MESSAGE_LOAD++" + listMaxThu + "+");
                    PlantTimeThuEntity plantTimeThuEntity = listThu.get(listMaxThu - 1);

                    firstPeriodEt = plantTimeThuEntity.getFirstPeriodThu();
                    Log.i("NullWhat", "whydelete" + firstPeriodEt);
                    twoPeriodEt = plantTimeThuEntity.getTwoPeriodThu();
                    Log.i("NullWhat", "whydelete2" + twoPeriodEt);
                    threePeriodEt = plantTimeThuEntity.getThreePeriodThu();
                    fourPeriodEt = plantTimeThuEntity.getFourPeriodThu();

                    listId = plantTimeThuEntity.getIdThu();
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
                    PlantTimeThuDatabase plantTimeThuDatabase = Room.databaseBuilder(getApplicationContext(),
                            PlantTimeThuDatabase.class, "ptdatabaseThu").build();
                    PlantTimeThuDao plantTimeThuDao = plantTimeThuDatabase.plantTimeThuDao();
                    PlantTimeThuEntity plantTimeThuEntity = new PlantTimeThuEntity(firstPeriodStr, twoPeriodStr, threePeriodStr, fourPeriodStr);

                    plantTimeThuDao.insert(plantTimeThuEntity);
                    Toast.makeText(ThuActivity.this, "書き込みが完了しました", Toast.LENGTH_SHORT).show();

                    loadDatabase();

                }catch (Exception e) {
                    Log.i("ERROR", "ERROR_MESSAGE_WRITE");

                }
            }
        }

        );
    }

    private void textResult() {
        txtLessonThu.setText(new StringBuilder()
                .append("１限目：").append(firstPeriodEt + "\n")
                .append("２限目：").append(twoPeriodEt + "\n")
                .append("３限目：").append(threePeriodEt + "\n")
                .append("４限目：").append(fourPeriodEt + "\n")
        );
    }
}