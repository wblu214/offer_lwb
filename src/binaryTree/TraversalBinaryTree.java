package binaryTree;

import java.util.Scanner;

public class TraversalBinaryTree {
    class TreeNode{
        String val;
        TreeNode left;
        TreeNode right;
        public TreeNode(String val){
            this.val = val;
        }
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        String str = sc.nextLine();
        String[] arr = str.replace("[","").replace("]","").split(",");

    }
}