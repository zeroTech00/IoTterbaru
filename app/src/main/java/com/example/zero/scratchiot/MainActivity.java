package com.example.zero.scratchiot;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.zero.scratchiot.DataBase.DatabaseHelper;
import com.example.zero.scratchiot.HTTPRequest.JSONParser;
import com.example.zero.scratchiot.Menu.AboutFragment;
import com.example.zero.scratchiot.Menu.ChartFragment;
import com.example.zero.scratchiot.Menu.StreamingFragment;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {

    Timer timer1;

    //-------------------- untuk data server ---------------------------------------
    public static final String tag_keyInfoConnectServer = "keyInfoConnectServer";
    public static final String tag_keyIDsql = "keyIdSqlAndro";

    public static JSONObject jsonObjectFromAsyn;

    String url_create = "http://104.248.159.38/ambildata";

    String sendBroadcastToServer, sendIDsql = "0", responFromServer, testRespon;
    String lastIDtoServer;

    //------------------------------------------------------------------------------

    //-------------------- untuk database -------------------------------------------
    DatabaseHelper mDB;
    boolean suksesDatabase;

    String saveIDServer, saveSensor1, saveSensor2, saveSensor3, saveSensor4, saveTanggal;

    String keySearch;


    //-------------------------------------------------------------------------------

    //---------------------- untuk Save to Sharedpreference --------------------------
    SharedPreferences sp;

    public static final String USER_PREF = "DATA_SAVE";
    public static final String KEY_DATA1 = "KEY_FIRSTID";
    public static final String KEY_DATA2 = "KEY_LASTIDSERVER";


    //--------------------------------------------------------------------------------

    public static String TAG = "DELOK";
    String tempLastIDServer,IDdbTerbaru;
    String tempTanggal[];
    boolean cek = true;

    int IDtujuan,IDdBTerakhir;
    boolean statusSaveDatabaseequal0, statusSaveDatabaseequalLastData;



    //==============================================================================================
    //==============================================================================================
    //==============================================================================================
    //==============================================================================================

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        BottomNavigationView bottomNav = findViewById(R.id.bottom_navigation);
        bottomNav.setOnNavigationItemSelectedListener(navListener);

        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                new ChartFragment()).commit();




        //---------------------- define database ---------------------------
        mDB = new DatabaseHelper(this);
        StringBuilder str = new StringBuilder();

        //---------------------- define sharePreference --------------------
        sp = getSharedPreferences(USER_PREF, Context.MODE_PRIVATE);

        //---------------------- Insert First Database + save xml --------------

        Log.d(TAG, "==================================================");
        Log.d(TAG, "==================================================");
        Log.d(TAG, "===================awal running===================");

        String IDdata = mDB.latest("ID");

        if (IDdata == null) {
            // meminta data ker server dari ID 0

            Log.d(TAG, "database belum ada mengirim data ke server 0");

            IDdbTerbaru = "0";

        } else {
            IDdbTerbaru = IDdata;

            Log.d(TAG, "pertama lihat latest ID database " + IDdbTerbaru);
        }

        // cara save data internal
//        saveInternal("1","2");
//
//        // cara load data dari memory internal
        String data     = sp.getString(KEY_DATA1, "");
//        String data2    = sp.getString(KEY_DATA2, "");

        Log.d(TAG, "LOAD data internal: " + data);


        //--------------------------------------------------------------------

        TimerServer tmServer = new TimerServer();
//        Timer timer1 = new Timer();
        timer1 = new Timer();
        timer1.schedule(tmServer, 1000, 5000);
    }

    //----------------------------------------------------------------------------------------------------------------------------------------
    //----------------------------------------------------------------------------------------------------------------------------------------


    //--------------------------- Timer + Asyntask ------------------------------------------


    class TimerServer extends TimerTask {

        final class kirimServer extends AsyncTask<String, String, JSONObject> {


            @Override
            protected void onPreExecute() {
                super.onPreExecute();
//                IDdbTerbaru = mDB.latest("ID");
            }

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

//                    lastIDtoServer = tempLastIDServer;
                    IDtujuan = Integer.valueOf(tempLastIDServer);

//                    Log.d(TAG, "id terkahir dari paket data server(lastIDtoServer): " + lastIDtoServer);
                    Log.d(TAG, "id terkahir dari paket data server(IDtujuan): " + IDtujuan);

                    publishProgress(lastIDtoServer);

                } catch (JSONException e) {
                    e.printStackTrace();
                }

                return jsonObj;
            }

            @Override
            protected void onPostExecute(JSONObject jsonObject) {
                super.onPostExecute(jsonObject);

                jsonObjectFromAsyn = jsonObject;

                String jsOBJ = jsonObject.toString();
//                Log.d(TAG, "jsonObjectFromAsyn: " + jsonObjectFromAsyn);

                new asyncSaveDatabase().execute();

            }
        }

        @Override
        public void run() {
            kirimServer runServer = new kirimServer();

            runServer.execute();
        }
    }

    final class asyncSaveDatabase extends AsyncTask<JSONObject, String, String> {


        @Override
        protected void onPreExecute() {
            super.onPreExecute();


        }

        @Override
        protected void onProgressUpdate(String... values) {
            super.onProgressUpdate(values);


        }

        @Override
        protected String doInBackground(JSONObject... jsonObjects) {

            JSONArray jsonArrayDataBase;

            try {

                String k = mDB.latest("ID");
                if(k == null) {
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

                    if(saveIDServer.equals("0")) {
                        statusSaveDatabaseequal0 = false;
                    } else {
                        statusSaveDatabaseequal0 = true;
                    }

                    if(IDdbTerbaru.equals(saveIDServer)) {
                        statusSaveDatabaseequalLastData = false;
                    } else {
                        statusSaveDatabaseequalLastData = true;
                    }

                    Log.d(TAG, "statusSaveDatabaseequalLastData : " + statusSaveDatabaseequalLastData);
                    Log.d(TAG, "statusSaveDatabaseequal0 : " + statusSaveDatabaseequal0);
                    Log.d(TAG, "IDdBTerakhir : " + IDdBTerakhir);
                    Log.d(TAG, "IDtujuan : " + IDtujuan);


                    if((IDdBTerakhir <= (IDtujuan)) && statusSaveDatabaseequal0 && statusSaveDatabaseequalLastData) {
                        suksesDatabase = mDB.insertData(saveIDServer, saveSensor1, saveSensor2, saveSensor3, saveSensor4, saveTanggal);
                    }

                }

                IDdbTerbaru = mDB.latest("ID");

                Log.d(TAG, "get id terkahir setelah penyimpanan: " + IDdbTerbaru);

                Log.d(TAG, "suksesDatabase : " + suksesDatabase);

            } catch (JSONException e) {
                e.printStackTrace();
            }

            Log.d(TAG, "==============================");

            return null;
        }

        @Override
        protected void onPostExecute (String s){
            super.onPostExecute(s);

        }
    }


    //---------------------- Toast -------------------------------------------------------
    private void showTost(String Text) {
        Toast.makeText(getApplicationContext(), Text, Toast.LENGTH_SHORT).show();
    }
    //-------------------------------------------------------------------------------------


    private BottomNavigationView.OnNavigationItemSelectedListener navListener =
            new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                    Fragment selectedFragment = null;

                    switch (menuItem.getItemId()) {
                        case R.id.nav_chart:
                            selectedFragment = new ChartFragment();
                            break;
                        case R.id.nav_streaming:
                            selectedFragment = new StreamingFragment();
                            break;
                        case R.id.nav_about:
                            selectedFragment = new AboutFragment();
                            break;
                    }

                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                            selectedFragment).commit();

                    return true;
                }
            };






    @Override
    protected void onResume() {
        super.onResume();


    }

    @Override
    protected void onPause() {
        super.onPause();


    }

}
