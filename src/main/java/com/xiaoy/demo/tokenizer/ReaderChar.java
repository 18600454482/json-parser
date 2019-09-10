package com.xiaoy.demo.tokenizer;

import java.io.IOException;
import java.io.Reader;

/**
 * @Auther: 小y
 * @Date: 2019/9/10 21:59
 * @Description:
 */
public class ReaderChar {

    private static final int BUFFER_SIZE = 1024;

    private Reader reader;

    private char[] buffer;

    private int size;

    private int index;

    public ReaderChar(Reader reader){
        this.reader = reader;
        buffer = new char[BUFFER_SIZE];
    }

    /**
     * 获得 index 下标的字符
     * @return
     */
    public char peek(){
        if(index -1 >= size){
            return (char)-1;
        }
        return buffer[Math.max(0, index - 1)];
    }

    /**
     * 获得 下一个字符
     * @return
     */
    public char next() throws IOException {
        if(!hasMore()){
            return (char)-1;
        }
        return buffer[index++];
    }

    /**
     * 是否有更多字符
     * @return
     */
    public boolean hasMore() throws IOException {
        if(index < size){
            return true;
        }
        fillBuffer();
        return index < size;
    }

    /**
     * 填充buffer数组
     */
    public void fillBuffer() throws IOException {
        int n = reader.read(buffer);
        if(n == -1){
            return;
        }
        index = 0;
        size = n;
    }
}
