package edu.cnm.deepdive.dominionandroid.controller;


import android.graphics.Color;
import android.os.Bundle;

import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import edu.cnm.deepdive.dominionandroid.R;
import edu.cnm.deepdive.dominionandroid.model.GameStateInfo;
import edu.cnm.deepdive.dominionandroid.model.PhaseState;
import edu.cnm.deepdive.dominionandroid.service.DominionApiService;
import edu.cnm.deepdive.dominionandroid.viewmodel.GameViewModel;
import java.util.HashMap;
import java.util.List;
import java.util.Observable;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class NewGameFragment extends Fragment implements OnClickListener {

    NavController navController= null;
    GameViewModel gameViewModel;

    public NewGameFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_new_game, container, false);
        gameViewModel= ViewModelProviders.of(getActivity()).get(GameViewModel.class);

        final Observer<GameStateInfo> gameStateInfoObserver = new Observer<GameStateInfo>()  {
            @Override
            public void onChanged(GameStateInfo gameStateInfo) {
//                if (gameStateInfo.getWhatStateAmIIn().equals(PhaseState.INITIAL)){
                    //TODO potentially load progress bar

//                    navController.navigate(R.id.action_newGameFragment_to_doActionFragment);
//                }
//                if (gameStateInfo.getWhatStateAmIIn() == PhaseState.INITIAL || gameStateInfo.getWhatStateAmIIn() == PhaseState.ACTING)
//                navController.navigate(R.id.action_newGameFragment_to_doActionFragment);
            }
        };
        gameViewModel.getGameStateInfo().observe(this, gameStateInfoObserver);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        navController= Navigation.findNavController(view);
        view.findViewById(R.id.new_game).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        gameViewModel.startNewGame();
    }
}
