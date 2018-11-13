package com.tomclaw.minion.demo.adapters;

import android.content.Context;

import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.tomclaw.minion.demo.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by solkin on 03.08.17.
 */
public class SectionsRecyclerAdapter extends RecyclerView.Adapter<SectionViewHolder> {

    private final Context context;
    private final List<SectionItem> list;

    private OnItemClickListener clickListener;

    public SectionsRecyclerAdapter(Context context) {
        this.context = context;
        this.list = new ArrayList<>();
    }

    public void setItems(List<SectionItem> items) {
        list.clear();
        list.addAll(items);
    }

    public void setClickListener(OnItemClickListener clickListener) {
        this.clickListener = clickListener;
    }

    @Override
    public SectionViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.section_item, parent, false);
        return new SectionViewHolder(view);
    }

    @Override
    public void onBindViewHolder(SectionViewHolder holder, int position) {
        SectionItem item = list.get(position);
        holder.bind(item, clickListener);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
}
