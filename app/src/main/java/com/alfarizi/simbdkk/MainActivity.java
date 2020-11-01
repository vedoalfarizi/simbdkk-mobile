package com.alfarizi.simbdkk;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Toast;

import com.alfarizi.simbdkk.api.ApiMail;
import com.alfarizi.simbdkk.model.Mail;
import com.alfarizi.simbdkk.model.MailVerification;
import com.alfarizi.simbdkk.service.ApiClient;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener{

    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        loadFragment(new SearchFragment());
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation_main);
        bottomNavigationView.setOnNavigationItemSelectedListener(this);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        Fragment selectedFragment = null;
        switch (menuItem.getItemId()){
            case R.id.nav_search:
                selectedFragment = new SearchFragment();
                break;
            case R.id.nav_hidden_option:
                IntentIntegrator integrator = new IntentIntegrator(this);

                integrator.setOrientationLocked(false);
                integrator.setPrompt("Verifikasi Tandatangan digital");
                integrator.setBeepEnabled(false);
                integrator.setDesiredBarcodeFormats(IntentIntegrator.QR_CODE);

                integrator.initiateScan();
                break;
            case R.id.nav_history:
                selectedFragment = new HistoryFragment();
                break;
        }

        return loadFragment(selectedFragment);
    }

    private boolean loadFragment(Fragment fragment){
        if(fragment != null){
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragment_container, fragment)
                    .commit();
            return true;
        }
        return false;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if(result != null){
            if(result.getContents() == null){
                Toast.makeText(this, "Cancelled", Toast.LENGTH_SHORT).show();
            }else{
                progressDialog = new ProgressDialog(this);
                progressDialog.setMessage("Memindai...");
                progressDialog.show();

                ApiMail apiMail = ApiClient.getRetrofitInstance().create(ApiMail.class);
                Call<MailVerification> mailVerificationCall = apiMail.verifiedMail(result.getContents());
                mailVerificationCall.enqueue(new Callback<MailVerification>() {
                    @Override
                    public void onResponse(Call<MailVerification> call, Response<MailVerification> response) {
                        progressDialog.dismiss();
                        if(response.isSuccessful()){
                            verifiedMailHandler(response.body().getMail());
                        }else{
                            try {
                                String error = response.errorBody().string();
                                Log.d("error message", error);

                                JSONObject errObj = new JSONObject(error);

                                String message = errObj.getString("message");
                                Toast.makeText(MainActivity.this, message, Toast.LENGTH_SHORT).show();
                            } catch (IOException | JSONException e) {
                                Toast.makeText(MainActivity.this, "Something went wrong", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<MailVerification> call, Throwable t) {
                        progressDialog.dismiss();
                        Toast.makeText(MainActivity.this, "Searching fail", Toast.LENGTH_SHORT).show();
                        Log.e("onFailure", t.toString());
                    }
                });
            }
        }else{
            super.onActivityResult(requestCode, resultCode, data);
        }
    }

    private void verifiedMailHandler(Mail mail){
        String delegate = TextUtils.join("\n", mail.getDelegation());

        Intent intent = new Intent(this, VerificationResultActivity.class);
        intent.putExtra("validation", mail.getValidMessage());
        intent.putExtra("info", mail.getInfo());
        intent.putExtra("delegate", delegate);

        startActivity(intent);
    }
}