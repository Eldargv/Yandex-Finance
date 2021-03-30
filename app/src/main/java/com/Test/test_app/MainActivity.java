package com.Test.test_app;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.FragmentActivity;
import androidx.appcompat.widget.SearchView;

import android.app.FragmentManager;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;

import com.Test.test_app.Fragments.DefaultFragment;
import com.Test.test_app.Fragments.SearchFragment;

public class MainActivity extends AppCompatActivity {

    private static App app;
    private static FragmentActivity fragmentActivity;
    private SearchView searchView;
    private Toolbar toolbar;
    private SearchFragment searchFragment = null;
    private DefaultFragment defaultFragment;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.i("TAG", "trying include activity");
        setContentView(R.layout.activity_main);
        Log.i("TAG", "activity started");

        app = (App)getApplication();
        fragmentActivity = this;

        //toolbar = findViewById(R.id.toolbar);
        searchView = (SearchView)findViewById(R.id.search_view);
        searchView.setQueryHint("Find company of ticker");
        defaultFragment = new DefaultFragment();
        searchFragment = SearchFragment.newInstance("");
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment_container, defaultFragment)
                .disallowAddToBackStack()
                .commit();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Log.i("TAG", "Back button pressed");
        searchFragment = SearchFragment.newInstance("");
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment_container, defaultFragment)
                .commit();
        /*searchFragment.onDestroy();/*
        getSupportFragmentManager().beginTransaction()
                .remove(searchFragment)
                .commit();*/
    }

    @Override
    protected void onResume() {
        super.onResume();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                if (query == null || query.isEmpty()) {
                    return false;
                }
                Log.i("TAG", "Query getted");
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.fragment_container, SearchFragment.newInstance(query))
                        .commit();
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
        searchView.setOnSearchClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i("TAG", "Search clicked");
                if (searchFragment == null) {
                    searchFragment = new SearchFragment();
                }
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.fragment_container, searchFragment)
                        .addToBackStack(null)
                        .commit();
            }
        });
        searchView.setOnCloseListener(new SearchView.OnCloseListener() {
            @Override
            public boolean onClose() {
                Log.i("TAG", "Search closed");
                searchFragment = SearchFragment.newInstance("");
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.fragment_container, defaultFragment)
                        .addToBackStack(null)
                        .commit();
                return false;
            }
        });

    }

    public static float spToPx(float sp, Context context) {
        return (float) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, sp, context.getResources().getDisplayMetrics());
    }

    public static App getApp() {
        return app;
    }

    public static FragmentActivity getFragmentAtivity() {
        return fragmentActivity;
    }


}