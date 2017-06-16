package com.guk2zzada.chammalo;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Locale;

public class JoinActivity extends AppCompatActivity {

    EditText edtID;
    EditText edtPW1;
    EditText edtPW2;
    EditText edtName;
    Button btnMale;
    Button btnFemale;
    Button btnBirth;
    Button btnSmoke;
    Button btnSubmit;

    // 생년월일
    int bYear = 2017;
    int bMonth = 1;
    int bDay = 1;

    // 흡연 시작일
    int sYear = 2017;
    int sMonth = 1;
    int sDay = 1;

    int gender = 0;     // 남자: 0, 여자: 1

    String strBirth = "2017-01-01";
    String strSmoke = "2017-01-01";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_join);

        edtID = (EditText) findViewById(R.id.edtID);
        edtPW1 = (EditText) findViewById(R.id.edtPW1);
        edtPW2 = (EditText) findViewById(R.id.edtPW2);
        edtName = (EditText) findViewById(R.id.edtName);
        btnMale = (Button) findViewById(R.id.btnMale);
        btnFemale = (Button) findViewById(R.id.btnFemale);
        btnBirth = (Button) findViewById(R.id.btnBirth);
        btnSmoke = (Button) findViewById(R.id.btnSmoke);
        btnSubmit = (Button) findViewById(R.id.btnSubmit);

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        btnBirth.setText(String.format(Locale.KOREA, "%4d년 %2d월 %2d일", 2017, 1, 1));
        btnSmoke.setText(String.format(Locale.KOREA, "%4d년 %2d월 %2d일", 2017, 1, 1));
        btnMale.setBackgroundResource(R.drawable.btn_white);
        btnFemale.setBackgroundResource(R.drawable.btn_transparent);
        gender = 0;

        btnMale.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnMale.setBackgroundResource(R.drawable.btn_white);
                btnFemale.setBackgroundResource(R.drawable.btn_transparent);
                gender = 0;
            }
        });

        btnFemale.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnMale.setBackgroundResource(R.drawable.btn_transparent);
                btnFemale.setBackgroundResource(R.drawable.btn_white);
                gender = 1;
            }
        });

        btnBirth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog(btnBirth);
            }
        });

        btnSmoke.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog(btnSmoke);
            }
        });

        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!edtID.getText().toString().equals("") && !edtPW1.getText().toString().equals("") && !edtPW1.getText().toString().equals("") && !edtName.getText().toString().equals("")) {
                    String strID = edtID.getText().toString();
                    String strPW1 = edtPW1.getText().toString();
                    String strPW2 = edtPW2.getText().toString();
                    String strName = edtName.getText().toString();

                    if(!strPW1.equals(strPW2)) {
                        showToast("암호가 일치하지 않습니다.");
                    } else {
                        sendData(strID, strPW2, strName);
                    }
                } else {
                    showToast("비어있는 항목이 있습니다.");
                }
            }
        });
    }

    private void sendData(String strID, String strPW, String strName) {
        String result = "0";
        try {
            URL url = new URL("http://sunsiri.cafe24.com/checkid-android.jsp");
            HttpURLConnection httpURLcon = (HttpURLConnection)url.openConnection();
            httpURLcon.setDefaultUseCaches(false);
            httpURLcon.setDoInput(true);
            httpURLcon.setDoOutput(true);
            httpURLcon.setRequestMethod("POST");
            httpURLcon.setRequestProperty("content-type", "application/x-www-form-urlencoded");
            StringBuffer sb = new StringBuffer();
            sb.append("id").append("=").append(strID).append("&");
            sb.append("pw").append("=").append(strPW).append("&");
            sb.append("name").append("=").append(strName).append("&");
            sb.append("gender").append("=").append(gender).append("&");
            sb.append("birth").append("=").append(strBirth).append("&");
            sb.append("smoke").append("=").append(strSmoke);
            PrintWriter pw = new PrintWriter(new OutputStreamWriter(httpURLcon.getOutputStream(), "UTF-8"));
            pw.write(sb.toString());
            pw.flush();
            BufferedReader bf = new BufferedReader(new InputStreamReader(httpURLcon.getInputStream(), "UTF-8"));
            String line;
            while((line = bf.readLine()) != null)
            {
                result = line;
            }

            // 서버에서 받아온 결과문을 확인합니다.
            System.out.println(result);

            XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
            XmlPullParser parser = factory.newPullParser();

            InputStream input = new ByteArrayInputStream(result.getBytes("UTF-8"));
            parser.setInput(input, "UTF-8");

            int EventType = parser.getEventType();

        } catch (Exception e) {
            e.printStackTrace();
        }

        if(result.equals("1")) {
            showToast("가입이 완료되었습니다.");
            finish();
        } else {
            showToast("중복된 아이디입니다.");
        }
    }

    private void alertDialog(final Button btn) {
        // 날짜 선택을 위한 다이얼로그
        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.setContentView(R.layout.dialog_date);

        final DatePicker datePicker = (DatePicker) dialog.findViewById(R.id.datePicker);
        Button dbtnOk = (Button) dialog.findViewById(R.id.dbtn_ok);
        Button dbtnCancel = (Button) dialog.findViewById(R.id.dbtn_cancel);

        dbtnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        // 버튼 ID 값에 따라 다르게 처리
        dbtnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch(btn.getId()) {
                    case R.id.btnBirth:
                        bYear = datePicker.getYear();
                        bMonth = datePicker.getMonth() + 1;
                        bDay = datePicker.getDayOfMonth();
                        strBirth = String.format(Locale.KOREA, "%04d-%02d-%02d", bYear, bMonth, bDay);
                        btn.setText(String.format(Locale.KOREA, "%04d년 %02d월 %02d일", bYear, bMonth, bDay));
                        break;

                    case R.id.btnSmoke:
                        sYear = datePicker.getYear();
                        sMonth = datePicker.getMonth() + 1;
                        sDay = datePicker.getDayOfMonth();
                        strSmoke = String.format(Locale.KOREA, "%04d-%02d-%02d", sYear, sMonth, sDay);
                        btn.setText(String.format(Locale.KOREA, "%04d년 %02d월 %02d일", sYear, sMonth, sDay));
                        break;

                    default:
                        break;
                }

                dialog.dismiss();
            }
        });
        dialog.show();
    }

    private void showToast(String str) {
        Toast.makeText(getApplicationContext(), str, Toast.LENGTH_SHORT).show();
    }
}
