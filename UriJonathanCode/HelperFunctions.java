package UriJonathanCode;

import UriJonathanCode.WAVLTree;
import UriJonathanCode.WAVLTree.WAVLNode;

public class HelperFunctions {

	   // TODO delete all of these!!!
	   
	   /*
	    * helper function for us do not check 
	    */
	   public  String toStringPreOrder(){
		   return toStringPreOrderNode(root);
	   }

	   /*
	    * helper function for us do not check
	    */
	   public String toStringPreOrderNode(WAVLNode parent){
		   if (parent == null){
		    return "";
		   } else {
		    return parent.toString() + " " + toStringPreOrderNode(parent.left) + toStringPreOrderNode(parent.right);
		   }
	   }
	   
	   /*
	    * helper function for us do not check
	    */
		public static boolean hasValidRanks(WAVLNode currentRoot){
			
			if (currentRoot == null){
				return true;
			}
			
			if (currentRoot.isLeaf()){
				if (rankDifference(currentRoot,currentRoot.left) == 1 && rankDifference(currentRoot,currentRoot.right) == 1 ){
					return true;
				} else {
					return false;
				}
			} else {
				if(rankDifference(currentRoot,currentRoot.left) > 2 || rankDifference(currentRoot,currentRoot.right) > 2 ){
					return false;
				}
			}
			
			return hasValidRanks(currentRoot.left) && hasValidRanks(currentRoot.right);
		}
		
		/*
		 * 
		 */
	   
		public static boolean isValidTree(WAVLNode currentRoot){
			
			if (currentRoot == null){ // external leaf
				return true;
			}
			
			// must have key smaller than right, bigger than left
			if (currentRoot.left != null && currentRoot.key < currentRoot.left.key){
				return false;
			}
			if(currentRoot.right != null && currentRoot.key > currentRoot.right.key ){
				return false;
			}
			
			if (currentRoot.isLeaf()){
				if (rankDifference(currentRoot,currentRoot.left) == 1 && rankDifference(currentRoot,currentRoot.right) == 1){
					return true;
				} else {
					return false;
				}
			} else {
				if(rankDifference(currentRoot,currentRoot.left) > 2 || rankDifference(currentRoot,currentRoot.right) > 2 ){
					return false;
				}
			}
			
			
			
			return isValidTree(currentRoot.left) && isValidTree(currentRoot.right);
			
		}
	
	
}
