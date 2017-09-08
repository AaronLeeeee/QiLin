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
            int[] bucket = new int[1024];

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


    /**
     * 输入一个表示整数的字符串，把该字符串转换成整数并输出。例如输入字符串"345"，则输出整数345。
     */
    static final class StringToInt {

        /**
         * 不考虑整形越界情形
         * @param inputString
         * @return
         */
        final int atoi(final String inputString) {
            if (inputString == null) {
                throw new IllegalArgumentException("字符串为空");
            }


            // 去除空格
            String sanitizedString = inputString.trim();
            String firstCharacter = sanitizedString.substring(0, 1);

            boolean startWithPrefix = false;
            if (firstCharacter.equalsIgnoreCase("+") || firstCharacter.equalsIgnoreCase("-")) {
                startWithPrefix = true;
            }

            if (startWithPrefix) {
                if (sanitizedString.length() <= 1) {
                    throw new IllegalArgumentException("字符串长度不够");
                }
                sanitizedString = sanitizedString.substring(1);
            }

            boolean illegalInput = false;
            int result = 0;

                for (int i = 0; i < sanitizedString.length(); i++) {
                char c = sanitizedString.charAt(i);

                if (c < '0' || c > '9') {
                    illegalInput = true;
                    break;
                }else {
                    result = result * 10 + (c - '0');
                }
            }


            if (illegalInput == true) {
                throw  new IllegalArgumentException("错误输入");
            }

            if (startWithPrefix) {
                    if (firstCharacter.equalsIgnoreCase("-")) {
                        result = 0 - result;
                    }
            }

            return result;
        }


        static void demo() {
            StringToInt stringToInt = new StringToInt();
            Algorithm.Utils.pln(stringToInt.atoi("-1"));
        }
    }


    /**
     * 如果字符串一的所有字符按其在字符串中的顺序出现在另外一个字符串二中，则字符串一称之为字符串二的子串。注意，并不要求子串（字符串一）的字符必须连续出现在字符串二中。请编写一个函数，输入两个字符串，求它们的最长公共子串，并打印出最长公共子串。
     *
     *
     *
     *  先介绍LCS问题的性质：记Xm={x0, x1,…xm-1}和Yn={y0,y1,…,yn-1}为两个字符串，而Zk={z0,z1,…zk-1}是它们的LCS，则：
        1.       如果xm-1=yn-1，那么zk-1=xm-1=yn-1，并且Zk-1是Xm-1和Yn-1的LCS；
        2.       如果xm-1≠yn-1，那么当zk-1≠xm-1时Z是Xm-1和Y的LCS；
        3.       如果xm-1≠yn-1，那么当zk-1≠yn-1时Z是Yn-1和X的LCS；

     下面简单证明一下这些性质：

     1.       如果zk-1≠xm-1，那么我们可以把xm-1（yn-1）加到Z中得到Z’，这样就得到X和Y的一个长度为k+1的公共子串Z’。这就与长度为k的Z是X和Y的LCS相矛盾了。因此一定有zk-1=xm-1=yn-1。

     既然zk-1=xm-1=yn-1，那如果我们删除zk-1（xm-1、yn-1）得到的Zk-1，Xm-1和Yn-1，显然Zk-1是Xm-1和Yn-1的一个公共子串，现在我们证明Zk-1是Xm-1和Yn-1的LCS。用反证法不难证明。假设有Xm-1和Yn-1有一个长度超过k-1的公共子串W，那么我们把加到W中得到W’，那W’就是X和Y的公共子串，并且长度超过k，这就和已知条件相矛盾了。

     2.       还是用反证法证明。假设Z不是Xm-1和Y的LCS，则存在一个长度超过k的W是Xm-1和Y的LCS，那W肯定也X和Y的公共子串，而已知条件中X和Y的公共子串的最大长度为k。矛盾。

     3.       证明同2。


     如果我们记字符串Xi和Yj的LCS的长度为c[i,j]，我们可以递归地求c[i,j]：

            /      0                               if i<0 or j<0
     c[i,j]=       c[i-1,j-1]+1                    if i,j>=0 and xi=xj
            \      max(c[i,j-1],c[i-1,j]           if i,j>=0 and xi≠xj

     *
     *
     */
    static final class LongestCommonSubsequence {


        int lcs( char[] X, char[] Y, int m, int n )
        {
            if (m == 0 || n == 0)
                return 0;
            if (X[m-1] == Y[n-1])
                return 1 + lcs(X, Y, m-1, n-1);
            else
                return Math.max(lcs(X, Y, m, n-1), lcs(X, Y, m-1, n));
        }


        static void demo() {
            LongestCommonSubsequence lcs = new LongestCommonSubsequence();
            String s1 = "AGGTAB";
            String s2 = "GXTXAYB";

            char[] X=s1.toCharArray();
            char[] Y=s2.toCharArray();
            int m = X.length;
            int n = Y.length;

            System.out.println("Length of LCS is" + " " +
                    lcs.lcs( X, Y, m, n ) );
        }
    }



}
