package com.tomclaw.minion;

/**
 * Created by solkin on 28.07.17.
 */
public interface ResultCallback {

    void onReady(Minion minion);

    void onFailure(Exception ex);

}
