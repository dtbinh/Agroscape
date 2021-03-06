package gr.agroscape.skeleton.agents.plot;

import gr.agroscape.main.AgroscapeConfiguration;
import gr.agroscape.skeleton.agents.AgroscapeAgent;
import gr.agroscape.utilities.GridValueLayerFunction;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;

import repast.simphony.space.grid.GridPoint;
import repast.simphony.valueLayer.GridValueLayer;

/**
 * This is a Plot. Many GridPoints constitute a Plot.<br />
 * The plot_id starts from 1<br />
 * The concern of this class is:
 * <ul>
 * <li> to give information about the Plot (Area, Average Soil Characteristics, etc.)</li>
 * </ul>
 * 
 * 
 * @author Dimitris Kremmydas
 *
 */

public class Plot extends AgroscapeAgent {

	/**
	 * A Plot is a set of GridPoints
	 */
    private ArrayList<GridPoint> gridPoints=new ArrayList<GridPoint>();
    
    /**
     * Which points are the corners of the plot 
     */   
    private ArrayList<GridPoint> corners = new ArrayList<>();
    
    
    
    /**
     * 
     * @param point
     * @param id
     */
    public Plot(GridPoint point, int id) {
    	this(new ArrayList<GridPoint>(Arrays.asList(point)),id);
	}
    
    /**
     * 
     * @param point
     */
    public Plot(GridPoint point) {
		this(new ArrayList<GridPoint>(Arrays.asList(point)));
	}
    

    
    
    /**
     * Create a new Plot from an ArrayList of GridPoints. <br />
     * <strong>Does not</strong> check for duplicate GidPoints in the ArrayList.
     * @param points
     * @param id
     */
    public Plot(ArrayList<GridPoint> points,  int id) {
		super(id);
		this.gridPoints = points;
	}
    
    
    /**
     * 
     * @param points
     */
    public Plot(ArrayList<GridPoint> points) {
		super();
		this.gridPoints = points;
	}
 
      
    
    /**
     * 
     * @param points
     * @param id
     */
    public Plot(int[][] points, int id) {
    	this(points);	
    	this.setName(id);
		
	}
    
    /**
     * 
     * @param points
     */
    public Plot(int[][] points) {
    	super();
    	for(int i=0;i<points.length;i++) {
			int[] gp = new int[2]; gp[0]=points[i][0];gp[1]=points[i][1];
			this.addGridPoint(new GridPoint(gp));
					//(Integer.valueOf(points[i][0]),Integer.valueOf(points[i][1])));
		}		
	}

    /**
     * Get the Area of the Plot (hectares)
     * @return
     */
	public double getArea() {
        return this.gridPoints.size()*AgroscapeConfiguration.getGridPointArea();
    }
	
	
	/**
	 * The average Value of all contained GridPoints in the {@link GridValueLayer} data <br/>
	 * For example, if someone wants to calculate the Average yield of the Plot for a specific Crop, he just has to pass the
	 * {@link GridValueLayer} that keeps the Yields for that Crop.
	 * 
	 * @return
	 */
	public double getAverage(GridValueLayer data) {
		if(data==null) return 0d;
		
		double r = 0d;
		for (Iterator<GridPoint> iterator = this.gridPoints.iterator(); iterator.hasNext();) {
			GridPoint gridPoint = iterator.next();
			r = (r + data.get(gridPoint.getX(),gridPoint.getY()));
		}
		return r/this.gridPoints.size();
	}
	
	
	/**
	 * Get the GridPoints of the Plot
	 * @return
	 */
	public ArrayList<GridPoint> getGridPoints() {
		return gridPoints;
	}

	private void addGridPoint(GridPoint p) {
		if(! this.gridPoints.contains(p))
		this.gridPoints.add(p);
	}
	
	@Override
	public String toString() {
		String r = "{"+super.toString()+"} ";
		r += " / Num of GridPoints: " + this.gridPoints.size();
		return r;
	}
	
	
	/**
	 * Clones a Plot
	 */	
	@SuppressWarnings("unchecked")
	@Override
	protected Plot clone() throws CloneNotSupportedException {
		ArrayList<GridPoint> gps = (ArrayList<GridPoint>) this.gridPoints.clone();
		Plot p = new Plot(gps);
		return p;
	}
	


	/**
	 * Sets the corresponding x-y coordinates of the {@link GridValueLayer} to the requested value
	 * @param vl
	 * @param v
	 */
	public void setGridValueLayer(GridValueLayer vl, double v) {
		for (Iterator<GridPoint> iterator = this.gridPoints.iterator(); iterator.hasNext();) {
			GridPoint gridPoint = iterator.next();
			vl.set(v, gridPoint.getX(),gridPoint.getY());
		}
	}
	
	
	public void updateGridValueLayer(GridValueLayer vl,GridValueLayerFunction function) {
		function.apply(vl, this.gridPoints);
	}
	
	
	/**
	 * Returns an arraylist with 2 GridPoints. The order of the elements is as follows:<br />
	 * 1. top-left<br />
	 * 2. bottom-right<br />
	 *
	 * @return
	 */
	public ArrayList<GridPoint> getCorners() {
		if(this.corners.isEmpty()) this.findCorners();
		return this.corners;
	}
	
	/**
	 * find the corners of the plot
	 */
	private void findCorners() {
		 GridPoint top_left_corner,bottom_right_corner;
		 top_left_corner=this.gridPoints.get(0);
		 bottom_right_corner=this.gridPoints.get(this.gridPoints.size()-1);
		
		 for (Iterator<GridPoint> iterator = this.gridPoints.iterator(); iterator.hasNext();) {
				GridPoint gp = iterator.next();
				if(gp.getX()<top_left_corner.getX() && gp.getY()<top_left_corner.getY()) top_left_corner=gp;
				if(gp.getX()>bottom_right_corner.getX() && gp.getY()>bottom_right_corner.getY()) bottom_right_corner=gp;
		}
		
		 this.corners.clear();
		 this.corners.add(top_left_corner);
		 this.corners.add(bottom_right_corner);
	}

	
} //end class
