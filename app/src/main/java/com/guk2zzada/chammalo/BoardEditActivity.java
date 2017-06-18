package com.guk2zzada.chammalo;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class BoardEditActivity extends AppCompatActivity {

    EditText edtSubject;
    EditText edtContents;
    Button btnSubmit;

    String strUser;
    String strSubject;
    String strContents;
    boolean boolEdit = false;
    int boardID = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_board_edit);

        edtSubject = (EditText) findViewById(R.id.edtSubject);
        edtContents = (EditText) findViewById(R.id.edtContents);
        btnSubmit = (Button) findViewById(R.id.btnSubmit);

        Intent intent = getIntent();
        strUser = intent.getStringExtra("strUser");
        strSubject = intent.getStringExtra("strSubject");
        strContents = intent.getStringExtra("strContents");
        boolEdit = intent.getBooleanExtra("boolEdit", false);
        boardID = intent.getIntExtra("boardID", 0);

        if(boolEdit) {
            edtSubject.setText(strSubject);
            edtContents.setText(strContents);
        }

        Log.e(">>>>>", strUser);

        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!edtSubject.getText().toString().equals("") && !edtContents.getText().toString().equals("")) {
                    strSubject = edtSubject.getText().toString();
                    strContents = edtContents.getText().toString();

                    if(!boolEdit) {
                        // 새로 등록
                        addData();
                    } else {
                        // 수정
                        updateData();
                    }
                } else {
                    showToast("모두 채워주세요.");
                }
            }
        });
    }

    private void addData() {
        String result = "0";
        try {
            URL url = new URL("http://sunsiri.cafe24.com/boardwrite-android.jsp");
            HttpURLConnection httpURLcon = (HttpURLConnection)url.openConnection();
            httpURLcon.setDefaultUseCaches(false);
            httpURLcon.setDoInput(true);
            httpURLcon.setDoOutput(true);
            httpURLcon.setRequestMethod("POST");
            httpURLcon.setRequestProperty("content-type", "application/x-www-form-urlencoded");
            StringBuffer sb = new StringBuffer();
            sb.append("writer").append("=").append(strUser).append("&");
            sb.append("subject").append("=").append(strSubject).append("&");
            sb.append("contents").append("=").append(strContents);
            PrintWriter pw = new PrintWriter(new OutputStreamWriter(httpURLcon.getOutputStream(), "UTF-8"));
            pw.write(sb.toString());
            pw.flush();
            BufferedReader bf = new BufferedReader(new InputStreamReader(httpURLcon.getInputStream(), "UTF-8"));
            String line;
            while((line = bf.readLine()) != null)
            {
                result = line;
            }

            // 서버에서 받아온 결과문을 확인한다.
            System.out.println(result);

        } catch (Exception e) {
            e.printStackTrace();
        }

        if(result.equals("1")) {
            showToast("게시글을 작성하였습니다..");
            finish();
        } else {
            showToast("게시글 작성에 실패했습니다.");
        }
    }

    private void updateData() {
        String result = "0";
        try {
            URL url = new URL("http://sunsiri.cafe24.com/boardupdate-android.jsp");
            HttpURLConnection httpURLcon = (HttpURLConnection)url.openConnection();
            httpURLcon.setDefaultUseCaches(false);
            httpURLcon.setDoInput(true);
            httpURLcon.setDoOutput(true);
            httpURLcon.setRequestMethod("POST");
            httpURLcon.setRequestProperty("content-type", "application/x-www-form-urlencoded");
            StringBuffer sb = new StringBuffer();
            sb.append("id").append("=").append(boardID).append("&");
            sb.append("subject").append("=").append(strSubject).append("&");
            sb.append("contents").append("=").append(strContents);
            PrintWriter pw = new PrintWriter(new OutputStreamWriter(httpURLcon.getOutputStream(), "UTF-8"));
            pw.write(sb.toString());
            pw.flush();
            BufferedReader bf = new BufferedReader(new InputStreamReader(httpURLcon.getInputStream(), "UTF-8"));
            String line;
            while((line = bf.readLine()) != null)
            {
                result = line;
            }

            // 서버에서 받아온 결과문을 확인한다.
            System.out.println(result);

        } catch (Exception e) {
            e.printStackTrace();
        }

        if(result.equals("1")) {
            showToast("게시글을 수정하였습니다..");
            Intent intent = new Intent(getApplicationContext(), BoardContentActivity.class);
            intent.putExtra("strTitle", strSubject);
            intent.putExtra("strContents", strContents);
            setResult(RESULT_OK, intent);
            finish();
        } else {
            showToast("게시글 수정정에 실패했습니다.");
       }
    }

    private void showToast(String str) {
        Toast.makeText(getApplicationContext(), str, Toast.LENGTH_SHORT).show();
    }
}
