package com.example.harsha1123.capstone.Activities;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.provider.Settings;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;

import com.example.harsha1123.capstone.R;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {


    FirebaseAuth mAuth;
    AdView mAdView;

    SharedPreferences preferences;
    SharedPreferences.Editor editor;
    ProgressDialog dialog;
    AutoCompleteTextView actv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        preferences = getSharedPreferences("pref", Context.MODE_PRIVATE);
        editor = preferences.edit();
        editor.clear();
        editor.apply();

        String[] data ={ "Blush",
                "Bronzer",
                "Eyebrow","Eyeliner","Eyeshadow","Foundation","Lip_liner","Lipstick","Mascara","Nail_polish"};
        ArrayAdapter<String> adapter = new ArrayAdapter<String>
                (this, android.R.layout.select_dialog_item, data);
        actv = (AutoCompleteTextView) findViewById(R.id.search);
        actv.setThreshold(1);//will start working from first character
        actv.setAdapter(adapter);//setting the adapter data into the AutoCompleteTextView
        actv.setTextColor(Color.RED);
        if (isOnline()) {
            mAuth = FirebaseAuth.getInstance();

            MobileAds.initialize(this, getString(R.string.banner_ad_unit_id));
            mAdView = findViewById(R.id.adView);
            AdRequest adRequest = new AdRequest.Builder().build();
            mAdView.loadAd(adRequest);

        } else {
            noOnline();
        }
    }


    public void search(View view) {

        String pName = actv.getText().toString();
        editor.clear();
        editor.putString("pro_key",pName);
        editor.apply();
        Intent intent = new Intent(this, ProductsActivity.class);
        startActivity(intent);
    }


    public boolean isOnline() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);

        return cm.getActiveNetworkInfo() != null;

    }

    private void noOnline() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(R.string.ale_msg);
        builder.setTitle(R.string.ale_ttl);
        builder.setPositiveButton(R.string.gset, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Intent intent = new Intent(Settings.ACTION_SETTINGS);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }
        }).show();
    }

    @Override
    public void onStart() {
        super.onStart();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if (currentUser == null) {
            sentoLogin();
        }
    }

    private void sentoLogin() {
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
        finish();
    }

}
