package com.gui.materialdesign;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.gui.lgrecycleviewdecorator.LGRecycleViewDecorator;
import com.gui.listener.OnLoadMoreListener;
import com.gui.model.User;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class LoadMoreActivity extends AppCompatActivity {
    private String TAG = "LoadMoreActivity";
    @BindView(R.id.recyclerview)
    RecyclerView mRecyclerView;
    private List<User> mUsers = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_load_more);
        ButterKnife.bind(this);

        for (int i = 0; i < 3; i++) {
            User user = new User();
            user.setName("Name " + i);
            user.setEmail("alibaba" + i + "@gmail.com");
            mUsers.add(user);
        }

        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        final UserAdapter userAdapter = new UserAdapter(mUsers);
        mRecyclerView.setAdapter(userAdapter);
        mRecyclerView.addItemDecoration(new LGRecycleViewDecorator(this));
        userAdapter.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore() {
                Log.d(TAG, "onLoadMore");
                Toast.makeText(LoadMoreActivity.this, "load more", Toast.LENGTH_SHORT).show();
//                userAdapter.setLoaded();
                mRecyclerView.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        for (int i = 0; i < 2; i++) {
                            User user = new User();
                            user.setName("load more Name " + i);
                            user.setEmail("alibaba" + i + "@gmail.com");
                            mUsers.add(user);
                        }
                        userAdapter.setStates(UserAdapter.STATES_LOAD_SUCCESS);
                        userAdapter.notifyDataSetChanged();
                    }
                }, 1000);
            }
        });
    }

    private class UserAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
        private final int VIEW_TYPE_ITEM = 0;
        private final int VIEW_TYPE_LOADING = 1;
        private OnLoadMoreListener mOnLoadMoreListener;
        private List<User> mUsers;
        private int totalItemCount;
        private int lastVisibleItem;
        private boolean loadOver = true;
        private int visibleThreshold = 0;
        private int previousTotal = 0;
        public static final int STATES_NOT_FULL = 0;
        public static final int STATES_LOAD_SUCCESS = 1;
        public static final int STATES_LOAD_FAILED = 2;
        public static final int STATES_CLICK_TO_LOADMORE = 3;
        public static final int STATES_LOADING = 4;
        public static final int STATES_EMPTY = 5;
        private int states = STATES_LOADING;
        private int parentHeight;
        private boolean loading = true;
        private boolean inFullMode = false;

        public UserAdapter(List<User> users) {
            this.mUsers = users;
            final LinearLayoutManager linearLayoutManager = (LinearLayoutManager) mRecyclerView.getLayoutManager();
            mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
                @Override
                public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                    super.onScrolled(recyclerView, dx, dy);
                    int scrollState = recyclerView.getScrollState();
//                    Log.d(TAG, "scrollState:" + scrollState);
                    int verticalOffsetY = recyclerView.computeVerticalScrollOffset();
                    int verticalScrollRange = recyclerView.computeVerticalScrollRange();
                    parentHeight = recyclerView.getHeight();
                    //Log.d(TAG, "verticalOffsetY:" + verticalOffsetY + " verticalScrollRange:" + verticalScrollRange + " height:" + height);

                    int childCount = linearLayoutManager.getChildCount();
                    totalItemCount = linearLayoutManager.getItemCount();
                    lastVisibleItem = linearLayoutManager.findLastVisibleItemPosition();
                    int firstVisibleItem = linearLayoutManager.findFirstVisibleItemPosition();
                    int visibleItemCount = recyclerView.getChildCount();
                    int scrollStatestate = recyclerView.getScrollState();
                    Log.d(TAG, "firstVisibleItem:" + firstVisibleItem +
                            " visibleItemCount:" + visibleItemCount +
                            " totalItemCount:" + totalItemCount +
                            " lastVisibleItem:" + lastVisibleItem +
                            " childCount:" + childCount);
                    View lastChild = recyclerView.getChildAt(childCount - 1);
                    if (lastChild != null && !inFullMode) {
                        int lastChildBottom = lastChild.getBottom();
                        int paddingBottom = lastChild.getPaddingBottom();
                        int lastChildTop = lastChild.getTop();
                        if (getItemViewType(childCount - 1) == VIEW_TYPE_LOADING) {
                            if (lastChildTop < recyclerView.getHeight()) {
                                inFullMode = false;
                                if (states != STATES_LOAD_SUCCESS)
                                    setStates(STATES_NOT_FULL);
                            }
                        } else {
                            inFullMode = true;
                        }
                        return;
                    }

                    if (loading) {
                        if (totalItemCount > previousTotal) {
                            //说明数据已经加载结束
                            loading = false;
                            previousTotal = totalItemCount;
                        }
                    }
                    //这里需要好好理解
                    if (!loading && totalItemCount - visibleItemCount <= firstVisibleItem) {
                        loadMore();
                        loading = true;
                    }
                }
            });
        }

        public void setOnLoadMoreListener(OnLoadMoreListener mOnLoadMoreListener) {
            this.mOnLoadMoreListener = mOnLoadMoreListener;
        }

        private void loadMore() {
            if (mOnLoadMoreListener != null) {
                setStates(STATES_LOADING);
                mOnLoadMoreListener.onLoadMore();
            }
        }

        @Override
        public int getItemViewType(int position) {
            if (position >= mUsers.size())
                return VIEW_TYPE_LOADING;
            return VIEW_TYPE_ITEM;
        }

        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            Log.d(TAG, "onCreateViewHolder");
            if (viewType == VIEW_TYPE_ITEM) {
                View view = LayoutInflater.from(LoadMoreActivity.this).inflate(R.layout.load_more_item, parent, false);
                return new UserViewHolder(view);
            } else if (viewType == VIEW_TYPE_LOADING) {
                View view = LayoutInflater.from(LoadMoreActivity.this).inflate(R.layout.loading_item, parent, false);
                return new LoadingViewHolder(view);
            }
            return null;
        }

        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
            Log.d(TAG, "onBindViewHolder pos:" + position);
            if (holder instanceof UserViewHolder) {
                User user = mUsers.get(position);
                UserViewHolder userViewHolder = (UserViewHolder) holder;
                userViewHolder.tvName.setText(user.getName());
                userViewHolder.tvEmailId.setText(user.getEmail());
            } else if (holder instanceof LoadingViewHolder) {
                final LoadingViewHolder loadingViewHolder = (LoadingViewHolder) holder;
                loadingViewHolder.progressBar.setIndeterminate(true);
                if (states == STATES_LOADING) {
                    loadingViewHolder.progressBar.setVisibility(View.VISIBLE);
                    loadingViewHolder.tips.setText("正在加载");
                } else if (states == STATES_EMPTY) {
                    loadingViewHolder.progressBar.setVisibility(View.GONE);
                    loadingViewHolder.tips.setText("--end--");
                    loadingViewHolder.tips.setOnClickListener(null);
                } else if (states == STATES_LOAD_SUCCESS) {
                    loadingViewHolder.progressBar.setVisibility(View.GONE);
                    loadingViewHolder.tips.setText("加载成功,点击加载更多");
                    loadingViewHolder.tips.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Log.d(TAG, "click to load more");
                            loadingViewHolder.tips.setText("正在加载");
                            loadingViewHolder.tips.setOnClickListener(null);
                            loadMore();
                        }
                    });
                } else if (states == STATES_LOAD_FAILED) {
                    loadingViewHolder.tips.setText("加载失败,点击重试");
                    loadingViewHolder.progressBar.setVisibility(View.GONE);
                    loadingViewHolder.tips.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Log.d(TAG, "click to load more");
                            loadingViewHolder.tips.setText("正在加载");
                            loadingViewHolder.tips.setOnClickListener(null);
                            loadMore();
                        }
                    });
                } else if (states == STATES_NOT_FULL) {
                    loadingViewHolder.progressBar.setVisibility(View.GONE);
                    loadingViewHolder.tips.setText("点击加载更多");
                    loadingViewHolder.tips.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Log.d(TAG, "click to load more");
                            loadingViewHolder.tips.setText("正在加载");
                            loadingViewHolder.tips.setOnClickListener(null);
                            loadMore();
                        }
                    });
                } else {
                    loadingViewHolder.root.setVisibility(View.GONE);
                }
            }
        }

        public int getStates() {
            return states;
        }

        public void setStates(int states) {
            if (this.states != states) {
                this.states = states;
                notifyDataSetChanged();
            }
        }

        @Override
        public int getItemCount() {
            if (mUsers == null || mUsers.size() == 0) {
                return 0;
            }
            return mUsers.size() + 1;
        }

        public void setLoaded() {
            loadOver = false;
        }
    }

    static class LoadingViewHolder extends RecyclerView.ViewHolder {
        public ProgressBar progressBar;
        public TextView tips;
        public View root;

        public LoadingViewHolder(View itemView) {
            super(itemView);
            root = itemView;
            tips = (TextView) itemView.findViewById(R.id.state_tips);
            progressBar = (ProgressBar) itemView.findViewById(R.id.progressBar);
        }
    }

    static class UserViewHolder extends RecyclerView.ViewHolder {
        public TextView tvName;
        public TextView tvEmailId;

        public UserViewHolder(View itemView) {
            super(itemView);
            tvName = (TextView) itemView.findViewById(R.id.tvName);

            tvEmailId = (TextView) itemView.findViewById(R.id.tvEmailId);
        }
    }
}
