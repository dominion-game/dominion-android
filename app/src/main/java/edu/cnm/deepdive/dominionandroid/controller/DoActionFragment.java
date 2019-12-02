package edu.cnm.deepdive.dominionandroid.controller;


import android.graphics.Color;
import android.os.Bundle;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.FragmentPagerAdapter;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.viewpager.widget.ViewPager;
import edu.cnm.deepdive.dominionandroid.R;
import edu.cnm.deepdive.dominionandroid.databinding.FragmentDoActionBinding;
import edu.cnm.deepdive.dominionandroid.model.Card;
import edu.cnm.deepdive.dominionandroid.model.Card.CardType;
import edu.cnm.deepdive.dominionandroid.model.GameStateInfo;
import edu.cnm.deepdive.dominionandroid.model.PhaseState;
import edu.cnm.deepdive.dominionandroid.viewmodel.GameViewModel;

/**
 * A simple {@link Fragment} subclass.
 */
public class DoActionFragment extends Fragment implements OnClickListener {

  NavController navController = null;
  GameViewModel gameViewModel;
  ViewPager viewPager;
  TextView actionsText;

  //  private String[] imageNames = new String[]{
//      "copper",
//      "village",
//      "militia",
//      "gold",
//      "workshop"
//  };
  private String[] imageNames;

  public DoActionFragment() {
    // Required empty public constructor
  }

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
      Bundle savedInstanceState) {
    // Inflate the layout for this fragment
//    View view = inflater.inflate(R.layout.fragment_do_action, container, false);

    FragmentDoActionBinding binding;
    binding = DataBindingUtil.inflate(
        inflater, R.layout.fragment_do_action, container, false);
    binding.setLifecycleOwner(this);
    View view = binding.getRoot();
    gameViewModel = ViewModelProviders.of(getActivity()).get(GameViewModel.class);
    binding.setViewModel(gameViewModel);

    viewPager = binding.viewPager;

    final Observer<GameStateInfo> gameStateInfoObserver = new Observer<GameStateInfo>() {
      @Override
      public void onChanged(GameStateInfo gameStateInfo) {
        //since we watch gameStateInfo and everyone else does too, we only respond if it is our state
        if (gameStateInfo.debugging || gameStateInfo.getWhatStateAmIIn()
            .equals(PhaseState.ACTING)) {
          //reset image names to be from gameStateInfo
          imageNames = gameStateInfo.getCardStringsInHand();

          ViewPagerAdapter adapter = new ViewPagerAdapter(getContext(), imageNames);
          viewPager.setAdapter(adapter);
//          navController.navigate(R.id.action_doActionFragment_to_doBuysFragment);
        }
      }
    };
    gameViewModel.getGameStateInfo().observe(this, gameStateInfoObserver);

    return view;
  }

  @Override
  public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);
    navController = Navigation.findNavController(view);
    view.findViewById(R.id.play_card).setOnClickListener(this);
    view.findViewById(R.id.end_action).setOnClickListener(this);
    view.findViewById(R.id.end_turn).setOnClickListener(this);
    actionsText = view.findViewById(R.id.actions);
  }

  @Override
  public void onClick(View v) {
    switch (v.getId()) {
      case R.id.play_card:
        playCurrentCard();
        break;
      case R.id.end_action:
        navController.navigate(R.id.action_doActionFragment_to_doBuysFragment);
        break;
      case R.id.end_turn:
        navController.navigate(R.id.action_doActionFragment_to_turnSummaryFragment);
        break;
    }
    actionsText.setBackgroundColor(Color.TRANSPARENT);
    //TODO need to implement button functionality for play card
  }

  private void playCurrentCard(){
    //TODO see if this actually gets the current card shown
    int currentCardIndex = viewPager.getCurrentItem();
    Card cardToPlay = gameViewModel.getCardsInHand().getValue().get(currentCardIndex);
    if (cardToPlay.getCardType() == CardType.CELLAR ||
        cardToPlay.getCardType() == CardType.MINE ||
        cardToPlay.getCardType() == CardType.REMODEL){
      //TODO if card has "extra" cost, need to have player "select" until done.
      //switch fragment button "Select Checkbox" on
    }
    //then make a card and play it
    gameViewModel.playCard(cardToPlay);
  }
}
