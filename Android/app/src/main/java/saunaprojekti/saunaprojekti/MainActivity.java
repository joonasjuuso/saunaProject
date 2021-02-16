package saunaprojekti.saunaprojekti;

import androidx.appcompat.app.AppCompatActivity;
import java.net.HttpURLConnection;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
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
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }
}
