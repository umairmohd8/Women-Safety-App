package com.aar.example.tapholdupbutton;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.telephony.PhoneNumberUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.aar.tapholdupbutton.TapHoldUpButton;

public class MainActivity extends AppCompatActivity {
    String emergency_number;
    EditText phone_i;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final TextView textView = findViewById(R.id.textView);
        TapHoldUpButton btn = findViewById(R.id.btn);
        btn.setOnButtonClickListener(new TapHoldUpButton.OnButtonClickListener() {
            @Override
            public void onLongHoldStart(View v) {
                textView.setText("on long click start");
            }

            @Override
            public void onLongHoldEnd(View v) {
                textView.setText("on long click end");
                Intent myIntent = new Intent(MainActivity.this, TestActivity.class);
                myIntent.putExtra("emergency_contact", emergency_number); //Optional parameters
                MainActivity.this.startActivity(myIntent);


            }

            @Override
            public void onClick(View v) {
                textView.setText("on click");
            }
        });

        Button add_contact = findViewById(R.id.button2);
        phone_i= (EditText) findViewById(R.id.editTextPhone);
        add_contact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(MainActivity.this, "clicked",
                        Toast.LENGTH_LONG).show();
                emergency_number = phone_i.getText().toString();


            }
        });
    }
}
