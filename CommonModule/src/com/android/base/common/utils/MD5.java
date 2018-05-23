/*
 * Copyright:  Beijing BaoFeng Technology Co., Ltd. Copyright 2014-2114,  All rights reserved
 */

package com.android.base.common.utils;

import android.text.TextUtils;

import com.android.base.common.value.ValueTAG;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class MD5 {
    public final static String Md5Encode(String data) {
        if (TextUtils.isEmpty(data))
            return "";
        MessageDigest md5;
        try {
            md5 = MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException e) {
            return data;
        }
        try {
            byte[] bytes = md5.digest(data.getBytes("utf8"));
            StringBuilder ret = new StringBuilder(bytes.length << 1);
            for (int i = 0; i < bytes.length; i++) {
                ret.append(Character.forDigit((bytes[i] >> 4) & 0xf, 16));
                ret.append(Character.forDigit(bytes[i] & 0xf, 16));
            }
            return ret.toString();

        } catch (UnsupportedEncodingException e) {
            return data;
        }
    }
    
    public final static String Md5FileEncode(String fileName) {
    	FileInputStream fis = null;
        byte[] buf = new byte[4096];
        MessageDigest md;
        boolean fileIsNull = true;
        try {
            fis = new FileInputStream(fileName);
            int len = 0;
            md = MessageDigest.getInstance("MD5");
            len = fis.read(buf);
            if (len > 0) {
                fileIsNull = false;
                while (len > 0) {
                    md.update(buf, 0, len);
                    len = fis.read(buf);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
        finally{
        	if(fis !=null){
        		try {
					fis.close();
				} catch (IOException e) {
					Logger.e(ValueTAG.EXCEPTION, "", e);
				}
        	}
        }
        if (fileIsNull) {
            return "";
        } else {
            byte[] result = md.digest();
            return bufferToHex(result);
        }
    }
    
    private static char hexDigits[] = { '0', '1', '2', '3', '4', '5', '6', '7',
        '8', '9', 'a', 'b', 'c', 'd', 'e', 'f' };
    
    private static String bufferToHex(byte bytes[]) {
        return bufferToHex(bytes, 0, bytes.length);
    }
    
    private static void appendHexPair(byte bt, StringBuffer stringbuffer) {
        char c0 = hexDigits[(bt & 0xf0) >> 4];
        char c1 = hexDigits[bt & 0xf];
        stringbuffer.append(c0);
        stringbuffer.append(c1);
    }

    private static String bufferToHex(byte bytes[], int m, int n) {
        StringBuffer stringbuffer = new StringBuffer(2 * n);
        int k = m + n;
        for (int l = m; l < k; l++) {
            appendHexPair(bytes[l], stringbuffer);
        }
        return stringbuffer.toString();
    }

}
