package algorithm;

import java.util.ArrayList;
import java.util.Stack;

/**
 * Created by AaronLee on 2017/9/7.
 */
public class QLBasicAlgorithm {


    /**
     * 题目
     * 定义栈的数据结构，要求添加一个min函数，能够得到栈的最小元素。要求函数min、push以及pop的时间复杂度都是O(1)
     *
     * 难点，时间复杂度
     *
     * 初始思路：定义一个辅助变量，用来记录最小元素，当push时候最小元素会被更新。存在问题：一旦最小元素被pop掉，则无法追溯上一个最小元素。时间复杂度（o(1))
     * 正确思路：时刻记录当前队列的最小元素的位置，由于进入的元素只与最小元素比较（最小元素即stack[minPostionStack.size-1])，因此比较次数均只有1次，即O(1)
     *
     *
     *
     * 举个例子演示上述代码的运行过程：

     步骤        数据栈      辅助栈        最小值
     1.push 3    3          0             3
     2.push 4    3,4        0,0           3
     3.push 2    3,4,2      0,0,2         2
     4.push 1    3,4,2,1    0,0,2,3       1
     5.pop       3,4,2      0,0,2         2
     6.pop       3,4        0,0           3
     7.push 0    3,4,0      0,0,2         0
     */

    static final class MinStack<E extends Comparable> {
        private Stack<E> stack;
        private Stack<Integer> minPostionStack;

        MinStack() {
            stack = new Stack<>();
            minPostionStack = new Stack<>();
        }

        void push(E element) {
            if (minPostionStack.size() == 0) {
                minPostionStack.push(0);
            }else {

                if (element.compareTo(stack.lastElement()) <= 0) {
                    minPostionStack.push(stack.size());
                }else {
                    minPostionStack.push(minPostionStack.lastElement());
                }
            }
            stack.push(element);
        }

        void pop() {
            stack.pop();
            minPostionStack.pop();
        }

        E minValue() {
            return stack.get(minPostionStack.lastElement());
        }


        static void demo() {
            QLBasicAlgorithm.MinStack<Integer> minStack = new QLBasicAlgorithm.MinStack<>();
            minStack.push(3);
            Algorithm.Utils.pln(minStack.minValue());

            minStack.push(4);
            Algorithm.Utils.pln(minStack.minValue());

            minStack.push(2);
            Algorithm.Utils.pln(minStack.minValue());

            minStack.push(1);
            Algorithm.Utils.pln(minStack.minValue());

            minStack.pop();
            Algorithm.Utils.pln(minStack.minValue());
        }
    }


    /**
     * 题目：
     * 输入一个整形数组，数组里有正数也有负数。数组中连续的一个或多个整数组成一个子数组，每个子数组都有一个和。求所有子数组的和的最大值。要求时间复杂度为O(n)。
     *
     * 思路：
     * 1）遍历所有子数组，并逐个计算。(所有数组个数 Cn1 + Cn2 + Cn3 + Cnn -- O(n2）加上对每个数组求和 O(n2*n) = O(n3)
     * 2）当前子数组和一旦为负，则重新计算
     *
     * 例如输入的数组为1, -2, 3, 10, -4, 7, 2, -5，和最大的子数组为3, 10, -4, 7, 2，因此输出为该子数组的和18。
     */

    static class BiggestChildArray {

        final int maxSumOfChildArray(final int[] dataArray) {
            if (dataArray == null) {
                throw new IllegalArgumentException("dataArray is null");
            }

            if (dataArray.length == 0) {
                throw new IllegalArgumentException("dataArray length is zero");
            }

            int maxSum = Integer.MIN_VALUE;
            int currentArraySum = 0;

            for (int element : dataArray) {
                currentArraySum += element;

                // 记录上一次最大和
                if (maxSum <= currentArraySum) {
                    maxSum = currentArraySum;
                }

                if (currentArraySum < 0) {
                    currentArraySum = 0;
                }
            }

            return maxSum;
        }

        static void demo() {

            BiggestChildArray childArray = new BiggestChildArray();
            int sampleData1[] = {1, -2, 3, 10, -4 ,7, 2, -5};
            int sampleData2[] = {-1, 1, -2, -3};

            Algorithm.Utils.pln(childArray.maxSumOfChildArray(sampleData2));
        }
    }


    /**
     * 输入一个已经按升序排序过的数组和一个数字，在数组中查找两个数，使得它们的和正好是输入的那个数字。要求时间复杂度是O(n)。如果有多对数字的和等于输入的数字，输出任意一对即可
     *
     * 思路1：如果我们不考虑时间复杂度，最简单想法的莫过去先在数组中固定一个数字，再依次判断数组中剩下的n-1个数字与它的和是不是等于输入的数字。可惜这种思路需要的时间复杂度是O(n2)。
     *
     * 思路2：双向遍历，由于升序，因此如果加起来的和 < 给定值，则左侧index右移，否则右侧index左移
     *
     * 例如输入数组1、2、4、7、11、15和数字15。由于4+11=15，因此输出4和11。
     *
     */

    static class Find2NumberWithSum {

        final ArrayList<Integer> findExact2Numbers(final int[] dataArray, final int targetValue) {
            if (dataArray == null) {
                return null;
            }

            if (dataArray.length == 0) {
                return null;
            }


            int leftIndex = 0;
            int rightIndex = dataArray.length - 1;

            ArrayList<Integer> resultList = new ArrayList<>();

            while (leftIndex <= rightIndex) {
                int smallerOne = dataArray[leftIndex];
                int biggerOne = dataArray[rightIndex];

                int addSum = smallerOne + biggerOne;
                if (addSum == targetValue) {
                    resultList.add(smallerOne);
                    resultList.add(biggerOne);
                    break;
                }else if (addSum > targetValue) {
                    rightIndex --;
                }else {
                    leftIndex ++;
                }
            }

            return resultList;
        }


        static void demo() {

            Find2NumberWithSum find2NumberWithSum = new Find2NumberWithSum();
            int[] sampleDataArray = {1, 2, 4, 7, 11, 15};
            ArrayList resultList = find2NumberWithSum.findExact2Numbers(sampleDataArray, 15);
            Algorithm.Utils.pln(resultList);
        }
    }


    /**
     * n个数字（0,1,…,n-1）形成一个圆圈，从数字0开始，每次从这个圆圈中删除第m个数字（第一个为当前数字本身，第二个为当前数字的下一个数字）。当一个数字删除后，从被删除数字的下一个继续删除第m个数字。求出在这个圆圈中剩下的最后一个数字。
     *
     * 思路:
     * 1）如果使用链表则时间复杂度为 每次删除均需遍历 m 步，则整体时间复杂度为 O(mn)
     * 2）时间复杂度为 O(n)
     *
     * 0, 1, 2, ... ,m-1, m, m+1, ... , n-1  f(n)  删掉第m个数字后
     *
     * 0, 1, 2, ..., m, m+1, ..., n-1 => m, m+1, ..., n-1, 0, 1, 2, ..., m-2 => 0, 1, 2, n-2    x = (x1 + m) % n  f(n-1)
     *
     * f(n) = (f(n-1) + m)%n
     *
     * f(0) = 0
     *
     */
    static class RemainNumber {

        final int getRemainingNumber(final int n, final int m) {

            if (n <= 1) {
                return 0;
            }


            int result = 0;

            for (int i = 2; i <= n; i++) {
                result = (result + m) % i;
            }

            return result;

        }

        final static void demo() {
            RemainNumber remainNumber = new RemainNumber();
            Algorithm.Utils.pln(remainNumber.getRemainingNumber(10, 3));
        }

    }


    /**
     *
     * 定义Fibonacci数列如下：
         /      0                      n=0
     f(n)=      1                      n=1
         \      f(n-1)+f(n-2)          n=2
     *
     *
     *
     *
     * 问题
     *
     *           f(10)
                /     \
            f(9)      f(8)
             / \       / \
        f(8)   f(7)  f(7) f(6)
        /   \   /  \
     f(7)  f(6)f(6)f(5)
     *
     *
     *
     */
    static class Fibonacci {

        final int calc(final int n) {
            if (n == 0) {
                return 0;
            }

            if (n == 1) {
                return 1;
            }



            int f0 = 0;
            int f1 = 1;
            int fn = 0;

            for (int i=2; i < n; i++) {
                fn = f0 + f1;
                f0 = f1;
                f1 = fn;
            }

            return fn;
        }


        static void demo() {

            Fibonacci fibonacci = new Fibonacci();
            Algorithm.Utils.pln(fibonacci.calc(10));
        }

    }
}
