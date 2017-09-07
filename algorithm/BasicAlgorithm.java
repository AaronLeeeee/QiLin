package algorithm;

import java.util.Stack;

/**
 * Created by AaronLee on 2017/9/7.
 */
public class BasicAlgorithm {


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
            BasicAlgorithm.MinStack<Integer> minStack = new BasicAlgorithm.MinStack<>();
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
}
