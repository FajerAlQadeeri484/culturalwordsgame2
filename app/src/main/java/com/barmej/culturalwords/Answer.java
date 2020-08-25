package com.barmej.culturalwords;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class Answer extends AppCompatActivity {

    private TextView text_view_answer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_answer);
        text_view_answer = findViewById(R.id.text_view_answer);
        String answer = getIntent().getStringExtra("theAnswer");
        if (answer != null){
            text_view_answer.setText(answer);
        }
    }

    public void onReturnClicked (View view){
        finish();
    }
}