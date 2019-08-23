package com.alag.agbao.business.core.util;

import lombok.extern.slf4j.Slf4j;

import java.util.Random;

@Slf4j
public class IDGenerator {

    public static String sixStrID() {
        Integer min = 100000;
        Integer max = 999999;

        Integer random = min + (new Random().nextInt() * (max - min));
        return random.toString();
    }

    public static void main(String[] args) {
        int row = -5;
        while (true) {
            System.out.println(row);
            row++;
            if (row > 0) {
                return;
            }
        }
    }
}
