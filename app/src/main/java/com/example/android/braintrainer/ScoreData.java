package com.example.android.braintrainer;

/**
 * Created by ritik on 24-12-2017.
 */

public class ScoreData {

    private String mName;
    private String mScore;

    public ScoreData(String name,String data){
        mName=name;
        mScore=data;
    }

    public String getName(){
        return mName;
    }

    public String getScore(){
        return mScore;
    }
}
