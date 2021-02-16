package saunaprojekti.saunaprojekti;


import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

class Sauna {
    private String PAIVAMAARA;
    private String AIKA;
    private String LAMPOTILA;
    private String ONKO;

    public Sauna(String PAIVAMAARA, String AIKA, String LAMPOTILA,
                 String ONKO) {
        this.PAIVAMAARA = PAIVAMAARA;
        this.AIKA = AIKA;
        this.LAMPOTILA = LAMPOTILA;
        this.ONKO = ONKO;
    }

    public void setPAIVAMAARA(String PAIVAMAARA) {
        this.PAIVAMAARA = PAIVAMAARA;
    }

    public void setAIKA(String AIKA) {
        this.AIKA = AIKA;
    }

    public void setLAMPOTILA(String LAMPOTILA) {
        this.LAMPOTILA = LAMPOTILA;
    }

    public void setONKO(String ONKO) {
        this.ONKO = ONKO;
    }

    public String getONKO() {
        return ONKO;
    }

    public String getAIKA() {
        return AIKA;
    }

    public String getPAIVAMAARA() {
        return PAIVAMAARA;
    }

    public String getLAMPOTILA() {
        return LAMPOTILA;
    }
}


public class connectionActivity extends Activity {

    private static final String TAG = "MainActivity";
    private TextView textView;
    private RequestQueue requestQueue;
    ListView listView;
    Sauna objSauna;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_connection);
        super.onCreate(savedInstanceState);

        Log.d(TAG, "onCreate: Started.");
        ListView mListView = (ListView) findViewById(R.id.list);
        ArrayList<Sauna> saunaList = new ArrayList<>();
        RequestQueue queue = Volley.newRequestQueue(this);
        String url = "https://kello9sauna.fi/js.php";
        PersonListAdapter adapter = new PersonListAdapter(this, R.layout.record, saunaList);
        mListView.setAdapter(adapter);

        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONArray array = new JSONArray(response);
                            for (int i = 0; i < array.length(); i++) {
                                JSONObject object = array.optJSONObject(i);
                                String line = object.optString("PAIVAMAARA");
                                String AIKA = object.optString("AIKA");
                                String lampoConv = object.optString("LAMPOTILA");
                                String ONKO = object.optString("ONKO");
                                Sauna yes = new Sauna(line, AIKA, lampoConv, ONKO);
                                saunaList.add(yes);
                            }
                            // Once we added the string to the array, we notify the arrayAdapter
                            adapter.notifyDataSetChanged();
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



