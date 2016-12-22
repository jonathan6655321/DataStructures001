package UriJonathanCode;

import java.util.Iterator;

import UriJonathanCode.WAVLTree.WAVLNode;

/*
 * Comments: 
 * 
 * when referring to n - n is the number of nodes in the tree, its size.
 * 
 * each functions complexity is written in its description. 
 * even if this complexity is used in the analyisis of a bigger function
 */







/**
 *
 *Uri and Jonathan
 *
 * WAVLTree
 *
 * An implementation of a WAVL Tree with distinct integer keys and info.
 * WAVLTree also holds pointers to nodes: root, maxNode, minNode. (Updated upon insert and delete). 
 * A variable treeSize: holds the tree size. (Updated upon insert and delete)
 * 
 *
 */

public class WAVLTree {

	public WAVLNode root = null;
	private WAVLNode minNode = null;
	private WAVLNode maxNode = null;
	private int treeSize = 0;
	
	public WAVLTree() {
		root = null;
	}
	
  /**
   * public boolean empty()
   *
   * returns true if and only if the tree is empty.
   * 
   * Complexity = O(1):
   * single access to existing pointer.
   */
  public boolean empty() {
    return (this.root == null);
  }

 /**
   * public String search(int k)
   *
   * returns the info of an item with key k if it exists in the tree, otherwise, returns null
   * 
   * Complexity = O(log n):
   * O(1) operations + a call to nodeSearch which performs at O(log n)  .
   * 
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
	  
	  return null;  
  }

  
  /**
   * public int insert(int k, String i)
   *
   *
   * inserts an item with key k and info i to the WAVL tree.
   * the tree must remain valid (keep its invariants).
   * returns the number of rebalancing operations, or 0 if no rebalancing operations were necessary.
   * returns -1 if an item with key k already exists in the tree.
   * 
   * Description: 
   * deals with empty tree case first. 
   * searches for place to insert node. 
   * updates min max and treeSize
   * promotes and rebalances if needed. 
   * 
   * 
   * Complexity = O(log n):
   * 
   * at most 1 call to updateMinInsert = O(1)  
   * at most 1 call to updateMaxInsert = O(1)  
   * 1 call to nodeSearch = O(log n)  
   * at most 1 call to promote = O(1)  
   * 1 call to rebalanceAfterPromoting = O(log n)  
   * 
   * 
   */
   public int insert(int k, String i) {
	   
	   WAVLNode newNode = new WAVLNode(k, i);
	   
	   // empty tree:
	   if (this.root == null){
		   this.root = newNode;
		   this.updateMinInsert(newNode);
		   this.updateMaxInsert(newNode);
		   this.treeSize++;
		   return 0;
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
    * assumes both subtrees of node are valid (in each recursive call we make sure this is satisfied)
    * returns total number of rotations + promotions (no demotions in this case) 
    * 
    * Description:
    * recursive function.
    * follows cases from slides (WAVL lecture)
    * 
    * Complexity = O(log n):
    * rotations are terminal cases, and are called O(1) times. each costs O(1) operations.  
    * demote can be called O(1) times and costs O(1) and happens only in terminal cases.  
    * each recursive call does O(1) calls to rankDifference and isLeftChild both have O(1) complexity.  
    * 
    * after each promote we recurse on a node higher (by at least 1) than the previous node.
    * O(log n) promotions each at O(1) complexity.
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
    * returns the rank difference between parent node and its child.
    * 
    *  Complexity = O(1):
    *  easy access to fields of nodes we have pointers to and some basic calculations. 
    */
   public static int rankDifference(WAVLNode parent, WAVLNode child){
	   
	   if (parent == null){
		   return -1;
	   }
	   
	   int externalLeafRank = -1;
	   
	   if (child == null) {
		   return parent.rank - externalLeafRank;
	   }
	   
	   return parent.rank - child.rank;
   }
   
   
   
   /*
    * changes pointers according to rotation guidelines.
    * 
    * Complexity = O(1):
    * constant number of operations.
    */
   public void rotateRight(WAVLNode child, WAVLNode parent) {
//	  System.out.println("I am rotating right!");
	   
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
   
   
   /*
    * changes pointers according to rotation guidelines.
    * 
    * Complexity = O(1):
    * constant number of operations.
    */
   public void rotateLeft(WAVLNode parent, WAVLNode child){
	  
//	   System.out.println("I am rotating left!");
	   
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
   * Description: 
   * if needed replaces node with its successor. 
   * updates min max root and size
   * follows delete guidelines from WAVL lecture.
   * 
   * 
   * Complexity = O(log n):
   * 
   * at most 1 call to updateMinInsert = O(1)
   * at most 1 call to updateMaxInsert = O(1)  
   * 1 call to nodeSearch = O(log n)  
   * O(1) calls to isUnary and to isLeaf = O(1)  
   * O(1) calls to isLeftChild and to hasLeftChild = O(1)  
   * O(1) calls to demote = O(1)  
   * O(1) calls to clear = O(1)  
   * 1 call to rebalanceAfterDeletion = O(log n)  
   * 1 call to successorOf = O(log n)  
   * long block of code for replacing node with successor if needed = O(1)
   * 
   * 
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
	  
	  
	  this.treeSize--;
	  updateMinDelete(toBeDeleted);
	  updateMaxDelete(toBeDeleted);
	  
	  if (toBeDeleted == this.root && this.root.isLeaf()){ 
		  this.root = null;		  
		  return 0;
	  }
	  
	  
	  
	  // collecting the details about the case
	  boolean isLeaf = toBeDeleted.isLeaf();
	  boolean isUnary = toBeDeleted.isUnary();
	 
	 
	  
	  // need to replace node with its successor.
	  if (!isLeaf && !isUnary){ //toBeDeleted is not a leaf nor an unary node
		  WAVLNode successor;
		  successor = successorOf(toBeDeleted);
		  
		  // switch ranks:
		  int toBeDeletedRank = toBeDeleted.rank;
		  toBeDeleted.rank = successor.rank;
		  successor.rank = toBeDeletedRank;
		  
		  // left child "switch"
		  toBeDeleted.left.parent = successor;		 
		  successor.left =  toBeDeleted.left;
		  toBeDeleted.left = null;
		  
		  // successor is right child case:
		  if (successor == toBeDeleted.right){
			  if (toBeDeleted.parent == null){
				  successor.parent = null;
				  toBeDeleted.right = successor.right; // successor.right could be null
				  if (successor.right != null){
					  successor.right.parent = toBeDeleted;  
				  }
				  successor.right = toBeDeleted;
				  toBeDeleted.parent = successor;
				  this.root = successor;
			  } else if (toBeDeleted.parent != null){
					  successor.parent = toBeDeleted.parent;
					  if (toBeDeleted.isLeftChild()){
						  toBeDeleted.parent.left = successor;
					  } else {
						  toBeDeleted.parent.right = successor;
					  }
					  toBeDeleted.parent = successor;
					  toBeDeleted.right = successor.right; // could be null
					  if (successor.right != null){
						  successor.right.parent = toBeDeleted;
					  }
					  successor.right = toBeDeleted;
				  }  
			  } else { // successor is a left child, not of toBeDeleted
				  if (toBeDeleted.parent == null) {
					  WAVLNode sucParent = successor.parent;
					  WAVLNode toBeRight = toBeDeleted.right;
					  
					  successor.parent = toBeDeleted.parent; // could be null
					  if (toBeDeleted.parent != null){
						  if (toBeDeleted.isLeftChild()){
							  toBeDeleted.parent.left = successor;
						  } else {
							  toBeDeleted.parent.right = successor;
						  }
					  }
					  toBeDeleted.right = successor.right; // could be null
					  if (successor.right != null){
						  successor.right.parent = toBeDeleted;
					  }
					  
					  successor.right = toBeRight;
					  toBeRight.parent = successor;
					  
					  toBeDeleted.parent = sucParent;
					  sucParent.left = toBeDeleted;
					  this.root = successor;
				  }  
			  }
	  }
	
	  
	  // now - toBeDeleted is the node to delete.. 
	  isLeaf = toBeDeleted.isLeaf(); // must be one of these exactly.. 
	  isUnary = toBeDeleted.isUnary();
	  boolean hasLeftChild = toBeDeleted.hasLeftChild();
	  WAVLNode parentNode = toBeDeleted.parent; // could be null
	  boolean isLeftChild = toBeDeleted.isLeftChild();
	  
	  // terminal case 1: toBeDeleted and his sister are both leafs
	  if (isLeaf && rankDifference(parentNode, toBeDeleted) == 1){ // neccessarily has parent, else 1 node tree case. handled.
		  if (isLeftChild){
			  if (parentNode.right != null){
				  parentNode.left = null;
				  toBeDeleted.parent = null;
				  return 0;	
			  }
		  } else {
			  if (parentNode.left != null){
				  parentNode.right = null;
				  toBeDeleted.parent = null;
				  return 0;	
			  }
		  }  	
	  }
	  
	  
	  // two options: terminal or not, depends on the Rank Difference with the parent
	  if (isUnary){
		  if (this.root == toBeDeleted){
			  if(hasLeftChild){
				  this.root = toBeDeleted.left;
				  toBeDeleted.left.clear();
				  toBeDeleted.clear();
			  } else {
				  this.root = toBeDeleted.right;
				  toBeDeleted.right.clear();
				  toBeDeleted.clear();
			  }
			  return 0;
		  }
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
			  toBeDeleted.clear();
			  return 0;	
		  }
		// non-terminal case 3: toBeDeleted is an unary node and the R-D with its parent is 2
		// will be handled in rebalance.
	  }
	  
	  
	  // non-terminal case 1: toBeDeleted is a leaf and his parent is an unary node
	  if (isLeaf && parentNode.isUnary()){
		  if (isLeftChild){
			  parentNode.left = null;
		  } else {
			  parentNode.right = null;
		  }
		  toBeDeleted.clear();
		  
		  parentNode.demote(); 
		  countActions++;
		  
		  if (parentNode.parent == null){
			  return 1;
		  } else {
			  parentNode = parentNode.parent; // in order to send father of parent node
		  }
	  }
	  
	  // non-terminal case 2: toBeDeleted is a leaf and the R-D with its parent is 2
	  if (isLeaf && rankDifference(parentNode, toBeDeleted)==2){
		  if (isLeftChild){
			  parentNode.left = null;
		  } else {
			  parentNode.right = null;
		  }
		  toBeDeleted.clear();
	  }
	  
	  
	  return countActions + rebalanceAfterDeletion(parentNode);
   }
	/*
	 * Description:
	 * follows cases from WAVL lecture
	 * 
	 * Complexity: O(log n)
	 * O(1) calls to rankDifferece each recursive call at cost of O(1)
	 * O(1) calls to demote and to premote each recursive call at cost of O(1) 
	 * O(1) calls to rotateRight and to rotateLeft each recursive call O(1)
	 * O(1) calls to isLeaf
	 * 
	 * the function recurses O(log n) times because in each recursive call we recurse at node of 
	 * height higher by 1 at least.
	 * 
	 * in total O(log n) calls of O(1). 
	 * 
	 */
   public int rebalanceAfterDeletion(WAVLNode parentNode) {
	   
	   
	   WAVLNode rightChild = parentNode.right;
	   WAVLNode leftChild = parentNode.left;
	   WAVLNode newParent = parentNode.parent; // could be null
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
			   
			   if (parentNode.isLeaf() && parentNode.rank > 0) { // 2,2 leaf case.
				   parentNode.demote();
				   return 4;
			   }
			   
			   return 3;			   
		   }
	   } else if ((leftRankDiff==3) && (rightRankDiff==1)) {
		   // case 3 (right) - "Rotate":  parentNode is a (3,1) node and the R-D of the right child with the rightmost grandson is 1
		   if (rankDifference(rightChild, rightChild.right) == 1){
			   WAVLNode climberNode = rightChild;
			   rotateLeft(parentNode,climberNode);
			   parentNode.demote();
			   climberNode.promote();
			   
			   if (parentNode.isLeaf() && parentNode.rank > 0) { // 2,2 leaf case
				   parentNode.demote();
				   return 4;
			   }
			   
			   return 3;
		   }
	   }
	   
	   
	   if ((leftRankDiff==1) && (rightRankDiff==3) ) {
		   // case 4 (left) - "Double Rotate":  parentNode is a (1,3) node and the R-D of the left child with the leftmost grandson is 2
		   if  (rankDifference(leftChild, leftChild.left) == 2){ // case 2 handled, only need to check this.
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
	   
	   // if no case entered the tree is valid and stop condition met.
	   return 0;
   }
   
   
   
   /**
    * public String min()
    *
    * Returns the info of the item with the smallest key in the tree,
    * or null if the tree is empty
    * 
    * Complexity = O(1):
    * simple access to node we have a pointer to
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
    * 
    * Complexity = O(1):
    * simple access to node we have a pointer to
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
   * Complexity = O(n): 
   * 1 call to nodeArray = O(n)
   * 1 iteration the n lengthed array of nodes O(n)
   * 
   */
  public int[] keysToArray()
  {
	  int[] keysArray = new int[this.size()];
	  WAVLNode[] nodeArray = nodeArray();
	  
	  for (int i=0; i<nodeArray.length; i++){
		  keysArray[i] = nodeArray[i].key;
	  }
        
        return keysArray;  
  
  }

  /**
   * public String[] infoToArray()
   *
   * Returns an array which contains all info in the tree,
   * sorted by their respective keys,
   * or an empty array if the tree is empty.
   * 
   * Complexity = O(n):
   * 1 call to nodeArray = O(n)
   * 1 iteration the n lengthed array of nodes O(n)
   * 
   */
  public String[] infoToArray()
  {
	  
	  String[] infoArray = new String[this.size()];
	  WAVLNode[] nodeArray = nodeArray();
	  
	  for (int i=0; i<nodeArray.length; i++){
		  infoArray[i] = nodeArray[i].info;
	  }
        
        return infoArray;                   
  }
  
  
  
  /*
   * returns an in-order (according to keys) array of WAVLNodes
   * 
   * Description:
   * does an in order recursive traversal of the tree while adding the nodes to 
   * an array of size n.
   * 
   * Complexity = O(n):
   * single recursive call per node and external leaf. O(1) operations per call. 
   */
  public WAVLNode[] nodeArray(){
	  final WAVLNode[] nodeArray = new WAVLNode[this.size()];
	  
	  WAVLNode node = this.root;
	  
	 class recursiveNodeToArrayClass {
		 
		 private int i = 0;
		 
		 private void recursiveNodeToArray(WAVLNode node){
			 
			 if (node == null){
				 return;
			 }
			 
			 recursiveNodeToArray(node.left);
			 nodeArray[this.i] = node;
			 this.i++;
			 recursiveNodeToArray(node.right);
			 
			 return;
		 }
		 
		 
	 }

	 recursiveNodeToArrayClass A = new recursiveNodeToArrayClass();
	 A.recursiveNodeToArray(this.root);
	  return nodeArray;
  }
  
  
  /*
   * returns successor of current node. 
   * if node is max the function returns node.
   * 
   * 
   * Complexity: O(log n)
   * case 1: node has a right child -->  right once and left all the way
   * worst case: O(log n), for successor of root.
   * case 2: node does not have a right child --> go up until you are a left child, return your parent
   * worst caseL O(log n), for successor of leaf which is predeccessor of root
   * 
   */
  
  public WAVLNode successorOf(WAVLNode node){
	  
	  if (node == this.maxNode){
		  return node;
	  }
	  
	  WAVLNode successor = node;
	  
	  // right once and left all the way
	  if (successor.right != null) {
		  successor = successor.right;
		  while (successor.left != null){
			  successor = successor.left;
		  }
		  return successor;
	  } 
	  else // no right child? go up until you are a left child, return your parent
	  { 
		  while (successor.parent != null){
			  if (successor == successor.parent.left){
				  return successor.parent;
			  } else {
				  successor = successor.parent;
			  }
		  }
	  }
	  
	  return successor;
  }

  
  /*
   * returns the predeccessor of the node received. 
   * if the node is the minimum node - function returns node.
   * 
   * Complexity: O(log n)
   * case 1: node has a left child -->  left once and right all the way
   * worst case: O(log n), for predeccessor of root.
   * case 2: node does not have a left child --> go up until you are a right child, return your parent
   * worst caseL O(log n), for predeccessor of leaf which is successor of root.
   * 
   */
  public WAVLNode predOf(WAVLNode node){
	  
	  if (node == this.minNode){
		  return node;
	  }
	  
	  WAVLNode pred = node;
	  
	  // left once and right all the way
	  if (pred.left != null) {
		  pred = pred.left;
		  while (pred.right != null){
			  pred = pred.right;
		  }
		  return pred;
	  } 
	  else // no left child? go up until you are a right child, return your parent
	  { 
		  while (pred.parent != null){
			  if (pred == pred.parent.right){
				  return pred.parent;
			  } else {
				  pred = pred.parent;
			  }
		  }
	  }
	  
	  return pred;
  }
 
  
  /*
   * updates this.minNode according to key of inserted node and key of minNode if exists.
   * 
   * Complexity = O(1):
   * constant number of operations.
   */
  private void updateMinInsert(WAVLNode insertedNode){
	  if (this.minNode == null){
		  this.minNode = insertedNode;
	  }
	  
	  else if(insertedNode.key < this.minNode.key){
		  this.minNode = insertedNode;
	  }
	  return;
  }
  
  /*
   * updates this.maxNode according to key of inserted node and key of maxNode if exists.
   * 
   * Complexity = O(1):
   * constant number of operations.
   */
  private void updateMaxInsert(WAVLNode insertedNode){
	  if (this.maxNode == null){
		  this.maxNode = insertedNode;
	  }
	  
	  else if(insertedNode.key > this.maxNode.key){
		  this.maxNode = insertedNode;
	  }
	  return;
  }

  /*
   * updates maxNode according to key of node inserted
   * 
   * Complexity = O(log n):
   * single call to successorOf = O(log n)
   * single call to isLeaf = O(1)
   */
  private void updateMinDelete(WAVLNode toBeDeletedNode){
	  if (this.minNode == null){ // trying to delete a node from an empty tree
		  return;
	  } else  if (toBeDeletedNode == this.minNode && this.root == toBeDeletedNode && toBeDeletedNode.isLeaf()){
		  minNode = null;
		  
	  } else if (toBeDeletedNode.key == this.minNode.key ){
		  this.minNode = successorOf(minNode);
	  }
	  
	  return;
  }
  
  
  /*
   * updates maxNode according to key of node inserted
   * 
   * Complexity = O(log n):
   * single call to predOf = O(log n)
   * single call to isLeaf = O(1)
   */
  private void updateMaxDelete(WAVLNode toBeDeletedNode){
	  if (this.maxNode == null){ // trying to delete a node from an empty tree
		  return;
	  } else if (toBeDeletedNode == this.minNode && this.root == toBeDeletedNode && toBeDeletedNode.isLeaf()){
		  minNode = null;
	  } else if (toBeDeletedNode.key == this.maxNode.key ){
		  this.maxNode = predOf(this.maxNode);
	  }
	  
	  return;
  }
  
   /**
    * public int size()
    *
    * Returns the number of nodes in the tree.
    *
    *Complexity = O(1)
    */
   public int size()
   {
	   return this.treeSize; 
   }
   
   
   /*
    * helper function for us
    * 
    * Complexity = O(1)
    */
   public WAVLNode getRoot(){ 
	   return this.root;
   }

   // TODO delete this
   public int getMinKey(){   
	   return this.minNode.key;
   }
   
   /*
    * helper function for us do not check
    */
   public static String toStringPreOrder(WAVLNode rootOfTree){
	   return toStringPreOrderNode(rootOfTree);
   }

   /*
    * helper function for us do not check
    */
   public static String toStringPreOrderNode(WAVLNode parent){
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
	  
	  public WAVLNode parent;
	  public WAVLNode left;
	  public WAVLNode right;

	  public WAVLNode(int key, String info) {
		  this.info = info;
		  this.key = key;  
	  }
	  
	  /*
	   * Complexity = O(1)
	   */
	  public String toString(){
		  return "(" + key + ":" + info + ")";
	  }
	  
	  /*
	   * recursively searches subtree starting from node. returns the node with the given key
	   * if it exists. If no such node exits return last node found. 
	   * (this is useful for inserts where this last node will be the parent of the to be inserted node)
	   * 
	   * Complexity = O(log n):
	   * As known, height of a WAVLTree is log n. nodeSearch initial call starts on a WAVLNode
	   * whose maximum height is log n.
	   * 
	   * first of all each call to the function makes O(1) actions and possibly a recursive call. we will analyze the
	   * number of these calls.
	   * each recursive call is made on a node of height smaller (by at least 1) than the previous node. 
	   * stop condition is reached when we call on an external leaf or found the key. 
	   * in total O(log n) calls. 
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
	   * 
	   * increase rank of node by 1
	   * 
	   * Complexity = O(1)
	   */
	  public void promote(){
		  this.rank += 1;
	  }
	  
	  /*
	   * demotes
	   * 
	   * decrease rank of node by 1
	   * 
	   * Complexity = O(1)
	   */
	  public void demote(){
		  this.rank -= 1;
	  }
	  
	  
	  /* 
	   * 
	   * Complexity = O(1):
	   * constant number of operations.
	   */
	  public boolean isLeftChild() {
		  if (this.parent == null){
			  return false;
		  }
		  
		   if (this == this.parent.left){
			   return true;
		   }
		   
		   return false;
	  }
	  
	  /*
	   * checks if both children are external leaves.
	   * 
	   * Complexity = O(1)
	   */
	  public boolean isLeaf() {
		   if (this.left==null && this.right==null){
			   return true;
		   }
		   
		   return false;
	  }
	  
	  /*
	   * checks if exactly on of its children is an external leave.
	   * 
	   * Complexity = O(1)
	   */
	  public boolean isUnary() {
		   if (this.left==null && this.right!=null){
			   return true;
		   }
		   if (this.left!=null && this.right==null){
			   return true;
		   }
		   
		   
		   return false;
	  }
	  
	  /*
	   * Complexity = O(1)
	   */
	  public boolean hasLeftChild() {
		   if (this.left != null){
			   return true;
		   }
		   
		   return false;
	  }
	  
	  /*
	   * nulls all pointers to parent, right, left.
	   * 
	   * Complexity O(1)
	   */
	  public void clear(){
		  this.parent = null;
		  this.right = null;
		  this.left = null;
	  }
  } // end of WAVLNode class

}
  

