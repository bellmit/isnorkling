package isnork.g9.comm;

import isnork.g9.utils.GameParams;

import java.awt.geom.Point2D;

public class MessageEvaluatorImpl<T extends Message> implements MessageEvaluator<T>{

	@Override
	public double evaluate(Point2D myLocation, T msg) {
		return msg.getEstimatedValue() * normalizeBySenderDistance(myLocation, msg) * normalizeByHVT(msg);
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
		return coeff;
	}
	
	private double normalizeByHVT(T msg){
		if(msg instanceof SimpleMessage){
			if(((SimpleMessage)msg).isDynamic()){
				return msg.getEstimatedValue() / GameParams.getDynamicHVT();
			}else{
				return msg.getEstimatedValue() / GameParams.getStaticHVT();
			}
		}
		return msg.getEstimatedValue()/GameParams.getOverallHVT();
	}

}
