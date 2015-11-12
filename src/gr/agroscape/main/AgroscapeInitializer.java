package gr.agroscape.main;

import gr.agroscape.behaviors.AgentBehavior;
import gr.agroscape.behaviors.BehaviorAction;
import gr.agroscape.behaviors.DefaultBehaviorsLoader;
import gr.agroscape.dataLoaders.AgroscapeAllBehaviorsDataLoader;
import gr.agroscape.dataLoaders.AgroscapeSkeletonDataLoader;
import gr.agroscape.skeleton.agents.AgroscapeAgent;
import gr.agroscape.skeleton.contexts.FarmersContext;
import gr.agroscape.skeleton.contexts.PlotsContext;
import gr.agroscape.skeleton.contexts.SimulationContext;

import java.util.ArrayList;

import org.apache.log4j.Level;

import repast.simphony.context.Context;
import repast.simphony.dataLoader.ContextBuilder;
import repast.simphony.engine.environment.RunEnvironment;
import repast.simphony.engine.schedule.ISchedule;
import repast.simphony.parameter.Schema;


/**
 * This is the "main" class. The purpose is to:
 * <ul>
 * <li>create the empty {@link SimulationContext}</li>
 * <li>create the empty {@link FarmersContext}</li> 
 * <li>create the empty {@link PlotsContext}</li> 
 * </ul> 
 *
 * @author Dimitris Kremmydas
 * @version $Revision$
 * @since 2.0
 *
 */
public class AgroscapeInitializer implements ContextBuilder<Object> {
	
	
	private SimulationContext simulationContext;
	
	private AgroscapeSkeletonDataLoader skeletonDataLoader;
	
	private AgroscapeAllBehaviorsDataLoader behaviorsDataLoader;

	//static Logger logger = Logger.getLogger(AgroscapeInitializer.class);
	
	/**
	 * Load values from the parameters.xml
	 */
	public AgroscapeInitializer() {
		this.simulationContext = SimulationContext.getInstance();
	}
	

	public AgroscapeInitializer(AgroscapeSkeletonDataLoader skeletonDataLoader,
			AgroscapeAllBehaviorsDataLoader behaviorsDataLoader) {
		this();
		this.skeletonDataLoader = skeletonDataLoader;
		this.behaviorsDataLoader = behaviorsDataLoader;
	}

	


	/**
	 * It builds the Contexts of Agroscape. <br />
	 * The steps that this method does, are: <br />
	 * 1. Create the MainContext as the parentContext <br />
	 * 2. Create empty SubContexts and load them to parentContext <br />
	 * 3. Create {@link AgroscapeSkeletonDataLoader dataLoader} and load data
	 * 
	 */

	@Override
	public Context<Object> build(Context<Object> context)  {
		
		//create Grid on SimulationContext
		this.simulationContext.initializeGrid();
		
		
		//load from xml dataloadrO
		if(this.skeletonDataLoader == null) this.loadFromParameters();
		
	
		//step 2, create empty  subContexts
		PlotsContext plots = new PlotsContext(); //create plots' context
		this.skeletonDataLoader.loadPlotsContext(plots);
		this.simulationContext.addSubContext(plots);
		
		FarmersContext farmers = new FarmersContext(); //create farmers' context
		this.skeletonDataLoader.loadFarmersContext(farmers);
		this.simulationContext.addSubContext(farmers);
		
		this.skeletonDataLoader.initLandPropertyRegistry(this.simulationContext.getLandPropertyRegistry());
		
		//load all behaviors
		this.behaviorsDataLoader.loadAllBehaviors(this.simulationContext);
		this.addAgroscapeAgentsBehaviorToSchedule(farmers.getAllFarmers());
	
		
		return this.simulationContext;
	}

	
	/**
	 * Add Behaviors to Schedule
	 * @param agents
	 */
	private void addAgroscapeAgentsBehaviorToSchedule(Iterable<? extends AgroscapeAgent> agents) {

		ISchedule timeline = RunEnvironment.getInstance().getCurrentSchedule();
		SimulationContext.logMessage(this.getClass(), Level.INFO, "Initializing simulation: Loading Behaviors");
		
		for (AgroscapeAgent ag : agents) {
			//AgroscapeInitializer.logger.info("Loading Agent");
			SimulationContext.logMessage(this.getClass(), Level.DEBUG, "Loading Agent: " + ag.toString());
			
			ArrayList<AgentBehavior> bhvs = (ArrayList<AgentBehavior>) ag.getBehaviors();
			for (AgentBehavior ab : bhvs) {
				SimulationContext.logMessage(this.getClass(), Level.DEBUG, "Loading Agent's Behavior: " + ab.toString());
				
				ArrayList<BehaviorAction> aSchA = (ArrayList<BehaviorAction>) ab.getScheduledActions();
				for (BehaviorAction beha : aSchA) {
					SimulationContext.logMessage(this.getClass(), Level.DEBUG, "Loading Agent's Behavior's Action: " + beha.toString());
					timeline.schedule(beha.getParams(), beha.getObject(), beha.getMethod());
				}
			}
		}
	}
	
	/**
	 * Load skeletonLoader and behaviorsDataLoader from parameters.xml
	 */
	private void loadFromParameters() {
		System.out.println(RunEnvironment.getInstance());
		String loaderName = RunEnvironment.getInstance().getParameters().getString("skeletonDataLoaderClass");
		try {
			this.skeletonDataLoader = (AgroscapeSkeletonDataLoader) Class.forName(loaderName).newInstance();

		} catch (InstantiationException | IllegalAccessException
				| ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new NullPointerException();
		}

		Schema bhvsSchema= RunEnvironment.getInstance().getParameters().getSchema();
		this.behaviorsDataLoader = new DefaultBehaviorsLoader(bhvsSchema);
	}


} //end class
