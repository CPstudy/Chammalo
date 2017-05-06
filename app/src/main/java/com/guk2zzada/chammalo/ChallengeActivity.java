package com.guk2zzada.chammalo;

import android.app.Activity;
import android.content.Context;
import android.support.v4.view.LayoutInflaterCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.zip.Inflater;

public class ChallengeActivity extends Activity {

    ArrayList<CustomList> arrCustomList;
    CustomList customlist;
    CustomListAdapter adapter;

    ListView list;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_challenge);

        list = (ListView) findViewById(R.id.list);

        arrCustomList = new ArrayList<CustomList>();
        customlist = new CustomList("도전 과제 1");
        arrCustomList.add(customlist);
        customlist = new CustomList("도전 과제 2");
        arrCustomList.add(customlist);
        customlist = new CustomList("도전 과제 3");
        arrCustomList.add(customlist);
        customlist = new CustomList("도전 과제 4");
        arrCustomList.add(customlist);
        customlist = new CustomList("도전 과제 5");
        arrCustomList.add(customlist);
        customlist = new CustomList("도전 과제 6");
        arrCustomList.add(customlist);
        customlist = new CustomList("도전 과제 7");
        arrCustomList.add(customlist);
        customlist = new CustomList("도전 과제 8");
        arrCustomList.add(customlist);
        customlist = new CustomList("도전 과제 9");
        arrCustomList.add(customlist);
        customlist = new CustomList("도전 과제 10");
        arrCustomList.add(customlist);
        adapter = new CustomListAdapter(this, R.layout.item_challenge, arrCustomList);
        list.setAdapter(adapter);
    }

    private class ViewHolder {
        private TextView txtText;
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

            if(convertView == null) {
                convertView = inflater.inflate(layout, parent, false);

                viewHolder = new ViewHolder();
                viewHolder.txtText = (TextView) convertView.findViewById(R.id.txtText);

                convertView.setTag(viewHolder);
            } else {
                viewHolder = (ViewHolder) convertView.getTag();
            }

            viewHolder.txtText.setText(arC.get(position).strText);

            return convertView;
        }
    }
}

class CustomList {
    String strText;

    CustomList(String strText) {
        this.strText = strText;
    }
}
