package edu.cnm.deepdive.dominionandroid.controller;


import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import edu.cnm.deepdive.dominionandroid.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class DoBuysFragment extends Fragment {


  public DoBuysFragment() {
    // Required empty public constructor
  }


  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
      Bundle savedInstanceState) {
    // Inflate the layout for this fragment
    return inflater.inflate(R.layout.fragment_do_buys, container, false);
  }

}
