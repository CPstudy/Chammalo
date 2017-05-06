package com.guk2zzada.chammalo;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class BoardListActivity extends Activity {

    ArrayList<CustomBoardList> arrCustomBoardList;
    CustomBoardList customboardlist;
    CustomBoardListAdapter adapter;

    ListView list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_board_list);

        list = (ListView) findViewById(R.id.list);
        arrCustomBoardList = new ArrayList<CustomBoardList>();
        customboardlist = new CustomBoardList("게시글 1", "2017-01-01 00:00");
        arrCustomBoardList.add(customboardlist);
        customboardlist = new CustomBoardList("게시글 2", "2017-01-01 01:00");
        arrCustomBoardList.add(customboardlist);
        customboardlist = new CustomBoardList("게시글 3", "2017-01-01 02:00");
        arrCustomBoardList.add(customboardlist);
        customboardlist = new CustomBoardList("게시글 4", "2017-01-01 03:00");
        arrCustomBoardList.add(customboardlist);
        customboardlist = new CustomBoardList("게시글 5", "2017-01-01 04:00");
        arrCustomBoardList.add(customboardlist);
        customboardlist = new CustomBoardList("게시글 6", "2017-01-01 05:00");
        arrCustomBoardList.add(customboardlist);
        customboardlist = new CustomBoardList("게시글 7", "2017-01-01 06:00");
        arrCustomBoardList.add(customboardlist);
        customboardlist = new CustomBoardList("게시글 8", "2017-01-01 07:00");
        arrCustomBoardList.add(customboardlist);
        customboardlist = new CustomBoardList("게시글 9", "2017-01-01 08:00");
        arrCustomBoardList.add(customboardlist);
        customboardlist = new CustomBoardList("게시글 10", "2017-01-01 09:00");
        arrCustomBoardList.add(customboardlist);

        adapter = new CustomBoardListAdapter(this, R.layout.item_board_list, arrCustomBoardList);
        list.setAdapter(adapter);
    }

    private class ViewHolder {
        private TextView txtTitle;
        private TextView txtTime;
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

                convertView.setTag(viewHolder);
            } else {
                viewHolder = (ViewHolder) convertView.getTag();
            }

            viewHolder.txtTitle.setText(arC.get(position).strTitle);
            viewHolder.txtTime.setText(arC.get(position).strTime);

            convertView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(getApplicationContext(), BoardContentActivity.class);
                    intent.putExtra("strTitle", arC.get(position).strTitle);
                    intent.putExtra("strTime", arC.get(position).strTime);
                    startActivity(intent);
                }
            });

            return convertView;
        }
    }
}

class CustomBoardList {
    String strTitle;
    String strTime;

    CustomBoardList(String strTitle, String strTime) {
        this.strTitle = strTitle;
        this.strTime = strTime;
    }
}