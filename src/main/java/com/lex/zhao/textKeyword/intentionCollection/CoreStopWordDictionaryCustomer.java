package com.lex.zhao.textKeyword.intentionCollection;

import com.hankcs.hanlp.HanLP;
import com.hankcs.hanlp.corpus.io.ByteArray;
import com.hankcs.hanlp.dictionary.stopword.CoreStopWordDictionary;
import com.hankcs.hanlp.dictionary.stopword.Filter;
import com.hankcs.hanlp.dictionary.stopword.StopWordDictionary;
import com.hankcs.hanlp.seg.common.Term;
import com.hankcs.hanlp.utility.TextUtility;

import java.io.DataOutputStream;
import java.io.File;
import java.io.FileOutputStream;

/**
 * Created by qtfs on 2018/5/10.
 */
public class CoreStopWordDictionaryCustomer {
    static StopWordDictionary dictionary;
    public static Filter FILTER;

    public CoreStopWordDictionaryCustomer() {
    }

    public static boolean contains(String key) {
        return dictionary.contains(key);
    }

    public static boolean shouldInclude(Term term) {
        if(term.nature == null) {
            return false;
        } else {
            String nature = term.nature.toString();
            char firstChar = nature.charAt(0);
            switch(firstChar) {
                case 'b':
                case 'c':
                case 'd':
                case 'e':
                case 'o':
                case 'p':
                case 'q':
                case 'r':
                case 't':
                case 'u':
                case 'v':
                case 'w':
                case 'y':
                case 'z':
                    return false;
                case 'a':
                case 'f':
                case 'g':
                case 'h':
                case 'i':
                case 'j':
                case 'k':
                case 'l':
                case 'm':
                case 'n':
                case 's':
                case 'x':
                    return true;
                default:
                    return term.word.length() > 1 && !contains(term.word);
            }
        }
    }

    static {
        ByteArray byteArray = ByteArray.createByteArray(HanLP.Config.CoreStopWordDictionaryPath + ".bin");
        if(byteArray == null) {
            try {
                dictionary = new StopWordDictionary(new File(HanLP.Config.CoreStopWordDictionaryPath));
                DataOutputStream out = new DataOutputStream(new FileOutputStream(HanLP.Config.CoreStopWordDictionaryPath + ".bin"));
                dictionary.save(out);
                out.close();
            } catch (Exception var2) {
                System.err.println("载入停用词词典" + HanLP.Config.CoreStopWordDictionaryPath + "失败" + TextUtility.exceptionToString(var2));
            }
        } else {
            dictionary = new StopWordDictionary();
            dictionary.load(byteArray);
        }

        FILTER = new Filter() {
            public boolean shouldInclude(Term term) {
                return CoreStopWordDictionary.shouldInclude(term);
            }
        };
    }
}
