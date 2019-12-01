package edu.cnm.deepdive.dominionandroid.controller;


import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.lifecycle.Observer;
import androidx.navigation.NavController;
import edu.cnm.deepdive.dominionandroid.R;
import edu.cnm.deepdive.dominionandroid.model.GameStateInfo;
import edu.cnm.deepdive.dominionandroid.model.PhaseState;
import edu.cnm.deepdive.dominionandroid.viewmodel.GameViewModel;

/**
 * A simple {@link Fragment} subclass.
 */
public class WaitingFragment extends Fragment {

  NavController navController= null;
  GameViewModel gameViewModel;

  public WaitingFragment() {
    // Required empty public constructor
  }


  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
      Bundle savedInstanceState) {
    // Inflate the layout for this fragment
    View view = inflater.inflate(R.layout.fragment_waiting, container, false);

    final Observer<GameStateInfo> gameStateInfoObserver = new Observer<GameStateInfo>()  {
      @Override
      public void onChanged(GameStateInfo gameStateInfo) {
        if (gameStateInfo.getWhatStateAmIIn().equals(PhaseState.INITIAL)) {
          navController.navigate(R.id.action_newGameFragment_to_waiting);
        }
//                if (gameStateInfo.getWhatStateAmIIn() == PhaseState.INITIAL || gameStateInfo.getWhatStateAmIIn() == PhaseState.ACTING)
        navController.navigate(R.id.action_newGameFragment_to_waiting);
//                navController.navigate(R.id.action_newGameFragment_to_doActionFragment);
      }
    };
    gameViewModel.getGameStateInfo().observe(this, gameStateInfoObserver);
    return view;
  }

}
