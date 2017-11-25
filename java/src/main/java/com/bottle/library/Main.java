package com.bottle.library;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class Main {

    public static void main(String[] args){
//        String time = "171016110330555789";
//        formatFileCreateTime(time);
//        String str = "南有乔木，不可休思";
//        String encodeString = Main.encode(str);
//        String decodeString = Main.decode(encodeString);
//        System.out.println(str);
//        System.out.println(encodeString);
//        System.out.println(decodeString);
//        formatId(System.currentTimeMillis());
//        String str = "name||branchId";
//        String[] strs = str.split("|");
//        int i = 0;
//        for(String s : strs){
//            System.out.println(s + "-" + ++i);
//        }
        String result = "callback( {\"client_id\":\"1104991338\",\"openid\":\"6EF365968403BA635E9A877807192BA4\",\"unionid\":\"UID_2282DC5EB21CB977E434E0FF81A5947F\"} );";
        String split = result.substring(result.indexOf("(") + 1, result.lastIndexOf(")"));
        System.out.print(split);
    }

    private static long formatFileCreateTime(String fileCreateTime){
        if(fileCreateTime == null){
            return 0l;
        }
        if(fileCreateTime.length() >= 18){
            String year = fileCreateTime.substring(0, 2);
            String month = fileCreateTime.substring(2, 4);
            String date = fileCreateTime.substring(4, 6);
            String hour = fileCreateTime.substring(6, 8);
            String minute = fileCreateTime.substring(8, 10);
            String second = fileCreateTime.substring(10, 12);
            String millisecond = fileCreateTime.substring(12, 15);
            String dateFormat = "20%s/%s/%s %s:%s:%s.%s";
            String realDateTime = String.format(dateFormat, year, month, date, hour, minute, second, millisecond);
            System.out.println(realDateTime);
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss.SSS", Locale.getDefault());
            try {
                Date dateTime = sdf.parse(realDateTime);
                System.out.println("Time:" + dateTime.getTime());
                System.out.println("current:" + System.currentTimeMillis());
                System.out.println("format:" + sdf.format(dateTime));
            } catch (ParseException e) {
                e.printStackTrace();
            }
            return 1l;
        }
        return Long.parseLong(fileCreateTime);
    }

    /**
     * URL编码
     * @param encodeString 编码前的字符串
     * @return 返回一个URL编码后的字符串
     */
    public static String encode(String encodeString){
        String result;
        try{
            result = URLEncoder.encode(encodeString, "UTF-8");
        }catch (UnsupportedEncodingException e){
            e.printStackTrace();
            result = encodeString;
        }
        return result;
    }

    /**
     * URL解码，UTF-8编码方式
     * @param encodeString 需要解码的字符串
     * @return 如果解码失败，返回输入的 encodeString
     */
    public static String decode(String encodeString){
        String result;
        try {
            result = URLDecoder.decode(encodeString, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            result = encodeString;
        }
        return result;
    }

    /**
     *
     * @param currentTimeMillis
     * @return yyMMddHHmmssSSSxyz yy只取后面两位
     */
    public static String formatId(long currentTimeMillis) {
        Date date = new Date(currentTimeMillis);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss.SSS");
        String result = sdf.format(date);
        System.out.print(result);
        return result;
    }
}
