package algorithm;

import java.util.ArrayList;
import java.util.Collections;
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


    /**
     *  输入一个正数n，输出所有和为n连续正数序列。

        例如输入15，由于1+2+3+4+5=4+5+6=7+8=15，所以输出3个连续序列1-5、4-6和7-8。

        思路：p1 p2 分别指向第1、2个元素，若p1 + p2 < n，则p2向右移动，否则p1向右移动，直到p1 到达 (n + 1) / 2

        p1 移动是做减法，p2 移动是做加法
     */
    static final class ContinuousSequence {
        void findContinuousSequence(final int n) {

           int p1 = 1;
           int p2 = 2;

           if (n < p1 + p2) {
               throw new IllegalArgumentException("无可用数组序列");
           }

           int guard = (n + 1) / 2;

           while (p1 < guard) {

               int tempSum = sum(p1, p2);
               if (tempSum < n) {
                   p2++;
               }else if(tempSum > n) {
                   p1++;
               }else {
                   Algorithm.Utils.pln("****************************************");
                   for (int i = p1; i <= p2; i++) {
                       Algorithm.Utils.pln(i);
                   }
                   p2++;
               }
           }
        }

        final int sum(final int start, final int end) {
            int sum = 0;
            for (int i = start; i <= end; i++) {
                sum += i;
            }
            return sum;
        }


        static void demo() {
            new ContinuousSequence().findContinuousSequence(15);
        }
    }


    /**
     * 输入一个字符串，打印出该字符串中字符的所有排列。例如输入字符串abc，则输出由字符a、b、c所能排列出来的所有字符串abc、acb、bac、bca、cab和cba。
     */
    static final class StringPermutation {

        void permutation(final String prefix, final String inputString) {
            if (inputString.length() < 1) {
                return;
            }

            for (int i = 0; i < inputString.length(); i ++) {
                String newString = inputString;

                if (i != 0) {
                    String leftPart = inputString.substring(1, i);
                    String rightPart = inputString.substring(i + 1);
                    newString = inputString.charAt(i) +leftPart + inputString.charAt(0) + rightPart;
                    Algorithm.Utils.pln(prefix + newString);
                }

                permutation(prefix + inputString.charAt(i), newString.substring(1));
            }

        }


        static void demo() {
            Algorithm.Utils.pln("abcd");
            new StringPermutation().permutation("", "abcd");
        }
    }




    /**
     * 输入一个整数数组，调整数组中数字的顺序，使得所有奇数位于数组的前半部分，所有偶数位于数组的后半部分。要求时间复杂度为O(n)
     */
    static final class ReplaceEvenOddArray {

        int[] reorder(int[] dataArray) {
            int oddIndex = 0;
            int evenIndex = dataArray.length - 1;


            while (oddIndex <= evenIndex) {
                int oddIndexValue = dataArray[oddIndex];
                int evenIndexValue = dataArray[evenIndex];

                if (oddIndexValue % 2 == 1) {
                    oddIndex ++;
                    continue;
                }

                if (evenIndexValue % 2 == 0) {
                    evenIndex --;
                    continue;
                }

                // Swap
                int temp = oddIndexValue;
                dataArray[oddIndex] = evenIndexValue;
                dataArray[evenIndex] = temp;

                oddIndex ++;
                evenIndex --;

            }

            return dataArray;
        }


        static void demo() {
            int []result = new ReplaceEvenOddArray().reorder(new int[] {1, 4, 3, 2, 5, 7, 6});
            for (int i = 0; i < result.length; i ++) {
                Algorithm.Utils.pln(result[i]);
            }
        }
    }


    /**
     * 一个整型数组里除了两个数字之外，其他的数字都出现了两次。请写程序找出这两个只出现一次的数字。要求时间复杂度是O(n)，空间复杂度是O(1)。
     *
     * 思路:
     * 我们还是从头到尾依次异或数组中的每一个数字，那么最终得到的结果就是两个只出现一次的数字的异或结果。因为其他数字都出现了两次，在异或中全部抵消掉了。由于这两个数字肯定不一样，那么这个异或结果肯定不为0，也就是说在这个结果数字的二进制表示中至少就有一位为1。我们在结果数字中找到第一个为1的位的位置，记为第N位。现在我们以第N位是不是1为标准把原数组中的数字分成两个子数组，第一个子数组中每个数字的第N位都为1，而第二个子数组的每个数字的第N位都为0。
     */
    static final class FindUnig2Numbers {

        final int[] findUniqNumbers(final int[] inputDataArray) {

            if (inputDataArray.length <2) {
                throw new IllegalArgumentException("输入数组长度不够");
            }

            int sum = inputDataArray[0];
            for (int i = 1; i < inputDataArray.length; i++) {
                sum ^= inputDataArray[i];
            }

            int indexOfFirstNonZero = 0;
            while ((sum & 1) == 0) {
                sum >>= 1;
                indexOfFirstNonZero++;
            }

            ArrayList<Integer> array1 = new ArrayList<>();
            ArrayList<Integer> array2 = new ArrayList<>();

            for (int i : inputDataArray) {
                if (isBitNOne(i, indexOfFirstNonZero)) {
                    array1.add(i);
                }else {
                    array2.add(i);
                }
            }


            int value1 = 0;
            for (int v1 : array1) {
                value1 ^= v1;
            }

            int value2 = 0;
            for (int v2: array2) {
                value2 ^= v2;
            }


            return new int[] {value1, value2};
        }


        final boolean isBitNOne(int value, int n) {
            return ((value >> n) & 1) == 1;
        }


        static void demo() {

            int[] dataArray = new FindUnig2Numbers().findUniqNumbers(new int[]{2, 2, 6, 3, 4, 4, 5, 5});
            for (int i : dataArray) {
                Algorithm.Utils.pln(i);
            }
        }

    }


    /**
     * 把一个数组最开始的若干个元素搬到数组的末尾，我们称之为数组的旋转。输入一个排好序的数组的一个旋转，输出旋转数组的最小元素。例如数组{3, 4, 5, 1, 2}为{1, 2, 3, 4, 5}的一个旋转，该数组的最小值为1。
     *
     *
     * 思路:
     *
     * 首先我们用两个指针，分别指向数组的第一个元素和最后一个元素。按照题目旋转的规则，第一个元素应该是大于或者等于最后一个元素的（这其实不完全对，还有特例。后面再讨论特例）。
     接着我们得到处在数组中间的元素。如果该中间元素位于前面的递增子数组，那么它应该大于或者等于第一个指针指向的元素。此时数组中最小的元素应该位于该中间元素的后面。我们可以把第一指针指向该中间元素，这样可以缩小寻找的范围。同样，如果中间元素位于后面的递增子数组，那么它应该小于或者等于第二个指针指向的元素。此时该数组中最小的元素应该位于该中间元素的前面。我们可以把第二个指针指向该中间元素，这样同样可以缩小寻找的范围。我们接着再用更新之后的两个指针，去得到和比较新的中间元素，循环下去。
     */
    static final class RotationReorder {

        int minValue(final int[] inputArray) {
            if (inputArray == null) {
                throw new IllegalArgumentException("输入为空");
            }

            if (inputArray.length <= 0) {
                throw new IllegalArgumentException("输入为空");
            }

            if (inputArray.length == 1) {
                return inputArray[0];
            }

            if (inputArray.length == 2) {
                return Math.min(inputArray[0], inputArray[1]);
            }

            // 2分查找 一个数，左边，右边都大于他。

            int midIndex = inputArray.length / 2;
            int left = Integer.MIN_VALUE;
            int right = Integer.MIN_VALUE;

            if (midIndex - 1 >= 0) {
                left = inputArray[midIndex - 1];
            }

            if (midIndex + 1 <= (inputArray.length - 1)) {
                right = inputArray[midIndex + 1];
            }

            int targetValue = inputArray[midIndex];

            if (targetValue <= left && targetValue <= right ) {
                return targetValue;
            }else if (targetValue >= inputArray[0]) {
                // 此时最小元素处于后半队列
                int[] dstArray = new int[inputArray.length - (midIndex)];
                System.arraycopy(inputArray, midIndex, dstArray, 0, dstArray.length);
                return minValue(dstArray);
            }else if (targetValue <= inputArray[inputArray.length - 1]) {
                // 此时最小元素处于前半段
                int[] dstArray = new int[midIndex + 1];
                System.arraycopy(inputArray, 0, dstArray, 0, dstArray.length);
                return minValue(dstArray);
            }else {
                throw new RuntimeException("Bug on");
            }

        }


        static void demo() {

            RotationReorder rr = new RotationReorder();
            Algorithm.Utils.pln(rr.minValue(new int[] {
                    3,4,1,2
            }));
        }
    }


    /**
     * 单例
     *
     */

    static final class SingletonDemo {

        private static SingletonDemo instance = null;

        public synchronized static final SingletonDemo getInstance() {
            if (instance == null) {
                instance = new SingletonDemo();
            }
            return instance;
        }

        private SingletonDemo() {

        }
    }


    /**
     * 数组中有一个数字出现的次数超过了数组长度的一半，找出这个数字
     */

    static final class MoreThanHalf {


        int findNumberAppearMoreThanHalf(final int[] dataArray) {
            if (dataArray == null) {
                throw new IllegalArgumentException("输入为空");
            }

            if (dataArray.length <= 2) {
                throw new IllegalArgumentException("输入太少");
            }


            int accumulateTimes = 0;
            int number = Integer.MIN_VALUE;

            for (int i = 0; i < dataArray.length; i++) {
                int currentValue = dataArray[i];

                if (accumulateTimes == 0) {
                    accumulateTimes ++;
                    number = currentValue;
                    continue;
                }


                if (currentValue == number) {
                    accumulateTimes ++;
                }else {
                    accumulateTimes --;
                }
            }

            return number;

        }


        static void demo() {
            MoreThanHalf moreThanHalf = new MoreThanHalf();
            Algorithm.Utils.pln(moreThanHalf.findNumberAppearMoreThanHalf(new int[] {
                   1, 3, 4, 3, 3
            }));
        }

    }


    /**
     *
     *  101 011
     *  5 + 3 = 8
     *
     *  1) 110 + 10
     *  2) 100 + 100
     *  3) 000 + 1000
     *  4) 1000 + 000
     *  5) 1000 + 0 = 8
     *
     */

    static final class SpecialAdd {

        final int specialAdd(int num1, int num2) {

            if (num1 == 0) {
                return num2;
            }

            if (num2 == 0) {
                return num1;
            }


            int sum = num1 ^ num2;
            int carry = (num1 & num2) << 1;

            return specialAdd(sum, carry);
        }


        static void demo() {
            SpecialAdd specialAdd = new SpecialAdd();
            Algorithm.Utils.pln(specialAdd.specialAdd(5, 3));
        }




    }


    /**
     *
     * 某公司有几万名员工，请完成一个时间复杂度为O(n)的算法对该公司员工的年龄作排序，可使用O(1)的辅助空间
     * 
     */
    static final class AgeHistorgram {


        void doAgeCount(final int[] people) {
            int[] ages = new int[99];
            for (int i = 0; i < people.length; i ++) {
                ages[people[i]] += 1;
            }


            for (int i = 0; i < ages.length; i++) {
                if (ages[i] != 0) {
                    Algorithm.Utils.pln(i + "岁出现了" + ages[i] + '次');
                }
            }
        }

        static void demo() {
           new AgeHistorgram().doAgeCount(new int[] {
                   20,  30 ,52 , 24, 32, 38, 19, 5
           });
        }

    }
}
