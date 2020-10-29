package com.alfarizi.simbdkk;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class VerificationResultActivity extends AppCompatActivity implements View.OnClickListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verification_result);

        EditText etValidation = findViewById(R.id.et_validation);
        Button btnBack = findViewById(R.id.btn_back);

        Intent intent = getIntent();
        if(intent != null){
            etValidation.setText(intent.getStringExtra("validation"));
        }

        btnBack.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.btn_back) {
            finish();
        }
    }
}