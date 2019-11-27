package edu.cnm.deepdive.dominionandroid.controller;


import android.os.Bundle;
import android.view.View.OnClickListener;
import android.widget.ImageView;
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
import edu.cnm.deepdive.dominionandroid.viewmodel.GameViewModel;

/**
 * A simple {@link Fragment} subclass.
 */
public class DoActionFragment extends Fragment implements OnClickListener {

  NavController navController = null;
  GameViewModel gameViewModel;

  private String[] imageUrls = new String[]{
      "https://pure-tundra-13659.herokuapp.com/pics/market",
      "https://pure-tundra-13659.herokuapp.com/pics/village",
      "https://pure-tundra-13659.herokuapp.com/pics/estate",
      "https://pure-tundra-13659.herokuapp.com/pics/gold",
      "https://pure-tundra-13659.herokuapp.com/pics/workshop"
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
    binding= DataBindingUtil.inflate(
        inflater,R.layout.fragment_do_action, container,false);
    binding.setLifecycleOwner(this);
    View view = binding.getRoot();
    GameViewModel gameViewModel= ViewModelProviders.of(this).get(GameViewModel.class);
    binding.setViewModel(gameViewModel);

    ViewPager viewPager= view.findViewById(R.id.view_pager);
    ViewPagerAdapter adapter= new ViewPagerAdapter(getContext(), imageUrls);
    viewPager.setAdapter(adapter);

//    Picasso.with(getContext()).load(R.drawable.dominion_logo).into(imageView);
//    Picasso.with(getContext()).load(R.drawable.duchy).into(imageView);
    return view;
  }

  @Override
  public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);
    navController= Navigation.findNavController(view);
    view.findViewById(R.id.play_card).setOnClickListener(this);
    view.findViewById(R.id.end_action).setOnClickListener(this);
    view.findViewById(R.id.end_turn).setOnClickListener(this);
  }

  @Override
  public void onClick(View v) {
    switch (v.getId()){
      case R.id.end_action:
        navController.navigate(R.id.action_doActionFragment_to_buysOptionsFragment);
        break;
      case R.id.end_turn:
        navController.navigate(R.id.action_doActionFragment_to_turnSummaryFragment);
        break;
    }
      //TODO need to implement button functionality for play card
  }

}
