package gr.agroscape.behaviors.landMarket.interfaces;

import java.util.List;

/**
 * Given various bids, who is the winner
 * @author Dimitris Kremmydas
 *
 */
public interface WinningRule {
	
	public Bid getWinner(List<Bid> bids);

}