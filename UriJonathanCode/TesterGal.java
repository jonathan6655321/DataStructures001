package UriJonathanCode;


import java.util.HashSet;

import java.util.Random;

import java.util.TreeSet;



public class TesterGal {



	public static void main(String[] args) throws Exception {

		WAVLTree t = new WAVLTree();

		int[] insArr =new int[10];

		int[] delArr = new int[10];

		int[] maxIns = new int[10];

		int[] maxDel = new int[10];

		for (int i =1; i <= 10; i++)

		{

		HashSet<Integer> l = new HashSet<Integer>();

		for(int j = 1; j<10000*i;j++)

		{

		Random d = new Random();

		Integer p = d.nextInt();

		if(l.contains(p))

		{

		j--;

		continue;

		}

		l.add(p);

		int o = t.insert(p, p + "");

		//WAVLTree.printBinaryTree(t.getRoot(), 0);

		insArr[i-1]+= o;

		if(o > maxIns[i-1])

		maxIns[i-1]=o;

		}

		for (Integer integer : l)

		{

		int o = t.delete(integer);

		//WAVLTree.printBinaryTree(t.getRoot(), 0);

		//if(!isWAVL(t.getRoot()))

		//{

			//System.out.println("ERROR!!!");

		//}

		delArr[i-1]+=o;

		if(o > maxDel[i-1])

		maxDel[i-1]=o;

		}

		System.out.println("Test " + i + ":");

		System.out.println("Insert Maximum= " + maxIns[i-1]);

		System.out.println("Insert Average= " + insArr[i-1]/(i*10000.0));

		System.out.println("Delete Maximum= " + maxDel[i-1]);

		System.out.println("Delete Average= " + delArr[i-1]/(i*10000.0));

		}

		System.out.println("Mission Completed");

		}


}