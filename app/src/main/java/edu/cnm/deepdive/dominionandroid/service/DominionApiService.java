package edu.cnm.deepdive.dominionandroid.service;

import edu.cnm.deepdive.dominionandroid.model.CardSet;
import edu.cnm.deepdive.dominionandroid.model.GameStateInfo;
import io.reactivex.Single;
import java.util.Optional;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.http.Body;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface DominionApiService {
  @POST("/get")
  Single<Response<GameStateInfo>> itemGet(@Body GameStateInfo request);

  @POST("/games/createorjoin")
  Single<Response<GameStateInfo>> createOrJoin();

  @POST ("/{gameId}/plays/{cardid}/action")
  Single<Response<GameStateInfo>> doAction(@Path ("cardid") int cardId);

  @POST ("/{gameId}/plays/{cardid}/buy")
  Single<Response<GameStateInfo>> buyCard(@Path ("cardid") int cardId);

  @POST("//{gameId}/plays/endphase")
  Single<Response<GameStateInfo>> endPhase();


}
