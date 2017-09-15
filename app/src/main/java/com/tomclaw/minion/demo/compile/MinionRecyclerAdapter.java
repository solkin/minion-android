package com.tomclaw.minion.demo.compile;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.tomclaw.minion.IniGroup;
import com.tomclaw.minion.IniRecord;
import com.tomclaw.minion.Minion;
import com.tomclaw.minion.demo.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by solkin on 03.09.17.
 */
@SuppressWarnings("WeakerAccess")
public class MinionRecyclerAdapter extends RecyclerView.Adapter<MinionViewHolder> {

    private static final int TYPE_GROUP = 0x01;
    private static final int TYPE_RECORD = 0x02;

    private
    @NonNull
    List<MinionItem> items;

    private LayoutInflater layoutInflater;

    private
    @Nullable
    GroupListener groupListener;
    private
    @Nullable
    RecordListener recordListener;

    public MinionRecyclerAdapter(Context context) {
        this.layoutInflater = LayoutInflater.from(context);
        items = new ArrayList<>();
    }

    public synchronized void setData(Minion minion) {
        items.clear();
        for (IniGroup group : minion.getGroups()) {
            items.add(new MinionItem(group));
            for (IniRecord record : group.getRecords()) {
                items.add(new MinionItem(group, record));
            }
        }
    }

    public void setGroupListener(@Nullable GroupListener groupListener) {
        this.groupListener = groupListener;
    }

    public void setRecordListener(@Nullable RecordListener recordListener) {
        this.recordListener = recordListener;
    }

    @SuppressLint("DefaultLocale")
    @Override
    public MinionViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;
        switch (viewType) {
            case TYPE_GROUP:
                view = layoutInflater.inflate(R.layout.group_item, parent, false);
                return new GroupViewHolder(view, groupListener);
            case TYPE_RECORD:
                view = layoutInflater.inflate(R.layout.record_item, parent, false);
                return new RecordViewHolder(view, recordListener);
            default:
                throw new IllegalArgumentException(String.format("Type %d is not supported", viewType));
        }
    }

    @Override
    public void onBindViewHolder(MinionViewHolder holder, int position) {
        MinionItem item = items.get(position);
        holder.bind(item);
    }

    @Override
    public int getItemViewType(int position) {
        return items.get(position).isGroup() ? TYPE_GROUP : TYPE_RECORD;
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

}
