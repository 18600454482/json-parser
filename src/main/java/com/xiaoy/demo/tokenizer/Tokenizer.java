package com.xiaoy.demo.tokenizer;

import com.xiaoy.demo.exception.JsonParseException;

import java.io.IOException;

/**
 * @Auther: 小y
 * @Date: 2019/9/10 22:10
 * @Description: 词法解析
 */
public class Tokenizer {

    private ReaderChar readerChar;

    private TokenList tokenList;
    public Tokenizer(ReaderChar readerChar){
        this.readerChar = readerChar;
    }
    public TokenList getTokenStream() throws IOException {
        this.tokenList = new TokenList();
        Token token;
        do {
            token = start();
            tokenList.add(token);
        }while (token.getTokenType() != TokenType.END_DOCUMENT);

        return tokenList;
    }
    //"{\"a\": 1, \"b\": \"b\", \"c\": {\"a\": 1, \"b\": null, \"d\": [0.1, \"a\", 1,2, 123, 1.23e+10, true, false, null]}}"
    private Token start() throws IOException {
        char ch;
        while (true){
            if(!readerChar.hasMore()){
                //解析完毕，没有更多
                return new Token(TokenType.END_DOCUMENT, null);
            }
            ch = readerChar.next();
            if(!isWhiteSpace(ch))
                break;
        }

        switch (ch){
            case '{':
                return new Token(TokenType.BEGIN_OBJECT, "{");
            case '}':
                return new Token(TokenType.END_OBJECT, "}");
            case ':':
                return new Token(TokenType.SEP_COLON, ":");
            case '[':
                return new Token(TokenType.BEGIN_ARRAY, "[");
            case ']':
                return new Token(TokenType.END_ARRAY, "]");
            case 'n':
                return readNULL();
            case ',':
                return new Token(TokenType.SEP_COMMA, ",");
            case 't':
                return null;
            case 'f':
                return null;
            case '"':
                return readString();
        }

        return null;
    }

    /**
     * 判断是否空白字符
     * @return
     */
    private boolean isWhiteSpace(char ch){
        return (ch == ' ' || ch == '\t' || ch == '\n' || ch == '\r');
    }
    private Token readNULL() throws IOException {
        if(readerChar.next() == 'u' && readerChar.next() == 'l' && readerChar.next() == 'l'){
            return new Token(TokenType.NULL, "NULL");
        }
        throw new JsonParseException("Invalid json string");
    }

    private Token readString() throws IOException {
        StringBuffer sb = new StringBuffer();
        while (true){
            char ch = readerChar.next();
            if(ch == '\\'){
                sb.append("\\");
            }else if(ch == '"'){ //下一个"引号代表字符解析结束
                return new Token(TokenType.STRING, sb.toString());
            }else{
                sb.append(ch);
            }
        }
    }
}
