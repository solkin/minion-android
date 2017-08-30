package com.tomclaw.minion.demo.parse;

import android.graphics.Color;
import android.text.Editable;
import android.text.Spannable;
import android.text.TextWatcher;
import android.text.style.ForegroundColorSpan;

/**
 * Created by solkin on 30.08.17.
 */
class SimpleSyntaxHighlighter implements TextWatcher {

    private final ColoredToken[] TOKENS = new ColoredToken[]{
            new ColoredToken("[", Color.BLUE),
            new ColoredToken("]", Color.BLUE),
            new ColoredToken("=", Color.RED),
            new ColoredToken(";", Color.GREEN),
            new ColoredToken("#", Color.GREEN),
            new ColoredToken(",", Color.MAGENTA)
    };

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
    }

    @Override
    public void afterTextChanged(Editable s) {
        for (ColoredToken coloredToken : TOKENS) {
            String token = coloredToken.getToken();
            int index = -1;
            while ((index = s.toString().indexOf(token, index + 1)) >= 0) {
                s.setSpan(
                        new ForegroundColorSpan(coloredToken.getColorSpan()),
                        index,
                        index + token.length(),
                        Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            }
        }
    }
}
