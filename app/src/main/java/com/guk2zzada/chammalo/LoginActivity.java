package com.guk2zzada.chammalo;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
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

public class LoginActivity extends AppCompatActivity {

    // 아이디와 비밀번호를 저장하기 위해 생성
    private SharedPreferences mPref;
    private SharedPreferences.Editor mPrefEdit;

    Button btnSign;
    Button btnJoin;
    EditText edtID;
    EditText edtPW;
    CheckBox ckbID;
    CheckBox ckbPW;

    boolean boolID;
    boolean boolPW;
    String strID;
    String strPW;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mPref = getSharedPreferences("Pref1", 0);
        mPrefEdit = mPref.edit();

        btnSign = (Button) findViewById(R.id.btnSign);
        btnJoin = (Button) findViewById(R.id.btnJoin);
        edtID = (EditText) findViewById(R.id.edtID);
        edtPW = (EditText) findViewById(R.id.edtPW);
        ckbID = (CheckBox) findViewById(R.id.ckbID);
        ckbPW = (CheckBox) findViewById(R.id.ckbPW);

        // 네트워크 통신을 위해 필요한 것
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        boolID = mPref.getBoolean("boolID", false);
        boolPW = mPref.getBoolean("boolPW", false);
        strID = mPref.getString("strID", "");
        strPW = mPref.getString("strPW", "");

        // 아이디 저장과 패스워드 저장 체크박스 초기화
        if(boolID) {
            ckbID.setChecked(true);
            edtID.setText(strID);
        } else {
            ckbID.setChecked(false);
        }

        if(boolPW) {
            ckbPW.setChecked(true);
            edtPW.setText(strPW);
        } else {
            ckbPW.setChecked(false);
        }

        // 회원가입 버튼
        btnJoin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), JoinActivity.class);
                startActivity(intent);
            }
        });

        // 
        btnSign.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String result = "0";

                // 입력 받은 아이디와 패스워드
                String postID = edtID.getText().toString();
                String postPW = edtPW.getText().toString();

                if(!edtID.getText().toString().equals("") && !edtPW.getText().toString().equals("")) {
                    try {
                        // 아래 URL에 접속해 JSON 배열을 받아와 파싱함
                        URL url = new URL("http://sunsiri.cafe24.com/login-android.jsp");
                        HttpURLConnection httpURLcon = (HttpURLConnection)url.openConnection();
                        httpURLcon.setDefaultUseCaches(false);
                        httpURLcon.setDoInput(true);
                        httpURLcon.setDoOutput(true);
                        httpURLcon.setRequestMethod("POST");
                        httpURLcon.setRequestProperty("content-type", "application/x-www-form-urlencoded");
                        StringBuffer sb = new StringBuffer();
                        sb.append("id").append("=").append(postID).append("&");
                        sb.append("pw").append("=").append(postPW);
                        PrintWriter pw = new PrintWriter(new OutputStreamWriter(httpURLcon.getOutputStream(), "UTF-8"));
                        pw.write(sb.toString());
                        pw.flush();
                        BufferedReader bf = new BufferedReader(new InputStreamReader(httpURLcon.getInputStream(), "UTF-8"));
                        String line;
                        while((line = bf.readLine()) != null)
                        {
                            result = line;
                        }

                        // 서버에서 받아온 결과문을 확인. 1 = 성공
                        System.out.println(result);

                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                    if(result.equals("1")) {
                        // 체크박스 설정값 저장
                        if(ckbID.isChecked()) {
                            mPrefEdit.putBoolean("boolID", true);
                            mPrefEdit.putString("strID", postID);
                        } else {
                            mPrefEdit.putBoolean("boolID", false);
                        }

                        if(ckbPW.isChecked()) {
                            mPrefEdit.putBoolean("boolPW", true);
                            mPrefEdit.putString("strPW", postPW);
                        } else {
                            mPrefEdit.putBoolean("boolPW", false);
                        }
                        mPrefEdit.commit();

                        // 아이디와 비밀번호가 확인되면 MainActivity로 이동하면서 사용자 아이디를 
                        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                        intent.putExtra("strID", postID);
                        startActivity(intent);
                        finish();
                    } else {
                        showToast("아이디나 비밀번호가 일치하지 않습니다.");
                    }

                } else {
                    showToast("모두 입력해주세요.");
                }
            }
        });
    }

    private void showToast(String str) {
        Toast.makeText(getApplicationContext(), str, Toast.LENGTH_SHORT).show();
    }
}
