package com.bottle.track.base.ui.coordinatorlayout;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bottle.track.R;

import java.util.ArrayList;
import java.util.List;

public class AppBarLayoutActivity extends AppCompatActivity {

    public static void start(Activity activity){
        Intent intent = new Intent(activity, AppBarLayoutActivity.class);
        activity.startActivity(intent);
    }

    private AppBarLayout appBarLayout;
    // private CollapsingToolbarLayout collapsingToolbar;
    private Toolbar toolbar;
    private RecyclerView recyclerView;
    private FloatingActionButton floatingActionButton;
    private MyAdapter myAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_coordinator_layout);
        appBarLayout = (AppBarLayout) findViewById(R.id.appBarLayout);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        // collapsingToolbar = (CollapsingToolbarLayout) findViewById(R.id.collapsingToolbar);
        floatingActionButton = (FloatingActionButton) findViewById(R.id.floatingActionButton);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Snackbar.make(view,"FAB",Snackbar.LENGTH_LONG)
                        .setAction("cancel", new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {

                            }
                        })
                        .show();
            }
        });
        initRecycleView();
    }

    private void initRecycleView(){
        List<String> data = new ArrayList<>();
        data.add("item1");
        data.add("item2");
        data.add("item3");
        data.add("item4");
        data.add("item5");
        data.add("item6");
        data.add("item7");
        data.add("item8");
        data.add("item9");
        myAdapter = new MyAdapter(this, data);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));//这里用线性显示 类似于list view
        recyclerView.setAdapter(myAdapter);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        myAdapter.notifyDataSetChanged();
    }

    public static class MyAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{

        Context context;
        List<String> data;
        LayoutInflater layoutInflater;

        public MyAdapter(Context context, List<String> data){
            this.context = context;
            this.data = data;
            layoutInflater = LayoutInflater.from(context);
        }

        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new ItemViewHolder(layoutInflater.inflate(R.layout.adapter_coordinator_demo, parent, false));
        }

        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
            if (holder instanceof ItemViewHolder) {
                ((ItemViewHolder)holder).initItemView(data.get(position), position);
            }
        }

        @Override
        public int getItemCount() {
            return data.size();
        }
    }

    public static class ItemViewHolder extends RecyclerView.ViewHolder{

        public TextView tvLeftDescription;

        public ItemViewHolder(View view) {
            super(view);
            tvLeftDescription = (TextView) view.findViewById(R.id.tvLeftDescription);
        }

        public void initItemView(String desc, int position){
            tvLeftDescription.setText(desc);
        }
    }

}
