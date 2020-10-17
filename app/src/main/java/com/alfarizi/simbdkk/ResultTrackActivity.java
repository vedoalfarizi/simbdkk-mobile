package com.alfarizi.simbdkk;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class ResultTrackActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result_track);

        TextView tvId =  findViewById(R.id.tv_id);
        TextView tvTitle =  findViewById(R.id.tv_title);
        TextView tvStatus =  findViewById(R.id.tv_status);
        TextView tvUpdatedAt =  findViewById(R.id.tv_updatedAt);
        Button btnBack = findViewById(R.id.btn_back);

        Intent intent = getIntent();
        if(intent != null){
            tvId.setText(intent.getStringExtra("id"));
            tvTitle.setText(intent.getStringExtra("title"));
            tvStatus.setText(intent.getStringExtra("status"));
            tvUpdatedAt.setText(intent.getStringExtra("updatedAt"));
        }

        btnBack.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btn_back:
                finish();
                break;
            case R.id.btn_history:
                break;
        }
    }
}