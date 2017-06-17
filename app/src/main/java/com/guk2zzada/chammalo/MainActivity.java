package com.guk2zzada.chammalo;

import android.app.ActionBar;
import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.ColorMatrix;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.StrictMode;
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

import org.json.JSONObject;
import org.w3c.dom.Text;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Calendar;
import java.util.Locale;

public class MainActivity extends Activity{

    Calendar cal = Calendar.getInstance();
    Calendar calToday = Calendar.getInstance();
    Calendar calStartDate = Calendar.getInstance();
    Calendar calStartSmoke = Calendar.getInstance();
    TextView txtDate, txtStartDate;
    TextView txtNumSmoke, txtNumDrink;
    TextView txtPercent;
    TextView txtTitle;
    TextView txtSmokingDay;
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

    int tYear;
    int tMonth;
    int tDay;

    String strID;
    String strName;
    String strStartDate;
    String strStartSmoke;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Intent intent = getIntent();
        strID = intent.getStringExtra("strID");
        iGender = intent.getIntExtra("iGender", 0);
        strName = intent.getStringExtra("strName");

        txtTitle = (TextView) findViewById(R.id.txtTitle);

        txtDate = (TextView) findViewById(R.id.txtDate);
        txtStartDate = (TextView) findViewById(R.id.txtStartDate);
        txtNumSmoke = (TextView) findViewById(R.id.txtNumSmoke);
        txtNumDrink = (TextView) findViewById(R.id.txtNumDrink);
        txtSmokingDay = (TextView) findViewById(R.id.txtSmokingDay);
        btnSmoke = (ImageButton) findViewById(R.id.btnSmoke);
        btnDrink = (ImageButton) findViewById(R.id.btnDrink);
        btnChallenge = (Button) findViewById(R.id.btnChallenge);
        btnBoard = (Button) findViewById(R.id.btnBoard);
        graphView = (GraphView) findViewById(R.id.graphView);
        txtPercent = (TextView) findViewById(R.id.txtPercent);

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        getInfo();
        init();
        smokeDate();

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

    private void init() {
        year = cal.get(Calendar.YEAR);
        month = cal.get(Calendar.MONTH) + 1;
        day = cal.get(Calendar.DATE);

        txtStartDate.setText(strStartDate);

        calToday.set(year, month, day);

        tYear = Integer.parseInt(strStartDate.split("-")[0]);
        tMonth = Integer.parseInt(strStartDate.split("-")[1]);
        tDay = Integer.parseInt(strStartDate.split("-")[2]);

        Log.d("day", tYear + "년" + tMonth + "월" + tDay + "일");
        calStartDate.set(tYear, tMonth, tDay);

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
        iLife = iAverage - (iSmoke / 20);
        txtPercent.setText(iLife + "/" + iAverage + "세");
        graphView.setGraph(iLife, iAverage);
    }

    private void smokeDate() {
        // 지금까지 흡연해온 날짜 계산
        int sYear;
        int sMonth;
        int sDay;

        sYear = Integer.parseInt(strStartSmoke.split("-")[0]);
        sMonth = Integer.parseInt(strStartSmoke.split("-")[1]);
        sDay = Integer.parseInt(strStartSmoke.split("-")[2]);

        calStartSmoke.set(sYear, sMonth, sDay);
        calStartDate.set(tYear, tMonth, tDay);
        long lStartSmoke = calStartSmoke.getTimeInMillis();
        long lStartDate = calStartDate.getTimeInMillis();
        long result = lStartDate - lStartSmoke;

        txtSmokingDay.setText((result / (24 * 60 * 60 * 1000)) + "일");
    }

    private void getInfo() {
        String result = "";
        try {
            URL url = new URL("http://sunsiri.cafe24.com/getmember-android.jsp");
            HttpURLConnection httpURLcon = (HttpURLConnection)url.openConnection();
            httpURLcon.setDefaultUseCaches(false);
            httpURLcon.setDoInput(true);
            httpURLcon.setDoOutput(true);
            httpURLcon.setRequestMethod("POST");
            httpURLcon.setRequestProperty("content-type", "application/x-www-form-urlencoded");
            StringBuffer sb = new StringBuffer();
            sb.append("id").append("=").append(strID);
            PrintWriter pw = new PrintWriter(new OutputStreamWriter(httpURLcon.getOutputStream(), "UTF-8"));
            pw.write(sb.toString());
            pw.flush();
            BufferedReader bf = new BufferedReader(new InputStreamReader(httpURLcon.getInputStream(), "UTF-8"));
            String line;
            while((line = bf.readLine()) != null)
            {
                result += line;
            }

            // 서버에서 받아온 결과문을 확인합니다.
            System.out.println(result);

            JSONObject jsonObject = new JSONObject(result);
            strName = jsonObject.getString("name");
            iGender = jsonObject.getInt("gender");
            strStartDate = jsonObject.getString("start_project");
            strStartSmoke = jsonObject.getString("start_smoke");
            iSmoke = jsonObject.getInt("smoke");
            iDrink = jsonObject.getInt("drink");
            txtTitle.setText(strName + "님");
            txtNumSmoke.setText(iSmoke + "개비");
            txtNumDrink.setText(iDrink + "잔");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void updateData(int smoke, int drink) {
        String result = "";
        try {
            URL url = new URL("http://sunsiri.cafe24.com/updatenumber-android.jsp");
            HttpURLConnection httpURLcon = (HttpURLConnection)url.openConnection();
            httpURLcon.setDefaultUseCaches(false);
            httpURLcon.setDoInput(true);
            httpURLcon.setDoOutput(true);
            httpURLcon.setRequestMethod("POST");
            httpURLcon.setRequestProperty("content-type", "application/x-www-form-urlencoded");
            StringBuffer sb = new StringBuffer();
            sb.append("id").append("=").append(strID).append("&");
            sb.append("smoke").append("=").append(smoke).append("&");
            sb.append("drink").append("=").append(drink);
            PrintWriter pw = new PrintWriter(new OutputStreamWriter(httpURLcon.getOutputStream(), "UTF-8"));
            pw.write(sb.toString());
            pw.flush();
            BufferedReader bf = new BufferedReader(new InputStreamReader(httpURLcon.getInputStream(), "UTF-8"));
            String line;
            while((line = bf.readLine()) != null)
            {
                result += line;
            }

            // 서버에서 받아온 결과문을 확인합니다.
            System.out.println(result);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void alertSmokeDialog() {
        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.setContentView(R.layout.dialog_smoke);

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
                updateData(iSmoke, iDrink);
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
                updateData(iSmoke, iDrink);
                dialog.dismiss();
            }
        });
        dialog.show();
    }
}
