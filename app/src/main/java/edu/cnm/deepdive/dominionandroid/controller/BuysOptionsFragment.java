package edu.cnm.deepdive.dominionandroid.controller;


import android.os.Bundle;

import android.view.View.OnClickListener;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import edu.cnm.deepdive.dominionandroid.R;

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
    return inflater.inflate(R.layout.fragment_buys_options, container, false);
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
