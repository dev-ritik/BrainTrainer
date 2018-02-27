package com.example.android.braintrainer;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.os.CountDownTimer;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.GridLayout;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Random;
import java.util.Set;

public class MainActivity extends AppCompatActivity {

    Button go;
    ArrayList<Integer> answers = new ArrayList<Integer>();
    int locationCorrect, correctResponse = 0, negativeAnswer = 0, numberOfOperatoon, noOfQues = 0, symbolSelect, answer, randomDirection, finalScore = 0;
    Button button0, button1, button2, button3;
    LinearLayout mainScreen;
    TextView answerResponse, points, textQues, timer;
    LinearLayout gameTypeMenu;
    GridLayout grid;
    MediaPlayer mplayer;
    String operationSymbol = "";
    boolean add, sub, into;
    boolean addSelected, subSelected, multiSelected;
    Random rand;
    Button submit;


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.include, menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        super.onOptionsItemSelected(item);
        switch (item.getItemId()) {
            case R.id.rules:
                Toast.makeText(MainActivity.this, "ANSWER AS MUCH AS YOU CAN\n1. Total time :30s\n2. correct answer :+3\n3. Wrong answer :-2\n4. Marks per attempt :+1", Toast.LENGTH_LONG).show();
                return true;
            case R.id.compete:
                Intent i = new Intent(MainActivity.this, ScoresActivity.class);
                startActivity(i);
                return true;
            case R.id.reset:

                new AlertDialog.Builder(this)
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .setTitle("Caution")
                        .setMessage("All records will be deleted")
                        .setPositiveButton("delete", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                ScoresActivity.name.clear();
                                ScoresActivity.score.clear();
                                System.out.println(ScoresActivity.name);
                                ScoresActivity.scoreAdapter.notifyDataSetChanged();

                                SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences("com.example.android.braintrainer", Context.MODE_PRIVATE);

//                                Set<String> setName = new LinkedHashSet<>(ScoresActivity.name);
//                                Set<String> setScore = new LinkedHashSet<>(ScoresActivity.score);

                                try {
                                    sharedPreferences.edit().putString("score", ObjectSerializer.serialize(ScoresActivity.name)).apply();
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }

                                try {
                                    sharedPreferences.edit().putString("score1", ObjectSerializer.serialize(ScoresActivity.score)).apply();
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }


//                                    sharedPreferences.edit().putStringSet("score", setName).apply();
//                                sharedPreferences.edit().putStringSet("score1", setScore).apply();

                                Toast.makeText(MainActivity.this, "done", Toast.LENGTH_SHORT).show();
                            }
                        })

                        .setNegativeButton("back", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Toast.makeText(MainActivity.this, "Data saved", Toast.LENGTH_LONG).show();
                            }
                        })
                        .show();

                return true;
            case R.id.version:
                Toast.makeText(MainActivity.this, "1.3\n Made by Ritik kumar", Toast.LENGTH_SHORT).show();
                return true;
            default:
                return false;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        go = (Button) findViewById(R.id.goButton);
        textQues = (TextView) findViewById(R.id.question);
        points = (TextView) findViewById(R.id.points);
        timer = (TextView) findViewById(R.id.timer);
        answerResponse = (TextView) findViewById(R.id.answerText);
        gameTypeMenu = (LinearLayout) findViewById(R.id.gameType);

        button0 = (Button) findViewById(R.id.button00);
        button1 = (Button) findViewById(R.id.button01);
        button2 = (Button) findViewById(R.id.button10);
        button3 = (Button) findViewById(R.id.button11);
        mainScreen = (LinearLayout) findViewById(R.id.linearLayout);

        grid = (GridLayout) findViewById(R.id.grid);

        rand = new Random();

        submit = (Button) findViewById(R.id.submit);

    }


    public void chooseOperation(int a, int b) {

        numberOfOperatoon = 0;
        if (add) numberOfOperatoon++;
        if (sub) numberOfOperatoon++;
        if (into) numberOfOperatoon++;

        symbolSelect = rand.nextInt(numberOfOperatoon);
        if (add) {
            if (symbolSelect == 0) {
                addSelected = true;
            }
            symbolSelect--;
        }
        if (sub) {
            if (symbolSelect == 0) {
                subSelected = true;
            }
            symbolSelect--;
        }
        if (into) {
            if (symbolSelect == 0) {
                multiSelected = true;
            }
        }

    }

    public void chooseAnswer(View view) {
        if (view.getTag().toString().equals(Integer.toString(locationCorrect))) {
            //correct chosed;
            answerResponse.setText("Correct! :-)");
            correctResponse++;
            noOfQues++;
            mplayer = MediaPlayer.create(getApplicationContext(), R.raw.correct);
            mplayer.start();
            mplayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                @Override
                public void onCompletion(MediaPlayer mp) {
                    mp.stop();
                    mp.release();

                }
            });
        } else {
            answerResponse.setText("Wrong :-(");
            negativeAnswer--;
            noOfQues++;
            mplayer = MediaPlayer.create(getApplicationContext(), R.raw.wrong);
            mplayer.start();
            mplayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                @Override
                public void onCompletion(MediaPlayer mp) {
                    mp.stop();
                    mp.release();

                }
            });
        }
        points.setText(correctResponse + "/" + negativeAnswer);
        generateQues();

    }

    public void generateQues() {

        addSelected = false;
        subSelected = false;
        multiSelected = false;

        int a = rand.nextInt(21);
        int b = rand.nextInt(21);

        chooseOperation(a, b);

        if (addSelected) {
            answer = a + b;
            operationSymbol = "+";
            randomDirection = 41;
        }
        if (subSelected) {
            if (a < b) {
                a = a + b;
                b = a - b;
                a = a - b;
            }
            answer = a - b;
            operationSymbol = "-";
            randomDirection = 21;
        }
        if (multiSelected) {
            answer = a * b;
            operationSymbol = "x";
            randomDirection = 401;
        }
        textQues.setText(a + operationSymbol + b + " = ?");

        locationCorrect = rand.nextInt(4);

        int incorrectnswer;


        answers.clear();

        for (int i = 0; i < 4; i++) {

            if (i == locationCorrect) {
                answers.add(answer);
            } else {

                incorrectnswer = rand.nextInt(randomDirection);
                while (incorrectnswer == answer) {
                    incorrectnswer = rand.nextInt(randomDirection);
                }
                answers.add(incorrectnswer);
            }
        }

        button0.setText(Integer.toString(answers.get(0)));
        button1.setText(Integer.toString(answers.get(1)));
        button2.setText(Integer.toString(answers.get(2)));
        button3.setText(Integer.toString(answers.get(3)));

    }


    public void startTimer() {
        new CountDownTimer(30100, 1000) {

            @Override
            public void onTick(long millisUntilFinished) {
                timer.setText(String.valueOf(millisUntilFinished / 1000) + "s");
                if ((int) (millisUntilFinished / 1000) == 9) {
                    mplayer = MediaPlayer.create(getApplicationContext(), R.raw.sound);
                    mplayer.start();
                }
                if (millisUntilFinished % 1000 == 0) mplayer.release();
            }

            @Override
            public void onFinish() {
                timer.setText("0s");
                finalScore = (correctResponse * 3) + noOfQues + (negativeAnswer * 2);
                answerResponse.setText("" + finalScore);
                go.setText("Play Again :-)");
                go.setVisibility(View.VISIBLE);
                gameTypeMenu.setVisibility(View.VISIBLE);
                grid.setVisibility(View.INVISIBLE);
                textQues.setVisibility(View.INVISIBLE);
                submit.setVisibility(View.VISIBLE);
                mplayer.release();
                //pending

                submit.setVisibility(View.VISIBLE);

            }
        }.start();
        generateQues();

    }


    public void start(View view) {
        //gameTypeMenu.setVisibility(View.INVISIBLE);
        add = ((CheckBox) findViewById(R.id.plus)).isChecked();
        sub = ((CheckBox) findViewById(R.id.minus)).isChecked();
        into = ((CheckBox) findViewById(R.id.multi)).isChecked();

        if (ScoresActivity.newName == null) {
            Toast.makeText(MainActivity.this, "Please enter your name in the compete menu", Toast.LENGTH_LONG).show();
        } else {
            if (!add && !sub && !into) {
                Toast.makeText(MainActivity.this, "please select an operation", Toast.LENGTH_SHORT).show();
            } else {
                go.setVisibility(view.INVISIBLE);

                correctResponse = 0;
                negativeAnswer = 0;
                noOfQues = 0;
                startTimer();
                mainScreen.setVisibility(View.VISIBLE);
                grid.setVisibility(View.VISIBLE);
                textQues.setVisibility(View.VISIBLE);
                gameTypeMenu.setVisibility(View.INVISIBLE);
                submit.setVisibility(View.INVISIBLE);
                System.out.println(ScoresActivity.name);
                System.out.println(ScoresActivity.score);
                finalScore = 0;
            }

        }


    }

    public void submitScore(View view) {
        SharedPreferences names = getApplicationContext().getSharedPreferences("com.example.android.braintrainer", Context.MODE_PRIVATE);
        SharedPreferences score = getApplicationContext().getSharedPreferences("com.example.android.braintrainer", Context.MODE_PRIVATE);

        ScoresActivity.score.add(Integer.toString(finalScore));
        ScoresActivity.name.add(ScoresActivity.newName);

        System.out.println(ScoresActivity.name);
        System.out.println(ScoresActivity.score);

        ScoresActivity.scoreAdapter.notifyDataSetChanged();


        try {
            names.edit().putString("score", ObjectSerializer.serialize(ScoresActivity.name)).apply();
        } catch (IOException e) {
            e.printStackTrace();

        }
        try {
            score.edit().putString("score1", ObjectSerializer.serialize(ScoresActivity.score)).apply();
        } catch (IOException e) {
            e.printStackTrace();

        }

        submit.setVisibility(View.INVISIBLE);

    }
}