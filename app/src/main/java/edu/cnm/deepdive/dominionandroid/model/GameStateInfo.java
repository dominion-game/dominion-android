package edu.cnm.deepdive.dominionandroid.model;

import java.util.List;
import java.util.Stack;

/**
 * Response entity for game state- catches response coming back from server.
 *
 */
public class GameStateInfo {

  public static class PlayerStateInfo{

    public static class Player{
      private int playerScore;
      private int extraGold;
      private int numAction;
      private int numBuy;
      private int extraGoldIfSilver;
    }

    private Turn thisTurn;
    private Player player;
    private Game game;
    private DrawPile drawPile;
    private Hand hand;
    private DiscardPile discardPile;
    private PhaseState phaseState;
  }

  public static class Game{}
  public static class Turn{}
  public static class StateMachine{}


  private PlayerStateInfo playerStateInfoPlayer1;
  private PlayerStateInfo playerStateInfoPlayer2;
  private PlayerStateInfo currentPlayerStateInfo;

  private Game game;
  private List<Turn> previousTurns;
  private long currentPlayerId;
  private StateMachine stateMachine;
  private List<Stack> stacks;


  public List<Card> cardsInHand;
}
