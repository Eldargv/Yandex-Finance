package com.Test.test_app.Fragments;

import android.animation.ValueAnimator;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import com.Test.test_app.R;
import com.Test.test_app.Adapters.ViewPagerAdapter;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

public class DefaultFragment extends Fragment {

    public DefaultFragment(){
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_page_default, container, false);

        Log.i("TAG", "trying create new viewpager");
        ViewPager2 viewPager = view.findViewById(R.id.viewpager);
        ViewPagerAdapter pagerAdapter = new ViewPagerAdapter(getActivity());

        Log.i("TAG", "new viewpager created");

        Log.i("TAG", "trying to set viewpager adapter");
        viewPager.setAdapter(pagerAdapter);

        Log.i("TAG", "viewpager adapter setted");
        final float constDisp = getResources().getDisplayMetrics().scaledDensity;
        final float startSize = 18 * constDisp;
        final float endSize = 28 * constDisp;
        final long animationDuration = 100;

        TabLayout tabLayout = view.findViewById(R.id.tabs);
        Log.i("TAG", "trying to attach tabs");
        new TabLayoutMediator(tabLayout, viewPager, (tab, position) -> {
            TextView tv = (TextView)LayoutInflater.from(getContext()).inflate(R.layout.activity_tab_text, null);
            if (position == 0) {
                tv.setText(R.string.Stocks);
                tv.setTextSize(28);
                tv.setTextColor(Color.BLACK);
                Log.i("TAG", "first tab attached");
            } else {
                tv.setText(R.string.Favorite);
                tv.setTextSize(18);
                tv.setTextColor(Color.GRAY);
                Log.i("TAG", "second tab attached");
            }
            tab.setCustomView(tv);
        }).attach();

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                TextView tv = tab.getCustomView().findViewById(R.id.text_tab);
                tv.setTextColor(Color.BLACK);

                Log.i("TAG", "Curr size in selected = " + tv.getTextSize());

                ValueAnimator animator = ValueAnimator.ofFloat(startSize, endSize);
                animator.setDuration(animationDuration);

                animator.addUpdateListener(valueAnimator -> {
                    float animatedValue = (float) valueAnimator.getAnimatedValue();
                    tv.setTextSize(TypedValue.COMPLEX_UNIT_PX, animatedValue);
                });

                animator.start();

            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                TextView tv = tab.getCustomView().findViewById(R.id.text_tab);
                tv.setTextColor(Color.GRAY);

                Log.i("TAG", "Curr size in unselected = " + tv.getTextSize());

                ValueAnimator animatorText = ValueAnimator.ofFloat(endSize, startSize);
                animatorText.setDuration(animationDuration);

                animatorText.addUpdateListener(valueAnimator -> {
                    float animatedValue = (float) valueAnimator.getAnimatedValue();
                    tv.setTextSize(TypedValue.COMPLEX_UNIT_PX,animatedValue);
                });

                animatorText.start();
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
            }
        });
        return view;
    }
}
