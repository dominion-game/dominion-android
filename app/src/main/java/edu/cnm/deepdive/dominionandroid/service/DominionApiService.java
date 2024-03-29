    package edu.cnm.deepdive.dominionandroid.service;

    import com.google.gson.Gson;
    import com.google.gson.GsonBuilder;
    import edu.cnm.deepdive.dominionandroid.model.Card;
    import edu.cnm.deepdive.dominionandroid.model.CardSet;
    import edu.cnm.deepdive.dominionandroid.model.GameStateInfo;
    import io.reactivex.Single;
    import java.util.List;
    import java.util.Optional;
    import okhttp3.OkHttpClient;
    import okhttp3.logging.HttpLoggingInterceptor;
    import okhttp3.logging.HttpLoggingInterceptor.Level;
    import retrofit2.Call;
    import retrofit2.Response;
    import retrofit2.Retrofit;
    import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
    import retrofit2.converter.gson.GsonConverterFactory;
    import retrofit2.http.Body;
    import retrofit2.http.GET;
    import retrofit2.http.POST;
    import retrofit2.http.Path;

public interface DominionApiService {

  @GET("/games/gamestateinfo")
  Single<GameStateInfo> getGameStateInfo();

  @POST("newgame")
  Single<GameStateInfo> newGame();

  @GET("/games/getstate")
  Single<String> getCurrentPhaseState();

  @POST ("/plays/{cardname}/action")
  Single<GameStateInfo> doAction(@Path ("cardname") String cardName);

  @POST ("/plays/{cardname}/action")
  Single<GameStateInfo> doAction(@Path ("cardname") String cardName, @Body List<String> cards);

  @POST ("/plays/{cardname}/buy")
  Single<GameStateInfo> buyCard(@Path ("cardname") String cardName);

  @POST ("/plays/{cardname}/buy")
  Single<GameStateInfo> buyCard(@Path ("cardname") String cardName, @Body List<String> cards);

  @POST("/games/endphase")
  Single<GameStateInfo> endPhase();

  @POST("/discard/{cardname}")
  Single<GameStateInfo> discardCard(@Path ("cardname") String cardName);

  static DominionApiService getInstance() {
    return InstanceHolder.INSTANCE;
  }

  class InstanceHolder {

    private static final DominionApiService INSTANCE;

    static {
      HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
      interceptor.setLevel(Level.BODY);
      OkHttpClient client = new OkHttpClient.Builder()
          .addInterceptor(interceptor)
          .build();
      Gson gson = new GsonBuilder()
          .excludeFieldsWithoutExposeAnnotation()
          .create();
      Retrofit retrofit = new Retrofit.Builder()
          .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
          .addConverterFactory(GsonConverterFactory.create(gson))
          .baseUrl("https://pure-tundra-13659.herokuapp.com")
          .client(client)
          .build();
      INSTANCE = retrofit.create(DominionApiService.class);
    }
  }
}
