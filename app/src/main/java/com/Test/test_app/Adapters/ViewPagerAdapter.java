package com.Test.test_app.Adapters;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.Test.test_app.Fragments.FavoriteFragment;
import com.Test.test_app.Fragments.StocksFragment;

public class ViewPagerAdapter extends FragmentStateAdapter {

    private static final StocksFragment stocksFragment = new StocksFragment();
    private static final FavoriteFragment favoriteFragment = new FavoriteFragment();

    public ViewPagerAdapter(FragmentActivity fm) {
        super(fm);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        if (position == 0) {
            Log.i("TAG", "stocks fragment created");
            return stocksFragment;
        } else {
            Log.i("TAG", "favorite fragment created");
            return favoriteFragment;
        }
    }

    @Override
    public int getItemCount() {
        return 2;
    }
}
