package UriJonathanCode;

import java.util.Iterator;

/**
 *
 *Uri and Jonathan
 *
 * WAVLTree
 *
 * An implementation of a WAVL Tree with
 * distinct integer keys and info
 *
 */

public class WAVLTree {

	private WAVLNode root = null;
	private WAVLNode minNode = null;
	private WAVLNode maxNode = null;
	private int treeSize = 0;
	
	public WAVLTree() {
		root = null;
	}
	
  /**
   * public boolean empty()
   *
   * returns true if and only if the tree is empty
   *
   */
  public boolean empty() {
    return (root == null);
  }

 /**
   * public String search(int k)
   *
   * returns the info of an item with key k if it exists in the tree
   * otherwise, returns null
   */
  public String search(int k)
  {
	  if (root == null){
		  return null;
	  }
	  
	  WAVLNode foundNode = this.root.nodeSearch(k);
	  if (foundNode.key == k ){
		  return foundNode.info;
	  }
	  
	  return null;  // to be replaced by student code
  }

  
  /**
   * public int insert(int k, String i)
   *
   * inserts an item with key k and info i to the WAVL tree.
   * the tree must remain valid (keep its invariants).
   * returns the number of rebalancing operations, or 0 if no rebalancing operations were necessary.
   * returns -1 if an item with key k already exists in the tree.
   */
   public int insert(int k, String i) {
	   
	   WAVLNode newNode = new WAVLNode(k, i);
	   
	   // empty tree:
	   if (this.root == null){
		   this.root = newNode;
	   }
	  
	   WAVLNode parentNode = this.root.nodeSearch(k);
	   
	   // node with key k already exists:
	   if (parentNode.key == k) {
		   return -1;
	   }
	   
	   // after we are sure the newNode will be inserted:
	   this.updateMinInsert(newNode);
	   this.updateMaxInsert(newNode);
	   this.treeSize++;
	   
	   // found node is the parent of the node we are inserting
	   if ( k > parentNode.key) {
		   parentNode.right = newNode;
	   } else {
		   parentNode.left = newNode;
	   }
	   
	   newNode.parent = parentNode;
	   
	   // rebalance: 
	   
	   //case B:
	   if ((parentNode.right != null) && (parentNode.left != null)){
		   return 0;
	   }
	   
	   //case A: 
	   parentNode.promote();
	   return 1 + rebalanceAfterPromoting(parentNode);
   }

   /*
    * after insertion:
    * rebalances tree starting from given node
    * assumes both subtrees of node are valid
    * returns total number of rotations + promotions (no demotions in this case) 
    */
   public int rebalanceAfterPromoting(WAVLNode node) {
	   
	   if (node == root){
		   return 0;
	   }
	   
	   WAVLNode parent = node.parent;
	   int parentRank = node.parent.rank;
	   int nodeRank = node.rank;
	   
	   
	   // case 1 - (1,1) above current node, or (2,2) before the promotion:
	   if (rankDifference(parent, node) == 1 ) {
		   return 0;
	   }

	   WAVLNode sister; // written in female form but addresses both sexes
	   if (node.isLeftChild()){
		   sister = parent.right;
	   } else { 
		   sister = parent.left;	   
	   }
	   
	   
	   // case 2 - (0,1) or (1,0) above current node (initial case too):
		   if (rankDifference(parent, sister) == 1) {
			   parent.promote();
			   return 1 + rebalanceAfterPromoting(parent);
		   }			   
	   
	   
	   // case 3 - (0,2) or (2,0) above current node:
	   // null sister is also a (0,2) case
	   if (node.isLeftChild()) {
		   // (1,2) below node - 1 rotation case:
		   if (rankDifference(node, node.left) == 1){
			   
			   rotateRight(node,parent);
			   parent.demote();
			   return 2;
		   } 
		   
		   // (2,1) below node - 2 rotations case:
		   else if (rankDifference(node, node.left) == 2 ) {
			   WAVLNode climberNode = node.right;
			   rotateLeft(node, climberNode);
			   rotateRight(climberNode, climberNode.parent); // climberNode.parent is original nodes parent
			   climberNode.left.demote();
			   climberNode.right.demote();
			   climberNode.promote();
			   return 5;
		   }
	   } else {
		   // (2,1) below node - 1 rotation case:
		   if (rankDifference(node, node.right) == 1){
			   rotateLeft(parent, node);
			   parent.demote();
			   return 2;
		   } 
		   // (1,2) below node - 2 rotations case: 
		   else if (rankDifference(node, node.right) == 2 ) {
			   WAVLNode climberNode = node.left;
			   rotateRight(climberNode, node);
			   rotateLeft(climberNode.parent, climberNode); // climberNode.parent is original nodes parent
			   climberNode.left.demote();
			   climberNode.right.demote();
			   climberNode.promote();
			   return 5;
		   }
	   }
	   
	   return -5000; 
   }
   
   
   /*
    * 
    */
   public int rankDifference(WAVLNode parent, WAVLNode child){
	   
	   if (parent == null){
		   return -1;
	   }
	   
	   int externalLeafRank = -1;
	   
	   if (child == null) {
		   return parent.rank - externalLeafRank;
	   }
	   
	   return parent.rank - child.rank;
   
//   new variation: 
//	   if(child == null){
//		   if((parent.right != null) || (parent.left!=null)){
//			   return 2;
//		   } else {
//			   return 1;
//		   }
//		   
//	   }
//	   return child.rankDifferenceFromParent;
   
   }
   
   
   
   /*
    * @pre: child isn't root
    * child is the left child of parent
    */
   
   public void rotateRight(WAVLNode child, WAVLNode parent) {
	  System.out.println("I am rotating right!");
	   
	   if(root == parent) {
		   child.parent = null;
		   root = child;
	   } else { 
		   child.parent = parent.parent;
		   if (parent.isLeftChild()) {
			   parent.parent.left = child;
		   } else {
			   parent.parent.right = child;
		   }		   
	   }
	   
	   
	   parent.left = child.right;
	   if (child.right != null) {
		   child.right.parent = parent;		   
	   }
	   
	   child.right = parent;
	   parent.parent = child;
	   
   }
   
   public void rotateLeft(WAVLNode parent, WAVLNode child){
	  
	   System.out.println("I am rotating left!");
	   
	   if(root == parent) {
		   child.parent = null;
		   root = child;
	   } else {
		   child.parent = parent.parent;
		   if (parent.isLeftChild()) {
			   parent.parent.left = child;
		   } else {
			   parent.parent.right = child;
		   }		   
	   }
	   
	   
	   parent.right = child.left;
	   if (child.left != null) {
		   child.left.parent = parent;		   
	   }
	   
	   child.left = parent;
	   parent.parent = child;
	   
   }

   
  /**
   * public int delete(int k)
   *
   * deletes an item with key k from the binary tree, if it is there;
   * the tree must remain valid (keep its invariants).
   * returns the number of rebalancing operations, or 0 if no rebalancing operations were needed.
   * returns -1 if an item with key k was not found in the tree.
   * 
   * 
   * for us:
   * update min max
   * update size
   */
   public int delete(int k)
   {
	   // empty tree
	   if (root == null){
		   return -1;
	   }
		  
		
	  WAVLNode toBeDeleted = this.root.nodeSearch(k);
	  int countActions = 0;
	  
	  // there is no node with the key k
	  if (toBeDeleted.key != k){
		  return -1;
	  }
	  
	  updateMinDelete(toBeDeleted);
	  updateMaxDelete(toBeDeleted);
	  
	  
	  // collecting the details about the case
	  boolean isLeaf = toBeDeleted.isLeaf();
	  boolean isUnary = toBeDeleted.isUnary();
	 
	  
	  if (!(isLeaf && isUnary)){ //toBeDeleted is not a leaf nor an unary node
//		  successor = toBeDeleted.getSuccessor();
//		  int successorKey = successor.key;
//		  String successorInfo = successor.info;
//		  successor.key = toBeDeleted.key;
//		  successor.info = toBeDeleted.info;
//		  toBeDeleted.key = successorKey;
//		  toBeDeleted.info = successorInfo;
//		  toBeDeleted = successor;
	  }
	
	  isLeaf = toBeDeleted.isLeaf();
	  isUnary = toBeDeleted.isUnary();
	  boolean hasLeftChild = toBeDeleted.hasLeftChild();
	  WAVLNode parentNode = toBeDeleted.parent; 
	  boolean isLeftChild = toBeDeleted.isLeftChild();
	  
	  // terminal case 1: toBeDeleted and his sister are both leafs
	  if (isLeaf){
		  if (isLeftChild){
			  if (parentNode.right != null){
				  parentNode.left = null;
				  this.treeSize--;
				  return 0;	
			  }
		  } else {
			  if (parentNode.left != null){
				  parentNode.right = null;
				  this.treeSize--;
				  return 0;	
			  }
		  }  	
	  }
	  
	  // two options: terminal or not, depends on the R-D with the parent
	  if (isUnary){
		  if (hasLeftChild && isLeftChild){ 
			  toBeDeleted.left.parent = parentNode;
			  parentNode.left = toBeDeleted.left;			  
		  }
		  else if (hasLeftChild && !isLeftChild){
			  toBeDeleted.left.parent = parentNode;
			  parentNode.right = toBeDeleted.left;
		  }
		  else if (!hasLeftChild && isLeftChild){
			  toBeDeleted.right.parent = parentNode;
			  parentNode.left = toBeDeleted.right;
		  }
		  else if (!hasLeftChild && !isLeftChild){
			  toBeDeleted.right.parent = parentNode;
			  parentNode.right = toBeDeleted.right;
		  }
		// terminal case 2: toBeDeleted is an unary node and the R-D with its parent is 1
		  if (rankDifference(parentNode, toBeDeleted)==1){
			  this.treeSize--;
			  return 0;	
		  }
		// non-terminal case 3: toBeDeleted is an unary node and the R-D with its parent is 2
	  }
	  
	  
	  // non-terminal case 1: toBeDeleted is a leaf and his parent is an unary node
	  if (isLeaf && parentNode.isUnary()){
		  if (isLeftChild){
			  parentNode.left = null;
		  } else {
			  parentNode.right = null;
		  }
		  parentNode.demote();
		  countActions++;
	  }
	  
	  // non-terminal case 2: toBeDeleted is a leaf and the R-D with its parent is 2
	  if (isLeaf && rankDifference(parentNode, toBeDeleted)==2){
		  if (isLeftChild){
			  parentNode.left = null;
		  } else {
			  parentNode.right = null;
		  }
	  }
	  
	  this.treeSize--;
	  return countActions + rebalanceAfterDeletion(parentNode);
   }

   public int rebalanceAfterDeletion(WAVLNode parentNode) {
	   
	   
	   WAVLNode rightChild = parentNode.right;
	   WAVLNode leftChild = parentNode.left;
	   WAVLNode newParent = parentNode.parent;
	   int leftRankDiff = rankDifference(parentNode, leftChild);
	   int rightRankDiff = rankDifference(parentNode, rightChild);
	   
	   
	   // case 1 - "Demote": parentNode is a (3,2) or (2,3) node
	   if ((leftRankDiff > 1) && (rightRankDiff > 1)){
		   parentNode.demote();
		   if (newParent == null){  // parentNode is root
			   return 1;
		   } else {
			   return 1 + rebalanceAfterDeletion(newParent);
		   }
	   } 
	  
	   
	   
	   if ((leftRankDiff==1) && (rightRankDiff==3)) {
		   // case 2 (left) - "Double demote":  parentNode is a (1,3) node and the left child is a (2,2) node
		   if  ((rankDifference(leftChild, leftChild.left) == 2) && (rankDifference(leftChild, leftChild.right) == 2)){
			   leftChild.demote();
			   parentNode.demote();
			   if (newParent == null){  // parentNode is root
				   return 2;
			   } else {
				   return 2 + rebalanceAfterDeletion(newParent);
			   }
		   }
	   } else if ((leftRankDiff==3) && (rightRankDiff==1)) {
		   // case 2 (right) - "Double demote":  parentNode is a (3,1) node and the right child is a (2,2) node
		   if  ((rankDifference(rightChild, rightChild.left) == 2) && (rankDifference(rightChild, rightChild.right) == 2)){
			   rightChild.demote();
			   parentNode.demote();
			   if (newParent == null){  // parentNode is root
				   return 2;
			   } else {
				   return 2 + rebalanceAfterDeletion(newParent);
			   }
		   }
	   }
	   
	   
	   if ((leftRankDiff==1) && (rightRankDiff==3) ) {
		   // case 3 (left) - "Rotate":  parentNode is a (1,3) node and the R-D of the left child with the leftmost grandson is 1
		   if  (rankDifference(leftChild, leftChild.left) == 1){
			   WAVLNode climberNode = leftChild;
			   rotateRight(climberNode,parentNode);
			   parentNode.demote();
			   climberNode.promote();
			   return 3;			   
		   }
	   } else if ((leftRankDiff==3) && (rightRankDiff==1)) {
		   // case 3 (right) - "Rotate":  parentNode is a (3,1) node and the R-D of the right child with the rightmost grandson is 1
		   if (rankDifference(rightChild, rightChild.right) == 1){
			   WAVLNode climberNode = rightChild;
			   rotateLeft(parentNode,climberNode);
			   parentNode.demote();
			   climberNode.promote();
			   return 3;
		   }
	   }
	   
	   
	   if ((leftRankDiff==1) && (rightRankDiff==3) ) {
		   // case 4 (left) - "Double Rotate":  parentNode is a (1,3) node and the R-D of the left child with the leftmost grandson is 2
		   if  (rankDifference(leftChild, leftChild.left) == 2){
			   WAVLNode climberNode = leftChild.right;
			   rotateLeft(leftChild,climberNode);
			   rotateRight(climberNode,parentNode);
			   climberNode.promote();
			   climberNode.promote();
			   leftChild.demote();
			   parentNode.demote();
			   parentNode.demote();
			   return 7;	   
		   }
	   } else if ((leftRankDiff==3) && (rightRankDiff==1)) {
		   // case 4 (right) - "Double Rotate":  parentNode is a (3,1) node and the R-D of the right child with the rightmost grandson is 2
		   if  (rankDifference(rightChild, rightChild.right) == 2){
			   WAVLNode climberNode = rightChild.left;
			   rotateRight(climberNode,rightChild);
			   rotateLeft(parentNode,climberNode);
			   climberNode.promote();
			   climberNode.promote();
			   rightChild.demote();
			   parentNode.demote();
			   parentNode.demote();
			   return 7;			   
		   }
	   }   
	   
	   return -10000;
   }
   
   
   
   /**
    * public String min()
    *
    * Returns the i×nfo of the item with the smallest key in the tree,
    * or null if the tree is empty
    */
   public String min()
   {
	   if (this.root == null){
		   return null;
	   } else {
		   return this.minNode.info;
	   }
   }

   /**
    * public String max()
    *
    * Returns the info of the item with the largest key in the tree,
    * or null if the tree is empty
    */
   public String max()
   {
	   if (this.root == null){
		   return null;
	   } else {
		   return this.maxNode.info;
	   }
   }

  /**
   * public int[] keysToArray()
   *
   * Returns a sorted array which contains all keys in the tree,
   * or an empty array if the tree is empty.
   */
  public int[] keysToArray()
  {
        int[] arr = new int[42]; // to be replaced by student code
        return arr;              // to be replaced by student code
  }

  /**
   * public String[] infoToArray()
   *
   * Returns an array which contains all info in the tree,
   * sorted by their respective keys,
   * or an empty array if the tree is empty.
   */
  public String[] infoToArray()
  {
        String[] arr = new String[42]; // to be replaced by student code
        return arr;                    // to be replaced by student code
  }
  
  private class inOrderIterator {
	  
	  private WAVLNode currentNode = null;
	  private WAVLNode lastNode = null;
	  
	  public inOrderIterator (WAVLNode minNode, WAVLNode maxNode){
		  this.currentNode = minNode;
		  this.lastNode = maxNode;
	  }
	  
	  public boolean hasNext() {
		  if (this.currentNode == this.lastNode){
			  return false;
		  }
		  else {
			  return true;
		  }
	  }
	  
	  public WAVLNode next(){
		  
		  
	  }
	  
	  public void remove(){
	  }
	  
  }

 
  private void updateMinInsert(WAVLNode insertedNode){
	  if (this.minNode == null){
		  this.minNode = insertedNode;
	  }
	  
	  else if(insertedNode.key < this.minNode.key){
		  this.minNode = insertedNode;
	  }
	  return;
  }
  
  private void updateMaxInsert(WAVLNode insertedNode){
	  if (this.maxNode == null){
		  this.maxNode = insertedNode;
	  }
	  
	  else if(insertedNode.key > this.maxNode.key){
		  this.maxNode = insertedNode;
	  }
	  return;
  }
  
  private void updateMinDelete(WAVLNode toBeDeletedNode){
	  if (this.minNode == null){ // trying to delete a node from an empty tree
		  return;
	  }
	  
	  if (toBeDeletedNode.key == this.minNode.key ){
		  if (this.root.key != this.minNode.key){
			  this.minNode = this.minNode.parent;
		  } else {
			  this.minNode = this.root.right;
		  }
	  }
	  
	  return;
  }
  
  private void updateMaxDelete(WAVLNode toBeDeletedNode){
	  if (this.maxNode == null){ // trying to delete a node from an empty tree
		  return;
	  }
	  
	  if (toBeDeletedNode.key == this.maxNode.key ){
		  if (this.root.key != this.maxNode.key){
			  this.maxNode = this.maxNode.parent;
		  } else {
			  this.maxNode = this.root.left;
		  }
	  }
	  
	  return;
  }
  
   /**
    * public int size()
    *
    * Returns the number of nodes in the tree.
    *
    * precondition: none
    * postcondition: none
    */
   public int size()
   {
	   return this.treeSize; // to be replaced by student code
   }

   public String toStringPreOrder(){
	   return toStringPreOrderNode(root);
   }
   
   public String toStringPreOrderNode(WAVLNode parent){
	   if (parent == null){
	    return "";
	   } else {
	    return parent.toString() + " " + toStringPreOrderNode(parent.left) + toStringPreOrderNode(parent.right);
	   }
   }
  /**
   * public class WAVLNode
   *
   * If you wish to implement classes other than WAVLTree
   * (for example WAVLNode), do it in this file, not in 
   * another file.
   * This is an example which can be deleted if no such classes are necessary.
   */
  public class WAVLNode{
	  
	  public String info;
	  public int key;
	  public int rank = 0;
//	  public int rankDifferenceFromParent = 
	  
	  public WAVLNode parent;
	  public WAVLNode left;
	  public WAVLNode right;

	  public WAVLNode(int key, String info) {
		  this.info = info;
		  this.key = key;  
	  }
	  
	  public String toString(){
		  return "(" + key + ":" + info + ")";
	  }
	  
	  /*
	   * recursively searches subtree starting from node
	   * returns the node with the given key
	   * or parent of that node if no node with such key exists
	   */
	  public WAVLNode nodeSearch(int key){
//		  System.out.println("Searching for "+key+", current node: "+this.key);
		  
		  if (key == this.key) {
			  return this;
		  }
		  
		  if (key > this.key){
			  if (this.right == null){
				  return this;
			  } else {
				  return this.right.nodeSearch(key);				  
			  }
		  }
		  
		  if (key < this.key){
			  if (this.left == null){
				  return this;
			  } else {
				  return this.left.nodeSearch(key);				  
			  }
		  }
		  
		  return this;
	  }
  
  
	  /*
	   * promotes
	   */
	  public void promote(){
		  this.rank += 1;
	  }
	  
	  /*
	   * demotes
	   */
	  public void demote(){
		  this.rank -= 1;
	  }
	  
	  
	  /*
	   * precondition - node is not root.
	   */
	  public boolean isLeftChild() {
		   if (this == this.parent.left){
			   return true;
		   }
		   
		   return false;
	  }
	  
	  public boolean isLeaf() {
		   if (this.left==null && this.right==null){
			   return true;
		   }
		   
		   return false;
	  }
	  
	  public boolean isUnary() {
		   if (this.left==null ^ this.right==null){
			   return true;
		   }
		   
		   return false;
	  }
	  
	  public boolean hasLeftChild() {
		   if (this.left != null){
			   return true;
		   }
		   
		   return false;
	  }
	  
  } // end of WAVLNode class

}
  

