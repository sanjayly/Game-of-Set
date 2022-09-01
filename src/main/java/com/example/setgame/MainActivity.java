package com.example.setgame;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    Button buttonClk;
    EditText user;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        buttonClk=findViewById(R.id.btnclick2);
        user=findViewById(R.id.username);
        //instructions = findViewById(R.id.instruction);
        buttonClk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String value = user.getText().toString();
                if(!(value.toString().trim().equals(""))) {
                    Bundle bundle = new Bundle();
                    bundle.putString("Hi", value);
                    Intent intent = new Intent(MainActivity.this, gameset.class);
                    intent.putExtras(bundle);
                    startActivity(intent);
                } else {
                    Toast.makeText(getApplicationContext(), "Enter your name", Toast.LENGTH_LONG).show();
                }
            }
        });

      /*  instructions.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent instructionIt = new Intent(MainActivity.this, com.example.setgame.instructions.class);
                startActivity(instructionIt);
            }
        });*/


    }
}