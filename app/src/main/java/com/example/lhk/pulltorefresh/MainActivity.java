package com.example.lhk.pulltorefresh;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.example.lhk.pulltorefresh.view.RefreshLayout;
import com.example.lhk.pulltorefresh.view.ShopView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        listView = (ListView) findViewById(R.id.listView);

        final RefreshLayout refreshLayout = (RefreshLayout) findViewById(R.id.refreshLayout);
        if (refreshLayout != null) {
            // 刷新状态的回调
            refreshLayout.setRefreshListener(new RefreshLayout.OnRefreshListener() {
                @Override
                public void onRefresh() {
                    // 延迟3秒后刷新成功
                    refreshLayout.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            refreshLayout.refreshComplete();
                            if (listView != null) {
                                listView.setAdapter(new MainAdapter());
                            }
                        }
                    }, 3000);
                }
            });
            ShopView shopView = new ShopView(this);
            refreshLayout.setRefreshHeader(shopView);
            refreshLayout.autoRefresh();
        }

    }

    class MainAdapter extends BaseAdapter {

        private List<String> mList;

        public MainAdapter(){
            mList = new ArrayList<>();
            for (int i=0;i<50;i++){
                mList.add("Item"+i);
            }
        }

        @Override
        public int getCount() {
            return mList.size();
        }

        @Override
        public Object getItem(int position) {
            return mList.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if(convertView == null) {
                TextView textView = new TextView(MainActivity.this);
                textView.setText(mList.get(position));
                textView.setTextColor(Color.BLACK);
                textView.setBackgroundColor(0x55ff0000);
                textView.setLayoutParams(new AbsListView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,200));
                textView.setGravity(Gravity.CENTER);
                convertView = textView;
            } else {
                ((TextView) convertView).setText(mList.get(position));
            }

            return convertView;
        }
    }
}
