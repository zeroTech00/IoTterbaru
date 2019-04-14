package com.example.zero.scratchiot.Menu;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.example.zero.scratchiot.DataBase.DatabaseHelper;
import com.example.zero.scratchiot.R;
import com.example.zero.scratchiot.ShowDatabase;


public class AboutFragment extends Fragment {

    Button btnShowDatabase;

    DatabaseHelper mDB;
    boolean suksesDatabase = false;
    String data;
    String saveIDServer, saveSensor1, saveSensor2, saveSensor3, saveSensor4, saveTanggal;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_about, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mDB = new DatabaseHelper(getActivity());

        data = getResources().getString(R.string.data);

        btnShowDatabase = view.findViewById(R.id.btn_showDatase);

        btnShowDatabase.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intentShowDatabase = new Intent(getActivity(), ShowDatabase.class);
                startActivity(intentShowDatabase);
            }
        });

    }

    private void showToast(String text) {
        Toast.makeText(getActivity(), text, Toast.LENGTH_SHORT).show();
    }

}
