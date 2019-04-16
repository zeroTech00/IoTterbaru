package com.example.zero.scratchiot;

import android.database.Cursor;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;

import com.example.zero.scratchiot.Adapter.DataBaseListAdapter;
import com.example.zero.scratchiot.DataBase.DatabaseHelper;
import com.example.zero.scratchiot.GetSetDataVariabel.variabelList;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

public class ShowDatabase extends AppCompatActivity {

    Timer timer1;
    private DataBaseListAdapter adapterList;
    private ListView listView;

    private static final String TAG = "DELOK";
    int a;

    DatabaseHelper mDB;

    int[] dataTemp;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_database);

        mDB = new DatabaseHelper(this);
        adapterList = new DataBaseListAdapter(this);

        listView = findViewById(R.id.listView_Database);
        listView.setAdapter(adapterList);

//        addItem();

        timerku tmServer = new timerku();
        timer1 = new Timer();
        timer1.schedule(tmServer, 1000, 100);
    }

    class timerku extends TimerTask {

        final class loop extends AsyncTask<String, String, Integer> {

            @Override
            protected void onPreExecute() {
                super.onPreExecute();

            }

            @Override
            protected Integer doInBackground(String... strings) {

                Cursor data = mDB.getData();
                ArrayList<Integer> listID = new ArrayList<Integer>();
                ArrayList<Integer> sensor1 = new ArrayList<Integer>();
                ArrayList<Integer> sensor2 = new ArrayList<Integer>();
                ArrayList<Integer> sensor3 = new ArrayList<Integer>();
                ArrayList<Integer> sensor4 = new ArrayList<Integer>();

                while (data.moveToNext()) {

                    listID.add(data.getInt(1));
                    sensor1.add(data.getInt(2));
                    sensor2.add(data.getInt(3));
                    sensor3.add(data.getInt(4));
                    sensor4.add(data.getInt(5));


                }
                Log.d(TAG, "listID: " + listID.get(0));
                Log.d(TAG, "listID: " + listID);
                Log.d(TAG, "sensor1: " + sensor1);
                Log.d(TAG, "sensor1: " + sensor2);
                Log.d(TAG, "sensor1: " + sensor3);
                Log.d(TAG, "sensor1: " + sensor4);
                return a;


            }

            @Override
            protected void onPostExecute(Integer s) {
                super.onPostExecute(s);


                Log.d(TAG, "additem sukses");

            }
        }

        @Override
        public void run() {
            loop runServer = new loop();

            runServer.execute();
        }
    }

    private void addItem() {
        Cursor data = mDB.getData();
        ArrayList<variabelList> variabelLists = new ArrayList<>();


        while (data.moveToNext()) {
            variabelList vl = new variabelList();
            vl.setIDServer(data.getString(1));
            vl.setSensor1(data.getString(2));
            vl.setSensor2(data.getString(3));
            vl.setSensor3(data.getString(4));
            vl.setSensor4(data.getString(5));
            vl.setTanggal(data.getString(6));

            variabelLists.add(vl);
        }

        adapterList.setVariabelLists(variabelLists);
    }
}
