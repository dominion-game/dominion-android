package edu.cnm.deepdive.dominionandroid.service;

import edu.cnm.deepdive.dominionandroid.model.Card;
import edu.cnm.deepdive.dominionandroid.model.CardSet;
import edu.cnm.deepdive.dominionandroid.model.GameStateInfo;
import io.reactivex.Single;
import java.util.List;
import java.util.Optional;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface DominionApiService {

  @GET("/games/gamestateinfo")
  Single<Response<GameStateInfo>> getGameStateInfo();

  @POST("/games/createorjoin")
  Single<Response<GameStateInfo>> createOrJoin();

  @POST ("/plays/{cardid}/action")
  Single<Response<GameStateInfo>> doAction(@Path ("cardid") String cardName);

  @POST ("/plays/{cardid}/action")
  Single<Response<GameStateInfo>> doAction(@Path ("cardid") String cardName, @Body List<Card> cards);

  @POST ("/plays/{cardid}/buy")
  Single<Response<GameStateInfo>> buyCard(@Path ("cardid") String cardName);

  @POST ("/plays/{cardid}/buy")
  Single<Response<GameStateInfo>> buyCard(@Path ("cardid") String cardName, @Body List<Card> cards);

  @POST("/plays/endphase")
  Single<Response<GameStateInfo>> endPhase();




}
