package com.tomclaw.minion;

/**
 * Created by solkin on 01.08.17.
 */
class StringHelper {

    static String join(String delimiter, String[] tokens) {
        StringBuilder sb = new StringBuilder();
        boolean firstTime = true;
        for (Object token : tokens) {
            if (firstTime) {
                firstTime = false;
            } else {
                sb.append(delimiter);
            }
            sb.append(token);
        }
        return sb.toString();
    }

}
