package edu.cnm.deepdive.dominionandroid.controller;


import android.os.Bundle;

import android.view.View.OnClickListener;
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
import edu.cnm.deepdive.dominionandroid.databinding.FragmentDoActionBinding;
import edu.cnm.deepdive.dominionandroid.viewmodel.GameViewModel;

/**
 * A simple {@link Fragment} subclass.
 */
public class BuysOptionsFragment extends Fragment implements OnClickListener {

  NavController navController = null;

  public BuysOptionsFragment() {
    // Required empty public constructor
  }


  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
      Bundle savedInstanceState) {
    // Inflate the layout for this fragment

    FragmentBuysOptionsBinding binding;
    binding= DataBindingUtil.inflate(
        inflater,R.layout.fragment_buys_options, container,false);
    binding.setLifecycleOwner(this);
    View view = binding.getRoot();
    GameViewModel gameViewModel= ViewModelProviders.of(this).get(GameViewModel.class);
    binding.setViewModel(gameViewModel);

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
