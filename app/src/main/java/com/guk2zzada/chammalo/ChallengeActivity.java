package com.guk2zzada.chammalo;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.media.Image;
import android.os.StrictMode;
import android.support.v4.view.LayoutInflaterCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
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
import java.util.ArrayList;
import java.util.zip.Inflater;

public class ChallengeActivity extends Activity {
    // 이미지를 흑백으로 만들기 위한 컬러 매트릭스
    ColorMatrix cm = new ColorMatrix();

    ArrayList<CustomList> arrCustomList;
    CustomList customlist;
    CustomListAdapter adapter;

    ListView list;
    ImageView imgView;

    int iDate = 0;
    String strUser = "";

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_challenge);

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        list = (ListView) findViewById(R.id.list);
        imgView = (ImageView) findViewById(R.id.imgView);

        arrCustomList = new ArrayList<CustomList>();
        customlist = new CustomList("프로젝트 시작", "드디어 시작합니다.", false, R.drawable.img_00);
        arrCustomList.add(customlist);
        customlist = new CustomList("1일 경과", "일산화탄소가 배출되고 폐 기능이 향상됩니다.", false, R.drawable.img_01);
        arrCustomList.add(customlist);
        customlist = new CustomList("2일 경과", "기분이 상쾌해지며 맛과 냄새 기능이 좋아집니다.", false, R.drawable.img_02);
        arrCustomList.add(customlist);
        customlist = new CustomList("3일 경과", "몸 속의 니코틴이 모두 배출됩니다.", false, R.drawable.img_03);
        arrCustomList.add(customlist);
        customlist = new CustomList("7일 경과", "진하던 가래가 묽어집니다.", false, R.drawable.img_04);
        arrCustomList.add(customlist);
        customlist = new CustomList("10일 경과", "금연으로 인한 불안감이 금연 이전으로 돌아갑니다.", false, R.drawable.img_05);
        arrCustomList.add(customlist);
        customlist = new CustomList("15일 경과", "불안, 집중력 감소, 초조 등의 금단 현상이 사라지기 시작합니다.", false, R.drawable.img_06);
        arrCustomList.add(customlist);
        customlist = new CustomList("21일 경과", "금단 현상이 거의 사라집니다.", false, R.drawable.img_07);
        arrCustomList.add(customlist);
        customlist = new CustomList("30일 경과", "혈압이 정상화되고 폐 기능이 30% 증가합니다.", false, R.drawable.img_08);
        arrCustomList.add(customlist);
        customlist = new CustomList("90일 경과", "폐의 자체 정화 기능이 완전 정상화됩니다.", false, R.drawable.img_09);
        arrCustomList.add(customlist);
        customlist = new CustomList("180일 경과", "기침, 코막힘, 호흡곤란 등이 감소합니다.", false, R.drawable.img_10);
        arrCustomList.add(customlist);
        customlist = new CustomList("270일 경과", "신체 활력이 전반적으로 증가합니다.", false, R.drawable.img_11);
        arrCustomList.add(customlist);
        customlist = new CustomList("365일 경과", "흡연으로 인한 심장병 발병 확률이 절반으로 감소합니다.", false, R.drawable.img_12);
        arrCustomList.add(customlist);
        adapter = new CustomListAdapter(this, R.layout.item_challenge, arrCustomList);
        list.setAdapter(adapter);

        Intent intent = getIntent();
        iDate = intent.getIntExtra("iDate", 0);
        strUser = intent.getStringExtra("strUser");

        checkScore();
    }

    private void checkScore() {
        if(iDate >= 0) {
            arrCustomList.get(0).boolSuccess = true;
            imgView.setImageResource(R.drawable.img_char01);
        }
        if(iDate >= 1) {
            arrCustomList.get(1).boolSuccess = true;
            imgView.setImageResource(R.drawable.img_char01);
        }

        if(iDate >= 2) {
            arrCustomList.get(2).boolSuccess = true;
            imgView.setImageResource(R.drawable.img_char01);
        }

        if(iDate >= 3) {
            arrCustomList.get(3).boolSuccess = true;
            imgView.setImageResource(R.drawable.img_char01);
        }

        if(iDate >= 7) {
            arrCustomList.get(4).boolSuccess = true;
            imgView.setImageResource(R.drawable.img_char02);
        }

        if(iDate >= 10) {
            arrCustomList.get(5).boolSuccess = true;
            imgView.setImageResource(R.drawable.img_char02);
        }

        if(iDate >= 15) {
            arrCustomList.get(6).boolSuccess = true;
            imgView.setImageResource(R.drawable.img_char02);
        }

        if(iDate >= 21) {
            arrCustomList.get(7).boolSuccess = true;
            imgView.setImageResource(R.drawable.img_char02);
        }

        if(iDate >= 30) {
            arrCustomList.get(8).boolSuccess = true;
            imgView.setImageResource(R.drawable.img_char02);
        }

        if(iDate >= 90) {
            arrCustomList.get(9).boolSuccess = true;
            imgView.setImageResource(R.drawable.img_char03);
        }

        if(iDate >= 180) {
            arrCustomList.get(10).boolSuccess = true;
            imgView.setImageResource(R.drawable.img_char03);
        }

        if(iDate >= 270) {
            arrCustomList.get(11).boolSuccess = true;
            imgView.setImageResource(R.drawable.img_char03);
        }

        if(iDate >= 365) {
            arrCustomList.get(12).boolSuccess = true;
            imgView.setImageResource(R.drawable.img_char04);
        }
    }

    private class ViewHolder {
        private RelativeLayout layBack;
        private TextView txtText;
        private TextView txtSubText;
        private ImageView imgIcon;
    }

    private class CustomListAdapter extends BaseAdapter {

        Context con;
        LayoutInflater inflater;
        ArrayList<CustomList> arC;
        int layout;

        private CustomListAdapter(Context context, int alayout, ArrayList<CustomList>  aarC) {
            con = context;
            inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            arC = aarC;
            layout = alayout;
        }

        // 어댑터의 항목 수 조사
        @Override
        public int getCount() {
            return arC.size();
        }

        // position 위치 항목 Name 반환
        @Override
        public Object getItem(int position) {
            return null;
        }

        // position 위치 항목 ID 반환
        @Override
        public long getItemId(int position) {
            return 0;
        }

        // 각각의 항목 뷰 생성
        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            ViewHolder viewHolder;
            cm.setSaturation(0);

            if(convertView == null) {
                convertView = inflater.inflate(layout, parent, false);

                viewHolder = new ViewHolder();
                viewHolder.layBack = (RelativeLayout) convertView.findViewById(R.id.layBack);
                viewHolder.txtText = (TextView) convertView.findViewById(R.id.txtText);
                viewHolder.txtSubText = (TextView) convertView.findViewById(R.id.txtSubText);
                viewHolder.imgIcon = (ImageView) convertView.findViewById(R.id.imgIcon);

                convertView.setTag(viewHolder);
            } else {
                viewHolder = (ViewHolder) convertView.getTag();
            }

            viewHolder.imgIcon.setImageResource(arC.get(position).iImage);

            if(arC.get(position).boolSuccess) {
                viewHolder.imgIcon.setColorFilter(null);
            } else {
                viewHolder.imgIcon.setColorFilter(new ColorMatrixColorFilter(cm));
            }

            viewHolder.txtText.setText(arC.get(position).strText);
            viewHolder.txtSubText.setText(arC.get(position).strSubText);

            convertView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(arC.get(position).boolSuccess) {
                        String result = "0";
                        try {
                            URL url = new URL("http://sunsiri.cafe24.com/updateemblem-android.jsp");
                            HttpURLConnection httpURLcon = (HttpURLConnection) url.openConnection();
                            httpURLcon.setDefaultUseCaches(false);
                            httpURLcon.setDoInput(true);
                            httpURLcon.setDoOutput(true);
                            httpURLcon.setRequestMethod("POST");
                            httpURLcon.setRequestProperty("content-type", "application/x-www-form-urlencoded");
                            StringBuffer sb = new StringBuffer();
                            sb.append("id").append("=").append(strUser).append("&");
                            sb.append("emblem").append("=").append(String.valueOf(position));
                            PrintWriter pw = new PrintWriter(new OutputStreamWriter(httpURLcon.getOutputStream(), "UTF-8"));
                            pw.write(sb.toString());
                            pw.flush();
                            BufferedReader bf = new BufferedReader(new InputStreamReader(httpURLcon.getInputStream(), "UTF-8"));
                            String line;
                            while ((line = bf.readLine()) != null) {
                                result = line;
                            }

                            // 서버에서 받아온 결과문을 확인합니다.
                            System.out.println(result);

                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        Toast.makeText(getApplicationContext(), "엠블럼이 변경되었습니다.", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(getApplicationContext(), "아직 엠블럼을 얻지 못 했습니다.", Toast.LENGTH_SHORT).show();
                    }
                }
            });

            return convertView;
        }
    }
}

class CustomList {
    String strText;
    String strSubText;
    boolean boolSuccess;
    int iImage;

    CustomList(String strText, String strSubText, Boolean boolSuccess, int iImage) {
        this.strText = strText;
        this.strSubText = strSubText;
        this.boolSuccess = boolSuccess;
        this.iImage = iImage;
    }
}
