package com.example.android.braintrainer;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Set;

public class ScoresActivity extends AppCompatActivity {

    SharedPreferences storeName;
    SharedPreferences storeScore;

    static String newName=null;

    EditText edit;

    static ArrayList<String> name;
    static ArrayList<String> score;

    static MAdapter scoreAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scores);

        name = new ArrayList<>();
        score = new ArrayList<>();

        this.getWindow().setBackgroundDrawableResource(R.drawable.back1) ;

        storeName = this.getSharedPreferences("com.example.android.braintrainer", Context.MODE_PRIVATE);
        storeScore = this.getSharedPreferences("com.example.android.braintrainer", Context.MODE_PRIVATE);

        try {
            name=(ArrayList<String>)ObjectSerializer.deserialize(storeName.getString("score",ObjectSerializer.serialize(new ArrayList<String>())));
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            score=(ArrayList<String>)ObjectSerializer.deserialize(storeScore.getString("score1",ObjectSerializer.serialize(new ArrayList<String>())));
        } catch (IOException e) {
            e.printStackTrace();
        }

        edit = (EditText) findViewById(R.id.name);

        ListView rootView = (ListView) findViewById(R.id.list);
          scoreAdapter = new MAdapter(this,name,score);

        rootView.setAdapter(scoreAdapter);

    }

    public void saveName(View view){

        newName=edit.getText().toString();

        InputMethodManager mgr=(InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        mgr.hideSoftInputFromWindow(edit.getWindowToken(),0);

        Intent i = new Intent(ScoresActivity.this, MainActivity.class);
        startActivity(i);


    }
}
