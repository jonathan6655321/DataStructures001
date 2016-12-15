package UriJonathanCode;

public class Tester {

	public static void main(String[] args) {
	
		WAVLTree tree1 = new WAVLTree();
		
		
		if (tree1.empty() != true) {
			System.out.println("error empty()");
		} else {
			System.out.println("passed empty()");
		}

		//1
		tree1.insert(5, "ahalan");
		if (!tree1.search(5).equals("ahalan")){
			System.out.println("error search() 1");
		} else {
			System.out.println("passed search() 1");
		}	
		//2
		tree1.insert(6, "six");
		if (!tree1.search(6).equals("six")){
			System.out.println("error search() 2");
		} else {
			System.out.println("passed search() 2");
		}
		//3
		tree1.insert(8, "seven");
		if (!tree1.search(8).equals("seven")){
			System.out.println("error search() 3");
		} else {
			System.out.println("passed search() 3");
		}	
		//4
		tree1.insert(5, "ahalan");
		if (!tree1.search(5).equals("ahalan")){
			System.out.println("error search() 4");
		} else {
			System.out.println("passed search() 4");
		}	
		

		tree1.insert(10, "hii");
		tree1.insert(13, "hii");
		System.out.println(tree1.toStringPreOrder());
		tree1.insert(2, "hey");
		tree1.insert(0, "hey");
		System.out.println(tree1.toStringPreOrder());
		

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
