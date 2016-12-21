package UriJonathanCode;

import java.util.Arrays;

import UriJonathanCode.WAVLTree.WAVLNode;

public class Tester {

	public static void main(String[] args) {
	
		WAVLTree tree1 = new WAVLTree();
		
		System.out.println(tree1.size());
		
		if (tree1.empty() != true) {
			System.out.println("error empty()");
		} else {
			System.out.println("passed empty()");
		}

		//1
		tree1.insert(5, "five");
		if (!tree1.search(5).equals("ahalan")){
			System.out.println("error search() 1");
		} else {
			System.out.println("passed search() 1");
		}
		System.out.println(tree1.size() + "after five");
		
		//2
		tree1.insert(6, "six");
		if (!tree1.search(6).equals("six")){
			System.out.println("error search() 2");
		} else {
			System.out.println("passed search() 2");
		}
		System.out.println(tree1.size() + "after six");
		//3
		tree1.insert(8, "eight");
		if (!tree1.search(8).equals("eight")){ //if 8 was not in our tree we would get an exception here. cause: tree1.search(8) == null
			System.out.println("error search() 3");
		} else {
			System.out.println("passed search() 3");
		}	
		System.out.println(tree1.size() + "after eight");
		//4
		tree1.insert(5, "five");
		if (!tree1.search(5).equals("five")){
			System.out.println("error search() 4");
		} else {
			System.out.println("passed search() 4");
		}	
		System.out.println(tree1.size() + "after another 5");

		tree1.insert(10, "ten");
		System.out.println(tree1.size() + "after 10");
		tree1.insert(13, "thirteen");
		System.out.println(tree1.size()+ "after 13");
		System.out.println(tree1.toStringPreOrder());
		tree1.insert(2, "two");
		System.out.println(tree1.size() + "after two");
		tree1.insert(0, "zero");
		System.out.println(tree1.size() + "after zero");
		System.out.println(tree1.toStringPreOrder());
		

		System.out.println(Arrays.deepToString(tree1.infoToArray()));
		System.out.println(Arrays.toString(tree1.keysToArray()));
		
		tree1.delete(5);
		System.out.println(tree1.toStringPreOrder());
		
		tree1.insert(9, "aloha");
		System.out.println(tree1.toStringPreOrder());
		
		tree1.insert(7, "tree");
		System.out.println(tree1.toStringPreOrder());
		
		System.out.println("");
		System.out.println("deleting (2:hey):");
		tree1.delete(2);
		System.out.println(tree1.toStringPreOrder());



	}
	
	
	

}
