package com.sagishchori.weatherapplication.ui.main;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.sagishchori.weatherapplication.adapters.CityAdapter;
import com.sagishchori.weatherapplication.R;
import com.sagishchori.weatherapplication.databinding.MainFragmentBinding;
import com.sagishchori.weatherapplication.interfaces.OnCityClickedListener;
import com.sagishchori.weatherapplication.interfaces.OnDataLoadedInterface;
import com.sagishchori.weatherapplication.models.current.CityWeatherItem;

import java.util.ArrayList;
import java.util.List;

public class MainFragment extends Fragment implements OnDataLoadedInterface {

    public static final String TAG = MainFragment.class.getCanonicalName();

    private MainViewModel viewModel;
    private MainFragmentBinding binding;
    private CityAdapter adapter;


    public static MainFragment newInstance(Bundle args) {
        MainFragment fragment = new MainFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        binding = DataBindingUtil.inflate(inflater, R.layout.main_fragment, container, false);
        return binding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        viewModel = ViewModelProviders.of(getActivity()).get(MainViewModel.class);
        viewModel.setOnDataLoadedInterface(this);
        viewModel.initCityList().observe(getActivity(), new Observer<List<CityWeatherItem>>() {
            @Override
            public void onChanged(List<CityWeatherItem> cityWeatherItems) {
                if (adapter != null) {
                    adapter.setData(cityWeatherItems);
                }
            }
        });

        if (adapter == null) {
            adapter = new CityAdapter(new ArrayList<>(), (OnCityClickedListener) getActivity());
        }

        LinearLayoutManager manager = new LinearLayoutManager(getActivity());
        manager.setOrientation(RecyclerView.VERTICAL);

        binding.recyclerView.setLayoutManager(manager);
        binding.recyclerView.setAdapter(adapter);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

    }

    @Override
    public void onDataLoaded() {

    }

    @Override
    public void onFailedToLoadData() {
        new AlertDialog.Builder(getActivity())
                .setCancelable(true)
                .setMessage(getString(R.string.city_not_found_error))
                .setTitle(getString(R.string.no_results_found))
                .show();
    }

    @Override
    public void onGeneralError() {
        new AlertDialog.Builder(getActivity())
                .setCancelable(true)
                .setMessage(getString(R.string.general_error_message))
                .setTitle(getString(R.string.general_error_title))
                .show();
    }
}
