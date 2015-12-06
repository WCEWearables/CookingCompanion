package com.wce.wearables.cookingcompanion;


import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;


public class RecipeFragment extends Fragment {

    private int position = -1;



    public void setPosition(int position) {
        this.position = position;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.recipe_layout, container, false);

        HtmlParse htmlParse = new HtmlParse((AppCompatActivity) getActivity());

        //check that the network is available, otherwise display a toast error message
        ConnectivityManager connMgr = (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();

        //we have network access
        if(networkInfo != null && networkInfo.isConnected()) {
            //fetch data from webpage
            new HtmlParse((android.support.v7.app.AppCompatActivity) getActivity()).execute(Global.AllRecipes.get(position));

        } else {
            //toast a message saying no network access
            String text = "Please make sure you are connected to the network";
            int duration = Toast.LENGTH_SHORT;
            Context context = getActivity().getApplicationContext();

            Toast toast = Toast.makeText(context, text, duration);
            toast.show();
        }

            return rootView;
    }
}
