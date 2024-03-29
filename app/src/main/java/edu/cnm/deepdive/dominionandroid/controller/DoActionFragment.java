package edu.cnm.deepdive.dominionandroid.controller;


import android.graphics.Color;
import android.os.Bundle;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.viewpager.widget.ViewPager;
import com.google.android.material.snackbar.Snackbar;
import edu.cnm.deepdive.dominionandroid.R;
import edu.cnm.deepdive.dominionandroid.databinding.FragmentDoActionBinding;
import edu.cnm.deepdive.dominionandroid.model.Card;
import edu.cnm.deepdive.dominionandroid.model.Card.CardType;
import edu.cnm.deepdive.dominionandroid.model.GameStateInfo;
import edu.cnm.deepdive.dominionandroid.model.PhaseState;
import edu.cnm.deepdive.dominionandroid.viewmodel.GameViewModel;
import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class DoActionFragment extends Fragment implements OnClickListener {

  NavController navController = null;
  GameViewModel gameViewModel;
  ViewPager viewPager;
  ViewPagerAdapter adapter;
  TextView actionsText;

  private boolean wasAttacked = false;
  private String[] imageNames;
  private int cardIndexToPlay;

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

//          ViewPagerAdapter adapter = new ViewPagerAdapter(getContext(), imageNames, gameStateInfo.isShowSelectCard());
          adapter = new ViewPagerAdapter(getContext(), imageNames,
              gameStateInfo.isShowSelectCard());
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
      view.findViewById(R.id.discard).setOnClickListener(this);

    actionsText = view.findViewById(R.id.actions);

    if (wasAttacked) {
      view.findViewById(R.id.play_card).setVisibility(View.INVISIBLE);
      view.findViewById(R.id.end_action).setVisibility(View.INVISIBLE);
      view.findViewById(R.id.end_turn).setVisibility(View.INVISIBLE);
      view.findViewById(R.id.discard).setVisibility(View.VISIBLE);
      Snackbar.make(view,"discard down to 3 cards",Snackbar.LENGTH_SHORT).show();
    }
  }

  @Override
  public void onClick(View v) {
    switch (v.getId()) {
      case R.id.play_card:
        playCurrentCard();
        break;
      case R.id.discard_cards:
        discardCards();
        break;
      case R.id.trash_cards:
        trashCard();
        break;
      case R.id.end_action:
        navController.navigate(R.id.doBuysFragment);
        break;
      case R.id.end_turn:
        navController.navigate(R.id.turnSummaryFragment);
        break;
      case R.id.discard:
        gameViewModel.discardCard(gameViewModel.getCardsInHand().getValue().get(viewPager.getCurrentItem()));
        navController.navigate(R.id.action_doActionFragment_to_doActionFragment);
        break;
    }
  }

  private void playCurrentCard() {
    //TODO see if this actually gets the current card shown
    cardIndexToPlay = viewPager.getCurrentItem();
    Card cardToPlay = gameViewModel.getCardsInHand().getValue().get(cardIndexToPlay);
//    if (cardToPlay.getCardType() == CardType.CELLAR)

    if (cardToPlay.getCardType() == CardType.COPPER){ //FIXME remove Copper
      //switch fragment button "Select Checkbox" on
      gameViewModel.setShowSelectCard(true);
      getView().findViewById(R.id.discard_cards).setVisibility(View.VISIBLE);
      getView().findViewById(R.id.play_card).setVisibility(View.INVISIBLE);
      getView().findViewById(R.id.end_action).setVisibility(View.INVISIBLE);
      getView().findViewById(R.id.end_turn).setVisibility(View.INVISIBLE);
      Snackbar.make(getView(), "Choose your cards to discard", Snackbar.LENGTH_SHORT).show();

    } else if (cardToPlay.getCardType() == CardType.MINE ||
        cardToPlay.getCardType() == CardType.DUCHY || //FIXME Remove DUCHY!!!
        cardToPlay.getCardType() == CardType.REMODEL) {
      //switch fragment button "Select Checkbox" on
      gameViewModel.setShowSelectCard(true);
      getView().findViewById(R.id.trash_cards).setVisibility(View.VISIBLE);
      getView().findViewById(R.id.play_card).setVisibility(View.INVISIBLE);
      getView().findViewById(R.id.end_action).setVisibility(View.INVISIBLE);
      getView().findViewById(R.id.end_turn).setVisibility(View.INVISIBLE);
      Snackbar.make(getView(), "Choose a card to Trash", Snackbar.LENGTH_SHORT).show();
    } else {
      gameViewModel.playCard(cardToPlay.getCardName());
    }
  }

  private void discardCards() {
    //TODO get selected cards
    List<String> selectedCards = new ArrayList<>();
    List<Card> cardsInHand = gameViewModel.getCardsInHand().getValue();
    for (int i = 0; i < cardsInHand.size(); i++) {
      if (cardIndexToPlay != i && //do not allow them to discard the card they are playing...
          adapter.isChecked(i)) {
        selectedCards.add(cardsInHand.get(i).getCardName());
      }
    }
    gameViewModel.playCard(cardsInHand.get(cardIndexToPlay).getCardName(), selectedCards);
  }

  private void trashCard() {
    //TODO get selected card
    //TODO get selected cards
    List<String> selectedCards = new ArrayList<>();
    List<Card> cardsInHand = gameViewModel.getCardsInHand().getValue();
    for (int i = 0; i < cardsInHand.size(); i++) {
      if (cardIndexToPlay != i && //do not allow them to discard the card they are playing...
          adapter.isChecked(i)) {
        selectedCards.add(cardsInHand.get(i).getCardName());
      }
    }
    if (selectedCards.size() != 1) {
      Toast.makeText(getContext(), "You may only select one card", Toast.LENGTH_SHORT).show();
      //TODO display YOU MUST SELECT ONLY ONE AND ONLY CARD TO TRASH message
    } else {
      gameViewModel.playCard(cardsInHand.get(cardIndexToPlay).getCardName(), selectedCards);
      //TODO display "Trash Selected Card" Button
      //TODO hide ALL other buttons
    }
  }
}
