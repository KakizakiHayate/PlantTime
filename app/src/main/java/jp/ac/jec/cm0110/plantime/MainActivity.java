package jp.ac.jec.cm0110.plantime;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import jp.ac.jec.cm0110.plantime.database.PlantTimeDao;
import jp.ac.jec.cm0110.plantime.database.PlantTimeDatabase;
import jp.ac.jec.cm0110.plantime.database.PlantTimeEntity;
import jp.ac.jec.cm0110.plantime.databaseFri.PlantTimeFriDao;
import jp.ac.jec.cm0110.plantime.databaseFri.PlantTimeFriDatabase;
import jp.ac.jec.cm0110.plantime.databaseFri.PlantTimeFriEntity;
import jp.ac.jec.cm0110.plantime.databaseSun.PlantTimeSunDao;
import jp.ac.jec.cm0110.plantime.databaseSun.PlantTimeSunDatabase;
import jp.ac.jec.cm0110.plantime.databaseSun.PlantTimeSunEntity;
import jp.ac.jec.cm0110.plantime.databaseThu.PlantTimeThuDao;
import jp.ac.jec.cm0110.plantime.databaseThu.PlantTimeThuDatabase;
import jp.ac.jec.cm0110.plantime.databaseThu.PlantTimeThuEntity;
import jp.ac.jec.cm0110.plantime.databaseTue.PlantTimeEntityTue;
import jp.ac.jec.cm0110.plantime.databaseTue.PlantTimeTueDao;
import jp.ac.jec.cm0110.plantime.databaseTue.PlantTimeTueDatabase;
import jp.ac.jec.cm0110.plantime.databaseWed.PlantTimeWedDao;
import jp.ac.jec.cm0110.plantime.databaseWed.PlantTimeWedDatabase;
import jp.ac.jec.cm0110.plantime.databaseWed.PlantTimeWedEntity;
import jp.ac.jec.cm0110.plantime.detabaseSat.PlantTimeSatDao;
import jp.ac.jec.cm0110.plantime.detabaseSat.PlantTimeSatDatabase;
import jp.ac.jec.cm0110.plantime.detabaseSat.PlantTimeSatEntity;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private View revView;
    public static final String EXTRA_MESSAGE_FIRST_PERIOD = "jp.ac.jec.cm0110.plantime.MESSAGE_FIRST_PERIOD";
    public static final String EXTRA_MESSAGE_TWO_PERIOD = "jp.ac.jec.cm0110.plantime.MESSAGET_TWO_PERIOD";
    public static final String EXTRA_MESSAGE_THREE_PERIOD = "jp.ac.jec.cm0110.plantime.MESSAGET_THREE_PERIOD";
    public static final String EXTRA_MESSAGE_FOUR_PERIOD = "jp.ac.jec.cm0110.plantime.MESSAGET_FOUR_PERIOD";

    private TextView txtLessonOne;
    private TextView txtLessonTwo;
    private TextView txtLessonThree;
    private TextView txtLessonFour;
    private Button btnRevision;
    private View viewBefore;
    private List<PlantTimeEntity> list;
    private List<PlantTimeEntityTue> listTue;
    private List<PlantTimeWedEntity> listWed;
    private List<PlantTimeThuEntity> listThu;
    private List<PlantTimeFriEntity> listFri;
    private List<PlantTimeSatEntity> listSat;
    private List<PlantTimeSunEntity> listSun;
    private int listMax = 1;
    private int listMaxTue = 1;
    private int listMaxWed = 1;

    //１〜４限目のEditText
    private EditText edtFirstPeriod;
    private EditText edtTwoPeriod;
    private EditText edtThreePeriod;
    private EditText edtFourPeriod;

    //Entityを通してデータを受け取る。
    private String firstPeriodEt = "";
    private String twoPeriodEt = "";
    private String threePeriodEt = "";
    private String fourPeriodEt = "";

    /**
     * 別アクティビティからの情報を受け取る
     */
//    ActivityResultLauncher<Intent> resultLauncher = registerForActivityResult(
//            new ActivityResultContracts.StartActivityForResult(),
////                            new ActivityResultCallback<ActivityResult>() {
////                                @Override
////                                public void onActivityResult(ActivityResult result) {
////
////                                }
////                            }
//            result -> {
//                if (result.getResultCode() == Activity.RESULT_OK) {
//                    Intent intentMon = result.getData();
//                    if (intentMon != null) {
//                        Log.i("activityResult", "finish+++");
//                        String resFirstPeriod = intentMon.getStringExtra(MainActivity.EXTRA_MESSAGE_FIRST_PERIOD);
//                        String resTwoPeriod = intentMon.getStringExtra(MainActivity.EXTRA_MESSAGE_TWO_PERIOD);
//                        String resThreePeriod = intentMon.getStringExtra(MainActivity.EXTRA_MESSAGE_THREE_PERIOD);
//                        String resFourPeriod = intentMon.getStringExtra(MainActivity.EXTRA_MESSAGE_FOUR_PERIOD);
////                        txtLessonOne.setText(resFirstPeriod);
////                        txtLessonTwo.setText(resTwoPeriod);
////                        txtLessonThree.setText(resThreePeriod);
////                        txtLessonFour.setText(resFourPeriod);
//                    }
//                }
//            }
//    );

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //修正ボタン
        btnRevision = findViewById(R.id.btnRevision);
        btnRevision.setEnabled(false);
        btnRevision.setOnClickListener(new clickEventRevision());


        //曜日ボタン
        int[] Weeks = {
                R.id.btnMon, R.id.btnTue, R.id.btnWed, R.id.btnThu,
                R.id.btnFri, R.id.btnSat, R.id.btnSun
        };
        for (int id: Weeks) {
            Button btnWeeks = findViewById(id);
            Log.i("MainBtnSwitch", "intId" + id);
            btnWeeks.setOnClickListener(this);
        }

        txtLessonOne = findViewById(R.id.txtLessonOne);
        txtLessonTwo = findViewById(R.id.txtLessonTwo);
        txtLessonThree = findViewById(R.id.txtLessonThree);
        txtLessonFour = findViewById(R.id.txtLessonFour);



    }

    /**
     * 曜日ボタン
     * @param view
     */
    @Override
    public void onClick(View view) {
        Button btnWeek = (Button) view;
        revView = view;
        if (viewBefore != null && viewBefore != view) {
            Button btnBefore = (Button) viewBefore;
            btnBefore.setEnabled(true);
        }
        loadDatabase();
        btnWeek.setEnabled(false);
        btnRevision.setEnabled(true);
        viewBefore = view;
    }

    /**
     * 修正ボタン
     */
    class clickEventRevision implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            switch (revView.getId()) {
                case R.id.btnMon:
                    Log.i("ClickEventRevisionBtnSwitch", "btnMonOk?++");
                    Intent intentMon = new Intent(getApplicationContext(),MonActivity.class);
//                    resultLauncher.launch(intentMon);
                    startActivity(intentMon);
                    break;
                case R.id.btnTue:
                    Log.i("ClickEventRevisionBtnSwitch", "btnTueOk?++");
                    Intent intentTue = new Intent(getApplicationContext(), TueActivity.class);
                    startActivity(intentTue);
                    break;
                case R.id.btnWed:
                    Intent intentWed = new Intent(getApplicationContext(), WedActivity.class);
                    startActivity(intentWed);
                    break;
                case R.id.btnThu:
                    Intent intentThu = new Intent(getApplicationContext(), ThuActivity.class);
                    startActivity(intentThu);
                    break;
                case R.id.btnFri:
                    Intent intentFri = new Intent(getApplicationContext(), FriActivity.class);
                    startActivity(intentFri);
                    break;
                case R.id.btnSat:
                    Intent intentSat = new Intent(getApplicationContext(), SatActivity.class);
                    startActivity(intentSat);
                    break;
                case R.id.btnSun:
                    Intent intentSun = new Intent(getApplicationContext(), SunActivity.class);
                    startActivity(intentSun);
                    break;
            }

        }
    }
    private void loadDatabase() {
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        executorService.execute(new Runnable() {
            @Override
            public void run() {
                try {
                    switch (revView.getId()) {
                        case R.id.btnMon:
                            Log.i("PlantTimeMon", "MonWhat++");
                            PlantTimeDatabase plantTimeDatabase = Room.databaseBuilder(getApplicationContext(),
                                    PlantTimeDatabase.class, "ptdatabase").build();
                            PlantTimeDao plantTimeDao = plantTimeDatabase.plantTimeDao();

                            list = plantTimeDao.getAll();
                            listMax = list.size();//リストの最後の番号

                            PlantTimeEntity plantTimeEntity = list.get(listMax - 1);
                            firstPeriodEt = plantTimeEntity.getFirstPeriod();
                            twoPeriodEt = plantTimeEntity.getTwoPeriod();
                            threePeriodEt = plantTimeEntity.getThreePeriod();
                            fourPeriodEt = plantTimeEntity.getFourPeriod();
                            Log.i("PlantTimeMon", "PeriodEtWhat??" + firstPeriodEt + twoPeriodEt + threePeriodEt + fourPeriodEt);
                            break;
                        case R.id.btnTue:
                            PlantTimeTueDatabase plantTimeTueDatabase = Room.databaseBuilder(getApplicationContext(),
                                    PlantTimeTueDatabase.class, "ptdatabaseTue").build();
                            PlantTimeTueDao plantTimeTueDao = plantTimeTueDatabase.plantTimeTueDao();

                            listTue = plantTimeTueDao.getAll();
                            listMaxTue = listTue.size();

                            PlantTimeEntityTue plantTimeEntityTue = listTue.get(listMaxTue - 1);
                            firstPeriodEt = plantTimeEntityTue.getFirstPeriodTue();
                            twoPeriodEt = plantTimeEntityTue.getTwoPeriodTue();
                            threePeriodEt = plantTimeEntityTue.getThreePeriodTue();
                            fourPeriodEt = plantTimeEntityTue.getFourPeriodTue();
                            break;
                        case R.id.btnWed:
                            PlantTimeWedDatabase plantTimeWedDatabase = Room.databaseBuilder(getApplicationContext(),
                                    PlantTimeWedDatabase.class, "ptdatabaseWed").build();
                            PlantTimeWedDao plantTimeWedDao = plantTimeWedDatabase.plantTimeWedDao();

                            listWed = plantTimeWedDao.getAll();
                            listMaxWed = listWed.size();

                            PlantTimeWedEntity plantTimeWedEntity = listWed.get(listMaxWed - 1);
                            firstPeriodEt = plantTimeWedEntity.getFirstPeriodWed();
                            twoPeriodEt = plantTimeWedEntity.getTwoPeriodWed();
                            threePeriodEt = plantTimeWedEntity.getThreePeriodWed();
                            fourPeriodEt = plantTimeWedEntity.getFourPeriodWed();
                            break;
                        case R.id.btnThu:
                            PlantTimeThuDatabase plantTimeThuDatabase = Room.databaseBuilder(getApplicationContext(),
                                    PlantTimeThuDatabase.class, "ptdatabaseThu").build();
                            PlantTimeThuDao plantTimeThuDao = plantTimeThuDatabase.plantTimeThuDao();

                            listThu = plantTimeThuDao.getAll();
                            listMax = listThu.size();

                            PlantTimeThuEntity plantTimeThuEntity = listThu.get(listMax - 1);
                            firstPeriodEt = plantTimeThuEntity .getFirstPeriodThu();
                            twoPeriodEt = plantTimeThuEntity .getTwoPeriodThu();
                            threePeriodEt = plantTimeThuEntity .getThreePeriodThu();
                            fourPeriodEt = plantTimeThuEntity .getFourPeriodThu();
                            break;
                        case R.id.btnFri:
                            PlantTimeFriDatabase plantTimeFriDatabase = Room.databaseBuilder(getApplicationContext(),
                                    PlantTimeFriDatabase.class, "ptdatabaseFri").build();
                            PlantTimeFriDao plantTimeFriDao = plantTimeFriDatabase.plantTimeFriDao();

                            listFri = plantTimeFriDao.getAll();
                            listMax = listFri.size();

                            PlantTimeFriEntity plantTimeFriEntity = listFri.get(listMax - 1);
                            firstPeriodEt = plantTimeFriEntity .getFirstPeriodFri();
                            twoPeriodEt = plantTimeFriEntity .getTwoPeriodFri();
                            threePeriodEt = plantTimeFriEntity .getThreePeriodFri();
                            fourPeriodEt = plantTimeFriEntity .getFourPeriodFri();
                            break;
                        case R.id.btnSat:
                            PlantTimeSatDatabase plantTimeSatDatabase = Room.databaseBuilder(getApplicationContext(),
                                    PlantTimeSatDatabase.class, "ptdatabaseSat").build();
                            PlantTimeSatDao plantTimeSatDao = plantTimeSatDatabase.plantTimeSatDao();

                            listSat = plantTimeSatDao.getAll();
                            listMax = listSat.size();

                            PlantTimeSatEntity plantTimeSatEntity = listSat.get(listMax - 1);
                            firstPeriodEt = plantTimeSatEntity .getFirstPeriodSat();
                            twoPeriodEt = plantTimeSatEntity .getTwoPeriodSat();
                            threePeriodEt = plantTimeSatEntity .getThreePeriodSat();
                            fourPeriodEt = plantTimeSatEntity .getFourPeriodSat();
                            break;
                        case R.id.btnSun:
                            PlantTimeSunDatabase plantTimeSunDatabase = Room.databaseBuilder(getApplicationContext(),
                                    PlantTimeSunDatabase.class, "ptdatabaseSun").build();
                            PlantTimeSunDao plantTimeSunDao = plantTimeSunDatabase.plantTimeSunDao();

                            listSun = plantTimeSunDao.getAll();
                            listMax = listSun.size();

                            PlantTimeSunEntity plantTimeSunEntity = listSun.get(listMax - 1);
                            firstPeriodEt = plantTimeSunEntity .getFirstPeriodSun();
                            twoPeriodEt = plantTimeSunEntity .getTwoPeriodSun();
                            threePeriodEt = plantTimeSunEntity .getThreePeriodSun();
                            fourPeriodEt = plantTimeSunEntity .getFourPeriodSun();
                            break;
                    }
                    textResult();
                } catch (Exception e) {

                }

//                PlantTimeDatabase plantTimeDatabase = Room.databaseBuilder(getApplicationContext(),
//                        PlantTimeDatabase.class, "ptdatabase").build();
//                PlantTimeDao plantTimeDao = plantTimeDatabase.plantTimeDao();


            }
        }

        );
    }

    private void writeDatabase() {
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        executorService.execute(new Runnable() {
            @Override
            public void run() {
                PlantTimeDatabase plantTimeDatabase = Room.databaseBuilder(getApplicationContext(),
                        PlantTimeDatabase.class, "ptdatabase").build();
                PlantTimeDao plantTimeDao = plantTimeDatabase.plantTimeDao();

            }
        }

        );
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
                   loadDatabase();
            }


    void ResultDatabase() {
        switch (revView.getId()) {

            case R.id.btnMon:
                Log.i("went","ResultDatabaseMonWent");
                PlantTimeDatabase plantTimeDatabase = Room.databaseBuilder(getApplicationContext(),
                        PlantTimeDatabase.class, "ptdatabase").build();
                PlantTimeDao plantTimeDao = plantTimeDatabase.plantTimeDao();

                list = plantTimeDao.getAll();
                listMax = list.size();//リストの最後の番号

                PlantTimeEntity plantTimeEntity = list.get(listMax - 1);
                firstPeriodEt = plantTimeEntity.getFirstPeriod();
                twoPeriodEt = plantTimeEntity.getTwoPeriod();
                threePeriodEt = plantTimeEntity.getThreePeriod();
                fourPeriodEt = plantTimeEntity.getFourPeriod();
                break;

            case R.id.btnTue:
                Log.i("went","ResultDatabaseWent");
                PlantTimeTueDatabase plantTimeTueDatabase = Room.databaseBuilder(getApplicationContext(),
                        PlantTimeTueDatabase.class, "ptdatabaseTue").build();
                PlantTimeTueDao plantTimeTueDao = plantTimeTueDatabase.plantTimeTueDao();

                listTue =plantTimeTueDao.getAll();
                listMaxTue = listTue.size();//リストの最後の番号

                PlantTimeEntityTue plantTimeTueEntity = listTue.get(listMaxTue - 1);
                firstPeriodEt = plantTimeTueEntity.getFirstPeriodTue();
                twoPeriodEt = plantTimeTueEntity.getTwoPeriodTue();
                threePeriodEt = plantTimeTueEntity.getThreePeriodTue();
                fourPeriodEt = plantTimeTueEntity.getFourPeriodTue();
                break;

            case R.id.btnWed:
                PlantTimeWedDatabase plantTimeWedDatabase = Room.databaseBuilder(getApplicationContext(),
                        PlantTimeWedDatabase.class, "ptdatabaseWed").build();
                PlantTimeWedDao plantTimeWedDao = plantTimeWedDatabase.plantTimeWedDao();

                listWed = plantTimeWedDao.getAll();
                listMaxWed = listWed.size();//リストの最後の番号

                PlantTimeWedEntity plantTimeWedEntity = listWed.get(listMaxWed - 1);
                firstPeriodEt = plantTimeWedEntity.getFirstPeriodWed();
                twoPeriodEt = plantTimeWedEntity.getTwoPeriodWed();
                threePeriodEt = plantTimeWedEntity.getThreePeriodWed();
                fourPeriodEt = plantTimeWedEntity.getFourPeriodWed();
                break;
//
//
//            case R.id.btnThu:
//                PlantTimeDatabase plantTimeDatabase = Room.databaseBuilder(getApplicationContext(),
//                        PlantTimeDatabase.class, "ptdatabase").build();
//                PlantTimeDao plantTimeDao = plantTimeDatabase.plantTimeDao();
//
//                list = plantTimeDao.getAll();
//                listMax = list.size();//リストの最後の番号
//
//                PlantTimeEntity plantTimeEntity = list.get(listMax - 1);
//                firstPeriodEt = plantTimeEntity.getFirstPeriod();
//                twoPeriodEt = plantTimeEntity.getTwoPeriod();
//                threePeriodEt = plantTimeEntity.getThreePeriod();
//                fourPeriodEt = plantTimeEntity.getFourPeriod();
//                break;
//
//
//            case R.id.btnFri:
//                PlantTimeDatabase plantTimeDatabase = Room.databaseBuilder(getApplicationContext(),
//                        PlantTimeDatabase.class, "ptdatabase").build();
//                PlantTimeDao plantTimeDao = plantTimeDatabase.plantTimeDao();
//
//                list = plantTimeDao.getAll();
//                listMax = list.size();//リストの最後の番号
//
//                PlantTimeEntity plantTimeEntity = list.get(listMax - 1);
//                firstPeriodEt = plantTimeEntity.getFirstPeriod();
//                twoPeriodEt = plantTimeEntity.getTwoPeriod();
//                threePeriodEt = plantTimeEntity.getThreePeriod();
//                fourPeriodEt = plantTimeEntity.getFourPeriod();
//                break;
//
//
//            case R.id.btnSat:
//                PlantTimeDatabase plantTimeDatabase = Room.databaseBuilder(getApplicationContext(),
//                        PlantTimeDatabase.class, "ptdatabase").build();
//                PlantTimeDao plantTimeDao = plantTimeDatabase.plantTimeDao();
//
//                list = plantTimeDao.getAll();
//                listMax = list.size();//リストの最後の番号
//
//                PlantTimeEntity plantTimeEntity = list.get(listMax - 1);
//                firstPeriodEt = plantTimeEntity.getFirstPeriod();
//                twoPeriodEt = plantTimeEntity.getTwoPeriod();
//                threePeriodEt = plantTimeEntity.getThreePeriod();
//                fourPeriodEt = plantTimeEntity.getFourPeriod();
//                break;
//
//
//            case R.id.btnSun:
//                PlantTimeDatabase plantTimeDatabase = Room.databaseBuilder(getApplicationContext(),
//                        PlantTimeDatabase.class, "ptdatabase").build();
//                PlantTimeDao plantTimeDao = plantTimeDatabase.plantTimeDao();
//
//                list = plantTimeDao.getAll();
//                listMax = list.size();//リストの最後の番号
//
//                PlantTimeEntity plantTimeEntity = list.get(listMax - 1);
//                firstPeriodEt = plantTimeEntity.getFirstPeriod();
//                twoPeriodEt = plantTimeEntity.getTwoPeriod();
//                threePeriodEt = plantTimeEntity.getThreePeriod();
//                fourPeriodEt = plantTimeEntity.getFourPeriod();
//                break;


        }
    }
    private void textResult() {
        txtLessonOne.setText(firstPeriodEt);
        txtLessonTwo.setText(twoPeriodEt);
        txtLessonThree.setText(threePeriodEt);
        txtLessonFour.setText(fourPeriodEt);
    }

}