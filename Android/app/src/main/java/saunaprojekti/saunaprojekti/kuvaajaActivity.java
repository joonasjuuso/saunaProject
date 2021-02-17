package saunaprojekti.saunaprojekti;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class kuvaajaActivity extends AppCompatActivity {

    ArrayList<Entry> x;
    ArrayList<String> y;
    private LineChart mChart;
    public String TAG = "YOUR CLASS NAME";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kuvaaja);
        x = new ArrayList<Entry>();
        y = new ArrayList<String>();
        mChart = (LineChart) findViewById(R.id.chart1);
        mChart.setDrawGridBackground(false);
        mChart.setTouchEnabled(true);
        mChart.setDragEnabled(true);
        mChart.setScaleEnabled(true);
        mChart.setPinchZoom(true);
        XAxis xl = mChart.getXAxis();
        xl.setAvoidFirstLastClipping(true);
        YAxis leftAxis = mChart.getAxisLeft();
        leftAxis.setInverted(true);
        YAxis rightAxis = mChart.getAxisRight();
        rightAxis.setEnabled(false);
        Legend l = mChart.getLegend();
        l.setForm(Legend.LegendForm.LINE);
        drawChart();
        mChart.notifyDataSetChanged();
    }

    private void drawChart() {
        Log.d(TAG, "Chart");
        RequestQueue queue = Volley.newRequestQueue(this);
        String url = "https://kello9sauna.fi/js.php";

        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONArray array = new JSONArray(response);
                            for (int i = 0; i < array.length(); i++) {
                                JSONObject object = array.optJSONObject(i);
                                Double AIKA = object.optDouble("AIKA");
                                String lampoConv = object.optString("LAMPOTILA");
                                float aikaPitka = AIKA.floatValue();
                                float lampoPitka = Float.parseFloat(lampoConv);
                                x.add(new Entry(aikaPitka,lampoPitka));
                                /*x.add(new Entry(lampoConv, i));
                                y.add(AIKA);*/
                            }
                            /*LineDataSet set1 = new LineDataSet(x, "NAV Data Value");
                            set1.setLineWidth(1.5f);
                            set1.setCircleRadius(4f);
                            LineData data = new LineData((ILineDataSet) y, set1);*/
                            LineDataSet set1 = new LineDataSet(x,"Saunadata");
                            set1.setAxisDependency(YAxis.AxisDependency.LEFT);
                            List<ILineDataSet> dataSets = new ArrayList<ILineDataSet>();
                            dataSets.add(set1);
                            LineData data = new LineData(dataSets);
                            mChart.setData(data);
                            mChart.notifyDataSetChanged();
                            mChart.invalidate();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
            }
        });
        queue.add(stringRequest);

    }
}