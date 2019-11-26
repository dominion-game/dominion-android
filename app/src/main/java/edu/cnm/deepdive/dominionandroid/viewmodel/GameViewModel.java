package edu.cnm.deepdive.dominionandroid.viewmodel;

import android.app.Application;
import android.content.Context;
import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import edu.cnm.deepdive.dominionandroid.model.Card;
import edu.cnm.deepdive.dominionandroid.model.PhaseState;
import edu.cnm.deepdive.dominionandroid.model.Play;
import edu.cnm.deepdive.dominionandroid.service.DominionApiService;
import edu.cnm.deepdive.dominionandroid.service.DominionClient;
import io.reactivex.disposables.CompositeDisposable;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class GameViewModel extends AndroidViewModel {

  MutableLiveData<List<Card>> cardsInHand;
  MutableLiveData<List<Card>> cardsInDiscard;
  MutableLiveData<List<Card>> cardsInDrawPile;
  MutableLiveData<Integer> myVictoryPoints;
  MutableLiveData<Integer> theirVictoryPoints;
  MutableLiveData<Integer> myActionsRemaining;
  MutableLiveData<Integer> myBuysRemaining;
  MutableLiveData<Integer> myBuyingPower;
  MutableLiveData<List<Integer>> numberOfCardsRemainingInEachStack;
  MutableLiveData<List<Play>> playsMadeLastTurnByOtherPlayer;
  MutableLiveData<PhaseState> whatStateAmIIn;
  private DominionApiService apiService;
  private DominionClient dominionClient;
  private ExecutorService executor;
  private CompositeDisposable pending = new CompositeDisposable();
  private Context context;
  private final MutableLiveData<GoogleSignInAccount> account =
      new MutableLiveData<>();
  private final MutableLiveData<Throwable> throwable = new MutableLiveData<>();

  public GameViewModel(@NonNull Application application) {
    super(application);
    context = application.getApplicationContext();
    pending = new CompositeDisposable();
    executor = Executors.newSingleThreadExecutor();
  }

  public MutableLiveData<List<Card>> getCardsInHand() {
    return cardsInHand;
  }

  public void setCardsInHand(
      MutableLiveData<List<Card>> cardsInHand) {
    this.cardsInHand = cardsInHand;
  }

  public MutableLiveData<List<Card>> getCardsInDiscard() {
    return cardsInDiscard;
  }

  public void setCardsInDiscard(
      MutableLiveData<List<Card>> cardsInDiscard) {
    this.cardsInDiscard = cardsInDiscard;
  }

  public MutableLiveData<List<Card>> getCardsInDrawPile() {
    return cardsInDrawPile;
  }

  public void setCardsInDrawPile(
      MutableLiveData<List<Card>> cardsInDrawPile) {
    this.cardsInDrawPile = cardsInDrawPile;
  }

  public MutableLiveData<Integer> getMyVictoryPoints() {
    return myVictoryPoints;
  }

  public void setMyVictoryPoints(MutableLiveData<Integer> myVictoryPoints) {
    this.myVictoryPoints = myVictoryPoints;
  }

  public MutableLiveData<Integer> getTheirVictoryPoints() {
    return theirVictoryPoints;
  }

  public void setTheirVictoryPoints(
      MutableLiveData<Integer> theirVictoryPoints) {
    this.theirVictoryPoints = theirVictoryPoints;
  }

  public MutableLiveData<Integer> getMyActionsRemaining() {
    return myActionsRemaining;
  }

  public void setMyActionsRemaining(
      MutableLiveData<Integer> myActionsRemaining) {
    this.myActionsRemaining = myActionsRemaining;
  }

  public MutableLiveData<Integer> getMyBuysRemaining() {
    return myBuysRemaining;
  }

  public void setMyBuysRemaining(MutableLiveData<Integer> myBuysRemaining) {
    this.myBuysRemaining = myBuysRemaining;
  }

  public MutableLiveData<Integer> getMyBuyingPower() {
    return myBuyingPower;
  }

  public void setMyBuyingPower(MutableLiveData<Integer> myBuyingPower) {
    this.myBuyingPower = myBuyingPower;
  }

  public MutableLiveData<List<Integer>> getNumberOfCardsRemainingInEachStack() {
    return numberOfCardsRemainingInEachStack;
  }

  public void setNumberOfCardsRemainingInEachStack(
      MutableLiveData<List<Integer>> numberOfCardsRemainingInEachStack) {
    this.numberOfCardsRemainingInEachStack = numberOfCardsRemainingInEachStack;
  }

  public MutableLiveData<List<Play>> getPlaysMadeLastTurnByOtherPlayer() {
    return playsMadeLastTurnByOtherPlayer;
  }

  public void setPlaysMadeLastTurnByOtherPlayer(
      MutableLiveData<List<Play>> playsMadeLastTurnByOtherPlayer) {
    this.playsMadeLastTurnByOtherPlayer = playsMadeLastTurnByOtherPlayer;
  }

  public MutableLiveData<PhaseState> getWhatStateAmIIn() {
    return whatStateAmIIn;
  }

  public void setWhatStateAmIIn(
      MutableLiveData<PhaseState> whatStateAmIIn) {
    this.whatStateAmIIn = whatStateAmIIn;
  }

  public MutableLiveData<Throwable> getThrowable() {
    return throwable;
  }
}
