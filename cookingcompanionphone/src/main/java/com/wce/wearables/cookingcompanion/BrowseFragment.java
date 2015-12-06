package com.wce.wearables.cookingcompanion;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SearchView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

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

                Global g = new Global();
                g.AllRecipes = new ArrayList<Recipe>();

                //check that the network is available, otherwise display a toast error message
                ConnectivityManager connMgr = (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
                NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();

                //we have network access
                if(networkInfo != null && networkInfo.isConnected()) {
                    updateListView();

                    //get the listview to add a click listener
                    ListView lv = (ListView) getActivity().findViewById(R.id.listView);

                    lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                            //on a click, swap out the Fragment with a new recipe fragment
                            FragmentManager fragmentManage = getActivity().getSupportFragmentManager();
                            RecipeFragment rFrag = new RecipeFragment();
                            rFrag.setPosition(position);
                            fragmentManage.beginTransaction()
                                .replace(R.id.container, rFrag)
                                .commit();
                        }
                    });


                } else { //we don't have network access - print out an error
                    String text = "Please make sure you are connected to the network";
                    int duration = Toast.LENGTH_SHORT;
                    Context context = getActivity().getApplicationContext();

                    Toast toast = Toast.makeText(context, text, duration);
                    toast.show();
                }

                return false;
            }
        });

        return rootView;
    }


    private void updateListView() {
        //call our API code and then build the ArrayList of recipes
        new Parser((android.support.v7.app.AppCompatActivity) getActivity()).execute(searchParams);
    }
}
