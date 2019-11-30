package edu.cnm.deepdive.dominionandroid.model;

import com.google.gson.annotations.Expose;

public class Card {

  @Expose
  private String cardType;
  @Expose
  private int cost;
  @Expose
  private String cardName;

  public String getCardType() {
    return cardType;
  }

  public void setCardType(String cardType) {
    this.cardType = cardType;
  }

  public int getCost() {
    return cost;
  }

  public void setCost(int cost) {
    this.cost = cost;
  }

  public String getCardName() {
    return cardName;
  }

  public void setCardName(String cardName) {
    this.cardName = cardName;
  }

  @Override
  public String toString() {
    return "CardType: " + cardType + ", Cost: " + cost  + ", CardName: " + cardName;
  }
}
