package gr.agroscape.behaviors.farmers.production.arableCropProduction;

import gr.agroscape.agents.Farmer;
import gr.agroscape.agents.Plot;
import gr.agroscape.behaviors.farmers.production.agriculturalActivities.ArableCropCultivation;
import gr.agroscape.behaviors.farmers.production.productionDecisions.AProductionDecision;
import gr.agroscape.behaviors.farmers.production.productionDecisions.ArableCropProductionDecision;
import gr.agroscape.contexts.FarmersContext;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;

import repast.simphony.engine.schedule.ScheduledMethod;
import repast.simphony.space.graph.Network;

/**
 * The farmer decides an arable crop according to what his connection to the "productionNetwork"@FarmersContext has decided.<br />
 * He plants for all his plots, the most "popular" plot that his connection has planted.
 * 
 * @author Dimitris Kremmydas 
 */
public class ArableCropProductionBhv_Network extends AArableCropProductionBhv {
	
	/**
	 * The network context
	 */
	private Network<Farmer> network;

	public ArableCropProductionBhv_Network(ArrayList<ArableCropCultivation> pC, long liquidity, Farmer f,ArableCropProductionBhvContext container) {
		super(pC, liquidity, f, container);
		this.network = FarmersContext.getInstance().getNetwork("productionNetwork");
		if(this.network == null) {throw new NullPointerException("'productionNetwork' not found in FarmersContext");}
	}

	/**
	 * His production decision is an imitation of the first connection of his network. <Br />
	 * @param plots
	 * @return
	 */
	@Override
	public Collection<? extends AProductionDecision> makeProductionDecision(Collection<Plot> plots) {
		
		ArrayList<ArableCropProductionDecision> r=new ArrayList<ArableCropProductionDecision>(); 
		
		//find the farmer's connection
		Iterable<Farmer> connections =  network.getAdjacent(this.owner);
		
		//take his first connection
		AArableCropProductionBhv connection_bhv = (AArableCropProductionBhv) this.container.findByFarmerOwner(connections.iterator().next());
		
		//find the most popular one
		ArableCropCultivation decision = findMostPopular(connection_bhv.lastProductionDecisions);
		
		for (Plot p : plots) {
			r.add(new ArableCropProductionDecision(p, decision));
		}
		return r;
	}
	
	//find the most popular ArableCropCultivation, weighted by plot area
	private ArableCropCultivation findMostPopular(ArrayList<ArableCropProductionDecision> pd) {
		ArableCropCultivation r = null;
		HashMap<ArableCropCultivation,Double> popularity = new HashMap<>();
		
		//construct popularity and find maximum (together)		
		for (ArableCropProductionDecision dec : pd) {
			if(popularity.containsKey(dec.getDecision())) 
				popularity.put(dec.getDecision(), popularity.get(dec.getDecision())+dec.getPlot().getArea());
			else
				popularity.put(dec.getDecision(), dec.getPlot().getArea());
			
			if(r==null){r=dec.getDecision();}
			if(popularity.get(r).compareTo(popularity.get(dec.getDecision()))==1) {
				r = dec.getDecision();
			}
		}
		
		return r;
	}


	/*
	 * do production
	 */
	@ScheduledMethod (start=1,interval = 1, priority=20)
	public void handleProduction() {
		
		@SuppressWarnings("unchecked")
		ArrayList<ArableCropProductionDecision> pd =  (ArrayList<ArableCropProductionDecision>)this.makeProductionDecision(this.getCultivatingPlots());
		this.lastProductionDecisions =pd;
		this.container.updateProductionDecisionsValueLayer(pd);
		
		System.err.println("Farmer(Net) " + this.owner.toString() + " Decisions:");
		System.err.println(pd.toString());
	}
	

	
	

}
