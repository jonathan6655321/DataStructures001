package UriJonathanCode;

import java.util.ArrayList;
import java.util.Collections;

public class StatsFromWAVL {

	public static void main(String[] args) {
		
		for (int size=1; size < 11; size++){
			
			int insertOperationNum = 0;
			int deleteOperationNum = 0;
			WAVLTree tree = new WAVLTree();
			
			for(int i: getRandomKeysArray(size)){
				insertOperationNum += tree.insert(i, Integer.toString(i));
			}
			
//			int currentMinKey;
//			while (tree.getRoot() != null){
//				currentMinKey = tree.getMinKey();
//				deleteOperationNum = tree.delete(currentMinKey);
//			}
			System.out.println(insertOperationNum + " is num of insert operations for tree of size: " + tree.size());
			System.out.println(insertOperationNum/tree.size() + " is the AVERAGE num of insert operations for tree of size: " + tree.size());
//			System.out.println(deleteOperationNum + " is num of delete operations  for tree of size: " + tree.size());
			
		}
		
	}
	
	
	/*
	 * returns a random ArrayList of Integers with length of: size*10000
	 */
	public static ArrayList<Integer> getRandomKeysArray(int size){
		
	       ArrayList<Integer> randomKeysArray = new ArrayList<Integer>();
	        for (int i=0; i<size*10000; i++) {
	            randomKeysArray.add(new Integer(i));
	        }
	        Collections.shuffle(randomKeysArray);
//	        for (int i=0; i<3; i++) {
//	            System.out.println(randomKeysArray.get(i));
//	        }
		return randomKeysArray;
	}

}
