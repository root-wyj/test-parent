package com.wyj.test.lucene.simple;

import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.analysis.tokenattributes.CharTermAttribute;

import java.io.IOException;

/**
 * @author wuyingjie
 * Date: 2023/10/13
 */
public class TokenStreamTest {

    public static void main(String[] args) throws IOException {
        TokenStream tokenStream = new StandardAnalyzer().tokenStream("content", "i am tom, this is jerry!");
        CharTermAttribute attribute = tokenStream.addAttribute(CharTermAttribute.class);
        tokenStream.reset();
        while (tokenStream.incrementToken()) {
            System.out.println(attribute);
        }
        tokenStream.end();
        tokenStream.close();
    }
}
