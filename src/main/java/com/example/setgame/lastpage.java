package com.example.setgame;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class lastpage extends AppCompatActivity {
    Button backbutton ;
  TextView winnertext;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lastpage);
        backbutton=findViewById(R.id.btnback);
        winnertext=findViewById(R.id.winnertxt);

        Bundle bundle = getIntent().getExtras();
        String value=bundle.getString("won");

        winnertext.setText("Game won by "+ value);

        backbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent it = new Intent(lastpage.this,MainActivity.class);
                startActivity(it);
            }
        });


    }
}