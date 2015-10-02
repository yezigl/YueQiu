package com.yidongle.yueqiu.time;

/**
 * Created on 15/10/1.
 *
 * @author yezi
 * @version 1.0
 * @since 1.0
 */
public class NTP {

    private static int diff;

    public static void sync() {

    }

    public static long currentTimeMillis() {
        return System.currentTimeMillis() + diff;
    }
}
