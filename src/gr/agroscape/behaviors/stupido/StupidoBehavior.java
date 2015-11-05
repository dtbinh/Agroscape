package gr.agroscape.behaviors.stupido;

import gr.agroscape.behaviors.AgentBehavior;
import gr.agroscape.skeleton.agents.AgroscapeAgent;
import gr.agroscape.skeleton.agents.plot.Plot;

import java.util.ArrayList;
import java.util.List;

import repast.simphony.engine.schedule.DefaultAction;
import repast.simphony.engine.schedule.IAction;
import repast.simphony.engine.schedule.ScheduleParameters;
import repast.simphony.engine.schedule.ScheduledMethod;

public class StupidoBehavior extends AgentBehavior {
	
	

	public StupidoBehavior(AgroscapeAgent owner, StupidoBehaviorContext bhvContext, StupidoBehaviorFactory bhvFactory) {
		super("Stupido", bhvFactory, owner, bhvContext);
	}

	@Override
	public List<DefaultAction> getScheduledActions() {
		List<DefaultAction> actions= new ArrayList<DefaultAction>();
		
		ScheduleParameters p= ScheduleParameters.createRepeating(1, 360);
		IAction action =  this.getBhvFactory().getActionFactory().createAction(this, "printHappiness");
		
		actions.add(new DefaultAction(p, action,1));		
			
		return actions;
	}
	
	@ScheduledMethod (start=1,interval=2)
	public void printHappiness() {
		
		int newNumber = (int) Math.round(Math.random()*100);
		StupidoBehaviorContext sbhvc= (StupidoBehaviorContext)this.getBehaviorContext();
		
		Plot rPlot = this.getOwner().getMainContext().getPlotsContext().getRandomObject();
		StupidoPlotIntegerProperty prop = (StupidoPlotIntegerProperty) rPlot.getBehaviorProperty(this.bhvFactory, "StupidoInteger");
		prop.setValue(new Integer(newNumber));
		
		System.out.println("I am a happy behavior of agent: " + this.getOwner() 
				+ ". The common property I see is: " + sbhvc.getCommonProperty()
				+ " and I set it to be " + newNumber + "\n"
				+ "I also picked a random Plot: " + rPlot.getId() + "and assign to it"
				+ " the property. So the plot now is: " + rPlot.toString()
			);
		
		sbhvc.setCommonProperty(newNumber);
		
	}

}
