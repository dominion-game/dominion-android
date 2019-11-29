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
import androidx.viewpager.widget.ViewPager;
import edu.cnm.deepdive.dominionandroid.R;
import edu.cnm.deepdive.dominionandroid.databinding.FragmentDoActionBinding;
import edu.cnm.deepdive.dominionandroid.databinding.FragmentDoBuysBinding;
import edu.cnm.deepdive.dominionandroid.viewmodel.GameViewModel;
import io.reactivex.internal.operators.observable.ObservableNever;

/**
 * A simple {@link Fragment} subclass.
 */
public class DoBuysFragment extends Fragment implements OnClickListener {

  NavController navController = null;

  private String[] imageUrls = new String[]{
      "https://pure-tundra-13659.herokuapp.com/pics/militia",
      "https://pure-tundra-13659.herokuapp.com/pics/province",
      "https://pure-tundra-13659.herokuapp.com/pics/silver",
      "https://pure-tundra-13659.herokuapp.com/pics/moat",
      "https://pure-tundra-13659.herokuapp.com/pics/workshop"
  };

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
    GameViewModel gameViewModel= ViewModelProviders.of(this).get(GameViewModel.class);
    binding.setViewModel(gameViewModel);

    ViewPager viewPager= view.findViewById(R.id.view_pager);
    ViewPagerAdapter adapter= new ViewPagerAdapter(getContext(), imageUrls);
    viewPager.setAdapter(adapter);

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
    navController.navigate(R.id.action_doBuysFragment_to_turnSummaryFragment);
  }
}
    //TODO implement "Buy Card" button