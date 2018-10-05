package com.tomclaw.minion.demo.compile;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.View;

/**
 * Created by solkin on 03.09.17.
 */
abstract class MinionViewHolder extends RecyclerView.ViewHolder {

    MinionViewHolder(@NonNull View itemView) {
        super(itemView);
    }

    public abstract void bind(@NonNull MinionItem item);
}
