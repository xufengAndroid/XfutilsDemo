package com.nj.xufeng.xfutils.widget;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.nj.xufeng.xfutils.utils.ScreenUtils;
import com.orhanobut.logger.Logger;


/**
 * Created by xufeng on 15/11/5.
 */
public class LUmLayout extends RelativeLayout {


    public final static byte PTR_STATUS_INIT = 1;
    private byte mStatus = PTR_STATUS_INIT;
    public final static byte PTR_STATUS_PREPARE = 2;
    public final static byte PTR_STATUS_LOADING = 3;
    public final static byte PTR_STATUS_COMPLETE = 4;


    private LmHandler lmHandler;
    private LUmHandler lUmHandler;


    private View mContent;
    private View mFoot;
    private View mTop;


    private boolean mEnabledFoot;
    private boolean mEnabledTop;
//    private RecyclerView mRecyclerView;

    public LUmLayout(Context context) {
        super(context);
    }

    public LUmLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public LUmLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public void setRecyclerView(RecyclerView recyclerView) {
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {


            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if (!mEnabledTop && !mEnabledFoot) {
                    return;
                }
                LinearLayoutManager layoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();
                if (layoutManager.findLastCompletelyVisibleItemPosition() == layoutManager.getItemCount() - 1) {
                    if (!mEnabledFoot) {
                        return;
                    }
                    mFoot.setVisibility(VISIBLE);
                    if (null != lmHandler && mStatus == PTR_STATUS_COMPLETE) {
                        mStatus = PTR_STATUS_PREPARE;
                        lmHandler.onRefreshBegin(LUmLayout.this, layoutManager.getItemCount());
                        mStatus = PTR_STATUS_LOADING;
                    }
                } else if (layoutManager.findFirstCompletelyVisibleItemPosition() == 0) {
                    if (!mEnabledTop) {
                        return;
                    }
                    mTop.setVisibility(VISIBLE);
                    if (null != lUmHandler && mStatus == PTR_STATUS_COMPLETE) {
                        mStatus = PTR_STATUS_PREPARE;
                        lUmHandler.onRefreshBegin(LUmLayout.this, layoutManager.getItemCount());
                        mStatus = PTR_STATUS_LOADING;
                    }
                } else {
                    mFoot.setVisibility(GONE);
                    mTop.setVisibility(GONE);
                }
//                Logger.d(layoutManager.findLastCompletelyVisibleItemPosition() + "|" + layoutManager.getItemCount());
            }


        });
    }

    public boolean enabledTop(int needRow, int currentRow) {
//        Logger.d("needRow:"+needRow+",currentRow:"+currentRow);
        if (currentRow < needRow) {
            setEnabledTop(false);
            return false;
        }
        setEnabledTop(true);
        return true;
    }

    public boolean enabledFoot(int needRow, int currentRow) {
//        Logger.d("needRow:"+needRow+",currentRow:"+currentRow);
        if (currentRow < needRow) {
            setEnabledFoot(false);
            return false;
        }
        setEnabledFoot(true);
        return true;
    }

    public void setEnabledFoot(boolean enabledFoot) {
        mEnabledFoot = enabledFoot;
    }

    public void setEnabledTop(boolean mEnabledTop) {
        this.mEnabledTop = mEnabledTop;
    }

    public void refreshComplete() {
        mStatus = PTR_STATUS_COMPLETE;
        mFoot.setVisibility(GONE);
        mTop.setVisibility(GONE);
    }

    public void setLmHandler(LmHandler lmHandler) {
        this.lmHandler = lmHandler;
    }

    public void setlUmHandler(LUmHandler lUmHandler) {
        this.lUmHandler = lUmHandler;
    }

    public interface LmHandler {


        public boolean checkCanDoRefresh(final LUmLayout frame, final View content, final View foot);

        /**
         * When refresh begin
         *
         * @param frame
         */
        public void onRefreshBegin(final LUmLayout frame, int count);


    }

    public interface LUmHandler {


        public boolean checkCanDoRefresh(final LUmLayout frame, final View content, final View foot);

        /**
         * When refresh begin
         *
         * @param frame
         */
        public void onRefreshBegin(final LUmLayout frame, int count);


    }


    public void addTopView(View view) {
        mTop = view;
        LayoutParams layoutParams = new LayoutParams(LayoutParams.MATCH_PARENT, ScreenUtils.dpToPxInt(getContext(), 30));
        layoutParams.addRule(RelativeLayout.ALIGN_PARENT_TOP, RelativeLayout.TRUE);
        layoutParams.addRule(RelativeLayout.CENTER_HORIZONTAL, RelativeLayout.TRUE);
//        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, ScreenUtils.dpToPxInt(getContext(), 30));
        addView(mTop, layoutParams);
        mStatus = PTR_STATUS_COMPLETE;
        view.setVisibility(GONE);
    }

    public void addFootView(View view) {
        mFoot = view;
        LayoutParams layoutParams = new LayoutParams(LayoutParams.MATCH_PARENT, ScreenUtils.dpToPxInt(getContext(), 30));
        layoutParams.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM, RelativeLayout.TRUE);
        layoutParams.addRule(RelativeLayout.CENTER_HORIZONTAL, RelativeLayout.TRUE);
//        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, ScreenUtils.dpToPxInt(getContext(), 30));
        addView(mFoot, layoutParams);
        mStatus = PTR_STATUS_COMPLETE;
        view.setVisibility(GONE);
    }


    @Override
    protected void onFinishInflate() {
        final int childCount = getChildCount();
        if (childCount > 2) {
            throw new IllegalStateException("LmLinearLayout only can host 2 elements");
        } else if (childCount == 1) {
            mContent = getChildAt(0);
            findRecyclerView(mContent);
        } else {
            TextView errorView = new TextView(getContext());
            errorView.setClickable(true);
            errorView.setTextColor(0xffff6600);
            errorView.setGravity(Gravity.CENTER);
            errorView.setTextSize(20);
            errorView.setText("The content view in PtrFrameLayout is empty. Do you forget to specify its id in xml layout file?");
            mContent = errorView;
            addView(mContent);
        }
        super.onFinishInflate();
    }

    public void findRecyclerView(View view) {
        if (view instanceof RecyclerView) {
            setRecyclerView((RecyclerView) view);
        } else if (view instanceof ViewGroup) {
            for (int i = 0; i < ((ViewGroup) view).getChildCount(); i++) {
                View v = ((ViewGroup) view).getChildAt(i);
                findRecyclerView(v);
            }
        }
    }


}
