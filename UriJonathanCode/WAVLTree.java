package UriJonathanCode;

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
	   if ((parentRank - nodeRank) == 1 ) {
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
		   } 
		   
		   // (2,1) below node - 2 rotations case:
		   else if (rankDifference(node, node.left) == 2 ) {
			   WAVLNode climberNode = node.right;
			   rotateLeft(node, climberNode);
			   rotateRight(climberNode, climberNode.parent); // climberNode.parent is original nodes parent
			   climberNode.left.demote();
			   climberNode.right.demote();
			   climberNode.promote();
		   }
	   } else {
		   // (2,1) below node - 1 rotation case:
		   if (rankDifference(node, node.right) == 1){
			   rotateLeft(parent, node);
			   parent.demote();
		   } 
		   // (1,2) below node - 2 rotations case: 
		   else if (rankDifference(node, node.right) == 2 ) {
			   WAVLNode climberNode = node.left;
			   rotateRight(climberNode, node);
			   rotateLeft(climberNode.parent, climberNode); // climberNode.parent is original nodes parent
			   climberNode.left.demote();
			   climberNode.right.demote();
			   climberNode.promote();
		   }
	   }
	   
	   return 1; 
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
   }
   
   
   /*
    * @pre: child isn't root
    * child is the left child of parent
    */
   
   public void rotateRight(WAVLNode child, WAVLNode parent) {
	   child.parent = parent.parent;
	   if (parent.isLeftChild()) {
		   parent.parent.left = child;
	   } else {
		   parent.parent.right = child;
	   }
	   
	   parent.left = child.right;
	   if (child.right != null) {
		   child.right.parent = parent;		   
	   }
	   
	   child.right = parent;
	   parent.parent =child;
	   
   }
   
   public void rotateLeft(WAVLNode parent, WAVLNode child){
	   child.parent = parent.parent;
	   if (parent.isLeftChild()) {
		   parent.parent.left = child;
	   } else {
		   parent.parent.right = child;
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
   */
   public int delete(int k)
   {
	   return 42;	// to be replaced by student code
   }

   /**
    * public String min()
    *
    * Returns the iמfo of the item with the smallest key in the tree,
    * or null if the tree is empty
    */
   public String min()
   {
	   return "42"; // to be replaced by student code
   }

   /**
    * public String max()
    *
    * Returns the info of the item with the largest key in the tree,
    * or null if the tree is empty
    */
   public String max()
   {
	   return "42"; // to be replaced by student code
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
	   return 42; // to be replaced by student code
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
	   * recursively searches subtree starting from node
	   * returns the node with the given key
	   * or parent of that node if no node with such key exists
	   */
	  public WAVLNode nodeSearch(int key){
		  
		  if (key == this.key) {
			  return this;
		  }
		  
		  if (key > this.key){
			  if (this.right == null){
				  return this;
			  } else {
				  return root.right.nodeSearch(key);				  
			  }
		  }
		  
		  if (key < this.key){
			  if (this.left == null){
				  return this;
			  } else {
				  return root.left.nodeSearch(key);				  
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
  
  } // end of WAVLNode class

}
  

