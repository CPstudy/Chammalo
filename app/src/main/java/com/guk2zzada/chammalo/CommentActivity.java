package com.guk2zzada.chammalo;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class CommentActivity extends AppCompatActivity {

    ArrayList<CustomCommentList> arrCustomCommentList;
    CustomCommentList customcommentlist;
    CustomCommentAdapter adapter;

    ListView list;
    EditText edtComment;
    Button btnSubmit;

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

    int boardID;
    int iEmblem;
    String strUser;
    String strName;
    String strComment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comment);

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        Intent intent = getIntent();
        boardID = intent.getIntExtra("boardID", 0);
        strUser = intent.getStringExtra("strUser");
        strName = intent.getStringExtra("strName");
        iEmblem = intent.getIntExtra("iEmblem", 0);

        btnSubmit = (Button) findViewById(R.id.btnSubmit);
        edtComment = (EditText) findViewById(R.id.edtComment);
        list = (ListView) findViewById(R.id.list);
        arrCustomCommentList = new ArrayList<CustomCommentList>();

        getCommentList();

        adapter = new CustomCommentAdapter(this, R.layout.item_comment, arrCustomCommentList);
        list.setAdapter(adapter);

        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!edtComment.getText().toString().equals("")) {
                    strComment = edtComment.getText().toString();
                    sendData();
                } else {
                    Toast.makeText(getApplicationContext(), "댓글을 입력해주세요.", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void sendData() {
        String result = "";
        try {
            URL url = new URL("http://sunsiri.cafe24.com/commentwrite-android.jsp");
            HttpURLConnection httpURLcon = (HttpURLConnection)url.openConnection();
            httpURLcon.setDefaultUseCaches(false);
            httpURLcon.setDoInput(true);
            httpURLcon.setDoOutput(true);
            httpURLcon.setRequestMethod("POST");
            httpURLcon.setRequestProperty("content-type", "application/x-www-form-urlencoded");
            StringBuffer sb = new StringBuffer();
            sb.append("user").append("=").append(strUser).append("&");
            sb.append("writer").append("=").append(strName).append("&");
            sb.append("board").append("=").append(String.valueOf(boardID)).append("&");
            sb.append("comment").append("=").append(strComment).append("&");
            sb.append("emblem").append("=").append(String.valueOf(iEmblem));
            PrintWriter pw = new PrintWriter(new OutputStreamWriter(httpURLcon.getOutputStream(), "UTF-8"));
            pw.write(sb.toString());
            pw.flush();
            BufferedReader bf = new BufferedReader(new InputStreamReader(httpURLcon.getInputStream(), "UTF-8"));
            String line;
            while((line = bf.readLine()) != null)
            {
                result += line;
            }

            Log.e(">> result ", result);
            if(result.equals("1")) {
                // 정상
                arrCustomCommentList.clear();
                getCommentList();
                adapter.notifyDataSetChanged();
                edtComment.setText("");
                list.setSelection(0);
            } else {
                // 비정상
                Toast.makeText(getApplicationContext(), "오류가 발생하였습니다.\n" + result, Toast.LENGTH_SHORT).show();
            }


        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void deleteData(int id) {
        String result = "";
        try {
            URL url = new URL("http://sunsiri.cafe24.com/commentdelete-android.jsp");
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
            Log.e(">> result ", result);
            if(result.equals("1")) {
                // 정상
                arrCustomCommentList.clear();
                getCommentList();
                adapter.notifyDataSetChanged();
                Toast.makeText(getApplicationContext(), "삭제했습니다.", Toast.LENGTH_SHORT).show();
            } else {
                // 비정상
                Toast.makeText(getApplicationContext(), "오류가 발생하였습니다.\n" + result, Toast.LENGTH_SHORT).show();
            }


        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private void getCommentList() {
        String result = "";
        try {
            URL url = new URL("http://sunsiri.cafe24.com/getcomment-android.jsp");
            HttpURLConnection httpURLcon = (HttpURLConnection)url.openConnection();
            httpURLcon.setDefaultUseCaches(false);
            httpURLcon.setDoInput(true);
            httpURLcon.setDoOutput(true);
            httpURLcon.setRequestMethod("POST");
            httpURLcon.setRequestProperty("content-type", "application/x-www-form-urlencoded");
            StringBuffer sb = new StringBuffer();
            sb.append("board").append("=").append(String.valueOf(boardID));
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
            JSONArray jArr = json.getJSONArray("comment");

            for(int i = 0; i < jArr.length(); i++) {
                json = jArr.getJSONObject(i);
                customcommentlist = new CustomCommentList(
                        json.getInt("id"),
                        json.getString("writer_id"),
                        json.getString("writer"),
                        json.getString("comment"),
                        json.getString("reg_date"),
                        json.getString("mod_date"),
                        json.getInt("emblem")
                );
                arrCustomCommentList.add(customcommentlist);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void alertDialog(final int id) {
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
                deleteData(id);
                dialog.dismiss();
            }
        });
        dialog.show();
    }

    private class ViewHolder {
        private ImageView imgIcon;
        private TextView txtComment;
        private TextView txtWriter;
        private TextView txtTime;
    }

    private class CustomCommentAdapter extends BaseAdapter {
        Context con;
        LayoutInflater inflater;
        ArrayList<CustomCommentList> arC;
        int layout;

        private CustomCommentAdapter(Context context, int alayout, ArrayList<CustomCommentList>  aarC) {
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
                viewHolder.imgIcon = (ImageView) convertView.findViewById(R.id.imgIcon);
                viewHolder.txtComment = (TextView) convertView.findViewById(R.id.txtComment);
                viewHolder.txtWriter = (TextView) convertView.findViewById(R.id.txtWriter);
                viewHolder.txtTime = (TextView) convertView.findViewById(R.id.txtTime);

                convertView.setTag(viewHolder);
            } else {
                viewHolder = (ViewHolder) convertView.getTag();
            }

            viewHolder.txtComment.setText(arC.get(position).strComment);
            viewHolder.txtTime.setText(arC.get(position).strRegDate);
            viewHolder.txtWriter.setText(arC.get(position).strWriter);
            viewHolder.imgIcon.setImageResource(EMBLEM[arC.get(position).iEmblem]);

            convertView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    if(strUser.equals(arC.get(position).strWriterId)) {
                        alertDialog(arC.get(position).id);
                    }
                    return false;
                }
            });

            return convertView;
        }
    }
}

class CustomCommentList {
    int id;
    String strWriterId;
    String strWriter;
    String strComment;
    String strRegDate;
    String strModDate;
    int iEmblem;

    CustomCommentList(int id, String strWriterId, String strWriter, String strComment, String strRegDate, String strModDate, int iEmblem) {
        this.id = id;
        this.strWriterId = strWriterId;
        this.strWriter = strWriter;
        this.strComment = strComment;
        this.strRegDate = strRegDate;
        this.strModDate = strModDate;
        this.iEmblem = iEmblem;
    }
}