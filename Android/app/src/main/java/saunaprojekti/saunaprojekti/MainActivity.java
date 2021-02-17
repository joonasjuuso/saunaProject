package saunaprojekti.saunaprojekti;

import androidx.appcompat.app.AppCompatActivity;
import java.net.HttpURLConnection;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.net.URL;

public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        Button tauluButton = findViewById(R.id.taulukkoButton);
        TextView viewName = findViewById(R.id.nameView);
        Button graphButton = findViewById(R.id.graphButton);
        Bundle extras = getIntent().getExtras();
        if(extras != null) {
            String name = extras.getString("name");
            viewName.setText("Hei "+ name +"!");
        }

        tauluButton.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               Intent intent = new Intent(getApplicationContext(), connectionActivity.class);
               startActivity(intent);
           }
        });
        graphButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent graphIntent = new Intent(getApplicationContext(),kuvaajaActivity.class);
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
    public boolean onOptionsItemSelected(MenuItem item) { switch(item.getItemId()) {
        case R.id.table:
            Intent intent = new Intent(getApplicationContext(), connectionActivity.class);
            startActivity(intent);
            return(true);
        case R.id.reset:
            //add the function to perform here
            return(true);
        case R.id.about:
            //add the function to perform here
            return(true);
        case R.id.exit:
            //add the function to perform here
            return(true);
    }
        return(super.onOptionsItemSelected(item));
    }
}
