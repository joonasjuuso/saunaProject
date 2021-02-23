package saunaprojekti.saunaprojekti;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import saunaprojekti.saunaprojekti.ui.login.LoginActivity;


public class kuvaajaActivity extends AppCompatActivity {

    ArrayList<Entry> x;
    ArrayList<String> y;
    private LineChart mChart;
    public String TAG = "YOUR CLASS NAME";
    String name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kuvaaja);
        Log.d("kuvaajaActivity","kuvaajaActivity");
        Bundle extras = getIntent().getExtras();
        name = extras.getString("name");
        x = new ArrayList<Entry>();
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
        //mChart.notifyDataSetChanged(); poista // jos ei toimi
        //mChart.invalidate();
        invalidateOptionsMenu();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_kuvaaja, menu);
        return true;
    }
    @Override
    public boolean onPrepareOptionsMenu(Menu menu2) {
        MenuItem item = menu2.findItem(R.id.logout);
        if (item.getTitle().equals("Logout")) {
            item.setTitle("Log out from " + name);
        }
        return super.onPrepareOptionsMenu(menu2);
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item2) { switch(item2.getItemId()) {
        case R.id.add:

            break;
        case R.id.reset:
            Intent intent = new Intent(getApplicationContext(), connectionActivity.class);
            intent.putExtra("name",name);
            startActivity(intent);
            finish();
            break;
        case R.id.menu:
            Intent menuIntent = new Intent(getApplicationContext(),MainActivity.class);
            menuIntent.putExtra("name",name);
            startActivity(menuIntent);
            finish();
            break;
        case R.id.about:
            Intent aboutIntent = new Intent(getApplicationContext(),aboutActivity.class);
            aboutIntent.putExtra("name",name);
            startActivity(aboutIntent);
            finish();
            break;
        case R.id.logout:
            Intent logoutIntent = new Intent(getApplicationContext(), LoginActivity.class);
            startActivity(logoutIntent);
            finish();
            break;
        case R.id.exit:
            //add the function to perform here
            finish();
            break;
    }
        return(super.onOptionsItemSelected(item2));
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
                                String AIKA = object.optString("AIKA");
                                String lampoConv = object.optString("LAMPOTILA");
                                Log.d("replace",AIKA);
                                float aikaPitka = Float.parseFloat(AIKA);
                                float lampoPitka = Float.parseFloat(lampoConv);
                                x.add(new Entry(aikaPitka,lampoPitka));
                                Log.d("data", String.valueOf(aikaPitka));
                            }
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