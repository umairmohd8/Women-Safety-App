package com.aar.example.tapholdupbutton;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.util.Timer;

public class PassActivity extends AppCompatActivity {
    Timer T=new Timer();
    int count = 0;
    TextView counter_view;
    Button submit_pin;
    EditText pin;
    int entered_pin;
    int set_pin = 1111;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pass_activity);

        counter_view = findViewById(R.id.counter_txt);


        final CountDownTimer timer = new CountDownTimer(5000, 1000) {

            public void onTick(long millisUntilFinished) {
                counter_view.setText("seconds remaining: " + millisUntilFinished / 1000);
                // logic to set the EditText could go here
            }

            public void onFinish() {
                counter_view.setText("Calling emergency!");

                Intent myIntent = new Intent(PassActivity.this, EmergencyActivity.class);
                //myIntent.putExtra("key", 10); //Optional parameters
                PassActivity.this.startActivity(myIntent);

            }


        };

        timer.start();

        submit_pin = findViewById(R.id.pin_sub_button);
        submit_pin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pin = findViewById(R.id.editTextNumber);
                String value= pin.getText().toString();
                entered_pin  =Integer.parseInt(value);

                if(entered_pin == set_pin){
                    timer.cancel();
                    Toast.makeText(PassActivity.this, "Person safe",
                            Toast.LENGTH_LONG).show();
                    finish();


                }
                else {
                    Toast.makeText(PassActivity.this, "wrong passcode",
                            Toast.LENGTH_LONG).show();
                }
            }
        });
    }
}
