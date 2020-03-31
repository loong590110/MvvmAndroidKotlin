package com.aaron.kotlindemo;

import android.os.Bundle;

import com.aaron.kotlindemo.base.BaseActivity;
import com.aaron.kotlindemo.base.Scheduler;
import com.aaron.kotlindemo.event.Message;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import kotlin.Unit;
import kotlin.jvm.functions.Function1;

/**
 * Created by Developer Zailong Shi on 2020-01-14.
 */
public class SettingsActivity extends BaseActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        publish(new Message("settings"));
    }

    @Override
    public void onSubscribe(@NotNull Scheduler scheduler) {
        scheduler.subscribe(new Function1<Message, Unit>() {
            @Override
            public Unit invoke(Message message) {
                return null;
            }
        });
    }
}
