package edu.cnm.deepdive.dominionandroid.controller;

import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import edu.cnm.deepdive.dominionandroid.R;
import edu.cnm.deepdive.dominionandroid.model.GameStateInfo;
import edu.cnm.deepdive.dominionandroid.service.DominionApiService;
import java.io.LineNumberInputStream;
import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class TestActivity extends AppCompatActivity {

  TextView textView;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_test);
    textView = findViewById(R.id.test_text);


    Retrofit retrofit = new Retrofit.Builder()
        .baseUrl("https://pure-tundra-13659.herokuapp.com")
        .addConverterFactory(GsonConverterFactory.create())
        .build();

    DominionApiService dominionApiService = retrofit.create(DominionApiService.class);
    Call<GameStateInfo> call = dominionApiService.getGameState();
    call.enqueue(new Callback<GameStateInfo>() {
      @Override
      public void onResponse(Call<GameStateInfo> call, Response<GameStateInfo> response) {

        for(String blah:response.body().cardsInHand) {

          textView.setText(blah);
        }

        textView.setText(response.body().);
      }

      @Override
      public void onFailure(Call<GameStateInfo> call, Throwable t) {

      }
    });
  }
}
