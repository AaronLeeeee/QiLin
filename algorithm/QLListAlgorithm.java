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


    /**
     *
     * 看到这个题目，第一反应就是蛮力法：在第一链表上顺序遍历每个结点。每遍历一个结点的时候，在第二个链表上顺序遍历每个结点。如果此时两个链表上的结点是一样的，说明此时两个链表重合，于是找到了它们的公共结点。如果第一个链表的长度为m，第二个链表的长度为n，显然，该方法的时间复杂度为O(mn)。
     *
     *
     * 接下来我们试着去寻找一个线性时间复杂度的算法。我们先把问题简化：如何判断两个单向链表有没有公共结点？前面已经提到，如果两个链表有一个公共结点，那么该公共结点之后的所有结点都是重合的。那么，它们的最后一个结点必然是重合的。因此，我们判断两个链表是不是有重合的部分，只要分别遍历两个链表到最后一个结点。如果两个尾结点是一样的，说明它们用重合；否则两个链表没有公共的结点。
     * 在上面的思路中，顺序遍历两个链表到尾结点的时候，我们不能保证在两个链表上同时到达尾结点。这是因为两个链表不一定长度一样。但如果假设一个链表比另一个长l个结点，我们先在长的链表上遍历l个结点，之后再同步遍历，这个时候我们就能保证同时到达最后一个结点了。由于两个链表从第一个公共结点考试到链表的尾结点，这一部分是重合的。因此，它们肯定也是同时到达第一公共结点的。于是在遍历中，第一个相同的结点就是第一个公共的结点。
     *
     */
    static final class FirstCommonNode {

        final int findFirstCommonNode(final QLList list1, final QLList list2) {

            int list1Len = 1;
            int list2Len = 1;

            QLList p1 = list1;
            QLList p2 = list2;

            while (p1.nextElement != null) {
                p1 = p1.nextElement;
                list1Len ++;
            }

            while (p2.nextElement != null) {
                p2 = p2.nextElement;
                list2Len ++;
            }

            int delta = list1Len - list2Len;


            QLList plist1Head = list1;
            QLList plist2Head = list2;

            if (delta < 0) {
                int absDelta = -delta;
                while (absDelta > 0) {
                    plist2Head = plist2Head.nextElement;
                    absDelta --;
                }
            }else {
                while (delta > 0) {
                    plist1Head = plist1Head.nextElement;
                    delta --;
                }
            }

            int targetValue = Integer.MIN_VALUE;

            while (plist1Head.nextElement != null) {
                if (plist1Head.storedValue == plist2Head.storedValue) {
                    targetValue = plist1Head.storedValue;
                    break;
                }
                plist1Head = plist1Head.nextElement;
                plist2Head = plist2Head.nextElement;
            }

            if (targetValue == Integer.MIN_VALUE) {
                throw new IllegalArgumentException("错误的输入");
            }

            return targetValue;
        }



        static void demo() {

            QLList a1 = new QLList(1, null);
            QLList a2 = new QLList(100, null);
            QLList a3 = new QLList(37, null);



            QLList b1 = new QLList(22, null);
            QLList b2 = new QLList(17, null);


            QLList c1 = new QLList(50, null);
            QLList c2 = new QLList(51, null);

            a1.nextElement = a2;
            a2.nextElement = a3;
            a3.nextElement = c1;


            b1.nextElement = b2;
            b2.nextElement = c1;

            c1.nextElement = c2;



            FirstCommonNode firstCommonNode = new FirstCommonNode();
            Algorithm.Utils.pln(firstCommonNode.findFirstCommonNode(a1, b1));
        }
    }


}
