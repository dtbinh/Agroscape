package gr.agroscape.agriculturalActivity;

import gr.agroscape.products.Product;

import java.util.ArrayList;
import java.util.HashMap;

public class ArableCropCultivation extends AAgriculturalActivity {

		
	/**
	 * Keep an array of name->Crop, so it is handy to get a {@link ArableCropCultivation} object by name
	 */
	private static HashMap<String,ArableCropCultivation> availableCrops=new HashMap<String,ArableCropCultivation>();
	
	/**
	 * The name of the Crop
	 */
	private String name;
	

	/**
	 * Constructor
	 * @param name
	 */
	public ArableCropCultivation(String name,ArrayList<Product> o) {
		super(o);
		this.name = name;
		availableCrops.put(name, this);	
	}
	
	public ArableCropCultivation(String name,Product o) {
		super(o);
		this.name = name;
		availableCrops.put(name, this);	
	}
	
	/**
	 * Constructor
	 * @param name
	 */
	public ArableCropCultivation(String name, int id,ArrayList<Product> o) {
		super(id,o);
		this.name = name;
		availableCrops.put(name, this);
	}


	/**
	 * Getter of name
	 * @return
	 */
	public String getName() {
		return name;
	}

	
	/**
	 * Get a Crop object by its name
	 * @param n
	 * @return
	 */
	public static ArableCropCultivation getCropByName(String n) {
		return ArableCropCultivation.availableCrops.get(n);
	}
	
	
	/**
	 * Getter
	 * @return
	 */
	public static HashMap<String, ArableCropCultivation> getAvailableCrops() {
		return availableCrops;
	}

	@Override
	public String toString() {
		return "\nCrop::" + this.getName()  
				+ " / Output Products:" + this.possibleOutput
				+" [id=" + this.myId + "]";
	}
	
	
}
