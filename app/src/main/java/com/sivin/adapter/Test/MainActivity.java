package com.sivin.adapter.Test;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.sivin.adapter.R;
import com.sivin.adapter.Test.bean.BeanWrapper;
import com.sivin.adapter.Test.bean.ItemBean1;
import com.sivin.adapter.Test.bean.ItemBean2;
import com.sivin.adapter.Test.bean.ItemBean3;
import com.sivin.adapter.base.ItemViewDelegate;
import com.sivin.adapter.base.ViewHolder;
import com.sivin.adapter.section.SectionDecoration;
import com.sivin.adapter.wrapper.LoadMoreWrapper;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";

    private RecyclerView mRecycleView;

    public Button mButton;

    LoadMoreWrapper<ItemBean1> mLoadMoreWrapper;

    private Handler mHandler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            mLoadMoreWrapper.loadErro();
            return true;
        }
    });

    List<BeanWrapper> Datas = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initData();
        mRecycleView = (RecyclerView) findViewById(R.id.recycle);

        mRecycleView.addItemDecoration(new DefalutItemDecoration(this, LinearLayoutManager.VERTICAL, R.drawable.itemdecoration));

        mRecycleView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));


        mButton = (Button) findViewById(R.id.refresh);


        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ItemBean3 bean3 = new ItemBean3(3, "数据1", "类型3");
                Datas.add(new BeanWrapper(bean3));
                mLoadMoreWrapper.notifyDataSetChanged();
            }
        });


        SectionAdapter adapter2 = new SectionAdapter(this,  R.layout.item_section_layout, Datas) {
            @Override
            protected void convertSection(ViewHolder holder, String tagValue) {
                holder.setText(R.id.category, tagValue);
            }
        };


        adapter2.addItemViewDelegate(new Item1Delegate());
        adapter2.addItemViewDelegate(new Item3Delegate());

        mRecycleView.addItemDecoration(new SectionDecoration(adapter2));

        mLoadMoreWrapper = new LoadMoreWrapper<>(adapter2);

        mLoadMoreWrapper.setLoadMoreView(R.layout.sample_common_list_footer);

        mLoadMoreWrapper.setOnLoadMoreListener(new LoadMoreWrapper.OnLoadMoreListener() {
            @Override
            public void onLoadMoreRequested() {
                getNetData();
            }
        });

        mRecycleView.setAdapter(mLoadMoreWrapper);
    }



    class Item1Delegate implements ItemViewDelegate<BeanWrapper>{


        @Override
        public int getItemViewLayoutId() {
            return R.layout.data_item1_layout;
        }

        @Override
        public boolean isForViewType(BeanWrapper wrapper, int position) {

            Log.d(TAG, "isForViewType: "+wrapper.getTagId()+"position = "+position);

            return wrapper.getTagId()==1;
        }

        @Override
        public void convert(ViewHolder holder,BeanWrapper wrapper, int position) {
            holder.setText(R.id.text,wrapper.getType1Data().getContent());
        }
    }


    class Item3Delegate implements ItemViewDelegate<BeanWrapper>{


        @Override
        public int getItemViewLayoutId() {
            return R.layout.data_item3_layout;
        }

        @Override
        public boolean isForViewType(BeanWrapper item, int position) {



            return item.getTagId()==3;
        }

        @Override
        public void convert(ViewHolder holder, BeanWrapper wrapper, int position) {
            holder.setText(R.id.text,wrapper.getType3Data().getContent());
        }
    }


    public void initData() {

        List<ItemBean1> list1 = new ArrayList<>();
        ItemBean1 bean1;
        bean1 = new ItemBean1(1, "数据0", "类型1");
        list1.add(bean1);
        bean1 = new ItemBean1(1, "数据1", "类型1");
        list1.add(bean1);
        bean1 = new ItemBean1(1, "数据2", "类型1");
        list1.add(bean1);
        bean1 = new ItemBean1(1, "数据3", "类型1");
        list1.add(bean1);
        bean1 = new ItemBean1(1, "数据4", "类型1");
        list1.add(bean1);
        bean1 = new ItemBean1(1, "数据5", "类型1");
        list1.add(bean1);
        bean1 = new ItemBean1(1, "数据6", "类型1");
        list1.add(bean1);
        bean1 = new ItemBean1(1, "数据7", "类型1");
        list1.add(bean1);
        bean1 = new ItemBean1(1, "数据8", "类型1");
        list1.add(bean1);
        bean1 = new ItemBean1(1, "数据9", "类型1");
        list1.add(bean1);

        bean1 = new ItemBean1(1, "数据10", "类型1");
        list1.add(bean1);

        List<ItemBean2> list2 = new ArrayList<>();
        ItemBean2 bean2;
        bean2 = new ItemBean2(2, "数据1", "类型2");
        list2.add(bean2);
        bean2 = new ItemBean2(2, "数据2", "类型2");
        list2.add(bean2);
        bean2 = new ItemBean2(2, "数据3", "类型2");
        list2.add(bean2);
        bean2 = new ItemBean2(2, "数据4", "类型2");
        list2.add(bean2);


        List<ItemBean3> list3 = new ArrayList<>();
        ItemBean3 bean3;
        bean3 = new ItemBean3(3, "数据1", "类型3");
        list3.add(bean3);
        bean3 = new ItemBean3(3, "数据2", "类型3");
        list3.add(bean3);
        bean3 = new ItemBean3(3, "数据3", "类型3");
        list3.add(bean3);
        bean3 = new ItemBean3(3, "数据4", "类型3");
        list3.add(bean3);
        bean3 = new ItemBean3(3, "数据5", "类型3");
        list3.add(bean3);
        bean3 = new ItemBean3(3, "数据6", "类型3");
        list3.add(bean3);



        for(ItemBean1 itme1:list1){
            Datas.add(new BeanWrapper(itme1));
        }

        //Datas.add(new BeanWrapper(list2));

        for(ItemBean3 itme3:list3){
            Datas.add(new BeanWrapper(itme3));
        }


    }


    private void getNetData() {

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
//                for(int i = 0;i<20;i++){
//                    list.add("增加的第"+i+"个条目");
//                }
                mHandler.sendEmptyMessage(0x11);


            }
        }).start();


    }
}


