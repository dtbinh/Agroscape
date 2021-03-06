package gr.agroscape.skeleton.projections;

import repast.simphony.space.projection.Projection;


/**
 * Represents the Simulation Space. 
 * 
 * @author Dimitris Kremmydas
 * @version $Revision$ $Date$
 *
 */
public class SimulationSpace {
	
		private Projection<Object> space;
		
		public SimulationSpace(Projection<Object> space) {
			this();
			this.setSpace(space);
		}
		
		public SimulationSpace() {
			super();
			this.space = null;
		}


		public Projection<Object> getSpace() {
			return space;
		}
		
		public void setSpace(Projection<Object> space) {
			this.space = space;
			//SimulationContext.getInstance().addProjection(this.space);
		}

		@SuppressWarnings("unchecked")
		public Class<? extends Projection<?>> getType() {
			return  (Class<? extends Projection<?>>) this.space.getClass();			
		}
		
}
