package edu.cnm.deepdive.dominionandroid.controller;

import android.content.Context;
import android.content.res.Resources;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.ImageView;
import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;
import com.squareup.picasso.Picasso;
import edu.cnm.deepdive.dominionandroid.R;

public class ViewPagerAdapter extends PagerAdapter {

  private Context context;
  private int[] imageIds;
  private final boolean discarding;
  private boolean[] selected;

  public ViewPagerAdapter(Context context, String[] imageNames) { // , boolean discarding) {
    this.context = context;
    this.discarding = true; //discarding;
    imageIds = new int[imageNames.length];
    selected= new boolean[imageNames.length];
    Resources res = context.getResources();
    String pkg = context.getPackageName();
    for (int i = 0; i < imageNames.length; i++) {
      imageIds[i] = res.getIdentifier(imageNames[i], "drawable", pkg);
    }
  }

  @Override
  public int getCount() {
    return imageIds.length;
  }

  @Override
  public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
    return view == object;
  }


  @NonNull
  @Override
  public Object instantiateItem(@NonNull ViewGroup container, int position) {
    View layout = LayoutInflater.from(context)
        .inflate(R.layout.card_image, container, false);
    ImageView imageView = layout.findViewById(R.id.card_image);
    CheckBox discardSelection = layout.findViewById(R.id.discard_selection);
    discardSelection.setChecked(selected[position]);
    discardSelection.setOnCheckedChangeListener(
        (buttonView, isChecked) -> selected[position] = isChecked);
    discardSelection.setVisibility(discarding ? View.VISIBLE : View.GONE);
    container.addView(layout);
//    imageView.setImageResource(imageIds[position]);
    Picasso.get()
        .load(imageIds[position])
        .fit()
        .into(imageView);
    return layout;
  }

  @Override
  public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
    container.removeView((View) object);
  }

  public boolean isChecked(int position) {
    return selected[position];
  }

}