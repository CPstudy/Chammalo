package com.guk2zzada.chammalo;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;

public class BoardContentActivity extends Activity {

    TextView txtTitle;
    TextView txtContents;
    TextView txtWriter;
    TextView txtTime;
    TextView txtHit;
    ImageView imgIcon;
    Button btnComment;
    ImageButton btnDelete;
    ImageButton btnModify;

    String strTitle, strContents;   // 제목, 내용
    String strRegTime, strModTime;  // 등록 시간, 수정 시간
    String strWriter;               // 작성자 아이디
    String strUser;                 // 로그인 아이디
    String strUserName;             // 로그인 한 사람 이름
    int iUserEmblem;                    // 로그인 한 사람 엠블럼
    int id, hit;                    // 게시글 아이디, 조회수

    final int EMBLEM[] = {
            R.drawable.img_00,
            R.drawable.img_01,
            R.drawable.img_02,
            R.drawable.img_03,
            R.drawable.img_04,
            R.drawable.img_05,
            R.drawable.img_06,
            R.drawable.img_07,
            R.drawable.img_08,
            R.drawable.img_09,
            R.drawable.img_10,
            R.drawable.img_11,
            R.drawable.img_12
    };

    // 작성자 정보
    String strName;     // 작성자 이름
    String strDate;     // 작성자 프로젝트 시작일
    int iEmblem;        // 작성자 엠블럼

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_board_content);

        txtTitle = (TextView) findViewById(R.id.txtTitle);
        txtContents = (TextView) findViewById(R.id.txtContents);
        txtWriter = (TextView) findViewById(R.id.txtWriter);
        txtTime = (TextView) findViewById(R.id.txtTime);
        txtHit = (TextView) findViewById(R.id.txtHit);
        imgIcon = (ImageView) findViewById(R.id.imgIcon);
        btnComment = (Button) findViewById(R.id.btnComment);
        btnDelete = (ImageButton) findViewById(R.id.btnDelete);
        btnModify = (ImageButton) findViewById(R.id.btnModify);

        Intent intent = getIntent();
        id = intent.getIntExtra("id", 0);
        hit = intent.getIntExtra("iHit", 0);
        strTitle = intent.getStringExtra("strTitle");
        strContents = intent.getStringExtra("strContents");
        strWriter = intent.getStringExtra("strWriter");
        strRegTime = intent.getStringExtra("strRegTime");
        strModTime = intent.getStringExtra("strModTime");
        strUser = intent.getStringExtra("strUser");
        strUserName = intent.getStringExtra("strName");
        iUserEmblem = intent.getIntExtra("iEmblem", 0);

        getInfo();
        updateHit();

        txtTitle.setText(strTitle);
        txtTime.setText(strRegTime);
        txtContents.setText(strContents);
        txtWriter.setText(strName);
        txtHit.setText(String.valueOf(hit + 1));
        imgIcon.setImageResource(EMBLEM[iEmblem]);

        if(!strUser.equals(strWriter)) {
            btnModify.setVisibility(View.INVISIBLE);
            btnDelete.setVisibility(View.INVISIBLE);
        } else {
            btnModify.setVisibility(View.VISIBLE);
            btnDelete.setVisibility(View.VISIBLE);
        }

        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog();
            }
        });

        btnModify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), BoardEditActivity.class);
                intent.putExtra("boardID", id);
                intent.putExtra("boolEdit", true);
                intent.putExtra("strSubject", strTitle);
                intent.putExtra("strContents", strContents);
                intent.putExtra("strUser", strUser);
                startActivityForResult(intent, 0);
            }
        });

        btnComment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), CommentActivity.class);
                intent.putExtra("boardID", id);
                intent.putExtra("strUser", strUser);
                intent.putExtra("strName", strUserName);
                intent.putExtra("iEmblem", iUserEmblem);
                startActivity(intent);
            }
        });
    }

    private void updateHit() {
        // 조회수 올리기
        String result = "";
        try {
            URL url = new URL("http://sunsiri.cafe24.com/updatehit-android.jsp");
            HttpURLConnection httpURLcon = (HttpURLConnection)url.openConnection();
            httpURLcon.setDefaultUseCaches(false);
            httpURLcon.setDoInput(true);
            httpURLcon.setDoOutput(true);
            httpURLcon.setRequestMethod("POST");
            httpURLcon.setRequestProperty("content-type", "application/x-www-form-urlencoded");
            StringBuffer sb = new StringBuffer();
            sb.append("id").append("=").append(String.valueOf(id)).append("&");
            sb.append("hit").append("=").append(String.valueOf(hit + 1));
            PrintWriter pw = new PrintWriter(new OutputStreamWriter(httpURLcon.getOutputStream(), "UTF-8"));
            pw.write(sb.toString());
            pw.flush();
            BufferedReader bf = new BufferedReader(new InputStreamReader(httpURLcon.getInputStream(), "UTF-8"));
            String line;
            while((line = bf.readLine()) != null)
            {
                result += line;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void deleteData() {
        String result = "";
        try {
            URL url = new URL("http://sunsiri.cafe24.com/boarddelete-android.jsp");
            HttpURLConnection httpURLcon = (HttpURLConnection)url.openConnection();
            httpURLcon.setDefaultUseCaches(false);
            httpURLcon.setDoInput(true);
            httpURLcon.setDoOutput(true);
            httpURLcon.setRequestMethod("POST");
            httpURLcon.setRequestProperty("content-type", "application/x-www-form-urlencoded");
            StringBuffer sb = new StringBuffer();
            sb.append("id").append("=").append(String.valueOf(id));
            PrintWriter pw = new PrintWriter(new OutputStreamWriter(httpURLcon.getOutputStream(), "UTF-8"));
            pw.write(sb.toString());
            pw.flush();
            BufferedReader bf = new BufferedReader(new InputStreamReader(httpURLcon.getInputStream(), "UTF-8"));
            String line;
            while((line = bf.readLine()) != null)
            {
                result += line;
            }
            Toast.makeText(getApplicationContext(), "삭제했습니다.", Toast.LENGTH_SHORT).show();
            finish();

        } catch (Exception e) {
            e.printStackTrace();
        }
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
            sb.append("id").append("=").append(strWriter);
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
            strDate = jsonObject.getString("start_project");
            iEmblem = jsonObject.getInt("emblem");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void alertDialog() {
        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.setContentView(R.layout.dialog_warning);

        Button dbtnOk = (Button) dialog.findViewById(R.id.dbtnOk);
        Button dbtnCancel = (Button) dialog.findViewById(R.id.dbtnCancel);

        dbtnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        dbtnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteData();
                dialog.dismiss();
            }
        });
        dialog.show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(resultCode == RESULT_OK) {
            strTitle = data.getStringExtra("strTitle");
            strContents = data.getStringExtra("strContents");

            txtTitle.setText(strTitle);
            txtContents.setText(strContents);
        }
    }
}
