package com.test.chenqifan.selectview;

import android.databinding.DataBindingUtil;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.ViewGroup;

import com.test.chenqifan.library.SelectView;
import com.test.chenqifan.selectview.databinding.ActivityMainBinding;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private ActivityMainBinding mBinding;
    private SelectView selectView;
    private List<String> list;
    private List<String> list2;
    private int i = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_main);

        selectView = new SelectView(this);

        list = new ArrayList<>();
        list.add("11111");
        list.add("22222");
        selectView.addData("111", list);

        mBinding.btn.setOnClickListener(v -> {
            if (selectView.isShowing()) {
                selectView.dismiss();
            } else {
                selectView.setName(mBinding.btn.getText().toString());
                selectView.showAsDropDown(mBinding.btn, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
            }
        });
        selectView.setItemClickListener(name -> {
            mBinding.btn.setText(name);
        });

        mBinding.btn2.setOnClickListener(v -> {
            list2 = new ArrayList<>();
            list2.add("AAAA" + String.valueOf(i));
            list2.add("BBBB" + String.valueOf(i));
            list2.add("CCCC" + String.valueOf(i));
            list2.add("DDDD" + String.valueOf(i));
            list2.add("EEEE" + String.valueOf(i));
            list2.add("FFFF" + String.valueOf(i));
            list2.add("GGGG" + String.valueOf(i));
            list2.add("HHHH" + String.valueOf(i));
            selectView.addData("aa" + String.valueOf(i), list2);
            i++;
        });

        mBinding.btn3.setOnClickListener(v -> {
            selectView.deleteData();
        });
    }
}
