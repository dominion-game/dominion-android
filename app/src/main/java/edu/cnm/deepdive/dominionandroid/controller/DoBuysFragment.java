package edu.cnm.deepdive.dominionandroid.controller;


import android.graphics.Color;
import android.os.Bundle;
import android.view.View.OnClickListener;
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
import edu.cnm.deepdive.dominionandroid.R;
import edu.cnm.deepdive.dominionandroid.databinding.FragmentDoActionBinding;
import edu.cnm.deepdive.dominionandroid.databinding.FragmentDoBuysBinding;
import edu.cnm.deepdive.dominionandroid.model.Card;
import edu.cnm.deepdive.dominionandroid.model.GameStateInfo;
import edu.cnm.deepdive.dominionandroid.model.PhaseState;
import edu.cnm.deepdive.dominionandroid.viewmodel.GameViewModel;
import io.reactivex.internal.operators.observable.ObservableNever;

/**
 * A simple {@link Fragment} subclass.
 */
public class DoBuysFragment extends Fragment implements OnClickListener {

  NavController navController = null;
  private GameViewModel gameViewModel;

//  private String[] imageUrls = new String[]{
//      "https://pure-tundra-13659.herokuapp.com/pics/militia",
//      "https://pure-tundra-13659.herokuapp.com/pics/province",
//      "https://pure-tundra-13659.herokuapp.com/pics/silver",
//      "https://pure-tundra-13659.herokuapp.com/pics/moat",
//      "https://pure-tundra-13659.herokuapp.com/pics/workshop"
//  };
  private String[] stackStrings;

  private ViewPager viewPager;
  public DoBuysFragment() {
    // Required empty public constructor
  }

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
      Bundle savedInstanceState) {
    // Inflate the layout for this fragment

    FragmentDoBuysBinding binding;
    binding= DataBindingUtil.inflate(
        inflater,R.layout.fragment_do_buys, container,false);
    binding.setLifecycleOwner(this);
    View view = binding.getRoot();
    gameViewModel = ViewModelProviders.of(getActivity()).get(GameViewModel.class);
    binding.setViewModel(gameViewModel);

    //TODO load all of the stacks into the pager.
    //TODO show number of cards per stack
    //TODO if number of cards for stack is 0, disable buy button, show grayed out
    viewPager = view.findViewById(R.id.view_pager);
//    ViewPagerAdapter adapter= new ViewPagerAdapter(getContext(), stackStrings);
//    viewPager.setAdapter(adapter);

    final Observer<GameStateInfo> gameStateInfoObserver = new Observer<GameStateInfo>()  {
      @Override
      public void onChanged(GameStateInfo gameStateInfo) {
        //since we watch gameStateInfo and everyone else does too, we only respond if it is our state
        if (gameStateInfo.debugging || gameStateInfo.getWhatStateAmIIn().equals(PhaseState.ACTING)) {
          //reset image names to be from gameStateInfo
          stackStrings = gameStateInfo.getStackStrings();

          ViewPagerAdapter adapter= new ViewPagerAdapter(getContext(), stackStrings, false);
          viewPager.setAdapter(adapter);
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
    view.findViewById(R.id.end_turn).setOnClickListener(this);
  }

  @Override
  public void onClick(View v) {
    switch (v.getId()){
      case R.id.buy_card:
        //need to know what you are seeing
        //TODO see if this actually gets the current card shown
        int currentStackIndex = viewPager.getCurrentItem();
//        Card cardToBuy = new Card();
//        cardToBuy.setCost();
            gameViewModel.getStacks().getValue().keySet().to;

        //TODO if card has "extra" cost, need to have player "select" until done.
        //then make a card and play it
//        gameViewModel.buyCard(cardToBuy);
        if (gameViewModel.getWhatStateAmIIn().getValue() != PhaseState.ACTING){
          navController.navigate(R.id.action_doActionFragment_to_doBuysFragment);
          break;
        }
      case R.id.end_turn:
        navController.navigate(R.id.action_doBuysFragment_to_turnSummaryFragment);
        break;
    }
//    actionsText.setBackgroundColor(Color.TRANSPARENT);
    //TODO need to implement button functionality for play card

  }
}
    //TODO implement "Buy Card" button