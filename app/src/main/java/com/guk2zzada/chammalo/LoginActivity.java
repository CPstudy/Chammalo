package com.guk2zzada.chammalo;

import android.content.Intent;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
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

    Button btnSign;
    Button btnJoin;
    EditText edtID;
    EditText edtPW;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        btnSign = (Button) findViewById(R.id.btnSign);
        btnJoin = (Button) findViewById(R.id.btnJoin);
        edtID = (EditText) findViewById(R.id.edtID);
        edtPW = (EditText) findViewById(R.id.edtPW);

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        btnJoin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), JoinActivity.class);
                startActivity(intent);
            }
        });

        btnSign.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String result = "0";

                String ID = "2mb";
                String PW = "1234";

                String Post_NAME = edtID.getText().toString();
                String Post_Addr = edtPW.getText().toString();

                if(!edtID.getText().toString().equals("") && !edtPW.getText().toString().equals("")) {
                    /*if (edtID.getText().toString().equals(ID) && edtPW.getText().toString().equals(PW)) {
                        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                        startActivity(intent);
                    } else {
                        showToast("아이디나 패스워드가 맞지 않습니다.");
                    }*/

                    try {
                        URL url = new URL("http://sunsiri.cafe24.com/login-android.jsp");
                        HttpURLConnection httpURLcon = (HttpURLConnection)url.openConnection();
                        httpURLcon.setDefaultUseCaches(false);
                        httpURLcon.setDoInput(true);
                        httpURLcon.setDoOutput(true);
                        httpURLcon.setRequestMethod("POST");
                        httpURLcon.setRequestProperty("content-type", "application/x-www-form-urlencoded");
                        StringBuffer sb = new StringBuffer();
                        sb.append("id").append("=").append(Post_NAME).append("&");
                        sb.append("pw").append("=").append(Post_Addr);
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
                        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
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
