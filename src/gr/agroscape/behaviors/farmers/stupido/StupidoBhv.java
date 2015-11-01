package gr.agroscape.behaviors.farmers.stupido;

import gr.agroscape.agents.human.Farmer;
import gr.agroscape.behaviors.farmers.AFarmerBehavior;
import repast.simphony.engine.schedule.ScheduledMethod;

public class StupidoBhv extends AFarmerBehavior   {

	/**
	 * A reference to the container context
	 */
	protected StupidoBhvContext container;

	public StupidoBhv(Farmer owner, StupidoBhvContext c) {
		super(owner);
		this.container=c;
	}

	private int stupidoProperty;

	
	@ScheduledMethod (start=2,interval = 2)
	public void setRandom() {
		this.stupidoProperty =  this.container.getRandom();
	}
	
	@ScheduledMethod (start=2,interval = 1)
	public void print() {
		System.err.println("Farmer, id="+this.owner.getId() + ", stupido random=" + this.stupidoProperty);
	}

}