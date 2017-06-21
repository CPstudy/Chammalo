package com.guk2zzada.chammalo;

import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class SettingActivity extends AppCompatActivity {
    // 담배 가격을 설정할 수 있는 액티비티

    private SharedPreferences mPref;
    private SharedPreferences.Editor mPrefEdit;

    EditText edtPrice;
    Button btnSubmit;

    int iPrice = 4500;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);

        mPref = getSharedPreferences("Pref1", 0);
        mPrefEdit = mPref.edit();

        iPrice = mPref.getInt("iPrice", 4500);

        edtPrice = (EditText) findViewById(R.id.edtPrice);
        btnSubmit = (Button) findViewById(R.id.btnSubmit);

        edtPrice.setText(String.valueOf(iPrice));

        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPrefEdit.putInt("iPrice", Integer.parseInt(edtPrice.getText().toString()));
                mPrefEdit.apply();
                mPrefEdit.commit();
                finish();
            }
        });
    }
}
