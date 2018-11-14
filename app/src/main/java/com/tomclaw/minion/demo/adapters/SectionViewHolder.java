package com.tomclaw.minion.demo.adapters;

import androidx.recyclerview.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.tomclaw.minion.demo.R;

/**
 * Created by solkin on 03.08.17.
 */
class SectionViewHolder extends RecyclerView.ViewHolder {

    private View itemView;
    private ImageView icon;
    private TextView text;
    private TextView description;

    SectionViewHolder(View itemView) {
        super(itemView);
        this.itemView = itemView;
        this.icon = itemView.findViewById(R.id.icon);
        this.text = itemView.findViewById(R.id.text);
        this.description = itemView.findViewById(R.id.description);
    }

    void bind(final SectionItem item, final OnItemClickListener listener) {
        icon.setImageResource(item.getIcon());
        text.setText(item.getText());
        description.setText(item.getDescription());

        if (listener != null) {
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onItemClick(item);
                }
            });
        }
    }
}
