package com.example.zero.scratchiot;

import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;

import com.example.zero.scratchiot.Adapter.DataBaseListAdapter;
import com.example.zero.scratchiot.DataBase.DatabaseHelper;
import com.example.zero.scratchiot.GetSetDataVariabel.variabelList;

import java.util.ArrayList;

public class ShowDatabase extends AppCompatActivity {

    private DataBaseListAdapter adapterList;
    private ListView listView;

    DatabaseHelper mDB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_database);

        mDB = new DatabaseHelper(this);
        adapterList = new DataBaseListAdapter(this);

        listView = findViewById(R.id.listView_Database);
        listView.setAdapter(adapterList);

        addItem();
    }

    private void addItem() {
        Cursor data = mDB.getData();
        ArrayList<variabelList> variabelLists = new ArrayList<>();


        while(data.moveToNext()) {
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
