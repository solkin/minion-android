package com.tomclaw.minion.demo.parse;

import android.support.annotation.ColorInt;
import android.text.Editable;
import android.text.Spannable;
import android.text.TextWatcher;
import android.text.style.ForegroundColorSpan;

import com.tomclaw.minion.UnsupportedFormatException;
import com.tomclaw.minion.demo.utils.MainExecutor;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Timer;
import java.util.TimerTask;

import static com.tomclaw.minion.StreamHelper.safeClose;

/**
 * Created by solkin on 30.08.17.
 */
@SuppressWarnings("WeakerAccess")
class IniSyntaxHighlighter implements TextWatcher {

    private static final String COMMENT_START_UNIX = "#";
    private static final String COMMENT_START_WINDOWS = ";";
    private static final String GROUP_START = "[";
    private static final String GROUP_END = "]";
    private static final String KEY_VALUE_DIVIDER = "=";
    private static final String ARRAY_VALUE_DELIMITER = ",";

    private static final int COMMENT_COLOR = 0xff7f8c8d;
    private static final int GROUP_COMMA_COLOR = 0xffc0392b;
    private static final int GROUP_NAME_COLOR = 0xffe74c3c;
    private static final int RECORD_KEY_COLOR = 0xff3498db;
    private static final int RECORD_EQ_COLOR = 0xff16a085;
    private static final int RECORD_VALUE_COLOR = 0xff2ecc71;
    private static final int RECORD_DIVIDER_COLOR = 0xff2c3e50;

    private final Timer timer;
    private HightlightTimerTask task;

    public IniSyntaxHighlighter() {
        timer = new Timer();
        task = null;
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
    }

    @Override
    public void afterTextChanged(Editable editable) {
        if (task != null) {
            task.cancel();
            timer.purge();
        }
        task = new HightlightTimerTask(this, editable);
        timer.schedule(task, 250);
    }

    private void highlight(Spannable spannable) {
        try {
            InputStream inputStream = new ByteArrayInputStream(spannable.toString().getBytes());
            BufferedReader reader = null;
            try {
                reader = new BufferedReader(new InputStreamReader(inputStream));
                String line;
                int offset = 0;
                while ((line = reader.readLine()) != null) {
                    try {
                        if (line.startsWith(COMMENT_START_UNIX) || line.startsWith(COMMENT_START_WINDOWS)) {
                            applySpan(spannable,
                                    COMMENT_COLOR,
                                    offset,
                                    offset + line.length());
                            continue;
                        }

                        if (line.startsWith(GROUP_START) && line.endsWith(GROUP_END)) {
                            String name = line.substring(1, line.length() - 1);
                            int groupOffset = offset;
                            applySpan(spannable,
                                    GROUP_COMMA_COLOR,
                                    groupOffset,
                                    groupOffset += 1);
                            applySpan(spannable,
                                    GROUP_NAME_COLOR,
                                    groupOffset,
                                    groupOffset += name.length());
                            applySpan(spannable,
                                    GROUP_COMMA_COLOR,
                                    groupOffset,
                                    groupOffset + 1);
                            continue;
                        }

                        if (line.contains(KEY_VALUE_DIVIDER)) {
                            int index = line.indexOf(KEY_VALUE_DIVIDER);
                            if (index <= 0) {
                                throw new UnsupportedFormatException();
                            }
                            String key = line.substring(0, index);
                            String value = line.substring(index + 1);

                            String[] arrayValue = value.split(ARRAY_VALUE_DELIMITER);

                            int recordOffset = offset;
                            applySpan(spannable,
                                    RECORD_KEY_COLOR,
                                    recordOffset,
                                    recordOffset += key.length());

                            applySpan(spannable,
                                    RECORD_EQ_COLOR,
                                    recordOffset,
                                    recordOffset += 1);

                            int itemsCount = 0;
                            for (String arrayItem : arrayValue) {
                                if (itemsCount > 0) {
                                    applySpan(spannable,
                                            RECORD_DIVIDER_COLOR,
                                            recordOffset,
                                            recordOffset += 1);
                                }
                                applySpan(spannable,
                                        RECORD_VALUE_COLOR,
                                        recordOffset,
                                        recordOffset += arrayItem.length());
                                itemsCount++;
                            }
                        }
                    } finally {
                        offset += line.length() + 1;
                    }
                }
            } finally {
                safeClose(reader);
            }
        } catch (Throwable ignored) {
        }
    }

    private void applySpan(Spannable spannable, @ColorInt int color, int start, int end) {
        spannable.setSpan(
                new ForegroundColorSpan(color),
                start,
                end,
                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
    }

    private static class HightlightTimerTask extends TimerTask {

        private final IniSyntaxHighlighter highlighter;
        private final Spannable spannable;

        HightlightTimerTask(IniSyntaxHighlighter highlighter, Spannable spannable) {
            this.highlighter = highlighter;
            this.spannable = spannable;
        }

        @Override
        public void run() {
            MainExecutor.execute(new Runnable() {
                @Override
                public void run() {
                    if (highlighter != null) {
                        highlighter.highlight(spannable);
                    }
                }
            });
        }
    }
}
