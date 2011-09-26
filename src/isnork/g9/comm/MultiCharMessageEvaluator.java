package isnork.g9.comm;

import java.awt.geom.Point2D;
import java.util.Set;

import isnork.g9.utils.GameParams;
import isnork.sim.Observation;
import isnork.sim.SeaLifePrototype;

public class MultiCharMessageEvaluator {
	
	public static double evaluate(Point2D myPosition, ObservationMemory<Observation> memory, MultiCharMessage msg){
		
		Point2D senderLoc = msg.diverLocations.get(0);
		
		double adjustedTargetDistance = GameParams.getVisibilityRadius() * msg.distance / 4.0 ;
		
		double growthFactor = adjustedTargetDistance / Math.sqrt(Math.pow(msg.dir.dx, 2.0) + Math.pow(msg.dir.dy, 2.0));
		
		double dx = msg.dir.dx * growthFactor;
		double dy = msg.dir.dy * growthFactor;
		
		
		Point2D targetLoc = new Point2D.Double(senderLoc.getX() + dx, senderLoc.getY() + dy);
		
		double distance = targetLoc.distance(myPosition);
		
		//TODO this can be smarter
		double seenPenalty = 1;
		Set<Integer> specIdMem = memory.getCreatureMemory().get(msg.species);
		
		if (specIdMem!= null) {
			int totalPossible = 0;
			for (int id : specIdMem) {
				if (id % 4 == msg.id) {
					totalPossible++;
				}
			}
			
			if (specIdMem.size() > 0) {
				seenPenalty = (specIdMem.size() - totalPossible) * 1.0 / specIdMem.size();
			}
		}
		
		int specFreq = 0; 
		if (memory.getSpeciesMemory() != null && memory.getSpeciesMemory().get(msg.species) != null) specFreq = memory.getSpeciesMemory().get(msg.species).getFrequency();
		
		if (specFreq > 2) {
			return 0;
		}
		
		SeaLifePrototype species = GameParams.getSeaLife(msg.species);
		
		double rawValue = Math.pow(0.5, specFreq) * species.getHappiness() * seenPenalty;
		
		double rawValueAdjustedForDistance = Math.max(rawValue * (1 - distance * 0.05), 0);
		
		return rawValueAdjustedForDistance;
	}

}
