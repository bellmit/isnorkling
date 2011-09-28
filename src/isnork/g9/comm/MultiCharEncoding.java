package isnork.g9.comm;

import isnork.g9.utils.DirectionUtil;
import isnork.g9.utils.GameParams;
import isnork.g9.utils.Parameter;
import isnork.sim.Observation;
import isnork.sim.SeaLifePrototype;

import java.awt.geom.Point2D;

public class MultiCharEncoding {
	
	private static int bitsForSpecies = 4;
	private static int bitsForId = 2;
	private static int bitsForDir = 3;
	private static int bitsForDistance = 2;
	private static int bitsForOctant = 3;
	
	private static int speciesMask = 1;
	private static int idMask = 1;
	private static int dirMask = 1;
	private static int distMask = 1;
	private static int octantMask = 1;
	
	private static int totalBits = 14;
	
	public static String encode(Observation obs, Point2D myPosition) {
		
		Point2D obsLoc = obs.getLocation();
		
		System.out.println(obs);
		
		int species = getSpeciesId(obs.getName());
		int id = obs.getId() % (1 << bitsForId);
		int direction = Parameter.DIRECTION_LOOKUP(obs.getDirection());
		int distance= (int)(obsLoc.distance(myPosition) * (1 << bitsForDistance) / GameParams.getVisibilityRadius());
		if (distance == 0) { distance = 1; }
		int octant = DirectionUtil.getRelativeOctant(myPosition, obsLoc);
		
		System.out.println("species: " + species);
		System.out.println("id: " + id);
		System.out.println("direction: " + direction);
		System.out.println("distance: " + distance);
		System.out.println("octant: " + octant);
		
		String speciesBin = pad(Integer.toBinaryString(species), bitsForSpecies);
		String idBin = pad(Integer.toBinaryString(id), bitsForId);
		String directionBin = pad(Integer.toBinaryString(direction), bitsForDir);
		String distanceBin = pad(Integer.toBinaryString(distance), bitsForDistance);
		String octantBin = pad(Integer.toBinaryString(octant), bitsForOctant);
		
		String finalBin = speciesBin + idBin + directionBin + distanceBin + octantBin;
		
		System.out.println("Binary representation: " + finalBin);
		
		int msg = toDec(finalBin);
		
		int mschar = (msg / (int)Math.pow(26, 2));
		int midchar = (msg / 26) %26;
		int lschar = msg % 26;
		
		System.out.println("  msg:" + msg);
		System.out.println("  mschar:" + mschar);
		System.out.println("  midchar:" + midchar);
		System.out.println("  lschar:" + lschar);
		
		return new String(new char[]{(char)('a'+mschar)})+
				new String(new char[]{(char)('a'+midchar)}) +
				new String(new char[]{(char)('a'+lschar)});

		
	}
	
	private static int getSpeciesId(String name) {
		int i = 0;
		for (SeaLifePrototype slp : GameParams.getSeaLifePossibilites()) {
			if (slp.getName().equals(name)) {
				return i;
			}
			i++;
		}
		return 0;
	}
	
	private static String getSpeciesName(int id) {
		int i = 0;
		for (SeaLifePrototype slp : GameParams.getSeaLifePossibilites()) {
			if (i==id) {
				return slp.getName();
			}
			i++;
		}
		
		return "";
	}
	
	private static int toDec(String bin) {
		return Integer.parseInt(bin, 2);
	}
	
	private static String pad(String bin, int len) {
		String r = bin;
		while (r.length() < len) {
			r = "0" + r;
		}
		
		return r;
	}

	public static MultiCharMessage decode(String msg) {
		
		char[] chars = msg.toCharArray();
		int mschar = chars[0] - 'a';
		int midchar = chars[1] - 'a';
		int lschar = chars[2] - 'a';
		
		int m = (int) (mschar*Math.pow(26, 2)) + midchar*26 + lschar;
		
		int species = (m & speciesMask) >> (bitsForId + bitsForDir + bitsForDistance + bitsForOctant);
		
		int id = (m & idMask) >> (bitsForDir + bitsForDistance + bitsForOctant);
		int dir = (m & dirMask) >> (bitsForDistance + bitsForOctant);
		int dist = (m & distMask) >> (bitsForOctant);
		int octant = (m & octantMask);
		
		MultiCharMessage message = new MultiCharMessage();
		message.id = id;
		message.dir = Parameter.ALL_DIRS[dir];
		message.distance = dist;
		message.octant = octant;
		message.species = getSpeciesName(species);
		return message;
	}
	
	static{
		

		for(int i=0; i < bitsForSpecies; i++){
			speciesMask <<= 1;
		}
		speciesMask <<= (totalBits - bitsForSpecies);
		
		

		for(int i=0; i < bitsForId; i++){
			idMask <<= 1;
		}
		idMask <<= (totalBits - bitsForSpecies - bitsForId);
		
		

		for(int i=0; i < bitsForDir; i++){
			dirMask <<= 1;
		}
		dirMask <<= (totalBits - bitsForSpecies - bitsForId - bitsForDir);
		
		
		for(int i=0; i < bitsForDistance; i++){
			distMask <<= 1;
		}
		distMask <<= (totalBits - bitsForSpecies - bitsForId - bitsForDir - bitsForDistance);
		
		

		for(int i=0; i < bitsForOctant; i++){
			octantMask <<= 1;
		}		
	}

}
