package isnork.g9.comm;

import isnork.sim.GameObject;
import isnork.sim.Observation;
import isnork.sim.SeaLifePrototype;

import java.awt.geom.Point2D;
import java.util.Iterator;
import java.util.Set;

/**
 *  
 * @author neil
 * 
 * A simple bit encoding scheme
 * 
 * Letters a-p are used for dynamic creatures
 * i.e. MSB is zero
 * 
 * Next q-x are used for static creatures
 * i.e. MSB is one
 * 
 * For Dynamic creatures, the remaining four bits give an estimate of the value
 * 
 *  For Static creatures, the second and third high order bits give the quadrant
 *  in which the creature is located relative to the diver
 *
 */

public class SimpleEncoding implements Encoding {
	
	private final int scalingFactorD; 
	private final int scalingFactorS; 
	private static final int NUM_HAPPINESS_VALUE_BITS_D = 4;
	private static final int NUM_HAPPINESS_VALUE_BITS_S = 1;
	
	private final int maxVal; 
	
	
	public int getMaxVal(){return maxVal;}
	
	
	public SimpleEncoding(Set<SeaLifePrototype> seaLifePossibilites, int penalty,
			int d, int r, int n){
		
		int maxd = -1;
		int maxs = -1;
		Iterator<SeaLifePrototype> iter = seaLifePossibilites.iterator();
		while(iter.hasNext()){
			SeaLifePrototype seaLifePrototype = iter.next();
			if(seaLifePrototype.getSpeed()==0 && seaLifePrototype.getHappiness() > maxs)
				maxs = seaLifePrototype.getHappiness();
			if(seaLifePrototype.getHappiness() > maxd)
				maxd = seaLifePrototype.getHappiness();

		}
		
		int _scalingFactorD = maxd / (1 << NUM_HAPPINESS_VALUE_BITS_D);
		int _scalingFactorS = maxs / (1 << NUM_HAPPINESS_VALUE_BITS_S);
		scalingFactorD = _scalingFactorD == 0 ? 1 : _scalingFactorD;
		scalingFactorS = _scalingFactorS == 0 ? 1 : _scalingFactorS;
		
		maxVal = maxd > maxs ? maxd : maxs;
		
		
	}
	
	@Override
	public String encode(Observation obs, Point2D myPosition){
		
		int msg = 0;
		String str = null;
				
		//Static or Dynamic
		if(obs.getDirection() == GameObject.Direction.STAYPUT){
			
			//Static sea creature
			msg = msg | (1 << 4);
			
			//Set Quadrant
			//		
			//		01	00
			//		11	10
			//
			
			if(obs.getLocation().getX() < myPosition.getX()){
				msg = msg | (1 << 1);
			}
			
			if(obs.getLocation().getY() < myPosition.getY()){
				msg = msg | (1 << 2);
			}
			
			//Set value
			msg = msg | (obs.happiness() / scalingFactorS);
			
			return new String(new char[]{(char)('a'+msg)});
			
		}else{
			
			//Dynamic sea creature
			
			
			
			//code the value
			return new String(new char[]{(char)('a' + (obs.happiness()/scalingFactorD))});
		}
		
	}

	@Override
	public Message decode(String str) {
		
		SimpleMessage msg = new SimpleMessage();
		int rawMsg = (int)str.charAt(0);
		msg.setRawMsg(str);
		
		boolean isDynamic = (rawMsg & (1<<4)) == 0;
		msg.setDynamic(isDynamic);
		
		if(isDynamic){
			msg.setEstValue(rawMsg*scalingFactorD);
		}
		else{
			msg.setEstValue((rawMsg & 1)*scalingFactorS);
		}
		
		return msg;
	}
}
