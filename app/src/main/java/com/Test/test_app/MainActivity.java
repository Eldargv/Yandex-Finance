package com.Test.test_app;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.lifecycle.ViewModelProvider;

import android.content.Context;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.Test.test_app.Data.StockViewModel;
import com.Test.test_app.Fragments.DefaultFragment;
import com.Test.test_app.Fragments.SearchFragment;
import com.google.android.material.snackbar.Snackbar;

import java.net.InetAddress;

public class MainActivity extends AppCompatActivity {

    private SearchView searchView;
    private DefaultFragment defaultFragment;
    private StockViewModel model;
    private TextView hintText;
    private static boolean networkState;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.i("TAG", "trying include activity");
        setContentView(R.layout.activity_main);
        Log.i("TAG", "activity started");

        networkState = isNetworkConnected() && internetIsConnected();

        model = new ViewModelProvider(this).get(StockViewModel.class);
        searchView = findViewById(R.id.search_view);
        hintText = findViewById(R.id.hint_text);
        defaultFragment = new DefaultFragment();

        searchView.setOnClickListener(v -> {
            searchView.setIconified(false);
            hintText.setVisibility(View.INVISIBLE);
        });

        if (!networkState) {
            Snackbar.make(findViewById(R.id.coordinator), R.string.snack,
                    Snackbar.LENGTH_INDEFINITE)
                    .show();
        }

        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment_container, defaultFragment)
                .disallowAddToBackStack()
                .commit();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        searchView.setQuery("", false);
        searchView.setIconified(true);
        hintText.setVisibility(View.VISIBLE);
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
            hintText.setVisibility(View.INVISIBLE);
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragment_container, SearchFragment.newInstance(""))
                    .addToBackStack(null)
                    .commit();
        });
        searchView.setOnCloseListener(() -> {
            Log.i("TAG", "Search closed");
            model.ClearSearchList();
            hintText.setVisibility(View.VISIBLE);
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragment_container, defaultFragment)
                    .disallowAddToBackStack()
                    .commit();
            return false;
        });
    }

    public static boolean isNetworkState() {
        return networkState;
    }

    public boolean isNetworkConnected() {
        ConnectivityManager cm = (ConnectivityManager) getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        return cm.getActiveNetworkInfo() != null;
    }

    public boolean internetIsConnected() {
        try {
            String command = "ping -c 1 google.com";
            return (Runtime.getRuntime().exec(command).waitFor() == 0);
        } catch (Exception e) {
            return false;
        }
    }

}