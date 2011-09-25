package isnork.g9.comm;

import isnork.g9.utils.DirectionUtil;
import isnork.g9.utils.GameParams;
import isnork.g9.utils.Parameter;
import isnork.sim.GameObject.Direction;
import isnork.sim.Observation;
import isnork.sim.iSnorkMessage;

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
		
		int id = obs.getId() % (1 << bitsForId);
		int species = getSpeciesId(obs.getName());
		int direction = Parameter.DIRECTION_LOOKUP(obs.getDirection());
		
		int shift = bitsForSpecies;
		int msg = species << (totalBits - shift);
		shift += bitsForId;
		msg = msg | id << (totalBits - shift);
		shift += bitsForDir; 
		msg = msg | direction << (totalBits - shift); 
		
		int dist = (int)(obsLoc.distance(myPosition) * (1 << bitsForDistance) / GameParams.getVisibilityRadius());
		if (dist == 0) { dist = 1; }
		
		shift += bitsForDistance;
		
		msg |= dist << (totalBits-shift);
		msg |= DirectionUtil.getRelativeOctant(myPosition, obsLoc);
		
		int mschar = (msg / (int)Math.pow(26, 2));
		int midchar = (msg / 26) %26;
		int lschar = msg % 26;
		return new String(new char[]{(char)('a'+mschar)})+
				new String(new char[]{(char)('a'+midchar)}) +
				new String(new char[]{(char)('a'+lschar)});

		
	}
	
	private static int getSpeciesId(String name) {
		return 0;
	}
	
	private static String getSpeciesName(int id) {
		return "";
	}

	public static MultiCharMessage decode(String msg) {
		
		char[] chars = msg.toCharArray();
		int mschar = chars[0] - 'a';
		int midchar = chars[1] - 'a';
		int lschar = chars[2] - 'a';
		
		int m = (int) (mschar*Math.pow(26, 2)) + midchar*26 + lschar;
		
		int species = m & speciesMask;
		int id = m & idMask;
		int dir = m & dirMask;
		int dist = m & distMask;
		int octant = m & octantMask;
		
		MultiCharMessage message = new MultiCharMessage();
		message.id = id;
		message.dir = Parameter.ALL_DIRS[dir];
		message.distance = dist;
		message.octant = octant;
		message.species = getSpeciesName(species);
		//TODO: Someone needs to set senderLoc on this shit
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
