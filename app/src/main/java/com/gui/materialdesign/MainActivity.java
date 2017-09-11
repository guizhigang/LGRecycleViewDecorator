package com.gui.materialdesign;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.gui.lgrecycleviewdecorator.LGRecycleViewDecorator;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {
    @BindView(R.id.root)
    View root;

    @BindView(R.id.drawer_layer)
    DrawerLayout drawerLayout;
    @BindView(R.id.nav_view)
    NavigationView navigationView;
    private String TAG = "MainActivity";
    @BindView(R.id.recyclerview)
    RecyclerView recyclerView;

    @BindView(R.id.image_test)
    ImageView imageView;

    private SimpleRecycleViewAdapter simpleRecycleViewAdapter;

    private List<String> valuesTes = new ArrayList();
    private static String[] imageUrls = new String[]{
            "http://d.hiphotos.bdimg.com/album/whcrop%3D657%2C370%3Bq%3D90/sign=2c994e578a82b9013df895711cfd9441/09fa513d269759eede0805bbb2fb43166d22df62.jpg",
            "http://pic2.ooopic.com/12/32/19/90bOOOPIC39_1024.jpg",
            "http://pic4.nipic.com/20091121/3764872_215617048242_2.jpg",
            "http://img.taopic.com/uploads/allimg/121126/240453-1211261I64263.jpg"
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        Glide.with(this).load("http://image.wenwan.gnsit.cn/image/2016/11/13/1782a").dontAnimate().into(imageView);

        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);
        //去除title
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayShowTitleEnabled(false);
        actionBar.setDisplayHomeAsUpEnabled(true);

        if (navigationView != null) {
            setUpDrawerLayout(navigationView);
        }

        /////recyclerView/////
        for (int i = 1; i <= 20; ++i) {
            valuesTes.add("TestData" + i);
        }
//        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setLayoutManager(new GridLayoutManager(this, 3));
        LGRecycleViewDecorator lgRecycleViewDecorator = new LGRecycleViewDecorator(this, R.drawable.list_divider);
        lgRecycleViewDecorator.setmInsets(5,10,20,20);
        recyclerView.addItemDecoration(lgRecycleViewDecorator);
        //adapter
        simpleRecycleViewAdapter = new SimpleRecycleViewAdapter(this, valuesTes);
        simpleRecycleViewAdapter.setSimpleListener(new SimpleRecycleViewAdapter.SimpleListener() {
            @Override
            public void onClick(int position) {
                valuesTes.remove(position);
                simpleRecycleViewAdapter.notifyItemRemoved(position);
            }
        });
        recyclerView.setAdapter(simpleRecycleViewAdapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu,menu);
        return true;
    }


    private void setUpDrawerLayout(NavigationView navigationView) {
        navigationView.setItemIconTintList(null);
        drawerLayout.addDrawerListener(new DrawerLayout.DrawerListener() {
            @Override
            public void onDrawerSlide(View drawerView, float slideOffset) {
                Log.d(TAG, "onDrawerSlide");
            }

            @Override
            public void onDrawerOpened(View drawerView) {
                Log.d(TAG, "onDrawerOpened");
            }

            @Override
            public void onDrawerClosed(View drawerView) {
                Log.d(TAG, "onDrawerClosed");
            }

            @Override
            public void onDrawerStateChanged(int newState) {
                Log.d(TAG, "onDrawerStateChanged");
            }
        });
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem item) {
                item.setChecked(true);
                drawerLayout.closeDrawers();
                switch (item.getItemId()) {
                    case R.id.menu_navigation_item_1:
                        Snackbar.make(root, "you clicked " + item.getTitle().toString(), Snackbar.LENGTH_INDEFINITE)
                                .setAction("Action", new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        Log.d(TAG, "Snackbar onClicked...");
                                    }
                                }).show();
                        return true;
                    case R.id.menu_navigation_item_2:

                        return true;
                    case R.id.menu_group2_navigation_item_1:

                        return true;
                    case R.id.menu_group2_navigation_item_2:

                        return true;
                    case R.id.menu_nav_link1:

                        return true;
                    case R.id.menu_nav_link2:

                        return true;
                    case R.id.menu_nav_link11:

                        return true;
                    case R.id.menu_nav_link22:

                        return true;
                }
                return false;
            }
        });
    }

    @OnClick(R.id.image_test)
    public void onClickImageTest1(View view) {
        //recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        startActivity(new Intent(this,MyViewGroupActivity.class));
    }

    @OnClick(R.id.image_test2)
    public void onClickImageTest2(View view) {
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
    }

    /**
     * 点击toolbar的icon弹出drawerLayout
     *
     * @param item
     * @return
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                drawerLayout.openDrawer(GravityCompat.START);
                return true;
            case R.id.gride:
                recyclerView.setLayoutManager(new GridLayoutManager(this, 3));
                return true;
            case R.id.line_horizontal:
                recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
                return true;
            case R.id.line_veritcal:
                recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
                return true;
            case R.id.stagger:
                recyclerView.setLayoutManager(new StaggeredGridLayoutManager(4,StaggeredGridLayoutManager.VERTICAL));
                return true;
            case R.id.load_more_test:
                startActivity(new Intent(this,LoadMoreActivity.class));
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @OnClick(R.id.fab)
    public void onClickFab(View view) {
        Snackbar.make(view, "you clicked floating bar", Snackbar.LENGTH_SHORT)
                .setAction("Done", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Log.d(TAG, "Snackbar onClicked...");
                    }
                }).show();
        valuesTes.add("Test");
        simpleRecycleViewAdapter.notifyDataSetChanged();
    }

    public static class SimpleRecycleViewAdapter extends RecyclerView.Adapter<SimpleRecycleViewAdapter.ViewHolder> {
        private List<String> mValues;
        private Context context;
        private SimpleListener simpleListener;

        public interface SimpleListener {
            void onClick(int position);
        }

        public SimpleRecycleViewAdapter(Context context, List<String> items) {
            this.context = context;
            mValues = items;
        }

        public void setSimpleListener(SimpleListener simpleListener) {
            this.simpleListener = simpleListener;
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item, parent, false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(final ViewHolder holder, final int position) {
            holder.textView.setText(mValues.get(position));
            Glide.with(holder.avatarView.getContext())
                    .load(imageUrls[position % imageUrls.length])
                    .placeholder(R.mipmap.ic_launcher)
                    .centerCrop()
                    .into(holder.avatarView);
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.d("ViewAdapter", "onBindViewHolder:" + position + " adpaterPosition:" + holder.getAdapterPosition());
                    if (simpleListener != null && holder.getAdapterPosition() != -1) {
                        simpleListener.onClick(holder.getAdapterPosition());
                    }
                }
            });
        }

        @Override
        public int getItemCount() {
            return mValues.size();
        }

        public static class ViewHolder extends RecyclerView.ViewHolder {
            @BindView(R.id.avatar)
            public ImageView avatarView;

            @BindView(R.id.text)
            public TextView textView;

            public View itemView;

            public ViewHolder(View itemView) {
                super(itemView);
                ButterKnife.bind(this, itemView);
                this.itemView = itemView;
            }
        }
    }
}
