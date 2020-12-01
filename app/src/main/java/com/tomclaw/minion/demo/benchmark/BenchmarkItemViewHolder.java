package com.tomclaw.minion.demo.benchmark;

import android.graphics.drawable.Drawable;
import android.view.View;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.tomclaw.minion.demo.R;

import me.zhanghai.android.materialprogressbar.IndeterminateHorizontalProgressDrawable;
import me.zhanghai.android.materialprogressbar.MaterialProgressBar;

/**
 * Created by solkin on 03.08.17.
 */
@SuppressWarnings("WeakerAccess")
public class BenchmarkItemViewHolder extends RecyclerView.ViewHolder {

    private final TextView title;
    private final MaterialProgressBar progress;
    private final TextView result;

    public BenchmarkItemViewHolder(View itemView) {
        super(itemView);
        this.title = itemView.findViewById(R.id.title);
        this.progress = itemView.findViewById(R.id.progress);
        this.result = itemView.findViewById(R.id.result);

        Drawable drawable = new IndeterminateHorizontalProgressDrawable(itemView.getContext());
        progress.setIndeterminateDrawable(drawable);
    }

    public void bind(final BenchmarkItem item) {
        title.setText(item.getTitle());
        int value = item.getProgress();
        if (value > 0) {
            progress.setIndeterminate(false);
            progress.setProgress(value);
        } else {
            progress.setIndeterminate(true);
        }
        result.setText(item.getResult());
    }
}
