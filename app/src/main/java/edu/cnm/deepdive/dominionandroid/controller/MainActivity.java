package edu.cnm.deepdive.dominionandroid.controller;

import android.content.Intent;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.appcompat.widget.Toolbar;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;
import androidx.viewpager.widget.ViewPager;
import edu.cnm.deepdive.dominionandroid.R;
import edu.cnm.deepdive.dominionandroid.databinding.ActivityMainBinding;
import edu.cnm.deepdive.dominionandroid.model.GameStateInfo;
import edu.cnm.deepdive.dominionandroid.service.GoogleSignInService;
import edu.cnm.deepdive.dominionandroid.viewmodel.GameViewModel;

public class MainActivity extends AppCompatActivity {

  GameViewModel gameViewModel;
  NavController navController;


  @Override
  public void onBackPressed() {
    //super.onBackPressed();
    Toast.makeText(getApplicationContext(),"THERE'S NO TURNING BACK!!!!!",Toast.LENGTH_LONG).show();
  }

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setupViewModel();
//    setContentView(R.layout.activity_main);
    ActivityMainBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
    Toolbar toolbar = findViewById(R.id.toolbar);
    setSupportActionBar(toolbar);
    navController = NavHostFragment.findNavController(getSupportFragmentManager().findFragmentById(R.id.nav_host_fragment));
    binding.setLifecycleOwner(this);
    binding.setViewModel(gameViewModel);
  }

  @Override
  public boolean onCreateOptionsMenu(Menu menu) {
   getMenuInflater().inflate(R.menu.options, menu);
    return true;
  }

  @Override
  public boolean onOptionsItemSelected(@NonNull MenuItem item) {
    boolean handled = true;
    Intent intent;
    switch (item
        .getItemId()) { //all primitive types except float, long double, wrappers for same times, enums, strings
      case R.id.sign_out:
        signOut();
        break;
//      case R.id.instructions:
//        gameInstructions();
//        break;
//            case R.id.preferences:
//        intent = new Intent(this, SettingsActivity.class);
//        startActivity(intent);
//        break;
      default:
        handled = super.onOptionsItemSelected(item);
    }
    return handled;
  }

//  @Override
//  public void onBackPressed() {
//    Toast.makeText(this, "There's no turning back...", Toast.LENGTH_SHORT).show();
//  }

  private void setupViewModel() {
    gameViewModel = ViewModelProviders.of(this).get(GameViewModel.class);
    getLifecycle().addObserver(gameViewModel);

    // TODO see if this is necessary
//    gameViewModel.getGameStateInfo().observe(this, this::);
    gameViewModel.getWhatStateAmIIn().observe(this, (state) -> {
      switch (state) {
//        case INITIAL:
//          navController.navigate(R.id.action_newGameFragment_to_waiting);
//          break;
        case PLAYER_1_DISCARDING:
          navController.navigate(R.id.doActionFragment);
          break;
        case ACTING:
          navController.navigate(R.id.doActionFragment);
          break;
        case BUYING:
          navController.navigate(R.id.doBuysFragment);
          break;
        case ENDING_TURN:
          navController.navigate(R.id.turnSummaryFragment);
          break;
        default:
          navController.navigate(R.id.doActionFragment);
          break;
      }
    });
    gameViewModel.getMyActionsRemaining().observe(this, (numCards) -> Log.d(getClass().getSimpleName(),
        String.format("Actions remaining = %d", numCards)));
    gameViewModel.getThrowable().observe(this, this::showError);
  }

//  private void refreshGameStateInfo(GameStateInfo gameStateInfo) {
//    PassphraseAdapter adapter = new PassphraseAdapter(this, passphrases, this, this);
//    passphraseList.setAdapter(adapter);
////    waiting.setVisibility(View.GONE);
//  }

  private void showError(Throwable throwable) {
    if (throwable != null) {
//      waiting.setVisibility(View.GONE);
      Toast.makeText(this, "Connection to server failed: {throwable.getMessage()}",
          Toast.LENGTH_LONG).show();
    }
  }


  private void signOut() {
    GoogleSignInService.getInstance().signOut()
        .addOnCompleteListener((task) -> {                           //success or fail
          Intent intent = new Intent(this, LoginActivity.class);
          intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
          startActivity(intent);
        });
  }

//  public void gameInstructions(){
//    Intent intent= new Intent(this, MainActivity.class);
//
//  }
}
