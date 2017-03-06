package com.sivin.adapter.wrapper;


import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewStub;

import com.sivin.adapter.R;
import com.sivin.adapter.base.ViewHolder;
import com.sivin.adapter.utils.WrapperUtils;


/**
 *
 * Created by Sivin on 2017/2/9.
 */
public class LoadMoreWrapper<T> extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final String TAG = "LoadMoreWrapper";

    public static final int FLAG_LOADING = 0;
    public static final int FLAG_LOAD_END = 1;
    public static final int FLAG_LOAD_ERR = 2;

    public static final int ITEM_TYPE_LOAD_MORE = Integer.MAX_VALUE - 2;

    private RecyclerView.Adapter mInnerAdapter;
    private View mRootView;
    private int mLayoutId;

    private int mLoadFlag = FLAG_LOADING;

    ViewStub mNetWorkStub;
    View mLoadingView;
    ViewStub mEndStub;

    public LoadMoreWrapper(RecyclerView.Adapter adapter) {
        mInnerAdapter = adapter;
    }

    private boolean hasLoadMore() {
        return mRootView != null || mLayoutId != 0;
    }


    private boolean isShowLoadMore(int position) {
        return hasLoadMore() && (position >= mInnerAdapter.getItemCount());
    }

    @Override
    public int getItemViewType(int position) {


        if (isShowLoadMore(position)) {

            return ITEM_TYPE_LOAD_MORE;
        }
        return mInnerAdapter.getItemViewType(position);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == ITEM_TYPE_LOAD_MORE) {
            ViewHolder holder;
            if (mRootView != null) {
                holder = ViewHolder.createViewHolder(parent.getContext(), mRootView);
            } else {
                holder = ViewHolder.createViewHolder(parent.getContext(), parent, mLayoutId);
                mRootView = holder.getConvertView();
            }

            mRootView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mLoadFlag == FLAG_LOAD_ERR) {
                        //网络错误点击从新加载
                        mNetWorkStub.setVisibility(View.GONE);
                        mLoadingView.setVisibility(View.VISIBLE);
                        mLoadFlag = FLAG_LOADING;
                        mOnLoadMoreListener.onLoadMoreRequested();
                    }
                }
            });

            return holder;
        }
        return mInnerAdapter.onCreateViewHolder(parent, viewType);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (isShowLoadMore(position)) {
            if (mOnLoadMoreListener != null && mLoadFlag == FLAG_LOADING) {
                mOnLoadMoreListener.onLoadMoreRequested();
            }



            return;
        }
        mInnerAdapter.onBindViewHolder(holder, position);
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        WrapperUtils.onAttachedToRecyclerView(mInnerAdapter, recyclerView, new WrapperUtils.SpanSizeCallback() {
            @Override
            public int getSpanSize(GridLayoutManager layoutManager, GridLayoutManager.SpanSizeLookup oldLookup, int position) {
                if (isShowLoadMore(position)) {
                    return layoutManager.getSpanCount();
                }
                if (oldLookup != null) {
                    return oldLookup.getSpanSize(position);
                }
                return 1;
            }
        });
    }


    @Override
    public void onViewAttachedToWindow(RecyclerView.ViewHolder holder) {
        mInnerAdapter.onViewAttachedToWindow(holder);

        if (isShowLoadMore(holder.getLayoutPosition())) {
            setFullSpan(holder);
        }
    }

    private void setFullSpan(RecyclerView.ViewHolder holder) {
        ViewGroup.LayoutParams lp = holder.itemView.getLayoutParams();

        if (lp != null && lp instanceof StaggeredGridLayoutManager.LayoutParams) {
            StaggeredGridLayoutManager.LayoutParams p = (StaggeredGridLayoutManager.LayoutParams) lp;

            p.setFullSpan(true);
        }
    }

    @Override
    public int getItemCount() {
        return mInnerAdapter.getItemCount() + (hasLoadMore() ? 1 : 0);
    }


    public interface OnLoadMoreListener {
        void onLoadMoreRequested();
    }

    private OnLoadMoreListener mOnLoadMoreListener;

    public LoadMoreWrapper setOnLoadMoreListener(OnLoadMoreListener loadMoreListener) {
        if (loadMoreListener != null) {
            mOnLoadMoreListener = loadMoreListener;
        }
        return this;
    }

    public LoadMoreWrapper setLoadMoreView(View loadMoreView) {
        mRootView = loadMoreView;
        return this;
    }

    public LoadMoreWrapper setLoadMoreView(int layoutId) {
        mLayoutId = layoutId;
        return this;
    }

    public void loadEnd() {
        if (mLoadFlag == FLAG_LOAD_END)
            return;
        if (mLoadingView == null) {
            mLoadingView = mRootView.findViewById(R.id.loading_view);
        }
        mLoadingView.setVisibility(View.GONE);
        mEndStub = (ViewStub) mRootView.findViewById(R.id.end_viewstub);
        mEndStub.inflate();
        mLoadFlag = FLAG_LOAD_END;
    }

    public void loadErro() {

        if (mLoadFlag == FLAG_LOAD_ERR) {
            return;
        }

        if (mLoadingView == null) {
            mLoadingView = mRootView.findViewById(R.id.loading_view);
        }
        mLoadingView.setVisibility(View.GONE);

        if (mNetWorkStub == null) {
            mNetWorkStub = (ViewStub) mRootView.findViewById(R.id.network_error_viewstub);
            mNetWorkStub.inflate();
        } else {
            mNetWorkStub.setVisibility(View.VISIBLE);
        }

        mLoadFlag = FLAG_LOAD_ERR;

    }
}
