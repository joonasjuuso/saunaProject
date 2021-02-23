package saunaprojekti.saunaprojekti;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import saunaprojekti.saunaprojekti.ui.login.LoginActivity;


public class aboutActivity extends AppCompatActivity {

        private String name;

    @Override
    public boolean onCreateOptionsMenu(Menu menu4) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_about, menu4);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item4) { switch(item4.getItemId()) {
        case R.id.table:
            Intent intent = new Intent(getApplicationContext(), connectionActivity.class);
            intent.putExtra("name",name);
            startActivity(intent);
            break;
        case R.id.mainmenu:
            Intent mainmenuIntent = new Intent(getApplicationContext(),MainActivity.class);
            mainmenuIntent.putExtra("name",name);
            startActivity(mainmenuIntent);
            break;
        case R.id.reset:
            return(true);
        case R.id.about:
            return(true);
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
        return(super.onOptionsItemSelected(item4));
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);
        Log.d("aboutActivity","aboutactivity");
        Bundle extras = getIntent().getExtras();
        name = extras.getString("name");
        invalidateOptionsMenu();
    }
}