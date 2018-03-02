package app.chaffer.Fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import app.chaffer.R;

/**
 * Created by Mac on 16/02/2018.
 */

public class FragmentOfferDetails extends Fragment {
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_offer_details,container,false) ;

        return view ;
    }
}
