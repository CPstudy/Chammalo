package com.guk2zzada.chammalo;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class BoardContentActivity extends Activity {

    TextView txtTitle;
    TextView txtContent;
    TextView txtWriter;
    TextView txtTime;

    String strTitle, strTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_board_content);

        txtTitle = (TextView) findViewById(R.id.txtTitle);
        txtContent = (TextView) findViewById(R.id.txtContent);
        txtWriter = (TextView) findViewById(R.id.txtWriter);
        txtTime = (TextView) findViewById(R.id.txtTime);

        Intent intent = getIntent();
        strTitle = intent.getStringExtra("strTitle");
        strTime = intent.getStringExtra("strTime");

        txtTitle.setText(strTitle);
        txtTime.setText(strTime);
    }
}
