package com.tomclaw.minion.demo.compile;

import com.tomclaw.minion.IniGroup;
import com.tomclaw.minion.IniRecord;

/**
 * Created by solkin on 03.09.17.
 */
public interface RecordListener {

    void onDeleteRecord(IniGroup group, IniRecord record);

}
