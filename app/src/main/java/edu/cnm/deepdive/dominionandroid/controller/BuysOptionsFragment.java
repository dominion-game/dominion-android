package edu.cnm.deepdive.dominionandroid.controller;


import android.os.Bundle;

import android.view.View.OnClickListener;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import edu.cnm.deepdive.dominionandroid.R;
import edu.cnm.deepdive.dominionandroid.databinding.FragmentBuysOptionsBinding;
import edu.cnm.deepdive.dominionandroid.model.GameStateInfo;
import edu.cnm.deepdive.dominionandroid.service.DominionApiService;
import edu.cnm.deepdive.dominionandroid.viewmodel.GameViewModel;
import java.util.HashMap;
import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class BuysOptionsFragment extends Fragment implements OnClickListener {

  NavController navController = null;
  TextView server_data;

  public BuysOptionsFragment() {
    // Required empty public constructor
  }


  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
      Bundle savedInstanceState) {
    // Inflate the layout for this fragment

    FragmentBuysOptionsBinding binding;
    binding = DataBindingUtil.inflate(
        inflater, R.layout.fragment_buys_options, container, false);
    binding.setLifecycleOwner(this);
    View view = binding.getRoot();
    GameViewModel gameViewModel = ViewModelProviders.of(this).get(GameViewModel.class);
    binding.setViewModel(gameViewModel);

    server_data = view.findViewById(R.id.server_data);
    DominionApiService dominionService = DominionApiService.getInstance();
    Call<GameStateInfo> call = dominionService.getGameStateInfo();
    call.enqueue(new Callback<GameStateInfo>() {
      @Override
      public void onResponse(Call<GameStateInfo> call, Response<GameStateInfo> response) {
        if (!response.isSuccessful()) {
          server_data.setText("Code: " + response.code());
          return;
        }
        GameStateInfo gameStateInfo = response.body();
        StringBuilder gameStateText = new StringBuilder();
        gameStateText.append("Cards in hand: \n");
        for (int i = 0; i < gameStateInfo.getCardsInHand().size(); i++) {
          gameStateText.append("Card [" + i + "]: " + gameStateInfo.getCardsInHand().get(i) + "\n");
        }
        HashMap<String, Integer> stacks = gameStateInfo.getStacks();
        for (String stackName : stacks.keySet()) {
          gameStateText.append("Stack " + stackName + ": " + stacks.get(stackName) + "\n");
        }
//        for(int i = 0; i< gameStateInfo.getStacks().size(); i++){
        gameStateText.append("My Victory Points: " + gameStateInfo.getMyVictoryPoints() + "\n");
        gameStateText.append("Their Victory Points: " + gameStateInfo.getTheirVictoryPoints() + "\n");
        gameStateText.append("Actions Remaining: " + gameStateInfo.getMyActionsRemaining() + "\n");
        gameStateText.append("Buys Remaining: " + gameStateInfo.getMyBuysRemaining() + "\n");
        gameStateText.append("Buying Power: " + gameStateInfo.getMyBuyingPower() + "\n");
        List<String> otherPlayerTurn = gameStateInfo.getPlaysMadeLastTurnByOtherPlayer();
        for (int i = 0; i < otherPlayerTurn.size(); i++) {
          gameStateText.append("Play [" + i + "]: " + otherPlayerTurn.get(i) + "\n");
        }
        gameStateText.append("Current State: " +gameStateInfo.getWhatState() + "\n");

        server_data.append(gameStateText);
      }

      @Override
      public void onFailure(Call<GameStateInfo> call, Throwable t) {
        server_data.setText(t.getMessage());
      }
    });
    return view;
  }

  @Override
  public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);
    navController = Navigation.findNavController(view);
    view.findViewById(R.id.money).setOnClickListener(this);
    view.findViewById(R.id.victory).setOnClickListener(this);
    view.findViewById(R.id.action).setOnClickListener(this);
    view.findViewById(R.id.end_turn).setOnClickListener(this);
  }

  @Override
  public void onClick(View v) {
    switch (v.getId()) {
      case R.id.money:
      case R.id.victory:
      case R.id.action:
        navController.navigate(R.id.action_buysOptionsFragment_to_doBuysFragment);
        break;
      case R.id.end_turn:
        navController.navigate(R.id.action_buysOptionsFragment_to_turnSummaryFragment);
        break;
    }

  }
}
