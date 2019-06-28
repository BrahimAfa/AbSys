package com.ofppt.absys.Main.TestActivities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;

import com.ofppt.absys.Main.Models.FILIERES;
import com.ofppt.absys.Main.Models.FORMATEURS;
import com.ofppt.absys.Main.Models.GROUPES;
import com.ofppt.absys.R;

public class TestTables extends AppCompatActivity {
    TextView txtformateur;
    TextView txtgroup;
    TextView txtfilier;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_tables);
        txtformateur= findViewById(R.id.txtformateur);
        txtgroup= findViewById(R.id.txtgroup);
        txtfilier= findViewById(R.id.txtfilier);

        txtformateur.setText(String.valueOf(FORMATEURS.getAll().size()));
        txtgroup.setText(String.valueOf(GROUPES.getAll().size()));
        txtfilier.setText(String.valueOf(FILIERES.getAll().size()));
    }
}
