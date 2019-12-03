package edu.cnm.deepdive.dominionandroid.viewmodel;

import android.annotation.SuppressLint;
import android.app.Application;
import android.content.Context;
import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.Lifecycle.Event;
import androidx.lifecycle.LifecycleObserver;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.OnLifecycleEvent;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import edu.cnm.deepdive.dominionandroid.model.Card;
import edu.cnm.deepdive.dominionandroid.model.GameStateInfo;
import edu.cnm.deepdive.dominionandroid.model.PhaseState;
import edu.cnm.deepdive.dominionandroid.service.DominionApiService;
import edu.cnm.deepdive.dominionandroid.service.DominionClient;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import retrofit2.Response;

public class GameViewModel extends AndroidViewModel implements LifecycleObserver {

  private MutableLiveData<List<Card>> cardsInHand = new MutableLiveData<>() ;
  private MutableLiveData<List<Card>> cardsInDiscard = new MutableLiveData<>();
  private MutableLiveData<List<Card>> cardsInDrawPile = new MutableLiveData<>();
  private MutableLiveData<Integer> myVictoryPoints = new MutableLiveData<>();
  private MutableLiveData<Integer> theirVictoryPoints = new MutableLiveData<>();
  private MutableLiveData<Integer> myActionsRemaining = new MutableLiveData<>();
  private MutableLiveData<Integer> myBuysRemaining = new MutableLiveData<>();
  private MutableLiveData<Integer> myBuyingPower = new MutableLiveData<>();
  private MutableLiveData<List<Integer>> numberOfCardsRemainingInEachStack = new MutableLiveData<>();
  private MutableLiveData<HashMap<String, Integer>> stacks = new MutableLiveData<>();
  private MutableLiveData<List<String>> playsMadeLastTurnByOtherPlayer = new MutableLiveData<>();
  private MutableLiveData<PhaseState> whatStateAmIIn = new MutableLiveData<>();
  private final MutableLiveData<GameStateInfo> gameStateInfo = new MutableLiveData<>();
  private final DominionApiService apiService = DominionApiService.getInstance();
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

  @OnLifecycleEvent(Event.ON_STOP)
  private void clearPending() {
    pending.clear();
  }

  public void setAccount(GoogleSignInAccount account) {
    this.account.setValue(account);
    getGameStateInfo();
  }

  public void setShowSelectCard(boolean toShow){
    GameStateInfo info = gameStateInfo.getValue();
    info.setShowSelectCard(toShow);
    gameStateInfo.postValue(info);
  }

  public void processNewGameState() {
    GameStateInfo info = gameStateInfo.getValue();
    if(info == null)
      return;
    if (info.getCardsInHand() != null) {
      this.cardsInHand.postValue(info.getCardsInHand());
    }

    if (info.getPlaysMadeLastTurnByOtherPlayer() != null) {
      this.playsMadeLastTurnByOtherPlayer.postValue(info.getPlaysMadeLastTurnByOtherPlayer());
    }
    if (info.getStacks() != null) {
      this.stacks.postValue(info.getStacks());
    }
    if (info.getWhatStateAmIIn() != null) {
      this.whatStateAmIIn.postValue(info.getWhatStateAmIIn());
    }
    this.myBuysRemaining.postValue(info.getMyBuysRemaining());
    this.myActionsRemaining.postValue(info.getMyActionsRemaining());
    this.myBuyingPower.postValue(info.getMyBuyingPower());
    this.myVictoryPoints.postValue(info.getMyBuyingPower());
    this.theirVictoryPoints.postValue(info.getTheirVictoryPoints());
  }

  public void startNewGame() {
    //String token = getApplication().getString(R.string.oauth_header, account.getIdToken());
    //Log.d("Oauth2.0 token", token);
    pending.add(apiService.newGame()
        .subscribeOn(Schedulers.io())
        .subscribe(
            (info) -> {
              gameStateInfo.postValue(info);
              processNewGameState();
            },
            this.throwable::postValue
        ));
    //TODO this is null?
  }

  @SuppressLint("CheckResult")
  public void playCard(String cardName) {
    //String token = getApplication().getString(R.string.oauth_header, account.getIdToken());
    //Log.d("Oauth2.0 token", token);
    pending.add(apiService.doAction(cardName)
        .subscribeOn(Schedulers.io())
        .subscribe(
            (info) -> {
              gameStateInfo.postValue(info);
              processNewGameState();
            },
            this.throwable::postValue
        ));
  }

  @SuppressLint("CheckResult")
  public void playCard(String cardName, List<String> extraCardNames) {
    //String token = getApplication().getString(R.string.oauth_header, account.getIdToken());
    //Log.d("Oauth2.0 token", token);
    pending.add(apiService.doAction(cardName, extraCardNames)
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe(
            (info) -> {
              gameStateInfo.postValue(info);
              processNewGameState();
            },
            this.throwable::postValue
        ));
  }

  @SuppressLint("CheckResult")
  public void buyCard(String cardName) {
    //String token = getApplication().getString(R.string.oauth_header, account.getIdToken());
    //Log.d("Oauth2.0 token", token);
    pending.add(apiService.buyCard(cardName)
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe(
            (info) -> {
              gameStateInfo.postValue(info);
              processNewGameState();
            },
            this.throwable::postValue
        ));
  }

  @SuppressLint("CheckResult")
  public void buyCard(Card card, List<Card> cards) {
    // String token = getApplication().getString(R.string.oauth_header, account.getIdToken());
    // Log.d("Oauth2.0 token", token);
    pending.add(apiService.buyCard(card.getCardName(), cards)
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe(
            (info) -> {
              gameStateInfo.postValue(info);
              processNewGameState();
            },
            this.throwable::postValue
        ));
  }
//
//  public GameStateInfo getGameStateInfo() {
//    return gameStateInfo();
//  }

  public LiveData<GameStateInfo> getGameStateInfo() {
    return gameStateInfo;
  }

//    @SuppressLint("CheckResult")
//  public void getGameStateInfo() {
//    //String token = getApplication().getString(R.string.oauth_header, account.getIdToken());
//    //Log.d("Oauth2.0 token", token);
//    pending.add(apiService.getGameStateInfo()
//        .subscribeOn(Schedulers.io())
//        .observeOn(AndroidSchedulers.mainThread())
//        .subscribe(
//            (info) -> {
//              gameStateInfo.postValue(info);
//              processNewGameState();
//            },
//            this.throwable::postValue
//        ));
//  }

  public void endPhase() {
    GoogleSignInAccount account = this.account.getValue();
    if (account != null) {
      endPhase(account);
    } else {
      //gameStateInfoLiveData.setValue(Collections.EMPTY_LIST);
    }
  }

  @SuppressLint("CheckResult")
  private void endPhase(GoogleSignInAccount account) {
    //String token = getApplication().getString(R.string.oauth_header, account.getIdToken());
    //Log.d("Oauth2.0 token", token);
    pending.add(
        apiService.endPhase()
        .subscribeOn(Schedulers.io())
        .subscribe(
            (info) -> {
              gameStateInfo.postValue(info);
              processNewGameState();
            },
            this.throwable::postValue
        ));
  }


  public LiveData<HashMap<String, Integer>> getStacks() {
    return stacks;
  }

  public LiveData<List<Card>> getCardsInHand() {
    return cardsInHand;
  }

  public void setCardsInHand(
      MutableLiveData<List<Card>> cardsInHand) {
    this.cardsInHand = cardsInHand;
  }

  public LiveData<List<Card>> getCardsInDiscard() {
    return cardsInDiscard;
  }

  public void setCardsInDiscard(
      MutableLiveData<List<Card>> cardsInDiscard) {
    this.cardsInDiscard = cardsInDiscard;
  }

  public LiveData<List<Card>> getCardsInDrawPile() {
    return cardsInDrawPile;
  }

  public void setCardsInDrawPile(
      MutableLiveData<List<Card>> cardsInDrawPile) {
    this.cardsInDrawPile = cardsInDrawPile;
  }

  public LiveData<Integer> getMyVictoryPoints() {
    return myVictoryPoints;
  }

  public void setMyVictoryPoints(MutableLiveData<Integer> myVictoryPoints) {
    this.myVictoryPoints = myVictoryPoints;
  }

  public LiveData<Integer> getTheirVictoryPoints() {
    return theirVictoryPoints;
  }

  public void setTheirVictoryPoints(
      MutableLiveData<Integer> theirVictoryPoints) {
    this.theirVictoryPoints = theirVictoryPoints;
  }

  public LiveData<Integer> getMyActionsRemaining() {
    return myActionsRemaining;
  }

  public LiveData<Integer> getMyBuysRemaining() {
    return myBuysRemaining;
  }

  public void setMyBuysRemaining(MutableLiveData<Integer> myBuysRemaining) {
    this.myBuysRemaining = myBuysRemaining;
  }

  public LiveData<Integer> getMyBuyingPower() {
    return myBuyingPower;
  }

  public void setMyBuyingPower(MutableLiveData<Integer> myBuyingPower) {
    this.myBuyingPower = myBuyingPower;
  }

  public LiveData<List<Integer>> getNumberOfCardsRemainingInEachStack() {
    return numberOfCardsRemainingInEachStack;
  }

  public void setNumberOfCardsRemainingInEachStack(
      MutableLiveData<List<Integer>> numberOfCardsRemainingInEachStack) {
    this.numberOfCardsRemainingInEachStack = numberOfCardsRemainingInEachStack;
  }

  public LiveData<List<String>> getPlaysMadeLastTurnByOtherPlayer() {
    return playsMadeLastTurnByOtherPlayer;
  }

  public void setPlaysMadeLastTurnByOtherPlayer(
      MutableLiveData<List<String>> playsMadeLastTurnByOtherPlayer) {
    this.playsMadeLastTurnByOtherPlayer = playsMadeLastTurnByOtherPlayer;
  }

  public LiveData<PhaseState> getWhatStateAmIIn() {
    return whatStateAmIIn;
  }

  public void setWhatStateAmIIn(
      MutableLiveData<PhaseState> whatStateAmIIn) {
    this.whatStateAmIIn = whatStateAmIIn;
  }

  public LiveData<Throwable> getThrowable() {
    return throwable;
  }
}