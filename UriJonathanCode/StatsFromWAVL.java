package UriJonathanCode;

import java.util.ArrayList;
import java.util.Collections;

public class StatsFromWAVL {

	public static void main(String[] args) {
		
		for (int size=1; size < 2; size++){
			
			double insertOperationNum = 0.0;
			double deleteOperationNum = 0.0;
			WAVLTree tree = new WAVLTree();
			
			ArrayList<Integer> randomKeys = getRandomKeysArray(size);
			
			for(int i: randomKeys ){
				insertOperationNum += tree.insert(i, Integer.toString(i));
			}
			System.out.println(insertOperationNum + " is num of insert operations for tree of size: " + size*10000);
			System.out.println(insertOperationNum/10000*size  + " is the AVERAGE num of insert operations for tree of size: " + tree.size());
			
			System.out.println(tree.toStringPreOrder());
			
			int currentMinKey;
			while (tree.getRoot() != null){
				if ( tree.getRoot().key == 98){
					System.out.println(tree.toStringPreOrder());
					System.out.println(tree.getRoot().right);
					System.out.println(tree.getMinKey());
				}
				currentMinKey = tree.getMinKey();
				System.out.println(currentMinKey);
				System.out.println(tree.getMinKey());
				deleteOperationNum += tree.delete(currentMinKey);
			}
			
			System.out.println(tree.getRoot());
			System.out.println(deleteOperationNum + " is num of delete operations  for tree of size: " + tree.size());
			System.out.println(deleteOperationNum/10000*size + " is num of AVERAGE delete operations  for tree of size: " + tree.size());
			
			
		}
		
	}
	
	
	/*
	 * returns a random ArrayList of Integers with length of: size*10000
	 */
	public static ArrayList<Integer> getRandomKeysArray(int size){
		
	       ArrayList<Integer> randomKeysArray = new ArrayList<Integer>();
	        for (int i=0; i<size*100; i++) {
	            randomKeysArray.add(new Integer(i));
	        }
	        Collections.shuffle(randomKeysArray);
//	        for (int i=0; i<3; i++) {
//	            System.out.println(randomKeysArray.get(i));
//	        }
		return randomKeysArray;
	}

}
