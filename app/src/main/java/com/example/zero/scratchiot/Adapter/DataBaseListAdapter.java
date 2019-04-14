package com.example.zero.scratchiot.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.zero.scratchiot.GetSetDataVariabel.variabelList;
import com.example.zero.scratchiot.R;

import java.util.ArrayList;

public class DataBaseListAdapter extends BaseAdapter {
    private final Context context;
    private ArrayList<variabelList> variabelLists;

    public void setVariabelLists(ArrayList<variabelList> variabelLists) {
        this.variabelLists = variabelLists;
    }

    public DataBaseListAdapter(Context context) {
        this.context = context;
        variabelLists = new ArrayList<>();
    }

    @Override
    public int getCount() {
        return variabelLists.size();
    }

    @Override
    public Object getItem(int i) {
        return variabelLists.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        if (view == null) {
            view = LayoutInflater.from(context).inflate(R.layout.itemlistview, viewGroup, false);
        }
        variabelList vl = (variabelList) getItem(i);

        TextView txtID_list         = view.findViewById(R.id.txt_id_List);
        TextView txtSensor1_list    = view.findViewById(R.id.txt_sensor1_list);
        TextView txtSensor2_list    = view.findViewById(R.id.txt_sensor2_list);
        TextView txtSensor3_list    = view.findViewById(R.id.txt_sensor3_list);
        TextView txtSensor4_list    = view.findViewById(R.id.txt_sensor4_list);
        TextView txtTanggal_list    = view.findViewById(R.id.txt_tanggal_list);

        txtID_list.setText(vl.getIDServer());
        txtSensor1_list.setText(vl.getSensor1());
        txtSensor2_list.setText(vl.getSensor2());
        txtSensor3_list.setText(vl.getSensor3());
        txtSensor4_list.setText(vl.getSensor4());
        txtTanggal_list.setText(vl.getTanggal());

        return view;
    }
}
