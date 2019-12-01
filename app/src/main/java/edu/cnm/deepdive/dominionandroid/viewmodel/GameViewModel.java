package edu.cnm.deepdive.dominionandroid.viewmodel;

import android.app.Application;
import android.content.Context;
import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LifecycleEventObserver;
import androidx.lifecycle.LifecycleObserver;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import edu.cnm.deepdive.dominionandroid.model.Card;
import edu.cnm.deepdive.dominionandroid.model.GameStateInfo;
import edu.cnm.deepdive.dominionandroid.model.PhaseState;
import edu.cnm.deepdive.dominionandroid.model.Play;
import edu.cnm.deepdive.dominionandroid.service.DominionApiService;
import edu.cnm.deepdive.dominionandroid.service.DominionClient;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class GameViewModel extends AndroidViewModel implements LifecycleObserver {

  MutableLiveData<GameStateInfo> gameStateInfo;
//  MutableLiveData<List<Card>> cardsInHand;
//  MutableLiveData<List<Card>> cardsInDiscard;
//  MutableLiveData<List<Card>> cardsInDrawPile;
//  MutableLiveData<Integer> myVictoryPoints;
//  MutableLiveData<Integer> theirVictoryPoints;
  MutableLiveData<String> myActionsRemaining;
  MutableLiveData<Integer> myBuysRemaining;
  MutableLiveData<Integer> myBuyingPower;
//  MutableLiveData<List<Integer>> numberOfCardsRemainingInEachStack;
  MutableLiveData<List<Play>> playsMadeLastTurnByOtherPlayer;
//  MutableLiveData<PhaseState> whatStateAmIIn;
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
    gameStateInfo = new MutableLiveData<>();

    apiService = DominionApiService.getInstance();
    context = application.getApplicationContext();
    pending = new CompositeDisposable();
    executor = Executors.newSingleThreadExecutor();
  }

  public String gameStateInfoString(){
    return gameStateInfo.getValue().toString();
  }
  public void startNewGame() {
//    apiService.newGame()
//        .subscribeOn(Schedulers.io())
//        .subscribe(this.gameStateInfo::postValue, this.throwable::postValue);
    apiService.newGame()
        .subscribeOn(Schedulers.io())
        .subscribe(this.gameStateInfo::postValue, this.throwable::postValue);
//            (this.throwable::postValue);

//    DominionApiService dominionService = DominionApiService.getInstance();
//    Call<GameStateInfo> call = dominionService.getGameStateInfo();
//    call.enqueue(new Callback<GameStateInfo>() {
//      @Override
//      public void onResponse(Call<GameStateInfo> call, Response<GameStateInfo> response) {
//        if (!response.isSuccessful()) {
////                    server_data.setText("Code: " + response.code());
//          return;
//        }
//        gameStateInfo::postValue(response.body());
//        GameStateInfo gameStateInfo = response.body();
//        StringBuilder gameStateText = new StringBuilder();
//        gameStateText.append("Cards in hand: \n");
//        for (int i = 0; i < gameStateInfo.getCardsInHand().size(); i++) {
//          gameStateText.append("Card [" + i + "]: " + gameStateInfo.getCardsInHand().get(i) + "\n");
//        }
//        HashMap<String, Integer> stacks = gameStateInfo.getStacks();
//        for (String stackName : stacks.keySet()) {
//          gameStateText.append("Stack " + stackName + ": " + stacks.get(stackName) + "\n");
//        }
////        for(int i = 0; i< gameStateInfo.getStacks().size(); i++){
//        gameStateText.append("My Victory Points: " + gameStateInfo.getMyVictoryPoints() + "\n");
//        gameStateText.append("Their Victory Points: " + gameStateInfo.getTheirVictoryPoints() + "\n");
//        gameStateText.append("Actions Remaining: " + gameStateInfo.getMyActionsRemaining() + "\n");
//        gameStateText.append("Buys Remaining: " + gameStateInfo.getMyBuysRemaining() + "\n");
//        gameStateText.append("Buying Power: " + gameStateInfo.getMyBuyingPower() + "\n");
//        List<String> otherPlayerTurn = gameStateInfo.getPlaysMadeLastTurnByOtherPlayer();
//        for (int i = 0; i < otherPlayerTurn.size(); i++) {
//          gameStateText.append("Play [" + i + "]: " + otherPlayerTurn.get(i) + "\n");
//        }
//        gameStateText.append("Current State: " +gameStateInfo.getWhatState() + "\n");
//
////                server_data.append(gameStateText);
//      }
//
//      @Override
//      public void onFailure(Call<GameStateInfo> call, Throwable t) {
//
//      }
//    });

  }

  public MutableLiveData<GameStateInfo> getGameStateInfo() {
    return gameStateInfo;
  }

  public void setGameStateInfo(
      MutableLiveData<GameStateInfo> gameStateInfo) {
    this.gameStateInfo = gameStateInfo;
  }

//  public MutableLiveData<List<Card>> getCardsInHand() {
//    return cardsInHand;
//  }
//
//  public void setCardsInHand(
//      MutableLiveData<List<Card>> cardsInHand) {
//    this.cardsInHand = cardsInHand;
//  }

//  public MutableLiveData<List<Card>> getCardsInDiscard() {
//    return cardsInDiscard;
//  }
//
//  public void setCardsInDiscard(
//      MutableLiveData<List<Card>> cardsInDiscard) {
//    this.cardsInDiscard = cardsInDiscard;
//  }


//  public MutableLiveData<List<Card>> getCardsInDrawPile() {
//    return cardsInDrawPile;
//  }
//
//  public void setCardsInDrawPile(
//      MutableLiveData<List<Card>> cardsInDrawPile) {
//    this.cardsInDrawPile = cardsInDrawPile;
//  }
//
//  public MutableLiveData<Integer> getMyVictoryPoints() {
//    return myVictoryPoints;
//  }
//
//  public void setMyVictoryPoints(MutableLiveData<Integer> myVictoryPoints) {
//    this.myVictoryPoints = myVictoryPoints;
//  }
//
//  public MutableLiveData<Integer> getTheirVictoryPoints() {
//    return theirVictoryPoints;
//  }
//
//  public void setTheirVictoryPoints(
//      MutableLiveData<Integer> theirVictoryPoints) {
//    this.theirVictoryPoints = theirVictoryPoints;
//  }

  public MutableLiveData<String> getMyActionsRemaining() {
    return myActionsRemaining;
  }

  public void setMyActionsRemaining(MutableLiveData<Integer> myActionsRemaining) {
    this.myActionsRemaining = new MutableLiveData<>();
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

//  public MutableLiveData<List<Integer>> getNumberOfCardsRemainingInEachStack() {
//    return numberOfCardsRemainingInEachStack;
//  }
//
//  public void setNumberOfCardsRemainingInEachStack(
//      MutableLiveData<List<Integer>> numberOfCardsRemainingInEachStack) {
//    this.numberOfCardsRemainingInEachStack = numberOfCardsRemainingInEachStack;
//  }

  public MutableLiveData<List<Play>> getPlaysMadeLastTurnByOtherPlayer() {
    return playsMadeLastTurnByOtherPlayer;
  }

  public void setPlaysMadeLastTurnByOtherPlayer(
      MutableLiveData<List<Play>> playsMadeLastTurnByOtherPlayer) {
    this.playsMadeLastTurnByOtherPlayer = playsMadeLastTurnByOtherPlayer;
  }

//  public MutableLiveData<PhaseState> getWhatStateAmIIn() {
//    return whatStateAmIIn;
//  }
//
//  public void setWhatStateAmIIn(
//      MutableLiveData<PhaseState> whatStateAmIIn) {
//    this.whatStateAmIIn = whatStateAmIIn;
//  }

  public MutableLiveData<Throwable> getThrowable() {
    return throwable;
  }
}
