package com.tomclaw.minion.demo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.tomclaw.minion.Minion;
import com.tomclaw.minion.ResultCallback;
import com.tomclaw.minion.storage.MemoryStorage;
import com.tomclaw.minion.storage.StringStorage;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ResultCallback callback = new ResultCallback() {

            @Override
            public void onReady(Minion minion) {

            }

            @Override
            public void onFailure(Exception ex) {

            }
        };

        Minion.lets()
                .load(StringStorage.create("[group]\nkey=value"))
                .async(callback);

//        Minion.lets()
//                .store(MemoryStorage.create())
//                .sync(callback);
//
//        MemoryStorage memoryStorage = MemoryStorage.create();
//        Minion.lets()
//                .load(memoryStorage)
//                .and()
//                .store(memoryStorage)
//                .sync(callback);
    }
}
