package saunaprojekti.saunaprojekti;


import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;


import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
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
import java.util.HashMap;

import saunaprojekti.saunaprojekti.ui.login.LoginActivity;


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


public class connectionActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    private TextView textView;
    private RequestQueue requestQueue;
    ListView mListView;
    Sauna objSauna;
    ArrayList<Sauna> saunaList = new ArrayList<>();
    String name;

    @Override
    public boolean onCreateOptionsMenu(Menu menu3) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu3);
        return true;
    }
    @Override
    public boolean onPrepareOptionsMenu(Menu menu3) {
        MenuItem item = menu3.findItem(R.id.logout);
        if (item.getTitle().equals("Logout")) {
            item.setTitle("Log out from " + name);
        }
        return super.onPrepareOptionsMenu(menu3);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item3) { switch(item3.getItemId()) {
        case R.id.menu:
            Intent intent = new Intent(connectionActivity.this, MainActivity.class);
            intent.putExtra("name",name);
            startActivity(intent);
            finish();
            break;
        case R.id.add:
            getData();
            break;
        case R.id.reset:
            Intent graphIntent = new Intent(connectionActivity.this,kuvaajaActivity.class);
            graphIntent.putExtra("name",name);
            startActivity(graphIntent);
            finish();
            break;
        case R.id.about:
            Intent aboutIntent = new Intent(connectionActivity.this,aboutActivity.class);
            aboutIntent.putExtra("name",name);
            startActivity(aboutIntent);
            finish();
            break;
        case R.id.logout:
            Intent logoutIntent = new Intent(connectionActivity.this, LoginActivity.class);
            startActivity(logoutIntent);
            finish();
            break;
        case R.id.exit:
            finish();
    }
        return(super.onOptionsItemSelected(item3));
    }

    protected void getData() {
        mListView = findViewById(R.id.list);
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_connection);
        super.onCreate(savedInstanceState);
        Bundle extras = getIntent().getExtras();
        name = extras.getString("name");
        Log.d(TAG, "onCreate: Started.");
        getData();
        invalidateOptionsMenu();
    }
}



