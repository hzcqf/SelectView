package com.test.chenqifan.library;

import android.databinding.DataBindingUtil;
import android.databinding.adapters.AdapterViewBindingAdapter;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.test.chenqifan.library.databinding.ItemSelectBinding;

import java.util.List;

/**
 * @author chenqifan
 * @Description: $description
 * @date 2018/3/22 下午2:53
 */
public class SelectAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<String> data;
    private String name = "";
    private OnItemClick onItemClick;


    interface OnItemClick {
        void onItemClick(String name);
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setOnItemClick(OnItemClick onItemClick) {
        this.onItemClick = onItemClick;
    }

    public SelectAdapter(List<String> data) {
        this.data = data;
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        ItemSelectBinding binding = DataBindingUtil.inflate(inflater, R.layout.item_select, parent, false);
        ViewHolder viewHolder = new ViewHolder(binding.getRoot());
        viewHolder.setBinding(binding);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ((ViewHolder) holder).bindHolder(data.get(position));
    }


    class ViewHolder extends RecyclerView.ViewHolder {
        private ItemSelectBinding mBinding;
        public void setBinding(ItemSelectBinding mBinding) {
            this.mBinding = mBinding;
        }

        public ViewHolder(View itemView) {
            super(itemView);

        }

        public void bindHolder(String data) {
            mBinding.name.setText(data);
            if (name.equals(data)) {
                mBinding.ifvYes.setVisibility(View.VISIBLE);
            } else {
                mBinding.ifvYes.setVisibility(View.GONE);
            }
            mBinding.recyclerView.setOnClickListener(v -> {
                if (onItemClick != null) {
                    onItemClick.onItemClick(data);
                }
            });
        }
    }
}

