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
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.viewpager.widget.ViewPager;
import edu.cnm.deepdive.dominionandroid.R;
import edu.cnm.deepdive.dominionandroid.databinding.FragmentDoActionBinding;
import edu.cnm.deepdive.dominionandroid.model.Card;
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

  private String[] imageNames = new String[]{
      "market",
      "village",
      "estate",
      "gold",
      "workshop"
  };

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
        inflater,R.layout.fragment_do_action, container,false);
    binding.setLifecycleOwner(this);
    View view = binding.getRoot();
    gameViewModel= ViewModelProviders.of(getActivity()).get(GameViewModel.class);
    binding.setViewModel(gameViewModel);

    viewPager= binding.viewPager;
    ViewPagerAdapter adapter= new ViewPagerAdapter(getContext(), imageNames);
    viewPager.setAdapter(adapter);

    return view;
  }

  void loadCards(){
    //TODO need to load cards based on players hand...
    for (int i = 0; i < gameViewModel.getCardsInHand().getValue().size(); i++){

    }
  }

  @Override
  public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);
    navController= Navigation.findNavController(view);
    view.findViewById(R.id.play_card).setOnClickListener(this);
    view.findViewById(R.id.end_action).setOnClickListener(this);
    view.findViewById(R.id.end_turn).setOnClickListener(this);
    actionsText = view.findViewById(R.id.actions);
  }

  @Override
  public void onClick(View v) {
    switch (v.getId()){
      case R.id.play_card:
        //need to know what you are seeing
        //TODO see if this actually gets the current card shown
        int currentCardIndex = viewPager.getCurrentItem();
        Card cardToPlay = gameViewModel.getCardsInHand().getValue().get(currentCardIndex);

        //TODO if card has "extra" cost, need to have player "select" until done.
        //then make a card and play it
        gameViewModel.playCard(cardToPlay);
        if (gameViewModel.getWhatStateAmIIn().getValue() != PhaseState.ACTING){
          navController.navigate(R.id.action_doActionFragment_to_doBuysFragment);
          break;
        }
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

}
