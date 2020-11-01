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

        TextView tvValidation = findViewById(R.id.tv_validation);
        TextView tvInfo = findViewById(R.id.tv_info);
        TextView tvDelegation = findViewById(R.id.tv_delegation);

        Button btnBack = findViewById(R.id.btn_back);

        Intent intent = getIntent();
        if(intent != null){
            tvValidation.setText(intent.getStringExtra("validation"));
            tvInfo.setText(intent.getStringExtra("info"));
            tvDelegation.setText(intent.getStringExtra("delegate"));
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