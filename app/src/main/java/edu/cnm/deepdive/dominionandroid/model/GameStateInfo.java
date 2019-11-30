package edu.cnm.deepdive.dominionandroid.model;

import androidx.lifecycle.MutableLiveData;
import com.google.gson.annotations.Expose;
import java.io.Serializable;
import java.sql.Array;
import java.util.Dictionary;
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
  Dictionary<String, Integer> stacks;

  @Expose
  List<Play> playsMadeLastTurnByOtherPlayer;

  @Expose
  PhaseState whatStateAmIIn;


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

  public Dictionary<String, Integer> getStacks() {
    return stacks;
  }

  public void setStacks(Dictionary<String, Integer> stacks) {
    this.stacks = stacks;
  }

  public List<Play> getPlaysMadeLastTurnByOtherPlayer() {
    return playsMadeLastTurnByOtherPlayer;
  }

  public void setPlaysMadeLastTurnByOtherPlayer(
      List<Play> playsMadeLastTurnByOtherPlayer) {
    this.playsMadeLastTurnByOtherPlayer = playsMadeLastTurnByOtherPlayer;
  }

  public PhaseState getWhatStateAmIIn() {
    return whatStateAmIIn;
  }

  public void setWhatStateAmIIn(PhaseState whatStateAmIIn) {
    this.whatStateAmIIn = whatStateAmIIn;
  }
}
