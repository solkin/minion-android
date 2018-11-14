package com.tomclaw.minion.demo.compile;

import android.content.res.Resources;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import android.view.View;
import android.widget.TextView;

import com.tomclaw.minion.IniGroup;
import com.tomclaw.minion.demo.R;

/**
 * Created by solkin on 03.09.17.
 */
class GroupViewHolder extends MinionViewHolder {

    @Nullable
    private final GroupListener groupListener;

    @NonNull
    private final TextView name;
    @NonNull
    private final TextView description;
    @NonNull
    private final View insert;
    @NonNull
    private final View delete;

    GroupViewHolder(@NonNull View itemView, @Nullable GroupListener groupListener) {
        super(itemView);
        this.groupListener = groupListener;
        this.name = itemView.findViewById(R.id.name);
        this.description = itemView.findViewById(R.id.description);
        this.insert = itemView.findViewById(R.id.insert);
        this.delete = itemView.findViewById(R.id.delete);
    }

    @Override
    public void bind(@NonNull MinionItem item) {
        final IniGroup group = item.getGroup();
        if (group == null) {
            throw new IllegalStateException("IniGroup might not be null");
        }
        Resources resources = itemView.getResources();
        name.setText(group.getName());
        description.setText(resources.getString(R.string.records_count, group.getRecordsCount()));
        insert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (groupListener != null) {
                    groupListener.onInsertRecord(group);
                }
            }
        });
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (groupListener != null) {
                    groupListener.onGroupDelete(group);
                }
            }
        });
    }
}
