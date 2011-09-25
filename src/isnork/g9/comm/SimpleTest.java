package isnork.g9.comm;

import isnork.g9.utils.DirectionUtil;
import isnork.g9.utils.GameParams;
import isnork.g9.utils.Parameter;
import isnork.sim.Observation;

import java.awt.geom.Point2D;
import java.security.spec.EncodedKeySpec;
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
		
		
		PriorityQueue<Integer> q = new PriorityQueue<Integer>();
		q.add(-1);
		q.add(0);
		q.add(1);
		System.out.println(q.remove());
		*/
		String str = encode();
		char[] chars = str.toCharArray();
		for(int i=0; i<chars.length; i++ ){
			System.out.println(chars[i]);
		}

		

	}
	
	static int bitsForSpecies = 4;
	static int bitsForId = 2;
	static int bitsForDistance = 2;
	static int BITS_FOR_DIR = 3;
	static int bitsForOctant = 3;
	
	static int totalBits = 14;
	
	
	public static String encode() {
		
		//Point2D obsLoc = obs.getLocation();
		
		int id = 2; //obs.getId();
		int species = 15; //getSpeciesId(obs.getName());
		int direction = 6;//Parameter.DIRECTION_LOOKUP(obs.getDirection());
		
		int shift = bitsForSpecies;
		int msg = species << (totalBits - shift);
		shift += bitsForId;
		msg = msg | id << (totalBits - shift);
		shift += BITS_FOR_DIR; 
		msg = msg | direction << (totalBits - shift); 
		
		int dist = 2;//(int)(obsLoc.distance(myPosition) * (1 << bitsForDistance) / GameParams.getVisibilityRadius());
		if (dist == 0) { dist = 1; }
		
		shift += bitsForDistance;
		
		msg |= dist << (totalBits-shift);
		msg |= 5;//DirectionUtil.getRelativeOctant(myPosition, obsLoc);
		
		int mschar = (msg / (int)Math.pow(26, 2));
		int midchar = (msg / 26) %26;
		int lschar = msg % 26;
		String str = new String(new char[]{(char)('a'+mschar)})+
				new String(new char[]{(char)('a'+midchar)}) +
				new String(new char[]{(char)('a'+lschar)});
		
		
		return str;
		
	}
	

}
