package edu.cnm.deepdive.dominionandroid.viewmodel;

import android.annotation.SuppressLint;
import android.app.Application;
import android.content.Context;
import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import edu.cnm.deepdive.dominionandroid.model.Card;
import edu.cnm.deepdive.dominionandroid.model.GameStateInfo;
import edu.cnm.deepdive.dominionandroid.model.PhaseState;
import edu.cnm.deepdive.dominionandroid.service.DominionApiService;
import edu.cnm.deepdive.dominionandroid.service.DominionClient;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import retrofit2.Response;

public class GameViewModel extends AndroidViewModel {

  private MutableLiveData<List<Card>> cardsInHand;
 private MutableLiveData<List<Card>> cardsInDiscard;
  private MutableLiveData<List<Card>> cardsInDrawPile;
 private MutableLiveData<Integer> myVictoryPoints;
  private MutableLiveData<Integer> theirVictoryPoints;
  private MutableLiveData<Integer> myActionsRemaining;
  private MutableLiveData<Integer> myBuysRemaining;
 private  MutableLiveData<Integer> myBuyingPower;
 private MutableLiveData<List<Integer>> numberOfCardsRemainingInEachStack;
 private MutableLiveData<HashMap<String, Integer>> stacks;
 private MutableLiveData<List<String>> playsMadeLastTurnByOtherPlayer;
  private MutableLiveData<PhaseState> whatStateAmIIn;
  private Response<GameStateInfo> gameStateInfo;
  private LiveData<Response<GameStateInfo>> gameStateInfoLiveData;
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

  public void setAccount(GoogleSignInAccount account) {
    this.account.setValue(account);
    getGameStateInfo();
  }
  
  public void processNewGameState(){
    if(gameStateInfo.body().getCardsInHand()!=null) {
      this.cardsInHand.setValue(gameStateInfo.body().getCardsInHand());
    }

    if(gameStateInfo.body().getPlaysMadeLastTurnByOtherPlayer()!=null) {
      this.playsMadeLastTurnByOtherPlayer.setValue(gameStateInfo.body().getPlaysMadeLastTurnByOtherPlayer());
    }
    if(gameStateInfo.body().getStacks()!=null) {
      this.stacks.setValue(gameStateInfo.body().getStacks());
    }
    if(gameStateInfo.body().getWhatStateAmIIn()!=null) {
      this.whatStateAmIIn.setValue(gameStateInfo.body().getWhatStateAmIIn());
    }
    this.myBuysRemaining.setValue(gameStateInfo.body().getMyBuysRemaining());
    this.myActionsRemaining.setValue(gameStateInfo.body().getMyActionsRemaining());
    this.myBuyingPower.setValue(gameStateInfo.body().getMyBuyingPower());
    this.myVictoryPoints.setValue(gameStateInfo.body().getMyBuyingPower());
    this.theirVictoryPoints.setValue(gameStateInfo.body().getTheirVictoryPoints());
  }

  @SuppressLint("CheckResult")
  public void playCard(Card card){
    //String token = getApplication().getString(R.string.oauth_header, account.getIdToken());
    //Log.d("Oauth2.0 token", token);
    apiService.doAction(card.getCardName())
        .subscribeOn(Schedulers.io())
        .subscribe(
            (gameStateInfoResponse) -> this.gameStateInfoLiveData.postValue(gameStateInfoResponse),
            this.throwable::postValue
        );
    gameStateInfo = gameStateInfoLiveData.getValue();
    processNewGameState();
  }
  @SuppressLint("CheckResult")
  public void playCard(Card card, List<Card> cards){
    //String token = getApplication().getString(R.string.oauth_header, account.getIdToken());
    //Log.d("Oauth2.0 token", token);
    apiService.doAction(card.getCardName(), cards)
        .subscribeOn(Schedulers.io())
        .subscribe(
            (gameStateInfoResponse) -> this.gameStateInfoLiveData.postValue(gameStateInfoResponse),
            this.throwable::postValue
        );
    gameStateInfo = gameStateInfoLiveData.getValue();
    processNewGameState();
  }
  @SuppressLint("CheckResult")
  public void buyCard(Card card){
    //String token = getApplication().getString(R.string.oauth_header, account.getIdToken());
    //Log.d("Oauth2.0 token", token);
    apiService.buyCard(card.getCardName())
        .subscribeOn(Schedulers.io())
        .subscribe(
            (gameStateInfoResponse) -> this.gameStateInfoLiveData.postValue(gameStateInfoResponse),
            this.throwable::postValue
        );
    gameStateInfo = gameStateInfoLiveData.getValue();
    processNewGameState();
  }
  @SuppressLint("CheckResult")
  public void buyCard(Card card, List<Card> cards){
   // String token = getApplication().getString(R.string.oauth_header, account.getIdToken());
   // Log.d("Oauth2.0 token", token);
    apiService.buyCard(card.getCardName(), cards)
        .subscribeOn(Schedulers.io())
        .subscribe(
            (gameStateInfoResponse) -> this.gameStateInfoLiveData.postValue(gameStateInfoResponse),
            this.throwable::postValue
        );
    gameStateInfo = gameStateInfoLiveData.getValue();
    processNewGameState();
  }
  @SuppressLint("CheckResult")
  public void getGameStateInfo(){
    //String token = getApplication().getString(R.string.oauth_header, account.getIdToken());
    //Log.d("Oauth2.0 token", token);
    apiService.getGameStateInfo()
        .subscribeOn(Schedulers.io())
        .subscribe(
            (gameStateInfoResponse) -> this.gameStateInfoLiveData.postValue(gameStateInfoResponse),
            this.throwable::postValue
        );
    gameStateInfo = gameStateInfoLiveData.getValue();
    processNewGameState();
  }
  @SuppressLint("CheckResult")
  public void endPhase(){
    //String token = getApplication().getString(R.string.oauth_header, account.getIdToken());
    //Log.d("Oauth2.0 token", token);
    apiService.endPhase()
        .subscribeOn(Schedulers.io())
        .subscribe(
            (gameStateInfoResponse) -> this.gameStateInfoLiveData.postValue(gameStateInfoResponse),
            this.throwable::postValue
        );
    gameStateInfo = gameStateInfoLiveData.getValue();
    processNewGameState();
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
    this.myActionsRemaining = new MutableLiveData<Integer>();
    this.myActionsRemaining.setValue(myActionsRemaining.getValue().toString());
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

  public MutableLiveData<List<String>> getPlaysMadeLastTurnByOtherPlayer() {
    return playsMadeLastTurnByOtherPlayer;
  }

  public void setPlaysMadeLastTurnByOtherPlayer(
      MutableLiveData<List<String>> playsMadeLastTurnByOtherPlayer) {
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
