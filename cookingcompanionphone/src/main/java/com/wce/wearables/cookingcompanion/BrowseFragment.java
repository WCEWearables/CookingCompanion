package com.wce.wearables.cookingcompanion;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SearchView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;


public class BrowseFragment extends Fragment{

    private SearchView search;
    private String searchParams;

    public static BrowseFragment newInstance() {

        return new BrowseFragment();
    }

    public BrowseFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.activity_browse, container, false);

        //search functionality
        search = (SearchView) rootView.findViewById(R.id.searchView);
        search.setQueryHint("Start typing to search ...");

        search.setOnQueryTextListener(new SearchView.OnQueryTextListener() {

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }

            @Override
            //handles the query/text input
            public boolean onQueryTextSubmit(String query) {
                searchParams = query;
                updateListView();
                return false;
            }
        });

        return rootView;
    }


    private void updateListView() {
        //call our API code and then build the ArrayList of recipes
        ArrayList<Recipe> rec = new Parser(searchParams).init();
        ArrayList<String> titles = new ArrayList<>();

        for(int i = 0; i < rec.size(); i++) {
            titles.add(rec.get(i).getTitle());
        }

        ArrayAdapter adapter = new ArrayAdapter(getActivity(), R.layout.activity_browse, R.id.listView, titles);
        ListView lv = (ListView) getActivity().findViewById(R.id.listView);

        lv.setAdapter(adapter);

    }



}
