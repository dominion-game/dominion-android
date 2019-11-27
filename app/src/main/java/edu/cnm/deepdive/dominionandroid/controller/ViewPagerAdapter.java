package edu.cnm.deepdive.dominionandroid.controller;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.PagerAdapter;
import com.squareup.picasso.Picasso;
import edu.cnm.deepdive.dominionandroid.R;
import java.util.Random;

public class ViewPagerAdapter extends PagerAdapter {

  private Context context;
  private String[] imageUrls;

  public ViewPagerAdapter(Context context, String[] imageUrls) {
    this.context = context;
    this.imageUrls = imageUrls;
  }

  @Override
  public int getCount() {
    return imageUrls.length;
  }

  @Override
  public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
    return view == object;
  }


  @NonNull
  @Override
  public Object instantiateItem(@NonNull ViewGroup container, int position) {
    ImageView imageView= (ImageView) LayoutInflater.from(context).inflate(R.layout.card_image, container, false);
    container.addView(imageView);
    Picasso.get()
        .load(imageUrls[position])
        .fit()
        .into(imageView);
    return imageView;
  }

  @Override
  public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
    container.removeView((View) object);
  }
}