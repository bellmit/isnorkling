package isnork.g9.comm;

import isnork.g9.utils.GameParams;

import java.awt.geom.Point2D;

public class MessageEvaluatorImpl<T extends Message> implements MessageEvaluator<T>{

	@Override
	public double evaluate(Point2D myLocation, T msg) {
		return normalizeBySenderDistance(myLocation, msg) * normalizeByHVT(msg);
	}
	
	private double normalizeBySenderDistance(Point2D myLocation, T msg){
		
		double senderDistance = myLocation.distance(msg.getSenderLocation());
		double coeff;
		if(senderDistance == 0) return 0;
		int r = GameParams.getVisibilityRadius();
		if(senderDistance < 2 * r){
			double theta = Math.acos(senderDistance / (2*r));
			coeff =  1 - (theta - Math.sin(theta))/Math.PI;
			//Should the value fall like coeff or coeff^2
		}else{
			coeff = r / senderDistance;
		}
		//System.out.println("Distance coeff: "+coeff);
		return coeff;
	}
	
	private double normalizeByHVT(T msg){
		double coeff;
		if(msg instanceof SimpleMessage){
			if(((SimpleMessage)msg).isDynamic()){
				coeff = msg.getEstimatedValue() / GameParams.getDynamicHVT();
			}else{
				coeff = msg.getEstimatedValue() / GameParams.getStaticHVT();
			}
		}else{
			coeff = msg.getEstimatedValue()/GameParams.getOverallHVT();
		}
		//System.out.println("HVT coeff: "+coeff);
		return coeff;
	}

}
