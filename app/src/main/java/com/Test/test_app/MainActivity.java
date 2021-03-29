package com.Test.test_app;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.FragmentActivity;
import androidx.appcompat.widget.SearchView;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;

import com.Test.test_app.Fragments.DefaultFragment;
import com.Test.test_app.Fragments.SearchListFragment;

public class MainActivity extends AppCompatActivity {

    private static App app;
    private static FragmentActivity fragmentActivity;
    private SearchView searchView;
    private Toolbar toolbar;
    private SearchListFragment searchListFragment;
    private DefaultFragment defaultFragment;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.i("TAG", "trying include activity");
        setContentView(R.layout.activity_main);
        Log.i("TAG", "activity started");

        app = (App)getApplication();
        fragmentActivity = this;

        toolbar = findViewById(R.id.toolbar);
        searchView = (SearchView)findViewById(R.id.search_view);
        //searchView.setQueryHint("Find company of ticker");
        searchListFragment = new SearchListFragment();
        defaultFragment = new DefaultFragment();
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment_container, defaultFragment)
                .disallowAddToBackStack()
                .commit();
    }

    @Override
    protected void onResume() {
        super.onResume();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                searchListFragment.newInstance(query);
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
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.fragment_container, searchListFragment)
                        .addToBackStack(null)
                        .commit();
            }
        });
        searchView.setOnCloseListener(new SearchView.OnCloseListener() {
            @Override
            public boolean onClose() {
                Log.i("TAG", "Search closed");
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