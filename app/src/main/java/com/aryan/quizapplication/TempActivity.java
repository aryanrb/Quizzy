package com.aryan.quizapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class TempActivity extends AppCompatActivity {

    TextView tvUser, tvPoints;
    Button btnDone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_temp);

        tvUser = findViewById(R.id.tvUser);
        tvPoints = findViewById(R.id.tvFinalPoints);
        btnDone = findViewById(R.id.btnOk);

        Intent intent = getIntent();
        tvPoints.setText("You have got " + intent.getStringExtra("points") + " Points");
        tvUser.setText(intent.getStringExtra("user"));

        btnDone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent goBack = new Intent(TempActivity.this, LoginActivity.class);
                startActivity(goBack);
            }
        });
    }
}
