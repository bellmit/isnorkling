package isnork.g9.comm;

import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import isnork.sim.iSnorkMessage;

public class Aggregator {
	private Map<Integer, String> buffer = new HashMap<Integer, String>(); 
	private Map<Integer, List<Point2D>> diverLocations = new HashMap<Integer, List<Point2D>>();
	
	public Set<MultiCharMessage> processMessage(Set<iSnorkMessage> ms) {
		
		Set<MultiCharMessage> parsedMsgs = new HashSet<MultiCharMessage>();
		
		for (iSnorkMessage m : ms) {
			
			if (m.getMsg() == null) {
				continue;
			}
			
			int sender = m.getSender();
			if (!buffer.containsKey(sender)) {
				buffer.put(sender, m.getMsg());
				
				List<Point2D> pastLocs = new ArrayList<Point2D>();
				pastLocs.add(m.getLocation());
				diverLocations.put(sender, pastLocs);
			} else {
				
				buffer.put(sender, buffer.get(sender) + m.getMsg());
				diverLocations.get(sender).add(m.getLocation());
				
				if (buffer.get(sender).length()==3) {
					MultiCharMessage multiMsg = MultiCharEncoding.decode(buffer.get(sender));
					
					multiMsg.diverLocations = diverLocations.get(sender);
					parsedMsgs.add(multiMsg);
					
					buffer.put(sender, "");
					diverLocations.put(sender, new ArrayList<Point2D>());
				}
			}
		}
		
		return parsedMsgs;
	}
}
