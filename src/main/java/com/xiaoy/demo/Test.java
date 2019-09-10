package com.xiaoy.demo;

import com.xiaoy.demo.tokenizer.ReaderChar;
import com.xiaoy.demo.tokenizer.TokenList;
import com.xiaoy.demo.tokenizer.Tokenizer;

import java.io.IOException;
import java.io.StringReader;

/**
 * @Auther: 小y
 * @Date: 2019/9/10 22:37
 * @Description:
 */
public class Test {
    public static void main(String[] args) {
        String json = "{\"姓名\": \"张三\", \"年龄\": \"\\r\"}";

        Tokenizer tokenizer = new Tokenizer(new ReaderChar(new StringReader(json)));
        try {
            TokenList tokenList = tokenizer.getTokenStream();
            System.out.println(tokenList);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
