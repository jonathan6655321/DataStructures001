package UriJonathanCode;

import java.util.*;

public class Main {

    public static void main(String[] args) throws Exception {
        int n = 20;
        int range = 99;// 99999999;

        CountInsertsAndDeletes(n, range);
    }

    private static void CountInsertsAndDeletes(int n, int range) throws Exception {
        System.out.println("test for n=" + n);

        WAVLTree x = new WAVLTree();
        Random r = new Random();
        double sum = 0;
        int max = 0;
        for (int i = 0; i < n; i++) {
            int item = new Integer (r.nextInt(range) + 1);
            int actions = x.insert(item, String.valueOf(item));
            sum += actions;
            if (actions > max)
                max = actions;
        }
        System.out.println("insert: avg: " + sum/n + " max: " + max);
        printTree(x.root);
        int[] keys = x.keysToArray();

        sum = 0;
        max = 0;
        for (int i = 0; i < keys.length; i++) {
//        for (int i = keys.length - 1; i >= 0; i--) {
//            int item = new Integer (r.nextInt(range) + 1);
            int actions = x.delete(keys[i]);
            sum += actions;
            if (actions > max)
                max = actions;
        }
        System.out.println("delete: avg: " + sum/n + " max: " + max);
        System.out.println("root: " + x.root);

    }

    public static void printTree(WAVLTree.WAVLNode root) {
        List<WAVLTree.WAVLNode> listOfNodes = new ArrayList<WAVLTree.WAVLNode>();

        traverseLevels(root, listOfNodes);

        // printing level wise
        int count = 0, level = 0;

        int maxLevel = Integer.toBinaryString(listOfNodes.size()).length();
        int maxLineLen = (int) Math.pow(2, maxLevel - 1);

        while (count < listOfNodes.size()){
            int printLen = (int) Math.pow(2, level++);
            int lineLength = printLen - count + 1;
            int whiteSpaces = maxLineLen - lineLength;

            for (int i = 0; i < whiteSpaces; i++){
                System.out.print(" ");
            }


            for (int i=count; i < printLen -1 && i < listOfNodes.size();++i){
                WAVLTree.WAVLNode node = listOfNodes.get(i);
                if (node.info == "placeholder")
                    System.out.print("x ");
                else
                System.out.print(node.key+" ");
            }
            System.out.println();
            count = printLen-1;
        }
    }

    private static void traverseLevels(WAVLTree.WAVLNode root, List<WAVLTree.WAVLNode> listOfNodes) {
        Queue<WAVLTree.WAVLNode> nodes1 = new LinkedList<>();
        Queue<WAVLTree.WAVLNode> nodes2 = new LinkedList<>();
        if (root!=null){
            nodes1.add(root);
            listOfNodes.add(root);
            while(!nodes1.isEmpty() || nodes2.isEmpty()){
                boolean allLineEmpty = true;

                while(!nodes1.isEmpty()) {

                    root = nodes1.poll();
                    if (root.left != null) {
                        allLineEmpty = false;
                        listOfNodes.add(root.left);
                        nodes2.add(root.left);
                    } else {
                        WAVLTree.WAVLNode placeholder = new WAVLTree.WAVLNode(root, 0, "placeholder");
                        nodes2.add(placeholder);
                        listOfNodes.add(placeholder);
                    }
                    if (root.right != null) {
                        allLineEmpty = false;
                        listOfNodes.add(root.right);
                        nodes2.add(root.right);
                    } else {
                        WAVLTree.WAVLNode placeholder = new WAVLTree.WAVLNode(root, 0, "placeholder");
                        nodes2.add(placeholder);
                        listOfNodes.add(placeholder);
                    }
                }
                if (allLineEmpty)
                    break;
                else
                    allLineEmpty = true;

                while(!nodes2.isEmpty()) {
                    root = nodes2.poll();
                    if (root.left != null) {
                        allLineEmpty = false;
                        listOfNodes.add(root.left);
                        nodes1.add(root.left);
                    } else {
                        WAVLTree.WAVLNode placeholder = new WAVLTree.WAVLNode(root, 0, "placeholder");
                        nodes1.add(placeholder);
                        listOfNodes.add(placeholder);
                    }
                    if (root.right != null) {
                        allLineEmpty = false;
                        listOfNodes.add(root.right);
                        nodes1.add(root.right);
                    } else {
                        WAVLTree.WAVLNode placeholder = new WAVLTree.WAVLNode(root, 0, "placeholder");
                        nodes1.add(placeholder);
                        listOfNodes.add(placeholder);
                    }
                }
                if (allLineEmpty)
                    break;
            }

        }
    }
}
