package cn.tj.ykt.financialoffice.fw.minilang;

import java.util.LinkedList;
import java.util.Queue;

public class MiniLangParse {

    private boolean isKey(char ch) {
        return String.valueOf(ch).matches("[_|a-z|A-Z|.|\\-|\u4e00-\u9fa5|（|）|、|：|0-9]");
    }

    // /**
    // * 判断读入的字符是否为描述字符
    // */
    // public static boolean isChineseChar(char ch) {
    // return String.valueOf(ch).matches("[]");
    // }

    public Queue<String> parse(String string) {

        String str = "";
        char ch = ' ';
        Queue<String> queue = new LinkedList<String>();

        int m = 0;
        string += ' ';
        for (int i = 0; i < string.length(); i++) {
            switch (m) {
            case 0:
                ch = string.charAt(i);
                if (ch == '(' || ch == ')') {
                    m = 1;
                } else if (isKey(ch = string.charAt(i))) {
                    str = "";
                    str += ch;
                    m = 2;
                    // } else if (isChineseChar(ch = string.charAt(i))) {
                    // str = "";
                    // str += ch;
                    // m = 3;
                } else {
                }
                break;

            case 1:
                i--;
                queue.offer(String.valueOf(ch));
                m = 0;
                break;

            case 2:
                if (isKey(ch = string.charAt(i))) {
                    str += ch;
                } else {
                    queue.offer(str);
                    i--;
                    m = 0;
                }
                break;

            // case 3:
            // if (isChineseChar(ch = string.charAt(i))) {
            // str += ch;
            // } else {
            // queue.offer(str);
            // i--;
            // m = 0;
            // }
            // break;
            }
        }

        return queue;
    }
}