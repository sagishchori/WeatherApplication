package com.sagishchori.weatherapplication;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.SearchView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProviders;

import com.sagishchori.weatherapplication.databinding.MainActivityBinding;
import com.sagishchori.weatherapplication.interfaces.OnCityClickedListener;
import com.sagishchori.weatherapplication.ui.main.ForecastFragment;
import com.sagishchori.weatherapplication.ui.main.MainFragment;
import com.sagishchori.weatherapplication.ui.main.MainViewModel;

public class MainActivity extends AppCompatActivity implements OnCityClickedListener {

    private MainActivityBinding binding;
    private MainViewModel viewModel;

    public static final String METRIC = "metric";
    public static final String IMPERIAL = "imperial";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = DataBindingUtil.setContentView(this, R.layout.main_activity);

        binding.setLifecycleOwner(this);

        viewModel = ViewModelProviders.of(this).get(MainViewModel.class);
        viewModel.init(this);

        if (savedInstanceState == null) {
            setFragmentToView(MainFragment.TAG, null);
        }
    }

    /**
     * Set the requested fragment to {@link android.view.View}.
     *
     * @param tag Use this to get a reference to fragment when needed
     */
    private void setFragmentToView(String tag, Bundle args) {
        Fragment fragment = getSupportFragmentManager().findFragmentByTag(tag);
        if (fragment == null) {
            if (MainFragment.TAG.equals(tag)) {
                fragment = MainFragment.newInstance(args);
            } else if (ForecastFragment.TAG.equals(tag)) {
                fragment = ForecastFragment.newInstance(args);
            }
        }
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.addToBackStack(null).replace(R.id.container, fragment, tag).commit();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.option_menu, menu);

        MenuItem menuItem = menu.findItem(R.id.action_search);
        SearchView searchView = (SearchView) menuItem.getActionView();

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                viewModel.queryCityDataFromWeb(query);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getItemId() == R.id.celsius) {
            viewModel.setFormat(METRIC);
        } else if (item.getItemId() == R.id.fahrenheit) {
            viewModel.setFormat(IMPERIAL);
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onCityClicked(String cityName) {
        Bundle args = new Bundle();
        args.putString(ForecastFragment.CITY_NAME, cityName);

        setFragmentToView(ForecastFragment.TAG, args);
    }

    @Override
    public void onBackPressed() {
        if (getSupportFragmentManager().getBackStackEntryCount() > 1) {
            getSupportFragmentManager().popBackStack();
            return;
        } else if (getSupportFragmentManager().getBackStackEntryCount() == 1) {
            finish();
        }

        super.onBackPressed();
    }
}
