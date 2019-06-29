package com.ofppt.absys.Main.UI;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import com.ofppt.absys.R;

public class About extends AppCompatActivity {
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menuv0, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            // action with ID action_refresh was selected
            case R.id.action_Setting:
                Intent intent = new Intent(this, Settings.class);
                startActivity(intent);
                break;
            // action with ID action_settings was selected
            default:
                finish();
                break;
        }
        return true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);
        ActionBar bar = getSupportActionBar();
        if (bar !=null) {
            bar.setBackgroundDrawable(getDrawable(R.drawable.actionbar_gradient));
            bar.setDisplayHomeAsUpEnabled(true);
            // bar.setDisplayShowTitleEnabled();

        }
    }
}
