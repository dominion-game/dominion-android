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
import io.reactivex.internal.operators.observable.ObservableNever;

/**
 * A simple {@link Fragment} subclass.
 */
public class DoBuysFragment extends Fragment implements OnClickListener {

  NavController navController = null;

  public DoBuysFragment() {
    // Required empty public constructor
  }


  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
      Bundle savedInstanceState) {
    // Inflate the layout for this fragment
    return inflater.inflate(R.layout.fragment_do_buys, container, false);
  }

  @Override
  public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);
    navController = Navigation.findNavController(view);
    view.findViewById(R.id.end_turn).setOnClickListener(this);
  }

  @Override
  public void onClick(View v) {
    navController.navigate(R.id.action_doBuysFragment_to_turnSummaryFragment);
  }
}
    //TODO implement "Buy Card" button