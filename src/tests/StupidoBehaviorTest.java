package tests;

import static org.junit.Assert.assertTrue;
import gr.agroscape.agents.human.Farmer;
import gr.agroscape.agents.plot.Plot;
import gr.agroscape.agents.plot.PlotUtils;
import gr.agroscape.behaviors.stupido.StupidoBehaviorFactory;
import gr.agroscape.contexts.SimulationContext;
import gr.agroscape.main.ContextManager;

import org.junit.Before;
import org.junit.Test;

import repast.simphony.context.DefaultContext;
import repast.simphony.engine.environment.DefaultScheduleRunner;
import repast.simphony.engine.environment.RunEnvironment;
import repast.simphony.engine.schedule.Schedule;
import repast.simphony.parameter.DefaultParameters;

public class StupidoBehaviorTest {
	
	private ContextManager builder;
	private SimulationContext simulationContext;
	

	@Before
	public void setUp() throws Exception {
		
		DefaultParameters p = new DefaultParameters();
		p.addParameter("gridWidth", "gridWidth", Integer.class, 30, false);
		p.addParameter("gridHeight", "gridHeight", Integer.class, 30, false);
		
		RunEnvironment.init(new Schedule(), new DefaultScheduleRunner(), p, false);

		builder = new ContextManager ();
		this.simulationContext = (SimulationContext) builder.build(new DefaultContext<Object> ());
		
	}

	@Test
	public void simulationContextCreation() {
		assertTrue("Simulation Context Created !", true);
	}
	
	@Test
	public void newRectanglePlotTest() {
		Plot p = PlotUtils.newRectanglePlot(1, 1, 3, 3);
		System.out.println(p.toString());
	}
	
	@Test
	public void addSkeletonAgents() {
			
		this.simulationContext.getFarmersContext().add( new Farmer());
		this.simulationContext.getFarmersContext().add( new Farmer());
		this.simulationContext.getFarmersContext().add( new Farmer());
		
		this.simulationContext.getPlotsContext().add(
				PlotUtils.newRectanglePlot(1, 1, 3, 3)
		);
		this.simulationContext.getPlotsContext().add(
				PlotUtils.newRectanglePlot(1, 4, 2, 6)
		);
		
		System.out.println(this.simulationContext.getFarmersContext().toString());
		System.out.println(this.simulationContext.getPlotsContext().toString());
		
		
		assertTrue("Agents added !", true);
	}
	
	@Test
	public void addStupidoBehavior() {
			
		this.simulationContext.getFarmersContext().add( new Farmer());
		this.simulationContext.getFarmersContext().add( new Farmer());
		this.simulationContext.getFarmersContext().add( new Farmer());
		
		this.simulationContext.getPlotsContext().add(
				PlotUtils.newRectanglePlot(1, 1, 3, 3)
		);
		this.simulationContext.getPlotsContext().add(
				PlotUtils.newRectanglePlot(1, 4, 2, 6)
		);
		
		System.out.println(this.simulationContext.getFarmersContext().toString());
		System.out.println(this.simulationContext.getPlotsContext().toString());
		
		
		StupidoBehaviorFactory sbf = new StupidoBehaviorFactory();		
		sbf.assignBehavior(this.simulationContext.getFarmersContext().getAgentLayer(Farmer.class));
		
		System.out.println(this.simulationContext.getFarmersContext().getAllFarmers());
		
		
		
		assertTrue("Agents added !", true);
	}
	

}
