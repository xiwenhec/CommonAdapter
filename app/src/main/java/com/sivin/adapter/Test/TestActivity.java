package com.sivin.adapter.Test;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.sivin.adapter.R;

import java.util.ArrayList;
import java.util.List;

public class TestActivity extends AppCompatActivity {

    private Context mContext;

    private static final String TAG = "TestActivity";

    RecyclerView mRecycleView;

    int i = 0;

    List<String> mList = new ArrayList<>();

    RecyclerView.Adapter<MyViewHolder> mAdapter;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);

        mContext = this;

        mRecycleView = (RecyclerView) findViewById(R.id.recycle);

        mRecycleView.setLayoutManager(new LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false));

        //mRecycleView.addItemDecoration(new DefalutItemDecoration(this, DefalutItemDecoration.VERTICAL_LIST, R.drawable.itemdecoration));

        for (int i = 0; i < 20; i++) {
            mList.add("测试的第" + i + "个条目");
        }


        mAdapter = new RecyclerView.Adapter<MyViewHolder>() {
            @Override
            public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(mContext).inflate(R.layout.data_item1_layout, parent, false);
                return new MyViewHolder(view);
            }

            @Override
            public void onBindViewHolder(MyViewHolder holder, final int position) {
                holder.mTextView.setText(mList.get(position));
            }

            @Override
            public int getItemCount() {
                return mList.size();
            }
        };


        mRecycleView.setAdapter(mAdapter);


        Button button = (Button) findViewById(R.id.add);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mList.add(5, "新增加一个项目" + i++);
                mAdapter.notifyItemChanged(5);
            }
        });


    }


    class MyViewHolder extends RecyclerView.ViewHolder {

        TextView mTextView;

        public MyViewHolder(View view) {
            super(view);
            mTextView = (TextView) view.findViewById(R.id.text);
        }
    }


}
