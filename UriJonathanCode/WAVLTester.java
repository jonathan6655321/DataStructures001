package UriJonathanCode;

import java.util.Random;


public class WAVLTester {
	
	public static void main(String[] args){	
		
		/* Tester for WAVLTree.
		 * Create tree
		 * Create array of random integers
		 * Add them to the tree
		 * Print the array, rebalancing actions and the tree
		 */
		System.out.println("WAVLTester");
		WAVLTree tree= new WAVLTree();
		longExp(tree);
		System.out.println("done");
	}

	/*
	 * The test - insert 10,000 keys in random (from 1..10000),
	 * Afterwards - delete 9,000 keys in random (from 1..10000),
	 * Repeat five times.
	 */
	public static void longExp(WAVLTree t){
		for (int i=0;i<5;i++){
		runN(t,10000);
		delShuffle(t,10000,9000);
		}
	}
	/*
	 * Create array of n keys shuffle them, and insert them to the tree
	 * The function checks that the tree is valid after each insert op
	 */
	public static void runN(WAVLTree t, int n) {
		int[] keys = keys(n);
		shuffleArray(keys);
		String[] data = infos(keys);
		int j=0;
		for (int i = 0; i < keys.length; i++) {
			j += t.insert(keys[i], data[i]);
			if (!WAVLTree.hasValidRanks(t.getRoot())){
				System.out.println("Fuck on runN");
			}
		}
		System.out.println("Average for shuffeled insert is "+(float)j/n);

	}
	/*
	 * Create an array of n keys, shuffle them, and delete the first j keys from the tree (j<=n)
	 * The function checks that the tree is valid after each delete op 
	 */
	public static void delShuffle(WAVLTree t, int n,int j) {
		int[] keys = keys(n);
		shuffleArray(keys);
		for (int i=0; i<j;i++){
			t.delete(keys[i]);
			if (!WAVLTree.hasValidRanks(t.getRoot())){
				System.out.println("Fuck on delShuffle");
			}
		}
	}

	public static void delN(WAVLTree t, int n) {
		int j=0;
		for (int i = 0; i < n; i++) {
			j += t.delete(i);
		}
		System.out.println("Average for sequential delete is "+(float)j/n);

	}

	static int[] keys(int length) {
		int[] ar = new int[length];
		for (int i = 1; i <= length; i++) {
			ar[i - 1] = i;
		}
		return ar;
	}

	static String[] infos(int[] keys) {
		String[] ar = new String[keys.length];
		for (int i = 0; i < keys.length; i++) {
			ar[i] = Integer.toString(keys[i]);
		}
		return ar;
	}

	static void shuffleArray(int[] ar) {
		Random rnd = new Random();
		for (int i = ar.length - 1; i > 0; i--) {
			int index = rnd.nextInt(i + 1);
			// Simple swap
			int a = ar[index];
			ar[index] = ar[i];
			ar[i] = a;
		}
	
	}

//		Random rand = new Random();
//		int[] keysArr = new int[15];
//		for(int i=0;i<15;i++){			
//			int x = rand.nextInt(100);
//			keysArr[i]=x;
//		}
//		
//		System.out.println("insert the following elements: \n"+Arrays.toString(keysArr)+":\n");
//		tree.insert(keysArr);
//		System.out.println(tree.toString());
//
//	}


}
