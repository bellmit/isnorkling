package isnork.g9.comm;

import isnork.g9.utils.GameParams;
import isnork.sim.GameObject;
import isnork.sim.Observation;
import isnork.sim.iSnorkMessage;

import java.awt.geom.Point2D;

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
	private static final int NEG_INFINITY = -9999;
	
	public SimpleEncoding(){
		int _scalingFactorD = GameParams.getDynamicHVT() / (1 << NUM_HAPPINESS_VALUE_BITS_D);
		int _scalingFactorS = GameParams.getStaticHVT() / (1 << NUM_HAPPINESS_VALUE_BITS_S);
		scalingFactorD = _scalingFactorD == 0 ? 1 : _scalingFactorD;
		scalingFactorS = _scalingFactorS == 0 ? 1 : _scalingFactorS;
	}
	
	@Override
	public String encode(Observation obs, Point2D myPosition){
		
		int msg = 0;
		String str = null;
				
		//TODO this shouldn't happen
		if (obs==null) {
			return "y";
		}
		
		//Static or Dynamic
		if(obs.getDirection() == null){
			
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
	public Message decode(iSnorkMessage iMsg) {
		
		String str = iMsg.getMsg();
		SimpleMessage sMsg = new SimpleMessage(iMsg);
		
		if(str==null){
			System.out.println("COMM ERROR: Received null incoming message");
			sMsg.setEstimatedValue(NEG_INFINITY);
			return sMsg;
		}
		
		
		int rawMsg = (int)str.charAt(0);
		
		boolean isDynamic = (rawMsg & (1<<4)) == 0;
		sMsg.setDynamic(isDynamic);
		
		if(isDynamic){
			sMsg.setEstimatedValue(rawMsg*scalingFactorD);
		}
		else{
			
			sMsg.setEstimatedValue((rawMsg & 1)*scalingFactorS);
		}
		
		return sMsg;
	}
}
