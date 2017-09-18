package algorithm;

import org.omg.PortableInterceptor.SYSTEM_EXCEPTION;

import java.util.Stack;

/**
 * Created by jingli on 2017/9/15.
 */
public class QLBinaryTree {

    static final class QLBinaryNode {
        int storedValue;

        QLBinaryNode leftNode;
        QLBinaryNode rightNode;
    }



    static final class SumPath {
        private Stack<Integer> pathStack;
        private int accumulateSum;


        SumPath() {
            pathStack = new Stack<>();
        }

        void printSumPath(final QLBinaryNode binaryTree, final int sum) {

            accumulateSum += binaryTree.storedValue;
            pathStack.push(binaryTree.storedValue);


            boolean isLeaf = (binaryTree.leftNode == null && binaryTree.rightNode == null);

            if (binaryTree.leftNode != null) {
                printSumPath(binaryTree.leftNode, sum);
            }


            if (binaryTree.rightNode != null) {
                printSumPath(binaryTree.rightNode, sum);
            }


            if (isLeaf) {
                if (accumulateSum == sum) {
                    System.out.println("********************************************************");
                    for (int i= 0; i < pathStack.size(); i++) {
                        System.out.print(pathStack.get(i) + " : ");
                    }
                }
            }

            accumulateSum -= binaryTree.storedValue;
            pathStack.pop();
        }



        static void demo() {

            QLBinaryNode p0 = new QLBinaryNode();
            QLBinaryNode p1 = new QLBinaryNode();
            QLBinaryNode p2 = new QLBinaryNode();
            QLBinaryNode p3 = new QLBinaryNode();
            QLBinaryNode p4 = new QLBinaryNode();

            p0.storedValue = 10;
            p1.storedValue = 5;
            p2.storedValue = 4;
            p3.storedValue = 7;
            p4.storedValue = 12;

            p0.leftNode = p1;
            p0.rightNode = p4;

            p1.leftNode = p4;
            p1.rightNode = p3;

            p4.leftNode = null;
            p4.rightNode = null;

            p2.leftNode = null;
            p2.rightNode = null;

            p3.leftNode = null;
            p3.rightNode = null;


            SumPath sumPath = new SumPath();
            sumPath.printSumPath(p0, 22);

        }
    }

}
