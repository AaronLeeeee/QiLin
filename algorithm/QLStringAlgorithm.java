package algorithm;

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


}
