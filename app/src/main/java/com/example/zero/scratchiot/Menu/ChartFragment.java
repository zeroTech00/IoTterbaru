package com.example.zero.scratchiot.Menu;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.zero.scratchiot.R;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.github.mikephil.charting.utils.ColorTemplate;

public class ChartFragment extends Fragment {
    TextView tv, tampil;
    LineChart chart, chart2, chart3, chart4;
    boolean TimerStart;
    Button tombol1;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate (R.layout.fragment_chart, container, false);
    }


    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated (view, savedInstanceState);
        InitiateGraph ();
        TimerStart=true;
        tombol1 = (Button) view.findViewById (R.id.btn_coba);
        tampil = (TextView) view.findViewById (R.id.tampil);
        addEntry(0,0);
        addEntry(10,20);
        addEntry2(0,0);
        addEntry3(0,0);
        addEntry4(0,0);


    }

    public void TombolKlik (View klik)
    {

    }

    private void InitiateGraph() {

        chart = getView ().findViewById (R.id.chart1);
        chart2 = getView ().findViewById (R.id.chart2);
        chart3 = getView ().findViewById (R.id.chart3);
        chart4 = getView ().findViewById (R.id.chart4);


        // enable description text
        chart.getDescription ().setEnabled (true);
        chart2.getDescription ().setEnabled (true);
        chart3.getDescription ().setEnabled (true);
        chart4.getDescription ().setEnabled (true);

        // enable touch gestures
        chart.setTouchEnabled (true);
        chart2.setTouchEnabled (true);
        chart3.setTouchEnabled (true);
        chart4.setTouchEnabled (true);

        // enable scaling and dragging
        chart.setDragEnabled (true);
        chart.setScaleEnabled (true);
        chart.setDrawGridBackground (false);

        chart2.setDragEnabled (true);
        chart2.setScaleEnabled (true);
        chart2.setDrawGridBackground (false);

        chart3.setDragEnabled (true);
        chart3.setScaleEnabled (true);
        chart3.setDrawGridBackground (false);

        chart4.setDragEnabled (true);
        chart4.setScaleEnabled (true);
        chart4.setDrawGridBackground (false);

        // if disabled, scaling can be done on x- and y-axis separately
        chart.setPinchZoom (true);
        chart2.setPinchZoom (true);
        chart3.setPinchZoom (true);
        chart4.setPinchZoom (true);

        // set an alternative background color
        chart.setBackgroundColor (Color.WHITE);
        chart2.setBackgroundColor (Color.WHITE);
        chart3.setBackgroundColor (Color.WHITE);
        chart4.setBackgroundColor (Color.WHITE);


        // data belum di set
        LineData data = new LineData();
        LineData data2 = new LineData();
        LineData data3 = new LineData();
        LineData data4 = new LineData();

        data.setValueTextColor (Color.BLACK);
        data2.setValueTextColor (Color.BLACK);
        data3.setValueTextColor (Color.BLACK);
        data4.setValueTextColor (Color.BLACK);

        // add empty data
        chart.setData (data);
        chart2.setData (data2);
        chart3.setData (data3);
        chart4.setData (data4);

        // get the legend (only possible after setting data)
        Legend l = chart.getLegend ();
        Legend l2 = chart2.getLegend ();
        Legend l3 = chart3.getLegend ();
        Legend l4 = chart4.getLegend ();
        // sampai sini
        // modify the legend ...
        l.setForm (Legend.LegendForm.LINE);
        l2.setForm (Legend.LegendForm.LINE);
        l3.setForm (Legend.LegendForm.LINE);
        l4.setForm (Legend.LegendForm.LINE);

        //l.setTypeface(tfLight);
        l.setTextColor (Color.rgb (89,149,186));
        l2.setTextColor (Color.rgb(89, 149, 186));
        l3.setTextColor (Color.rgb(89, 149, 186));
        l4.setTextColor (Color.rgb(89, 149, 186));



        chart.getDescription ().setEnabled (false);
        chart2.getDescription ().setEnabled (false);
        chart3.getDescription ().setEnabled (false);
        chart4.getDescription ().setEnabled (false);

        XAxis xl = chart.getXAxis ();
        XAxis x2 = chart2.getXAxis ();
        XAxis x3 = chart3.getXAxis ();
        XAxis x4 = chart4.getXAxis ();


        //xl.setTypeface(tfLight);
        xl.setTextColor (Color.BLACK);
        xl.setDrawGridLines (false);
        xl.setAvoidFirstLastClipping (true);
        xl.setEnabled (true);
        xl.setPosition (XAxis.XAxisPosition.BOTTOM);



        x2.setTextColor (Color.BLACK);
        x2.setDrawGridLines (false);
        x2.setAvoidFirstLastClipping (true);
        x2.setEnabled (true);


        x3.setTextColor (Color.BLACK);
        x3.setDrawGridLines (false);
        x3.setAvoidFirstLastClipping (true);
        x3.setEnabled (true);

        x4.setTextColor (Color.BLACK);
        x4.setDrawGridLines (false);
        x4.setAvoidFirstLastClipping (true);
        x4.setEnabled (true);

        YAxis leftAxis1 = chart.getAxisLeft ();
        YAxis leftAxis2 = chart2.getAxisLeft ();
        YAxis leftAxis3 = chart3.getAxisLeft ();
        YAxis leftAxis4 = chart4.getAxisLeft ();


        //leftAxis.setTypeface(tfLight);
        leftAxis1.setTextColor (Color.BLACK);
        leftAxis1.setAxisMaximum (30f);
        leftAxis1.setAxisMinimum (0f);
        leftAxis1.setDrawGridLines (true);

        leftAxis2.setTextColor (Color.BLACK);
        leftAxis2.setAxisMaximum (3f);
        leftAxis2.setAxisMinimum (0f);
        leftAxis2.setDrawGridLines (true);


        leftAxis3.setTextColor (Color.BLACK);
        leftAxis3.setAxisMaximum (3f);
        leftAxis3.setAxisMinimum (0f);
        leftAxis3.setDrawGridLines (true);


        leftAxis4.setTextColor (Color.BLACK);
        leftAxis4.setAxisMaximum (3f);
        leftAxis4.setAxisMinimum (0f);
        leftAxis4.setDrawGridLines (true);

        chart.getXAxis ().setPosition (XAxis.XAxisPosition.BOTTOM);
        chart2.getXAxis ().setPosition (XAxis.XAxisPosition.BOTTOM);
        chart3.getXAxis ().setPosition (XAxis.XAxisPosition.BOTTOM);
        chart4.getXAxis ().setPosition (XAxis.XAxisPosition.BOTTOM);


        YAxis rightAxis1 = chart.getAxisRight ();
        YAxis rightAxis2 = chart2.getAxisRight ();
        YAxis rightAxis3 = chart3.getAxisRight ();
        YAxis rightAxis4 = chart4.getAxisRight ();

        rightAxis1.setEnabled (false);
        rightAxis2.setEnabled (false);
        rightAxis3.setEnabled (false);
        rightAxis4.setEnabled (false);


    }

    private void addEntry(float valuesX, float ValuesY) {

        LineData data = chart.getData ();


        if (data != null) {

            ILineDataSet set = data.getDataSetByIndex (0);
            // set.addEntry(...); // can be called as well

            if (set == null) {
                set = createSet ();
                data.addDataSet (set);
            }

            if (TimerStart) {
                data.addEntry (new Entry(valuesX, ValuesY), 0);
                data.notifyDataChanged ();

                // let the chart know it's data has changed
                chart.notifyDataSetChanged ();

                // limit the number of visible entries
                chart.setVisibleXRangeMaximum (120);

                 //chart.setVisibleYRange(30, XAxis.XAxisPosition.BOTTOM);

                // move to the latest entry
                chart.moveViewToX (data.getEntryCount ());
            } else {
            }
            // this automatically refreshes the chart (calls invalidate())
            // chart.moveViewTo(data.getXValCount()-7, 55f,
            // AxisDependency.LEFT);
        }
    }

    private void addEntry2(float valuesX, float ValuesY) {

        LineData data = chart2.getData ();


        if (data != null) {

            ILineDataSet set = data.getDataSetByIndex (0);
            // set.addEntry(...); // can be called as well

            if (set == null) {
                set = createSet2 ();
                data.addDataSet (set);
            }

            if (TimerStart) {
                data.addEntry (new Entry(valuesX, ValuesY), 0);
                data.notifyDataChanged ();

                // let the chart know it's data has changed
                chart2.notifyDataSetChanged ();

                // limit the number of visible entries
                chart2.setVisibleXRangeMaximum (120);
                // chart.setVisibleYRange(30, AxisDependency.LEFT);

                // move to the latest entry
                chart2.moveViewToX (data.getEntryCount ());
            } else {
            }
            // this automatically refreshes the chart (calls invalidate())
            // chart.moveViewTo(data.getXValCount()-7, 55f,
            // AxisDependency.LEFT);
        }
    }

    private void addEntry3(float valuesX, float ValuesY) {

        LineData data = chart3.getData ();


        if (data != null) {

            ILineDataSet set = data.getDataSetByIndex (0);
            // set.addEntry(...); // can be called as well

            if (set == null) {
                set = createSet3 ();
                data.addDataSet (set);
            }

            if (TimerStart) {
                data.addEntry (new Entry(valuesX, ValuesY), 0);
                data.notifyDataChanged ();

                // let the chart know it's data has changed
                chart3.notifyDataSetChanged ();

                // limit the number of visible entries
                chart3.setVisibleXRangeMaximum (120);
                // chart.setVisibleYRange(30, AxisDependency.LEFT);

                // move to the latest entry
                chart3.moveViewToX (data.getEntryCount ());
            } else {
            }
            // this automatically refreshes the chart (calls invalidate())
            // chart.moveViewTo(data.getXValCount()-7, 55f,
            // AxisDependency.LEFT);
        }
    }

    private void addEntry4(float valuesX, float ValuesY) {

        LineData data = chart4.getData ();


        if (data != null) {

            ILineDataSet set = data.getDataSetByIndex (0);
            // set.addEntry(...); // can be called as well

            if (set == null) {
                set = createSet4 ();
                data.addDataSet (set);
            }

            if (TimerStart) {
                data.addEntry (new Entry(valuesX, ValuesY), 0);
                data.notifyDataChanged ();

                // let the chart know it's data has changed
                chart4.notifyDataSetChanged ();

                // limit the number of visible entries
                chart4.setVisibleXRangeMaximum (120);
                // chart.setVisibleYRange(30, AxisDependency.LEFT);

                // move to the latest entry
                chart4.moveViewToX (data.getEntryCount ());
            } else {
            }
            // this automatically refreshes the chart (calls invalidate())
            // chart.moveViewTo(data.getXValCount()-7, 55f,
            // AxisDependency.LEFT);
        }
    }

    private LineDataSet createSet() {

        LineDataSet set = new LineDataSet(null, "Sensor 1");
        set.setAxisDependency (YAxis.AxisDependency.LEFT);
        set.setColor (ColorTemplate.getHoloBlue ());
        set.setCircleColor (Color.BLUE);
        set.setLineWidth (2f);
        set.setCircleRadius (4f);
        set.setFillAlpha (65);
        set.setFillColor (ColorTemplate.getHoloBlue ());
        set.setHighLightColor (Color.rgb (244, 117, 117));
        set.setValueTextColor (Color.BLACK);
        set.setValueTextSize (9f);
        set.setDrawValues (false);
        return set;
    }

    private LineDataSet createSet2() {

        LineDataSet set = new LineDataSet(null, "Sensor 2");
        set.setAxisDependency (YAxis.AxisDependency.LEFT);
        set.setColor (ColorTemplate.getHoloBlue ());
        set.setCircleColor (Color.BLUE);
        set.setLineWidth (2f);
        set.setCircleRadius (4f);
        set.setFillAlpha (65);
        set.setFillColor (ColorTemplate.getHoloBlue ());
        set.setHighLightColor (Color.rgb (244, 117, 117));
        set.setValueTextColor (Color.BLACK);
        set.setValueTextSize (9f);
        set.setDrawValues (false);
        return set;
    }

    private LineDataSet createSet3() {

        LineDataSet set = new LineDataSet(null, "Sensor 3");
        set.setAxisDependency (YAxis.AxisDependency.LEFT);
        set.setColor (ColorTemplate.getHoloBlue ());
        set.setCircleColor (Color.BLUE);
        set.setLineWidth (2f);
        set.setCircleRadius (4f);
        set.setFillAlpha (65);
        set.setFillColor (ColorTemplate.getHoloBlue ());
        set.setHighLightColor (Color.rgb (244, 117, 117));
        set.setValueTextColor (Color.BLACK);
        set.setValueTextSize (9f);
        set.setDrawValues (false);
        return set;
    }

    private LineDataSet createSet4() {

        LineDataSet set = new LineDataSet(null, "Sensor 4");
        set.setAxisDependency (YAxis.AxisDependency.LEFT);
        set.setColor (ColorTemplate.getHoloBlue ());
        set.setCircleColor (Color.BLUE);
        set.setLineWidth (2f);
        set.setCircleRadius (4f);
        set.setFillAlpha (65);
        set.setFillColor (ColorTemplate.getHoloBlue ());
        set.setHighLightColor (Color.rgb (244, 117, 117));
        set.setValueTextColor (Color.BLACK);
        set.setValueTextSize (9f);
        set.setDrawValues (false);
        return set;
    }


}
