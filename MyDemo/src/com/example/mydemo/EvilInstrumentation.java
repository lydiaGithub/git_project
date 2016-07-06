package com.example.mydemo;

import java.lang.reflect.Method;

import android.app.Activity;
import android.app.Instrumentation;
import android.app.Instrumentation.ActivityResult;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;


public class EvilInstrumentation extends Instrumentation {

    private static final String TAG = "EvilInstrumentation";

    // ActivityThread��ԭʼ�Ķ���, ��������
    Instrumentation mBase;

    public EvilInstrumentation(Instrumentation base) {
        mBase = base;
    }

    public ActivityResult execStartActivity(
            Context who, IBinder contextThread, IBinder token, Activity target,
            Intent intent, int requestCode, Bundle options) {

        // Hook֮ǰ, XXX����һ��!
        Log.d(TAG, "\nִ����startActivity, ��������: \n" + "who = [" + who + "], " +
                "\ncontextThread = [" + contextThread + "], \ntoken = [" + token + "], " +
                "\ntarget = [" + target + "], \nintent = [" + intent +
                "], \nrequestCode = [" + requestCode + "], \noptions = [" + options + "]");

        // ��ʼ����ԭʼ�ķ���, ������������,���ǲ����õĻ�, ���е�startActivity��ʧЧ��.
        // ����������������ص�,�����Ҫʹ�÷������;�����ҵ��������
        try {
            Method execStartActivity = Instrumentation.class.getDeclaredMethod(
                    "execStartActivity",
                    Context.class, IBinder.class, IBinder.class, Activity.class, 
                    Intent.class, int.class, Bundle.class);
            execStartActivity.setAccessible(true);
            return (ActivityResult) execStartActivity.invoke(mBase, who, 
                    contextThread, token, target, intent, requestCode, options);
        } catch (Exception e) {
            // ĳ������rom�޸���  ��Ҫ�ֶ�����
            throw new RuntimeException("do not support!!! pls adapt it");
        }
    }
}