package com.Test.test_app;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.Test.test_app.Adapters.StocksAdapter;
import com.Test.test_app.Api.ApiHolder;
import com.Test.test_app.Api.pojoModels.CompanyProfile;
import com.Test.test_app.Api.pojoModels.QuoteModel;
import com.Test.test_app.Api.pojoModels.SearchList;
import com.Test.test_app.Api.pojoModels.SearchResultList;
import com.Test.test_app.Data.StockVIewModel;
import com.Test.test_app.Fragments.DefaultFragment;
import com.Test.test_app.Fragments.SearchFragment;
import com.Test.test_app.Fragments.StocksFragment;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    private SearchView searchView;
    private SearchFragment searchFragment = null;
    private DefaultFragment defaultFragment;
    private String TAG = "TAG";
    private ApiHolder apiHolder;
    StockVIewModel model;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.i("TAG", "trying include activity");
        setContentView(R.layout.activity_main);
        Log.i("TAG", "activity started");

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
//                Bundle args = new Bundle();
//                args.putString("argQuery", query);
//                searchFragment.setArguments(args);
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
                    .replace(R.id.fragment_container, searchFragment)
                    .addToBackStack(null)
                    .commit();
        });
        searchView.setOnCloseListener(() -> {
            Log.i("TAG", "Search closed");
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragment_container, defaultFragment)
                    .disallowAddToBackStack()
                    .commit();
            return false;
        });

    }

}