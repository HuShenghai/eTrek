package com.bottle.library.security;

import java.io.File;
import java.io.FileInputStream;
import java.security.MessageDigest;

/**
 * @Date 2017/11/24 17:09
 * @Author halfbottle
 * @Version 1.0.0
 * @Description
 */
public class SecurityMain {

    public static String getFileMD5(File file) {
        if (!file.isFile()) {
            return null;
        }
        MessageDigest digest = null;
        FileInputStream in = null;
        byte buffer[] = new byte[1024];
        int len;
        try {
            digest = MessageDigest.getInstance("MD5");
            in = new FileInputStream(file);
            while ((len = in.read(buffer, 0, 1024)) != -1) {
                digest.update(buffer, 0, len);
            }
            in.close();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        return bytesToHexString(digest.digest());
    }

    public static String bytesToHexString(byte[] src) {
        StringBuilder stringBuilder = new StringBuilder("");
        if (src == null || src.length <= 0) {
            return null;
        }
        for (int i = 0; i < src.length; i++) {
            int v = src[i] & 0xFF;
            String hv = Integer.toHexString(v);
            if (hv.length() < 2) {
                stringBuilder.append(0);
            }
            stringBuilder.append(hv);
        }
        return stringBuilder.toString();
    }

    public static void main(String[] args){
        String path = "C:\\Users\\lenovo\\Desktop\\Tasks\\TIM图片20171113161420.png";
        File file = new File(path);
        if(file.exists()){
            String md5 = getFileMD5(file);
            System.out.println(md5);
        }
    }
}
