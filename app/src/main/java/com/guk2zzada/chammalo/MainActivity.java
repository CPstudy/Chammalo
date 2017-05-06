package com.guk2zzada.chammalo;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.ColorMatrix;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

public class MainActivity extends Activity {

    TextView txtDate;

    String strName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Intent intent = getIntent();
        strName = intent.getStringExtra("strName");

        TextView txtTitle = (TextView) findViewById(R.id.txtTitle);
        txtTitle.setText(strName + "님");

        txtDate = (TextView) findViewById(R.id.txtDate);
        txtDate.setText("250");
    }
}
