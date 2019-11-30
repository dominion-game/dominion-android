package edu.cnm.deepdive.dominionandroid.model;

import com.google.gson.annotations.Expose;
import java.io.Serializable;
import java.util.HashMap;
import java.util.List;

/**
 * Response entity for game state- catches response coming back from server.
 *
 */
public class GameStateInfo implements Serializable {

  @Expose
  List<Card> cardsInHand;

  @Expose
  int myVictoryPoints;

  @Expose
  int theirVictoryPoints;

  @Expose
  int myActionsRemaining;

  @Expose
  int myBuysRemaining;

  @Expose
  int myBuyingPower;

  @Expose
  HashMap<String, Integer> stacks;

  @Expose
  List<String> playsMadeLastTurnByOtherPlayer;

  @Expose
  String whatState;


  public List<Card> getCardsInHand() {
    return cardsInHand;
  }

  public void setCardsInHand(List<Card> cardsInHand) {
    this.cardsInHand = cardsInHand;
  }

  public int getMyVictoryPoints() {
    return myVictoryPoints;
  }

  public void setMyVictoryPoints(int myVictoryPoints) {
    this.myVictoryPoints = myVictoryPoints;
  }

  public int getTheirVictoryPoints() {
    return theirVictoryPoints;
  }

  public void setTheirVictoryPoints(int theirVictoryPoints) {
    this.theirVictoryPoints = theirVictoryPoints;
  }

  public int getMyActionsRemaining() {
    return myActionsRemaining;
  }

  public void setMyActionsRemaining(int myActionsRemaining) {
    this.myActionsRemaining = myActionsRemaining;
  }

  public int getMyBuysRemaining() {
    return myBuysRemaining;
  }

  public void setMyBuysRemaining(int myBuysRemaining) {
    this.myBuysRemaining = myBuysRemaining;
  }

  public int getMyBuyingPower() {
    return myBuyingPower;
  }

  public void setMyBuyingPower(int myBuyingPower) {
    this.myBuyingPower = myBuyingPower;
  }

  public HashMap<String, Integer> getStacks() {
    return stacks;
  }

  public void setStacks(HashMap<String, Integer> stacks) {
    this.stacks = stacks;
  }

  public List<String> getPlaysMadeLastTurnByOtherPlayer() {
    return playsMadeLastTurnByOtherPlayer;
  }

  public void setPlaysMadeLastTurnByOtherPlayer(
      List<String> playsMadeLastTurnByOtherPlayer) {
    this.playsMadeLastTurnByOtherPlayer = playsMadeLastTurnByOtherPlayer;
  }

  public String getWhatState() {
    return whatState;
  }

  public void setWhatState(String whatState) {
    this.whatState = whatState;
  }
}
