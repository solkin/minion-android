package com.tomclaw.minion.demo.compile;

import com.tomclaw.minion.IniGroup;

/**
 * Created by solkin on 03.09.17.
 */
interface GroupListener {

    void onInsertRecord(IniGroup item);

    void onGroupDelete(IniGroup item);

}
