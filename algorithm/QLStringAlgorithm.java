package algorithm;

import java.util.HashMap;

/**
 * Created by AaronLee on 2017/9/7.
 */
public class QLStringAlgorithm {

    static final class Reverse {
        final String reverseSentence(final String inputSentence) {
            if (inputSentence == null) {
                return null;
            }

            final String[] substrArray = inputSentence.split("\\s+");
            final StringBuffer stringBuffer = new StringBuffer();

            for (int i = 0; i< substrArray.length; i++) {
                final String substr = substrArray[i];
                final String reversedSubstr = new StringBuffer(substr).reverse().toString();

                stringBuffer.append(reversedSubstr);
                if (i != substrArray.length -1) {
                    stringBuffer.append(" ");
                }
            }

            return stringBuffer.reverse().toString();
        }


        static void demo() {
            String sampleSentence = "I am a student!";
            QLStringAlgorithm.Reverse stringAlgorithm = new QLStringAlgorithm.Reverse();
            Algorithm.Utils.pln(stringAlgorithm.reverseSentence(sampleSentence));
        }
    }


    /**
     * 在一个字符串中找到第一个只出现一次的字符。如输入abaccdeff，则输出b。
     *
     * 思路1：如果遍历，则需要将每一个字符分别和剩下所有字符比较，时间复杂度为O(n2)
     * 思路2：考虑用HashMap来存储每个字符出现的次数
     *
     * abaccdeff   -->   b
     *
     */
    static final class AppearOnce {

        final char findCharacterFisrtAppearOnce(final String inputString) {
            int[] bucket = new int[256];

            for (int i = 0; i < inputString.length(); i++) {
                char c = inputString.charAt(i);

                bucket[c] += 1;
            }


            for (int i = 0; i < bucket.length; i++) {
                if (bucket[i] == 1) {
                    return (char)i;
                }
            }

            return '-';
        }


        static void demo() {
            AppearOnce appearOnce = new AppearOnce();
            char c = appearOnce.findCharacterFisrtAppearOnce("abaccdeff");

            Algorithm.Utils.pln(c);
        }
    }


}
