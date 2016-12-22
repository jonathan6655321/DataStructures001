package UriJonathanCode;

import java.util.ArrayList;

import java.util.Collections;

import javax.print.attribute.HashPrintServiceAttributeSet;



public class StatsFromWAVL {
	
	private static final int SIZE = 10000;
	private static final int MULTIPLE = 11;

	public static void main(String[] args) {
		
		double deleteOperationNum;
		double insertOperationNum;
		WAVLTree tree;
		
		for (int multiple=1; multiple < MULTIPLE; multiple++){
			
			insertOperationNum = 0.0;
			deleteOperationNum = 0.0;
			tree = new WAVLTree();
			
			ArrayList<Integer> randomKeys = getRandomKeysArray(multiple);
			
			for(int i: randomKeys ){
				insertOperationNum += tree.insert(i, Integer.toString(i));
			}
			System.out.println(insertOperationNum + " is num of insert operations for tree of size: " + multiple*SIZE);
			System.out.println(insertOperationNum/(SIZE*multiple)  + " is the AVERAGE num of insert operations for tree of size: " + tree.size());
			
			System.out.println("does the tree have valid ranks?: " + WAVLTree.hasValidRanks(tree.getRoot()));
			
//			System.out.println(tree.toStringPreOrder());
			
			
			ArrayList<Integer> randomKeysDel = getRandomKeysArray(multiple);
			
			for(int i: randomKeysDel ){
				deleteOperationNum += tree.delete(i);
			}
//			
//			int currentMinKey;
//			while (tree.getRoot() != null){
////				if ( tree.getRoot().key >= 98){
////					System.out.println(tree.toStringPreOrder());
////				}
//				currentMinKey = tree.getMinKey();
////				System.out.println(currentMinKey);
//				deleteOperationNum += tree.delete(currentMinKey);
//			}
			
			System.out.println(tree.getRoot());
			System.out.println("tree size after deletion is: " + tree.size());
			System.out.println(deleteOperationNum + " is num of delete operations  for tree of size: " + multiple*SIZE);
			System.out.println(deleteOperationNum/(SIZE*multiple) + " is num of AVERAGE delete operations  for tree of size: " + multiple*SIZE);
			
			System.out.println("");
			System.out.println("=================== " + SIZE*multiple + " case done ======================");
			System.out.println("");
		}
		
	}
	
	
	/*
	 * returns a random ArrayList of Integers with length of: size*10000
	 */
	public static ArrayList<Integer> getRandomKeysArray(int multiple){
		
	       ArrayList<Integer> randomKeysArray = new ArrayList<Integer>();
	        for (int i=0; i<multiple*SIZE; i++) {
	            randomKeysArray.add(new Integer(i));
	        }
	        Collections.shuffle(randomKeysArray);
//	        for (int i=0; i<3; i++) {
//	            System.out.println(randomKeysArray.get(i));
//	        }
		return randomKeysArray;
	}

}