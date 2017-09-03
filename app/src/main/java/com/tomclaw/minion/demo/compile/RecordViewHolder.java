package com.tomclaw.minion.demo.compile;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.TextView;

import com.tomclaw.minion.IniGroup;
import com.tomclaw.minion.IniRecord;
import com.tomclaw.minion.demo.R;

/**
 * Created by solkin on 03.09.17.
 */
public class RecordViewHolder extends MinionViewHolder {

    private final
    @Nullable
    RecordListener recordListener;

    private final
    @NonNull
    TextView key;
    private final
    @NonNull
    TextView value;
    private final
    @NonNull
    View delete;

    public RecordViewHolder(@NonNull View itemView, @Nullable RecordListener recordListener) {
        super(itemView);
        this.recordListener = recordListener;
        this.key = (TextView) itemView.findViewById(R.id.key);
        this.value = (TextView) itemView.findViewById(R.id.value);
        this.delete = itemView.findViewById(R.id.delete);
    }

    @Override
    public void bind(@NonNull MinionItem item) {
        final IniGroup group = item.getGroup();
        final IniRecord record = item.getRecord();
        if (group == null || record == null) {
            throw new IllegalStateException("IniGroup on IniRecord might not be null");
        }
        key.setText(record.getKey());
        StringBuilder builder = new StringBuilder();
        for (String value : record.getValue()) {
            if (builder.length() > 0) {
                builder.append(", ");
            }
            builder.append(value);
        }
        value.setText(builder.toString());
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (recordListener != null) {
                    recordListener.onDeleteRecord(group, record);
                }
            }
        });
    }
}
