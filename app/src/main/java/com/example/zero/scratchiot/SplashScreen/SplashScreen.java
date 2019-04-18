package com.example.zero.scratchiot.SplashScreen;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Build;
import android.os.CountDownTimer;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.zero.scratchiot.DataBase.DatabaseHelper;
import com.example.zero.scratchiot.HTTPRequest.JSONParser;
import com.example.zero.scratchiot.MainActivity;
import com.example.zero.scratchiot.R;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class SplashScreen extends AppCompatActivity {

    ProgressBar progressBarLoad;
    TextView tvNilaiProgresBarAwal, tvNilaiProgresBarAkhir;

    private CountDownTimer countDownTimer;
    private boolean timerHasStarted = false;
    private final long startTime = 2 * 1000;
    private final long interval = 1 * 1000;

    public asynSyncDataServer asynDS;
    public asyncSaveDatabase asynDB;
    public asyncSkipSync asynSkip;

    //=================== JSON ====================================================
    public static final String tag_keyInfoConnectServer = "keyInfoConnectServer";
    public static final String tag_keyIDsql = "keyIdSqlAndro";

    String url_create = "http://104.248.159.38/ambildata";

    String sendBroadcastToServer, sendIDsql = "0";
    String lastIDtoServer;

    public static JSONObject jsonObjectFromAsyn;

    public static String TAG = "DELOK";
    String tempLastIDServer, IDdbTerbaru;
    String tempTanggal[];

    int IDtujuan, IDdBTerakhir;
    int IDtujuanSplashScreen, IDdbTerbaruSplashScreen;
    boolean statusSaveDatabaseequal0, statusSaveDatabaseequalLastData;
    //================================================================================

    //==================== DATABASE ===================================================
    DatabaseHelper mDB;
    boolean suksesDatabaseSplash;

    String saveIDServer, saveSensor1, saveSensor2, saveSensor3, saveSensor4, saveTanggal;
    //=======================================================================================


    int syncDataAkhir, syncData, syncSkip, syncSkipMax;
    int statusSkip = 0;
    boolean statusTimerSplash = true;


    //========================================================================================
    //########################################################################################
    //****************************************************************************************

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        //---------------------- define database ---------------------------
        mDB = new DatabaseHelper(this);
        StringBuilder str = new StringBuilder();

        //---------------------- Insert First Database + save xml --------------

        Log.d(TAG, "==================================================");
        Log.d(TAG, "==================================================");
        Log.d(TAG, "===================awal running splash===================");

        String IDdata = mDB.latest("ID");

        if (IDdata == null) {
            // meminta data ker server dari ID 0

            Log.d(TAG, "database belum ada mengirim data ke server 0");

            IDdbTerbaru = "0";
            IDdbTerbaruSplashScreen = Integer.valueOf(IDdbTerbaru);

        } else {
            IDdbTerbaru = IDdata;
            IDdbTerbaruSplashScreen = Integer.valueOf(IDdbTerbaru);

            Log.d(TAG, "pertama lihat latest ID database " + IDdbTerbaru);
            Log.d(TAG, "pertama lihat latest ID database(IDdbTerbaruSplashScreen) " + IDdbTerbaruSplashScreen);
        }


        progressBarLoad = findViewById(R.id.progresBar_load);
        tvNilaiProgresBarAwal = findViewById(R.id.txt_nilaiProgresBarAwal);
        tvNilaiProgresBarAkhir = findViewById(R.id.txt_nilaiProgresBarAkhir);

        progressBarLoad.setProgressTintList(ColorStateList.valueOf(Color.GREEN));
        progressBarLoad.setScaleY(2f);

        countDownTimer = new MyCountDownTimer(startTime, interval);

        countDownTimer.start();

        asynDS = new asynSyncDataServer();
        asynDS.execute();


    }

    class MyCountDownTimer extends CountDownTimer {

        public MyCountDownTimer(long startTime, long interval) {
            super(startTime, interval);

        }

        @Override
        public void onTick(long millisUntilFinished) {
            Log.d(TAG, "onTick: " + millisUntilFinished / 1000);
        }

        @Override
        public void onFinish() {
            statusTimerSplash = true;
            Log.d(TAG, "onFinish:statusTimerSplash " + statusTimerSplash);

        }


    }


    //==============================================================================================
    //==============================================================================================
    //==============================================================================================
    //==============================================================================================
    //==============================================================================================


    final class asynSyncDataServer extends AsyncTask<String, String, JSONObject> {


        @Override
        protected void onProgressUpdate(String... values) {
            super.onProgressUpdate(values);
        }


        @Override
        protected JSONObject doInBackground(String... strings) {
            JSONParser jsonParser = new JSONParser();
            JSONObject jsonObj;
            JSONArray jsonArrayIDSelection;

            List<NameValuePair> params = new ArrayList<>();

            sendBroadcastToServer = "90";
            sendIDsql = IDdbTerbaru;

            Log.d(TAG, "kirim data ke server(sendIDsql) : " + sendIDsql);

            params.add(new BasicNameValuePair(tag_keyInfoConnectServer, sendBroadcastToServer));
            params.add(new BasicNameValuePair(tag_keyIDsql, sendIDsql));

            jsonObj = jsonParser.makeHttpRequest(url_create, "GET", params);

            try {
                jsonArrayIDSelection = jsonObj.getJSONArray("sendToAndro");
                for (int j = 0; j < jsonArrayIDSelection.length(); j++) {
                    JSONObject objID1 = jsonArrayIDSelection.getJSONObject(j);
                    tempLastIDServer = objID1.getString("id");
                }

                IDtujuan = Integer.valueOf(tempLastIDServer);
                IDtujuanSplashScreen = IDtujuan;


                Log.d(TAG, "id terkahir dari paket data server(IDtujuan): " + IDtujuan);
                Log.d(TAG, "id terkahir dari paket data server(IDtujuanSplashScreen): " + IDtujuanSplashScreen);

                publishProgress(lastIDtoServer);


            } catch (NullPointerException e) {
                e.printStackTrace();
                Log.d(TAG, "NullPointerException: ");

            } catch (JSONException e) {
                e.printStackTrace();
                Log.d(TAG, "JSONException: ");
            } catch (RuntimeException e) {
                e.printStackTrace();
                Log.d(TAG, "RuntimeException: ");
            }

            return jsonObj;
//            retun null;
        }

        @Override
        protected void onPostExecute(JSONObject jsonObject) {
            super.onPostExecute(jsonObject);
            Log.d(TAG, "onPostExecute: ");

            try {
                jsonObjectFromAsyn = jsonObject;
                asynDB = new asyncSaveDatabase();
                asynDB.execute();

            } catch (NullPointerException e) {
                e.printStackTrace();
                Log.d(TAG, "NullPointerException: ");

            } catch (RuntimeException e) {
                e.printStackTrace();
                Log.d(TAG, "RuntimeException: ");
            }


        }
    }


    //-----------------------------------------------------------------------------------------------

    final class asyncSaveDatabase extends AsyncTask<JSONObject, Integer, String> {


        @Override
        protected void onPreExecute() {
            super.onPreExecute();


        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);


            if (statusSkip == 0) {
                progressBarLoad.setMax(values[1]);
                progressBarLoad.setProgress(values[0]);
                tvNilaiProgresBarAwal.setText(Integer.toString(values[0]));
                tvNilaiProgresBarAkhir.setText(Integer.toString(values[1]));
            }


        }

        @Override
        protected String doInBackground(JSONObject... jsonObjects) {

            JSONArray jsonArrayDataBase;

            try {

                String k = mDB.latest("ID");
                if (k == null) {
                    IDdBTerakhir = 0;
                    Log.d(TAG, "get id awal String :" + k);
                    Log.d(TAG, "get id awal Integer:" + IDdBTerakhir);
                } else {
                    IDdBTerakhir = Integer.valueOf(k);
                    Log.d(TAG, "get id awal String :" + k);
                    Log.d(TAG, "get id awal Integer:" + IDdBTerakhir);
                }

                jsonArrayDataBase = jsonObjectFromAsyn.getJSONArray("sendToAndro");

                for (int i = 0; i < jsonArrayDataBase.length(); i++) {
                    JSONObject objID = jsonArrayDataBase.getJSONObject(i);

                    saveIDServer = objID.getString("id");
                    saveSensor1 = objID.getString("sensor1");
                    saveSensor2 = objID.getString("sensor2");
                    saveSensor3 = objID.getString("sensor3");
                    saveSensor4 = objID.getString("sensor4");
                    tempTanggal = objID.getString("created_at").split(" ");
                    saveTanggal = tempTanggal[0];


                    Log.d(TAG, "looping parsing: " + saveIDServer);

                    Log.d(TAG, "saveTanggal: " + saveTanggal);

                    if (saveIDServer.equals("0")) {
                        statusSaveDatabaseequal0 = false;
                    } else {
                        statusSaveDatabaseequal0 = true;
                    }

                    if (IDdbTerbaru.equals(saveIDServer)) {
                        statusSaveDatabaseequalLastData = false;
                    } else {
                        statusSaveDatabaseequalLastData = true;
                    }

                    Log.d(TAG, "statusSaveDatabaseequalLastData : " + statusSaveDatabaseequalLastData);


                    if ((IDdBTerakhir <= (IDtujuan)) && statusSaveDatabaseequal0 && statusSaveDatabaseequalLastData) {
                        suksesDatabaseSplash = mDB.insertData(saveIDServer, saveSensor1, saveSensor2, saveSensor3, saveSensor4, saveTanggal);
                    }

                    syncDataAkhir = IDtujuanSplashScreen - IDdbTerbaruSplashScreen;

                    if (IDtujuanSplashScreen == 0 || syncDataAkhir < 1) {
                        statusSkip = 1;
                        syncData = 0;
                        syncDataAkhir = 0;
                    }

                    Log.d(TAG, "syncDataAkhir: " + syncDataAkhir);

                    if (suksesDatabaseSplash) {
                        syncData = Integer.valueOf(saveIDServer) - Integer.valueOf(IDdbTerbaru);
                        Log.d(TAG, "database berhasil di simpan");
                        Log.d(TAG, "syncData: " + syncData);
                    } else {
                        Log.d(TAG, "database gagal simpan ");
                    }

                    publishProgress(syncData, syncDataAkhir, statusSkip);

                    if (syncDataAkhir > 100) {
                        Thread.sleep(25);
                    } else if (syncDataAkhir > 50 && syncDataAkhir <= 100) {
                        Thread.sleep(40);
                    } else {
                        Thread.sleep(70);
                    }

//                    Thread.sleep(25);

                }

                IDdbTerbaru = mDB.latest("ID");

                Log.d(TAG, "get id terkahir setelah penyimpanan: " + IDdbTerbaru);


                Log.d(TAG, "suksesDatabase: " + suksesDatabaseSplash);

                return Integer.toString(statusSkip);


            } catch (NullPointerException e) {
                e.printStackTrace();
                Log.d(TAG, "NullPointerException 2: ");

            } catch (RuntimeException e) {
                e.printStackTrace();
                Log.d(TAG, "RuntimeException 2: ");
            } catch (InterruptedException e) {
                e.printStackTrace();
                Log.d(TAG, "InterruptedException 2: ");
            } catch (JSONException e) {
                e.printStackTrace();
                Log.d(TAG, "JSONException 2: ");

            }

            Log.d(TAG, "==============================");

            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

            try {
                Log.d(TAG, "statusSkip: " + s);

                if (s.equals("1")) {
                    asynSkip = new asyncSkipSync();
                    asynSkip.execute();
                } else {
                    if (statusTimerSplash) {
                        Intent IntentMain = new Intent(SplashScreen.this, MainActivity.class);
                        startActivity(IntentMain);
                    }
                }

            } catch (NullPointerException e) {
                e.printStackTrace();
                Log.d(TAG, "NullPointerException 3: ");
                asynSkip = new asyncSkipSync();
                asynSkip.execute();
                Log.d(TAG, "Run asyn 3: ");

            } catch (RuntimeException e) {
                e.printStackTrace();
                Log.d(TAG, "RuntimeException 3: ");
            }


        }
    }

    final class asyncSkipSync extends AsyncTask<String, Integer, String> {

        int k, l, m;

        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);

            progressBarLoad.setMax(values[2]);
            progressBarLoad.setProgress(values[1]);
            tvNilaiProgresBarAwal.setText(Integer.toString(values[0]));
            tvNilaiProgresBarAkhir.setText(Integer.toString(values[0]));
        }

        @Override
        protected String doInBackground(String... strings) {
            for (int z = 0; z < 300; z++) {
                k = 0;
                l = z;
                m = 300;
                try {
                    Thread.sleep(20);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                publishProgress(k, l, m);

                Log.d(TAG, "doInBackground: " + l + " " + z);
            }

            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
//
            Intent IntentMain = new Intent(SplashScreen.this, MainActivity.class);
            startActivity(IntentMain);

            asynDS.cancel(true);
            asynDB.cancel(true);
            asynSkip.cancel(true);
            Log.d(TAG, "onPause: async cenceled");

            Log.d(TAG, "onPostExecute:  intent");
        }
    }


    @Override
    protected void onPause() {
        super.onPause();

        asynDS.cancel(true);
        asynDB.cancel(true);
        asynSkip.cancel(true);
        Log.d(TAG, "onPause: async cenceled");
        finish();
        
        
    }

    @Override
    protected void onStop() {
        super.onStop();

        asynDS.cancel(true);
        asynDB.cancel(true);
        asynSkip.cancel(true);
        Log.d(TAG, "onPause: async cenceled");
        finish();
    }
}
