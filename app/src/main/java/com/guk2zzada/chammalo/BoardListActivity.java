package com.guk2zzada.chammalo;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class BoardListActivity extends Activity {

    ArrayList<CustomBoardList> arrCustomBoardList;
    CustomBoardList customboardlist;
    CustomBoardListAdapter adapter;

    ListView list;
    ImageButton btnAdd;

    String strUser;
    String strName;
    int iEmblem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_board_list);

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        Intent intent = getIntent();
        strUser = intent.getStringExtra("strUser");
        strName = intent.getStringExtra("strName");
        iEmblem = intent.getIntExtra("iEmblem", iEmblem);

        list = (ListView) findViewById(R.id.list);
        btnAdd = (ImageButton) findViewById(R.id.btnAdd);
        arrCustomBoardList = new ArrayList<CustomBoardList>();
        getBoardList();

        adapter = new CustomBoardListAdapter(this, R.layout.item_board_list, arrCustomBoardList);
        list.setAdapter(adapter);

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), BoardEditActivity.class);
                intent.putExtra("boolEdit", false);
                intent.putExtra("strUser", strUser);
                startActivity(intent);
            }
        });
    }

    public void onResume() {
        super.onResume();
        arrCustomBoardList.clear();
        getBoardList();
        adapter.notifyDataSetChanged();
    }

    private void getBoardList() {
        String result = "";
        try {
            URL url = new URL("http://sunsiri.cafe24.com/getboardlist-android.jsp");
            HttpURLConnection httpURLcon = (HttpURLConnection)url.openConnection();
            httpURLcon.setDefaultUseCaches(false);
            httpURLcon.setDoInput(true);
            httpURLcon.setDoOutput(true);
            httpURLcon.setRequestMethod("POST");
            httpURLcon.setRequestProperty("content-type", "application/x-www-form-urlencoded");
            StringBuffer sb = new StringBuffer();
            sb.append("start").append("=").append("0");
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
            Log.e(">> result = ", result);
            JSONObject json = new JSONObject(result);
            JSONArray jArr = json.getJSONArray("board");

            for(int i = 0; i < jArr.length(); i++) {
                json = jArr.getJSONObject(i);
                customboardlist = new CustomBoardList(
                        json.getInt("id"),
                        json.getString("writer"),
                        json.getString("subject"),
                        json.getString("contents"),
                        json.getInt("hit"),
                        json.getString("reg_date"),
                        json.getString("mod_date")
                );
                arrCustomBoardList.add(customboardlist);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private class ViewHolder {
        private TextView txtTitle;
        private TextView txtTime;
        private TextView txtWriter;
        private TextView txtHit;
    }

    private class CustomBoardListAdapter extends BaseAdapter {
        Context con;
        LayoutInflater inflater;
        ArrayList<CustomBoardList> arC;
        int layout;

        private CustomBoardListAdapter(Context context, int alayout, ArrayList<CustomBoardList>  aarC) {
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

            if(convertView == null) {
                convertView = inflater.inflate(layout, parent, false);

                viewHolder = new ViewHolder();
                viewHolder.txtTitle = (TextView) convertView.findViewById(R.id.txtTitle);
                viewHolder.txtTime = (TextView) convertView.findViewById(R.id.txtTime);
                viewHolder.txtWriter = (TextView) convertView.findViewById(R.id.txtWriter);
                viewHolder.txtHit = (TextView) convertView.findViewById(R.id.txtHit);

                convertView.setTag(viewHolder);
            } else {
                viewHolder = (ViewHolder) convertView.getTag();
            }

            viewHolder.txtTitle.setText(arC.get(position).subject);
            viewHolder.txtTime.setText(arC.get(position).reg_date);
            viewHolder.txtWriter.setText(arC.get(position).writer);
            viewHolder.txtHit.setText(String.valueOf(arC.get(position).hit));

            convertView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(getApplicationContext(), BoardContentActivity.class);
                    intent.putExtra("strUser", strUser);
                    intent.putExtra("strName", strName);
                    intent.putExtra("iEmblem", iEmblem);
                    intent.putExtra("id", arC.get(position).id);
                    intent.putExtra("strTitle", arC.get(position).subject);
                    intent.putExtra("strContents", arC.get(position).contents);
                    intent.putExtra("strRegTime", arC.get(position).reg_date);
                    intent.putExtra("strModTime", arC.get(position).mod_date);
                    intent.putExtra("strWriter", arC.get(position).writer);
                    intent.putExtra("iHit", arC.get(position).hit);
                    startActivity(intent);
                }
            });

            return convertView;
        }
    }
}

class CustomBoardList {
    int id;                 // 게시글 아이디
    String writer;          // 작성자 아이디
    String subject;         // 게시글 제목
    String contents;        // 게시글 내용
    int hit;                // 조회수
    String reg_date;        // 등록 시간
    String mod_date;        // 수정 시간

    CustomBoardList(int id, String writer, String subject, String contents, int hit, String reg_date, String mod_date) {
        this.id = id;
        this.writer = writer;
        this.subject = subject;
        this.contents = contents;
        this.hit = hit;
        this.reg_date = reg_date;
        this.mod_date = mod_date;
    }
}