package com.tomclaw.minion.demo.compile;

import android.content.res.Resources;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.TextView;

import com.tomclaw.minion.IniGroup;
import com.tomclaw.minion.demo.R;

/**
 * Created by solkin on 03.09.17.
 */
public class GroupViewHolder extends MinionViewHolder {

    private final
    @Nullable
    GroupListener groupListener;

    private final
    @NonNull
    TextView name;
    private final
    @NonNull
    TextView description;
    private final
    @NonNull
    View insert;
    private final
    @NonNull
    View delete;

    public GroupViewHolder(@NonNull View itemView, @Nullable GroupListener groupListener) {
        super(itemView);
        this.groupListener = groupListener;
        this.name = (TextView) itemView.findViewById(R.id.name);
        this.description = (TextView) itemView.findViewById(R.id.description);
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
