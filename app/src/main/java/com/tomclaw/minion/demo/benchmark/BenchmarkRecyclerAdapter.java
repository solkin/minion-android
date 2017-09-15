package com.tomclaw.minion.demo.benchmark;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.tomclaw.minion.demo.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by solkin on 03.08.17.
 */
class BenchmarkRecyclerAdapter extends RecyclerView.Adapter<BenchmarkItemViewHolder> {

    private final Context context;
    private final List<BenchmarkItem> list;

    BenchmarkRecyclerAdapter(Context context) {
        this.context = context;
        this.list = new ArrayList<>();
        setHasStableIds(true);
    }

    int appendItem(BenchmarkItem item) {
        list.add(item);
        return list.size() - 1;
    }

    @Override
    public BenchmarkItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.benchmark_item, parent, false);
        return new BenchmarkItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(BenchmarkItemViewHolder holder, int position) {
        BenchmarkItem item = list.get(position);
        holder.bind(item);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    @Override
    public long getItemId(int position) {
        return list.get(position).getId();
    }
}
