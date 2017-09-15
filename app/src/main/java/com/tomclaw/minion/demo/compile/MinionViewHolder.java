package com.tomclaw.minion.demo.compile;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
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
