package gr.agroscape.behaviors;

import gr.agroscape.agents.Farmer;
import gr.agroscape.contexts.Space;

import java.nio.file.Path;
import java.util.Collection;

/**
 * An interface declaring actions for a BehaviorDataLoader.<br />
 * It accepts (at minimum) a {@link Collection} of objects, the {@link Space} object
 * and returns a {@link Collection} of objects (possibly subclassing the accepted objects)
 * that implement {@link IScheduledBehavior} interface.
 * 
 *  //TODO make it more flexible. Functor or Command pattern
 * 
 * @author Dimitris Kremmydas
 *
 */
public interface IScheduledBehaviorDataLoader<T> {
	
	/**
	 * Setups owners and space
	 * 
	 * @param df
	 */
	public Collection<IScheduledBehavior<T>> setup(Collection<? super Farmer> owners, Space space, ABehaviorContext<T> container,Path dataFile);
	
	/**
	 * Load without data file
	 * @param owners
	 * @param space
	 * @return
	 */
	public Collection<IScheduledBehavior<T>> setup(Collection<? super Farmer> owners, Space space, ABehaviorContext<T> container);


}
