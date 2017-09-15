package com.tomclaw.minion.demo.utils;

/**
 * Created with IntelliJ IDEA.
 * User: Solkin
 * Date: 31.10.13
 * Time: 11:08
 */
@SuppressWarnings("WeakerAccess")
public abstract class Task implements Runnable {

    @Override
    public void run() {
        try {
            executeBackground();
            onSuccessBackground();
            MainExecutor.execute(new Runnable() {
                @Override
                public void run() {
                    onPostExecuteMain();
                    onSuccessMain();
                }
            });
        } catch (final Throwable ex) {
            onFailBackground();
            MainExecutor.execute(new Runnable() {
                @Override
                public void run() {
                    onPostExecuteMain();
                    onFailMain(ex);
                }
            });
        }
    }

    public boolean isPreExecuteRequired() {
        return false;
    }

    public void onPreExecuteMain() {
        // Empty method for optional override
    }

    public abstract void executeBackground() throws Throwable;

    public void onPostExecuteMain() {
        // Empty method for optional override
    }

    public void onSuccessBackground() {
        // Empty method for optional override
    }

    public void onFailBackground() {
        // Empty method for optional override
    }

    public void onSuccessMain() {
        // Empty method for optional override
    }

    public void onFailMain(Throwable ex) {
        // Empty method for optional override
    }
}
