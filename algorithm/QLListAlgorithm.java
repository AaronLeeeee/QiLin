package algorithm;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by AaronLee on 2017/9/7.
 */
public class QLListAlgorithm {

    static final class QLList {
        int storedValue;
        QLList nextElement;

        QLList(final int storedValue, final QLList nextElement) {
            this.storedValue = storedValue;
            this.nextElement = nextElement;
        }
    }


    /**
     * 输入一个单向链表，输出该链表中倒数第k个结点。链表结点定义如上QLList
     *
     * 思路：
     * 1）遍历两次，第一次获取链表长度N，算出N - k + 1，第二次使用一个计数器，边遍历边计数，直到遍历到N - k + 1位置即可，总共遍历了2遍
     * 2）遍历一遍，使用两个引用指向头节点，第一个引用先走k步，然后两个引用一起走，当第一个引用走至结尾时，第二个引用即为倒数第k个结点的位置。
     *
     *               k = 3   倒数第三个，即正数第 N - k + 1
     * 0 1 2 3 4 5 6 7 8 9
     * 9 8 7 6 5 4 3 2 1 0
     *
     */
    static final class KInBackwards {

        final int findTheKthElementInBackwards(final QLList list, final int k) {

            int currentStep = 0;

            QLList slowerReference = list;
            QLList quickerRefernce = list;


            while (quickerRefernce.nextElement != null) {
                currentStep++;

                if (currentStep > k) {
                    slowerReference = slowerReference.nextElement;
                }
                quickerRefernce = quickerRefernce.nextElement;
            }

            return slowerReference.storedValue;
        }



        static void demo() {

            KInBackwards kInBackwards = new KInBackwards();


            List<QLList> qlLists = new ArrayList<>();

            for (int i = 0; i <= 9; i++) {
                qlLists.add(new QLList(i, null));
            }

            for (int i = qlLists.size() - 1; i >= 0; i--) {
                QLList list = qlLists.get(i);
                if (i == qlLists.size() - 1) {
                    list.nextElement = null;
                }else {
                    list.nextElement = qlLists.get(i + 1);
                }
            }


            Algorithm.Utils.pln(kInBackwards.findTheKthElementInBackwards(qlLists.get(0), 2));
        }
    }


    /**
     * 输入一个链表的头结点，反转该链表，并返回反转后链表的头结点。链表结点定义如下：
     *
     *
     *      p0 -> p1 -> p2 -> p3 -> pn -> null
     *          ||
     * null<-p0 <- p1 <- p2 <- p3 <- pn
     */
    static final class ReverseList {


        final QLList reverseList(final QLList firstElement) {

            QLList pPre = firstElement;
            QLList pCurrent = firstElement.nextElement;
            QLList pIndex = pCurrent;

            firstElement.nextElement = null;


            while (pIndex.nextElement != null) {
                pIndex = pIndex.nextElement;

                pCurrent.nextElement = pPre;
                pPre = pCurrent;
                pCurrent = pIndex;
            }

            pCurrent.nextElement = pPre;

            return pCurrent;
        }


        static void demo() {

            ReverseList rList = new ReverseList();

            List<QLList> qlLists = new ArrayList<>();

            for (int i = 0; i <= 2; i++) {
                qlLists.add(new QLList(i, null));
            }

            for (int i = qlLists.size() - 1; i >= 0; i--) {
                QLList list = qlLists.get(i);
                if (i == qlLists.size() - 1) {
                    list.nextElement = null;
                }else {
                    list.nextElement = qlLists.get(i + 1);
                }
            }

            QLList newList = rList.reverseList(qlLists.get(0));
            QLList head = newList;

            while (head.nextElement != null) {
                Algorithm.Utils.pln(head.storedValue);
                head = head.nextElement;
            }

            Algorithm.Utils.pln(head.storedValue);
        }
    }
}
