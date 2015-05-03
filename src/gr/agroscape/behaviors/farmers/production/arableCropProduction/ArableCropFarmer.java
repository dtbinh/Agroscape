package gr.agroscape.behaviors.farmers.production.arableCropProduction;

import gr.agroscape.agents.Farmer;
import gr.agroscape.agents.Plot;
import gr.agroscape.behaviors.farmers.ABehavingFarmer;
import gr.agroscape.behaviors.farmers.production.agriculturalActivities.ArableCropCultivation;
import gr.agroscape.behaviors.farmers.production.interfaces.ArableCropProductionDecision;
import gr.agroscape.behaviors.farmers.production.interfaces.IHasProductionAbility;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

import repast.simphony.engine.schedule.ScheduledMethod;


/**
 * The behavior of a {@link Farmer} that produces arable crops <p />
 * <p>This is the abstract class that gives some basic functionality:
 *  <ul>
 *  <li>Every farmer has some common properties:
 *  <ul>
 *  
 *  <li>{@link #liquidity Liquidity} (synonym for working capital)</li>
 *  <li>{@link #potentialCrops Potential Crops}, since some farmers might not even consider 
 *  cultivating some of the crops considered in the simulation</li>
 *  <li>thisStepProduction 
 *  //TODO decide if this class is a good place  for the property to be
 *  </li>
 *  </ul>
 *  </li>
 *  
 *  <li>There are also here some helper functions:</li>
 *  <ul>
 *  <li>Get a List of Cultivating (owned+rented) Plots ({@link #getCultivatingPlots()})</li>
 *  <li>Calculate the Cultivating Plot Area ({@link #getCultivatingPlotArea()})</li>
 *  <li>Add a production to the current thisStepProduction ({@link #aggregateProduction(HashMap)})</li>
 *  </ul>
 *  
 *  </ul>
 *  </p>
 *  <p></p>
 * @author Dimitris Kremmydas
  */
public abstract class ArableCropFarmer extends ABehavingFarmer<Farmer> implements IHasProductionAbility {
	
	
    /**
     * The liquidity at the current moment (�cents)
     */
    protected long liquidity;
    
    /**
     * Potential Crops that he can cultivate
     */
    protected ArrayList<ArableCropCultivation> potentialAgriculturalActivity = new ArrayList<ArableCropCultivation>();

    
    
	/**
	 * Constructor
	 * @param id
	 * @param grid
	 */	
	public ArableCropFarmer(ArrayList<ArableCropCultivation> pC, long liquidity , Farmer f) {
		super(f);
		this.potentialAgriculturalActivity=pC;
		this.liquidity = liquidity;
	}
 
	
	/**
	 * 
	 * @return float (hectares)
	 */
	public  float getTotalArableLand() {
		   float r=0;
	       ArrayList<Plot> myCultivationPlots = this.owner.getMainContext().getLandPropertyRegistry().getRentedPlots(this.owner);
	       for(Plot v: myCultivationPlots) {r+=v.getArea();}
	       return r;
	}

	
	@Override
	public Object getAnnotatedClass() {
		return this.getClass();
	}
	
	@ScheduledMethod(interval = 1)
	public void handleProduction() {
		
		@SuppressWarnings("unchecked")
		ArrayList<ArableCropProductionDecision> pd =  (ArrayList<ArableCropProductionDecision>)this.makeProductionDecision(this.getCultivatingPlots());
		
		
	}
	
	/**
	 * 
	 * @return
	 */
	public ArrayList<ArableCropCultivation> getPotentialCrops() {
		return potentialAgriculturalActivity;
	}

	/**
	 * 
	 * @param potentialCrops
	 */
	public void setPotentialCrops(ArrayList<ArableCropCultivation> potentialCrops) {
		this.potentialAgriculturalActivity = potentialCrops;
	}
	
	/**
	 * 
	 * @return
	 */
	public ArrayList<Plot> getCultivatingPlots() {
		return this.owner.getMainContext().getLandPropertyRegistry().getCultivatingPlots(this.owner);
	}
	
	/**
	 * 
	 * @return
	 */
	public double getCultivatingPlotArea() {
		double r= 0d;
		ArrayList<Plot> ps = this.getCultivatingPlots();
		for (Iterator<Plot> iterator = ps.iterator(); iterator.hasNext();) {
			Plot plot = iterator.next();
			r += plot.getArea();
		}
		return r;
	}
	

	abstract void calculateExpectations();	
	


}


