package edu.cnm.deepdive.dominionandroid.service;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import edu.cnm.deepdive.dominionandroid.BuildConfig;
import edu.cnm.deepdive.dominionandroid.model.CardSet;
import edu.cnm.deepdive.dominionandroid.model.GameStateInfo;
import io.reactivex.Single;
import java.util.Optional;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Body;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface DominionApiService {
  @POST("/get")
  Single<Response<GameStateInfo>> itemGet(@Body GameStateInfo gameStateInfo);

//  @POST("/get")
//  Single<Response<GameStateInfo>> itemGet(@Body GameStateInfo request);


  @POST("/games/createorjoin")
  Single<Response<GameStateInfo>> createOrJoin();

  @POST ("/{gameId}/plays/{cardid}/action")
  Single<Response<GameStateInfo>> doAction(@Path ("cardid") int cardId);

  @POST ("/{gameId}/plays/{cardid}/buy")
  Single<Response<GameStateInfo>> buyCard(@Path ("cardid") int cardId);

  @POST("//{gameId}/plays/endphase")
  Single<Response<GameStateInfo>> endPhase();

  static DominionApiService getInstance() {
    return InstanceHolder.INSTANCE;
  }

  class InstanceHolder {

    private static final DominionApiService INSTANCE;

    static {
      // TODO Investigate logging interceptor issues.
      Gson gson = new GsonBuilder()
          .excludeFieldsWithoutExposeAnnotation()
          .create();
      Retrofit retrofit = new Retrofit.Builder()
          .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
          .addConverterFactory(GsonConverterFactory.create(gson))
          .baseUrl(BuildConfig.BASE_URL)
          .build();
      INSTANCE = retrofit.create(DominionApiService.class);
    }

  }



}
