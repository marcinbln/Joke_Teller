package com.example.androidjoker;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class JokerActivity extends AppCompatActivity {

    public static String INTENT_JOKE_EXTRA = "joke_extra";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_android_joker);

        Intent intent = getIntent();
        String joke = intent.getStringExtra(INTENT_JOKE_EXTRA);
        TextView tv = (findViewById(R.id.joke_text_view));

        if (joke != null && joke.length() != 0) {
            tv.setText(joke);
        }


    }
}
