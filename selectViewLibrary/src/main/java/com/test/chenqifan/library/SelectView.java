package com.test.chenqifan.library;

import android.app.Activity;
import android.content.Context;
import android.databinding.DataBindingUtil;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.os.Build;
import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.PopupWindow;


import com.test.chenqifan.library.databinding.CustomSelectBinding;

import java.util.ArrayList;
import java.util.List;

import static android.view.ViewGroup.LayoutParams.WRAP_CONTENT;

/**
 * @author chenqifan
 * @Description: $description
 * @date 2018/3/22 下午2:35
 */
public class SelectView extends PopupWindow {
    private CustomSelectBinding mBinding;
    private View view;
    private Activity mContext;
    private String name = "";
    private ItemClickListener itemClickListener;
    private List<String> titiles = new ArrayList<>();
    private List<List<String>> lists = new ArrayList<>();

    private SelectAdapter selectAdapter;
    private List<String> datas = new ArrayList<>();

    public interface ItemClickListener {
        void itemClickListener(String name);
    }

    public void setItemClickListener(ItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }

    public void setName(String name) {
        selectAdapter.setName(name);
        selectAdapter.notifyDataSetChanged();
    }

    //区域的选择
    public SelectView(FragmentActivity mContext) {
        this.mContext = mContext;
        mBinding = DataBindingUtil.inflate(LayoutInflater.from(mContext), R.layout.custom_select, null, false);
        view = mBinding.getRoot();
        setContentView(view);
        setWidth(WindowManager.LayoutParams.MATCH_PARENT);
        setHeight(WRAP_CONTENT);
        setBackgroundDrawable(new BitmapDrawable());
        setFocusable(false);
        setOutsideTouchable(true);
        setTouchable(true);
        setTouchInterceptor((view, motionEvent) -> {
            if (motionEvent.getAction() == MotionEvent.ACTION_OUTSIDE) {
                return true;
            }
            return false;
        });
        setAnimationStyle(R.style.take_photo_anim);
        initEvents();
    }

    @Override
    public void setContentView(View contentView) {
        if (contentView != null) {
            super.setContentView(contentView);
            contentView.setFocusableInTouchMode(true);
            contentView.setOnKeyListener((view, keyCode, keyEvent) -> {
                switch (keyCode) {
                    case KeyEvent.KEYCODE_BACK:
                        dismiss();
                        return true;
                    default:
                        break;
                }
                return false;
            });
        }
    }

    private void initEvents() {
        selectAdapter = new SelectAdapter(datas);
        mBinding.recyclerView.setLayoutManager(new LinearLayoutManager(mContext));
        mBinding.recyclerView.setAdapter(selectAdapter);
        selectAdapter.setOnItemClick(name1 -> {
            if (itemClickListener != null) {
                itemClickListener.itemClickListener(name1);
                dismiss();
            }
        });
        mBinding.tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                if (lists.size() > 0) {
                    datas.clear();
                    datas.addAll(lists.get(tab.getPosition()));
                    selectAdapter.notifyDataSetChanged();
                    setHeight(lists.get(tab.getPosition()));
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
        mBinding.hiddenView.setOnClickListener(view1 -> {
            dismiss();
        });
    }

    private void setHeight(List<String> list) {
        if (list.size() > 5) {
            ViewGroup.LayoutParams layoutParams = mBinding.recyclerView.getLayoutParams();
            layoutParams.height = dip2px(mContext, 200);
            mBinding.recyclerView.setLayoutParams(layoutParams);
        } else {
            ViewGroup.LayoutParams layoutParams = mBinding.recyclerView.getLayoutParams();
            layoutParams.height = WRAP_CONTENT;
            mBinding.recyclerView.setLayoutParams(layoutParams);
        }
    }

    @Override
    public void showAsDropDown(View anchor) {
        if (Build.VERSION.SDK_INT >= 24) {
            Rect visibleFrame = new Rect();
            anchor.getGlobalVisibleRect(visibleFrame);
            int height = anchor.getResources().getDisplayMetrics().heightPixels - visibleFrame.bottom;
            setHeight(height);
        }
        super.showAsDropDown(anchor);
    }

    public void addData(String titile, List<String> list) {
        mBinding.tabLayout.addTab(mBinding.tabLayout.newTab().setText(titile));
        this.titiles.add(titile);
        this.lists.add(list);
        datas.clear();
        datas.addAll(lists.get(0));
        selectAdapter.notifyDataSetChanged();
        mBinding.tabLayout.getTabAt(0).select();
        setHeight(lists.get(0));
        if (titiles.size() > 1) {
            mBinding.tabLayout.setVisibility(View.VISIBLE);
        } else {
            mBinding.tabLayout.setVisibility(View.GONE);
        }
    }

    public void deleteData() {
        if (this.titiles.size() > 1) {
            mBinding.tabLayout.removeTab(mBinding.tabLayout.getTabAt(titiles.size() - 1));
            this.titiles.remove(titiles.size() - 1);
            this.lists.remove(lists.size() - 1);
            datas.clear();
            datas.addAll(lists.get(0));
            selectAdapter.notifyDataSetChanged();
            mBinding.tabLayout.getTabAt(0).select();
            setHeight(lists.get(0));
            if (titiles.size() > 1) {
                mBinding.tabLayout.setVisibility(View.VISIBLE);
            } else {
                mBinding.tabLayout.setVisibility(View.GONE);
            }
        }
    }

    public static int dip2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }
}
