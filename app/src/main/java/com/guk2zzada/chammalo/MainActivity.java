package com.guk2zzada.chammalo;

import android.app.ActionBar;
import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.ColorMatrix;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageButton;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.Calendar;
import java.util.Locale;

public class MainActivity extends Activity{

    Calendar cal = Calendar.getInstance();
    Calendar calToday = Calendar.getInstance();
    Calendar calStartDate = Calendar.getInstance();
    TextView txtDate, txtStartDate;
    TextView txtNumSmoke, txtNumDrink;
    TextView txtPercent;
    ImageButton btnSmoke, btnDrink;
    Button btnChallenge, btnBoard;
    GraphView graphView;

    final int LIFEMALE = 77;
    int LIFEFEMALE = 84;
    int iGender = 0;
    int iSmoke = 0;
    int iDrink = 0;
    int iLife = 0;
    int iAverage = 0;
    int cntSmoke = 1;
    int cntDrink = 1;

    int year;
    int month;
    int day;

    String strName;
    String strToday;
    String strStartDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Intent intent = getIntent();
        iGender = intent.getIntExtra("iGender", 0);
        strName = intent.getStringExtra("strName");

        TextView txtTitle = (TextView) findViewById(R.id.txtTitle);
        txtTitle.setText(strName + "님");

        txtDate = (TextView) findViewById(R.id.txtDate);
        txtStartDate = (TextView) findViewById(R.id.txtStartDate);
        txtNumSmoke = (TextView) findViewById(R.id.txtNumSmoke);
        txtNumDrink = (TextView) findViewById(R.id.txtNumDrink);
        btnSmoke = (ImageButton) findViewById(R.id.btnSmoke);
        btnDrink = (ImageButton) findViewById(R.id.btnDrink);
        btnChallenge = (Button) findViewById(R.id.btnChallenge);
        btnBoard = (Button) findViewById(R.id.btnBoard);
        graphView = (GraphView) findViewById(R.id.graphView);
        txtPercent = (TextView) findViewById(R.id.txtPercent);

        year = cal.get(Calendar.YEAR);
        month = cal.get(Calendar.MONTH) + 1;
        day = cal.get(Calendar.DATE);

        strToday = String.format(Locale.KOREA, "%04d년 %02d월 %02d일", year, month, day);
        txtStartDate.setText(strToday);

        calToday.set(year, month, day);
        calStartDate.set(2015, 6, 14);

        long lToday = calToday.getTimeInMillis();
        long lStartDate = calStartDate.getTimeInMillis();
        long result = lToday - lStartDate;

        txtDate.setText(String.valueOf(result / (24 * 60 * 60 * 1000)));

        if(iGender == 0) {
            iLife = LIFEMALE;
            iAverage = LIFEMALE;
            graphView.setGraph(iLife, LIFEMALE);
        } else {
            iLife = LIFEFEMALE;
            iAverage = LIFEFEMALE;
        }
        txtPercent.setText(iLife + "/" + iAverage + "세");
        graphView.setGraph(iLife, iAverage);

        btnSmoke.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertSmokeDialog();
            }
        });

        btnDrink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDrinkDialog();
            }
        });

        btnChallenge.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), ChallengeActivity.class);
                startActivity(intent);
            }
        });

        btnBoard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), BoardListActivity.class);
                startActivity(intent);
            }
        });
    }

    private void alertSmokeDialog() {
        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.setContentView(R.layout.dialog_smoke);

        final DatePicker datePicker = (DatePicker) dialog.findViewById(R.id.datePicker);
        Button dbtnOk = (Button) dialog.findViewById(R.id.dbtnOk);
        Button dbtnCancel = (Button) dialog.findViewById(R.id.dbtnCancel);
        Button dbtnPlus = (Button) dialog.findViewById(R.id.dbtnPlus);
        Button dbtnMinus = (Button) dialog.findViewById(R.id.dbtnMinus);
        final TextView dtxtNum = (TextView) dialog.findViewById(R.id.dtxtNum);

        dbtnMinus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(cntSmoke != 1) {
                    cntSmoke--;
                    dtxtNum.setText(String.valueOf(cntSmoke));
                }
            }
        });

        dbtnPlus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cntSmoke++;
                dtxtNum.setText(String.valueOf(cntSmoke));
            }
        });

        dbtnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        dbtnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                iSmoke = iSmoke + cntSmoke;
                txtNumSmoke.setText(iSmoke + "개비");
                cntSmoke = 1;
                iLife = iAverage - (iSmoke / 20);
                txtPercent.setText(iLife + "/" + iAverage + "세");
                graphView.setGraph(iLife, iAverage);
                graphView.invalidate();
                dialog.dismiss();
            }
        });
        dialog.show();
    }

    private void alertDrinkDialog() {
        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.setContentView(R.layout.dialog_drink);

        final DatePicker datePicker = (DatePicker) dialog.findViewById(R.id.datePicker);
        Button dbtnOk = (Button) dialog.findViewById(R.id.dbtnOk);
        Button dbtnCancel = (Button) dialog.findViewById(R.id.dbtnCancel);
        Button dbtnPlus = (Button) dialog.findViewById(R.id.dbtnPlus);
        Button dbtnMinus = (Button) dialog.findViewById(R.id.dbtnMinus);
        final TextView dtxtNum = (TextView) dialog.findViewById(R.id.dtxtNum);

        dbtnMinus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(cntDrink != 1) {
                    cntDrink--;
                    dtxtNum.setText(String.valueOf(cntDrink));
                }
            }
        });

        dbtnPlus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cntDrink++;
                dtxtNum.setText(String.valueOf(cntDrink));
            }
        });

        dbtnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        dbtnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                iDrink = iDrink + cntDrink;
                txtNumDrink.setText(iDrink + "잔");
                cntDrink = 1;
                dialog.dismiss();
            }
        });
        dialog.show();
    }
}
