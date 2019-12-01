package edu.cnm.deepdive.dominionandroid.controller;

import android.content.Context;
import android.content.res.Resources;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;
import com.squareup.picasso.Picasso;
import edu.cnm.deepdive.dominionandroid.R;

public class ViewPagerAdapter extends PagerAdapter {

  private Context context;
  private int[] imageIds;

  public ViewPagerAdapter(Context context, String[] imageNames) {
    this.context = context;
    imageIds = new int[imageNames.length];
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
    ImageView imageView = (ImageView) LayoutInflater.from(context)
        .inflate(R.layout.card_image, container, false);
    container.addView(imageView);
    Picasso.get()
        .load(imageIds[position])
        .fit()
        .into(imageView);
    return imageView;
  }

  @Override
  public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
    container.removeView((View) object);
  }
}