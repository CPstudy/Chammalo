package com.guk2zzada.chammalo;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ViewFlipper;

import java.util.Locale;

public class PersonInfoActivity extends Activity {

    Button btnBirth, btnSmoke, btnDrink;
    Button btnMale, btnFemale;
    Button btnPrev, btnNext;
    EditText edtName, edtSmoke, edtDrink;
    EditText edtGoalSmoke, edtGoalDrink;
    ViewFlipper viewFlipper;

    Animation slide_in_left, slide_out_right;
    Animation slide_in_right, slide_out_left;

    int bYear;
    int bMonth;
    int bDay;

    int sYear;
    int sMonth;
    int sDay;

    int dYear;
    int dMonth;
    int dDay;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_person_info);

        btnBirth = (Button) findViewById(R.id.btnBirth);
        btnSmoke = (Button) findViewById(R.id.btnSmoke);
        btnDrink = (Button) findViewById(R.id.btnDrink);
        btnMale = (Button) findViewById(R.id.btnMale);
        btnFemale = (Button) findViewById(R.id.btnFemale);
        btnPrev = (Button) findViewById(R.id.btnPrev);
        btnNext = (Button) findViewById(R.id.btnNext);
        edtName = (EditText) findViewById(R.id.edtName);
        edtSmoke = (EditText) findViewById(R.id.edtSmoke);
        edtDrink = (EditText) findViewById(R.id.edtDrink);
        edtGoalSmoke = (EditText) findViewById(R.id.edtGoalSmoke);
        edtGoalDrink = (EditText) findViewById(R.id.edtGoalDrink);
        viewFlipper = (ViewFlipper) findViewById(R.id.viewFlipper);

        // 뷰 플리퍼 애니메이션
        slide_in_left = AnimationUtils.loadAnimation(this, R.anim.slide_in_left);
        slide_out_right = AnimationUtils.loadAnimation(this, R.anim.slide_out_right);
        slide_in_right = AnimationUtils.loadAnimation(this, R.anim.slide_in_right);
        slide_out_left = AnimationUtils.loadAnimation(this, R.anim.slide_out_left);

        // 버튼 텍스트 초기화
        btnBirth.setText(String.format(Locale.KOREA, "%4d년 %2d월 %2d일", 2017, 1, 1));
        btnSmoke.setText(String.format(Locale.KOREA, "%4d년 %2d월 %2d일", 2017, 1, 1));
        btnDrink.setText(String.format(Locale.KOREA, "%4d년 %2d월 %2d일", 2017, 1, 1));

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

        btnDrink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog(btnDrink);
            }
        });

        btnMale.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnMale.setBackgroundResource(R.drawable.btn_white);
                btnFemale.setBackgroundResource(R.drawable.btn_transparent);
            }
        });

        btnFemale.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnMale.setBackgroundResource(R.drawable.btn_transparent);
                btnFemale.setBackgroundResource(R.drawable.btn_white);
            }
        });

        btnPrev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 뷰 플리퍼 이전 버튼 클릭시
                viewFlipper.setInAnimation(slide_in_left);
                viewFlipper.setOutAnimation(slide_out_right);
                viewFlipper.showPrevious();
            }
        });

        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 뷰 플리퍼 다음 버튼 클릭시
                viewFlipper.setInAnimation(slide_in_right);
                viewFlipper.setOutAnimation(slide_out_left);
                viewFlipper.showNext();
            }
        });
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
                        btn.setText(String.format(Locale.KOREA, "%4d년 %2d월 %2d일", bYear, bMonth, bDay));
                        break;

                    case R.id.btnSmoke:
                        sYear = datePicker.getYear();
                        sMonth = datePicker.getMonth() + 1;
                        sDay = datePicker.getDayOfMonth();
                        btn.setText(String.format(Locale.KOREA, "%4d년 %2d월 %2d일", sYear, sMonth, sDay));
                        break;

                    case R.id.btnDrink:
                        dYear = datePicker.getYear();
                        dMonth = datePicker.getMonth() + 1;
                        dDay = datePicker.getDayOfMonth();
                        btn.setText(String.format(Locale.KOREA, "%4d년 %2d월 %2d일", dYear, dMonth, dDay));
                        break;

                    default:
                        break;
                }

                dialog.dismiss();
            }
        });
        dialog.show();
    }
}
