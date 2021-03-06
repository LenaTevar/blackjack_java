package model;

import model.rules.interfaces.IHitStrategy;
import model.rules.interfaces.INewGameStrategy;
import model.rules.interfaces.IRulesFactory;
import model.rules.interfaces.IWinnerOnTie;

public class Dealer extends Player {

	 private Deck m_deck;
	  private INewGameStrategy m_newGameRule;
	  private IHitStrategy m_hitRule;
	  private IWinnerOnTie m_winRule;

	  public Dealer(IRulesFactory a_rulesFactory) {
	  
	    m_newGameRule = a_rulesFactory.getNewGameStrategy();
	    m_hitRule = a_rulesFactory.getHitStrategy();
	    m_winRule = a_rulesFactory.getWinnerOnTie();
	    
	    /*for(Card c : m_deck.GetCards()) {
	      c.Show(true);
	      System.out.println("" + c.GetValue() + " of " + c.GetColor());
	    }    */
	  }
	  
	  
	  public boolean NewGame(Player a_player) {
	    if (m_deck == null || IsGameOver()) {
	      m_deck = new Deck();
	      ClearHand();
	      a_player.ClearHand();
	      return m_newGameRule.NewGame(m_deck, this, a_player);   
	    }
	    return false;
	  }

	  public boolean Hit(Player a_player) {
	    if (m_deck != null && a_player.CalcScore() < g_maxScore && !IsGameOver()) {
	    	getShowAndDealCardTo(a_player, true);
	    	
//	    	Card c;
//	    	c = m_deck.GetCard();
//	    	c.Show(true);
//	    	a_player.DealCard(c);
	      
	      return true;
	    }
	    return false;
	  }

	  public boolean IsDealerWinner(Player a_player) {
		  return m_winRule.dealerWins(a_player, this, g_maxScore);
//	    if (a_player.CalcScore() > g_maxScore) {
//	      return true;
//	    } else if (CalcScore() > g_maxScore) {
//	      return false;
//	    }
//	    return CalcScore() >= a_player.CalcScore();
	  }

	  public boolean IsGameOver() {
	    if (m_deck != null && m_hitRule.DoHit(this) != true) {
	        return true;
	    }
	    return false;
	  }
	  
	  public boolean Stand() {
	      if (m_deck != null) {
	          this.ShowHand();          
	          while (m_hitRule.DoHit(this)) {
	        	  getShowAndDealCardTo(this, true);
//	              Card c = m_deck.GetCard();
//	              c.Show(true);
//	              this.DealCard(c);              
	          }
	         return true;
	       }
	     return false;
	   }
	  
	  public void getShowAndDealCardTo(Player a_player, boolean visibility) {
		  Card c = m_deck.GetCard();
		  c.Show(visibility);
		  a_player.DealCard(c);
	  }
}
