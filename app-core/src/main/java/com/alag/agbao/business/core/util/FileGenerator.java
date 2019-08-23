package com.alag.agbao.business.core.util;

import java.io.*;
import java.util.Random;

public class FileGenerator {

    public static void main(String[] args) throws Exception {

        File file1 = new File("/Users/alag/Desktop/agbao/orderSqlFile");
        File file2 = new File("/Users/alag/Desktop/agbao/orderIDFile");
        long min = 100000000000000000L;
        long max = 999999999999999999L;

        BufferedWriter bw1 = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file1, true), "utf-8"));
        BufferedWriter bw2 = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file2, true), "utf-8"));
        String rangeLong = "";
        String oID2 = "";

        for (int i = 0; i < 1000; i++) {
            rangeLong = min + (((long) (new Random().nextDouble() * (max - min)))) + "";
            oID2 = "'" + rangeLong + "'";
            bw2.write(rangeLong);
            bw2.newLine();

            String sql = "insert INTO ord_order (`order_no`,`user_id`,`payment`,`status`,`payment_time`,`create_time`,`update_time`)VALUES("+oID2+",'6587','100',10,NULL,now(),now());";
            bw1.write(sql);
            bw1.newLine();
        }

        bw1.flush();
        bw2.flush();
    }
}
