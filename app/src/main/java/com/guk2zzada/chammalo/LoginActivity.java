package com.guk2zzada.chammalo;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

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

        btnJoin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), PersonInfoActivity.class);
                startActivity(intent);
            }
        });

        btnSign.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String ID = "2mb";
                String PW = "1234";

                if(!edtID.getText().toString().equals("") && !edtPW.getText().toString().equals("")) {
                    if (edtID.getText().toString().equals(ID) && edtPW.getText().toString().equals(PW)) {
                        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                        startActivity(intent);
                    } else {
                        showToast("아이디나 패스워드가 맞지 않습니다.");
                    }
                } else {
                    showToast("모두 입력해주세요.");
                }
            }
        });
    }

    public void showToast(String str) {
        Toast.makeText(getApplicationContext(), str, Toast.LENGTH_SHORT).show();
    }
}
