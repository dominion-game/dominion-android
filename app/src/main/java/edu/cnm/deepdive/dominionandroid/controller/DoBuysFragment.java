package edu.cnm.deepdive.dominionandroid.controller;


import android.graphics.Color;
import android.os.Bundle;
import android.view.View.OnClickListener;
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
import androidx.viewpager.widget.ViewPager.OnPageChangeListener;
import com.google.android.material.snackbar.Snackbar;
import edu.cnm.deepdive.dominionandroid.R;
import edu.cnm.deepdive.dominionandroid.databinding.FragmentDoActionBinding;
import edu.cnm.deepdive.dominionandroid.databinding.FragmentDoBuysBinding;
import edu.cnm.deepdive.dominionandroid.model.Card;
import edu.cnm.deepdive.dominionandroid.model.Card.CardType;
import edu.cnm.deepdive.dominionandroid.model.GameStateInfo;
import edu.cnm.deepdive.dominionandroid.model.PhaseState;
import edu.cnm.deepdive.dominionandroid.viewmodel.GameViewModel;
import io.reactivex.internal.operators.observable.ObservableNever;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;

/**
 * A simple {@link Fragment} subclass.
 */
public class DoBuysFragment extends Fragment implements OnClickListener {

  NavController navController = null;
  private GameViewModel gameViewModel;
  private ViewPager viewPager;
  private String[] resourceNames;

  public DoBuysFragment() {
    // Required empty public constructor
  }

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
      Bundle savedInstanceState) {
    // Inflate the layout for this fragment

    FragmentDoBuysBinding binding;
    binding = DataBindingUtil.inflate(
        inflater, R.layout.fragment_do_buys, container, false);
    binding.setLifecycleOwner(this);
    View view = binding.getRoot();
    gameViewModel = ViewModelProviders.of(getActivity()).get(GameViewModel.class);
    binding.setViewModel(gameViewModel);

    //TODO show number of cards per stack
    //TODO if number of cards for stack is 0, disable buy button, show grayed out
    viewPager = view.findViewById(R.id.view_pager);
//    ViewPagerAdapter adapter= new ViewPagerAdapter(getContext(), stackStrings);
//    viewPager.setAdapter(adapter);

//    final Observer<GameStateInfo> gameStateInfoObserver = new Observer<GameStateInfo>()  {
//      @Override
//      public void onChanged(GameStateInfo gameStateInfo) {
//        //since we watch gameStateInfo and everyone else does too, we only respond if it is our state
//        if (gameStateInfo.debugging || gameStateInfo.getWhatStateAmIIn().equals(PhaseState.ACTING)) {
//          //reset image names to be from gameStateInfo
//          stackStrings = gameStateInfo.getStackStrings();
//
//          ViewPagerAdapter adapter= new ViewPagerAdapter(getContext(), stackStrings, false);
//          viewPager.setAdapter(adapter);
//        }
//      }
//    };
//    gameViewModel.getGameStateInfo().observe(this, gameStateInfoObserver);
    gameViewModel.getStacks().observe(this, (stacks) -> {
      String[] keys = stacks.keySet().stream()
          .sorted(Comparator.comparing(String::toLowerCase))
          .toArray(String[]::new);
      resourceNames = Arrays.stream(keys)
          .map(String::toLowerCase)
          .toArray(String[]::new);
      ViewPagerAdapter adapter = new ViewPagerAdapter(getContext(), resourceNames, false);
      viewPager.setAdapter(adapter);
      viewPager.addOnPageChangeListener(new OnPageChangeListener() {
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        }

        @Override
        public void onPageSelected(int position) {
          binding.numCardsInStack.setText(stacks.get(keys[position]).toString());
        }

        @Override
        public void onPageScrollStateChanged(int state) {
        }
      });
    });

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
    switch (v.getId()) {
      case R.id.buy_card:
        buyCurrentCard();
        break;
      case R.id.end_turn:
        navController.navigate(R.id.action_doActionFragment_to_turnSummaryFragment);
        break;
    }
    navController.navigate(R.id.action_doBuysFragment_to_turnSummaryFragment);
  }

  private void buyCurrentCard() {
    //TODO see if this actually gets the current card shown
    String[] namesOfStacks= {"Cellar", "Copper", "Duchy", "Estate", "Gold", "Market", "Merchant",
        "Militia", "Mine", "Moat", "Province", "Silver", "Smithy", "Trash", "Village", "Workshop"};

    int cardIndexToBuy = viewPager.getCurrentItem();
    String cardToBuy = namesOfStacks[cardIndexToBuy];

    if (Card.CardType.valueOf(cardToBuy.toUpperCase()).cost() > gameViewModel.getMyBuyingPower().getValue()){
      Toast.makeText(getContext(), "You do not have enough Gold to buy this Card", Toast.LENGTH_SHORT)
          .show();
    } else if (Card.CardType.valueOf(cardToBuy.toUpperCase()).cost() <= gameViewModel.getMyBuyingPower().getValue()){
    gameViewModel.buyCard(cardToBuy);
    }
  }
}