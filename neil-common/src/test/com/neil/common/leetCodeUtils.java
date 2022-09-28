package com.neil.common;

import com.alibaba.druid.sql.visitor.functions.Char;
import org.junit.Test;

/**
 * @Decription
 * @Author NEIL
 * @Date 2022/9/13 10:06
 * @Version 1.0
 */
public class leetCodeUtils {

    public static void main(String[] args) {
        convert("PAYPALISHIRING", 3);
    }

    public static String convert(String s, int numRows) {
        int n = numRows;
        int l = s.length();
        int r = 0;
        if (l <= n) r = 1;
        else if (l < 2 * n - 2) r = 1 + l - n;
        else {
            int k = l / (2 * n - 2);
            int j = l % ((2 * n - 2));
            if (j == 0) r = k * (n - 1);
            else if (j <= n) r = k * (n - 1) + 1;
            else if (j < 2 * n - 2) r = k * (n - 1) + 1 + l - n;
        }

        Character[][] res = new Character[n][r];
        int x = 0;
        int y = 0;
        int index = 0;
        outWhile:
        while (index != l) {
            res[x][y] = s.charAt(index);
            while (y < n - 1) {
                y++;
                index++;
                res[x][y] = s.charAt(index);
                if (index == l - 1) break outWhile;
            }
            res[x][y] = s.charAt(index);
            while (x < r && y > 0) {
                y--;
                x++;
                index++;
                res[x][y] = s.charAt(index);
                if (index == l - 1) break outWhile;
            }
        }

        Character[] str = new Character[l];
        int strIdx = 0;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < r; j++) {
                if (res[i][j] != null) {
                    str[strIdx++] = res[i][j];
                }
            }
        }
        return str.toString();
    }
}
