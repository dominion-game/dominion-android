package edu.cnm.deepdive.dominionandroid.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
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
      @SerializedName("whatState")
  PhaseState whatStateAmIIn;

  public boolean debugging = true;

  public List<Card> getCardsInHand() {
    return cardsInHand;
  }

  public String[] getCardStringsInHand() {
    String[] cardStrings = new String[cardsInHand.size()];
    for (int i = 0; i < cardsInHand.size() ; i++) {
      cardStrings[i] = cardsInHand.get(i).getCardName().toLowerCase();
    }
    return cardStrings;
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

  public String[] getStackStrings() {
    String[] stackStrings = new String[stacks.size()];
    String[] keysStrings = stacks.keySet().toArray(new String[stacks.size()]);
    for (int i = 0; i < keysStrings.length ; i++) {
      stackStrings[i] = keysStrings[i].toLowerCase();
    }
    return stackStrings;
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

  public PhaseState getWhatStateAmIIn() {
    return whatStateAmIIn;
  }

  public void setWhatStateAmIIn(PhaseState whatStateAmIIn) {
    this.whatStateAmIIn = whatStateAmIIn;
  }

  @Override
  public String toString() {
    StringBuilder gameStateInfoString = new StringBuilder();
    gameStateInfoString.append("Cards in hand: \n");
    for (int i = 0; i < getCardsInHand().size(); i++) {
      gameStateInfoString.append("Card [" + i + "]: " + getCardsInHand().get(i) + "\n");
    }
    HashMap<String, Integer> stacks = getStacks();
    for (String stackName : stacks.keySet()) {
      gameStateInfoString.append("Stack " + stackName + ": " + stacks.get(stackName) + "\n");
    }
    gameStateInfoString.append("My Victory Points: " + getMyVictoryPoints() + "\n");
    gameStateInfoString.append("Their Victory Points: " + getTheirVictoryPoints() + "\n");
    gameStateInfoString.append("Actions Remaining: " + getMyActionsRemaining() + "\n");
    gameStateInfoString.append("Buys Remaining: " + getMyBuysRemaining() + "\n");
    gameStateInfoString.append("Buying Power: " + getMyBuyingPower() + "\n");
    List<String> otherPlayerTurn = getPlaysMadeLastTurnByOtherPlayer();
    for (int i = 0; i < otherPlayerTurn.size(); i++) {
      gameStateInfoString.append("Play [" + i + "]: " + otherPlayerTurn.get(i) + "\n");
    }
    gameStateInfoString.append("Current State: " + getWhatStateAmIIn() + "\n");
    return gameStateInfoString.toString();
  }


}
