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
		tree1.insert(7, "seven");
		if (!tree1.search(7).equals("seven")){
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
	}

}
