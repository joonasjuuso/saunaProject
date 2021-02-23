package saunaprojekti.saunaprojekti;

import androidx.appcompat.app.AppCompatActivity;
import java.net.HttpURLConnection;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.net.URL;

import saunaprojekti.saunaprojekti.ui.login.LoginActivity;


public class MainActivity extends AppCompatActivity {

    String name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        Log.d("MainACtivitiy","MainActivity");
        Button tauluButton = findViewById(R.id.taulukkoButton);
        TextView viewName = findViewById(R.id.nameView);
        Button graphButton = findViewById(R.id.graphButton);
        invalidateOptionsMenu();
        Bundle extras = getIntent().getExtras();
        if(extras != null) {
            name = extras.getString("name");
            viewName.setText("Hei "+ name +"!");
        }

        tauluButton.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               Intent intent = new Intent(getApplicationContext(), connectionActivity.class);
               intent.putExtra("name",name);
               startActivity(intent);
           }
        });
        graphButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent graphIntent = new Intent(getApplicationContext(),kuvaajaActivity.class);
                graphIntent.putExtra("name",name);
                startActivity(graphIntent);
            }
        });
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_table, menu);
        return true;
    }
    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        MenuItem item = menu.findItem(R.id.logout);
        if (item.getTitle().equals("Logout")) {
            item.setTitle("Log out from " + name);
        }
        return super.onPrepareOptionsMenu(menu);
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) { switch(item.getItemId()) {
        case R.id.table:
            Intent intentTable = new Intent(this, connectionActivity.class);
            intentTable.putExtra("name",name);
            startActivity(intentTable);
            finish();
            break;
        case R.id.reset:
            Intent graphIntent = new Intent(this,kuvaajaActivity.class);
            graphIntent.putExtra("name",name);
            startActivity(graphIntent);
            finish();
            break;
        case R.id.about:
            Intent aboutIntent = new Intent(getApplicationContext(),aboutActivity.class);
            aboutIntent.putExtra("name",name);
            startActivity(aboutIntent);
            finish();
            break;
        case R.id.logout:
            Intent logoutIntent = new Intent(this, LoginActivity.class);
            startActivity(logoutIntent);
            finish();
            break;
        case R.id.exit:
            finish();
    }
        return(super.onOptionsItemSelected(item));
    }
}
