package com.Test.test_app;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;
import android.util.Log;

import com.Test.test_app.Data.StockViewModel;
import com.Test.test_app.Fragments.DefaultFragment;
import com.Test.test_app.Fragments.SearchFragment;

public class MainActivity extends AppCompatActivity {

    private SearchView searchView;
    private DefaultFragment defaultFragment;
    private StockViewModel model;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.i("TAG", "trying include activity");
        setContentView(R.layout.activity_main);
        Log.i("TAG", "activity started");

        model = new ViewModelProvider(this).get(StockViewModel.class);
        searchView = findViewById(R.id.search_view);
        searchView.setQueryHint("Find company of ticker");
        defaultFragment = new DefaultFragment();

        searchView.setOnClickListener(v -> searchView.setIconified(false));

        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment_container, defaultFragment)
                .disallowAddToBackStack()
                .commit();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        searchView.setIconified(true);
        Log.i("TAG", "Back button pressed");
        model.ClearSearchList();
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment_container, defaultFragment)
                .commit();
    }

    @Override
    protected void onResume() {
        super.onResume();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
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
        searchView.setOnSearchClickListener(v -> {
            Log.i("TAG", "Search clicked");
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragment_container, SearchFragment.newInstance(""))
                    .addToBackStack(null)
                    .commit();
        });
        searchView.setOnCloseListener(() -> {
            Log.i("TAG", "Search closed");
            model.ClearSearchList();
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragment_container, defaultFragment)
                    .disallowAddToBackStack()
                    .commit();
            return false;
        });

    }

}