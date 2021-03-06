package com.three.sharecare.bootapi.utils;

import org.apache.commons.lang3.StringUtils;

import java.util.UUID;

public class UUIDUtils {


    /**
     * 获得指定数目的UUID
     *
     * @param number int 需要获得的UUID数量
     * @return String[] UUID数组
     */
    public static String[] getUUIDArrays(int number) {
        if (number < 1) {
            return null;
        }
        String[] retArray = new String[number];
        for (int i = 0; i < number; i++) {
            retArray[i] = getUUID();
        }
        return retArray;
    }

    /**
     * 获得一个UUID
     *
     * @return String UUID
     */
    public static String getUUID() {
        String uuid = UUID.randomUUID().toString();
        //去掉“-”符号
        return uuid.replaceAll("-", "").toUpperCase();
    }


    /**
     * 获得一个UUID
     *
     * @return String UUID
     */
    public static String get10LenUUID() {
        String uuid = UUID.randomUUID().toString();
        //去掉“-”符号
        uuid = uuid.replaceAll("-", "").toUpperCase();
        return StringUtils.substring(uuid,0,10);
    }

}
