package com.wce.wearables.cookingcompanion;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by Chloe on 11/8/2015.
 */
public class FavoritesFragment extends Fragment{

    public static FavoritesFragment newInstance() {
        FavoritesFragment fragment = new FavoritesFragment();
        return fragment;
    }

    public FavoritesFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.activity_favorites, container, false);
        android.app.ActionBar actionBar = getActivity().getActionBar();

        if(actionBar == null) {
            Log.d("FavoritesFrag", "HERE");
        } else {

            actionBar.setTitle("Favorites");
            actionBar.setDisplayShowTitleEnabled(true);
        }

        return rootView;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        ((MainActivity) activity).onSectionAttached(1);
    }
}
