package isnork.g9.comm;

import java.util.PriorityQueue;

public class SimpleTest {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		char a = 'a';
		/*
		System.out.println('b'-'a');
		System.out.println(1|(1<<4));
		System.out.println((char)('a'+((1<<3)+1)));
		
		System.out.println("See below:"+(char)('a' + (1000/500)));
		System.out.println(new String(new char[]{'j'}));
		
		System.out.println("Char as int: "+((int)'a'));
		*/
		
		PriorityQueue<Integer> q = new PriorityQueue<Integer>();
		q.add(-1);
		q.add(0);
		q.add(1);
		System.out.println(q.remove());

	}

}
