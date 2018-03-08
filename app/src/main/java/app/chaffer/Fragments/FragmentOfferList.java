package app.chaffer.Fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import app.chaffer.LoginActivity;
import app.chaffer.R;

/**
 * Created by Mac on 08/03/2018.
 */

public class FragmentOfferList extends Fragment {

    private String url= LoginActivity.IP + "/users/my";

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_offers_list,container,false) ;






        return view ;

    }






}
