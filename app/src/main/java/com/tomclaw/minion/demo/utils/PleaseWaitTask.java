package com.tomclaw.minion.demo.utils;

import android.app.ProgressDialog;
import android.content.Context;
import androidx.annotation.NonNull;
import androidx.annotation.StringRes;

import java.lang.ref.WeakReference;

/**
 * Created with IntelliJ IDEA.
 * User: Solkin
 * Date: 09.11.13
 * Time: 14:19
 */
public abstract class PleaseWaitTask<C extends Context> extends WeakObjectTask<C> {

    private WeakReference<ProgressDialog> weakProgressDialog;

    public PleaseWaitTask(@NonNull C context) {
        super(context);
    }

    @Override
    public boolean isPreExecuteRequired() {
        return true;
    }

    @Override
    public final void onPreExecuteMain() {
        C context = getWeakObject();
        if (context != null) {
            try {
                ProgressDialog progressDialog = ProgressDialog.show(
                        context, null, context.getString(getWaitStringId()));
                weakProgressDialog = new WeakReference<>(progressDialog);
            } catch (Throwable ignored) {
            }
        }
    }

    @Override
    public final void onPostExecuteMain() {
        ProgressDialog progressDialog = weakProgressDialog.get();
        if (progressDialog != null) {
            try {
                progressDialog.dismiss();
            } catch (Throwable ignored) {
            }
            onProgressClosed();
        }
    }

    public void onProgressClosed() {
    }

    public abstract
    @StringRes
    int getWaitStringId();
}
