package com.example.android.braintrainer;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ritik on 24-12-2017.
 */

public class MAdapter extends ArrayAdapter<String> {

//        private int mBackgroundColor;
          private List<String> nameArray;

        public MAdapter(@NonNull Context context, @NonNull List<String> nane,List<String> objects) {
            super(context, 0, objects);
//            mBackgroundColor = color;
            nameArray=nane;

            System.out.println(2222);
            System.out.println(nameArray);
            System.out.println(ScoresActivity.score);


        }

        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

            View listItemView = convertView;
            if (listItemView == null) {
                listItemView = LayoutInflater.from(getContext()).inflate(R.layout.activity_list_style, parent, false);
            }

            System.out.println(position+15555);


            String convertScore = getItem(position);
            String convertWord=nameArray.get(position);

            System.out.println(nameArray);
            System.out.println(ScoresActivity.score);

            TextView name = (TextView) listItemView.findViewById(R.id.nameSaved);
            name.setText(convertWord);

            System.out.println(convertWord);
            System.out.println(convertScore);

            TextView score = (TextView) listItemView.findViewById(R.id.scoreSaved);
            score.setText(convertScore);

            System.out.println(position+155);

            TextView sno=(TextView) listItemView.findViewById(R.id.sno);
            sno.setText(position+1+"");

//            View textContainer = listItemView.findViewById(R.id.text);
//            int color = ContextCompat.getColor(getContext(), mBackgroundColor);
//            textContainer.setBackgroundColor(color);
            return listItemView;


        }
    }

