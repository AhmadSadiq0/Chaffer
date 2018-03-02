package app.chaffer.Fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import app.chaffer.Messages;
import app.chaffer.R;
import app.chaffer.adapter.MessageListAdapter;

/**
 * Created by Mac on 12/02/2018.
 */

public class FragmentMessagesList extends Fragment {

    ArrayList<Messages> messgaesList=new ArrayList<>() ;

    MessageListAdapter adapter ;
    RecyclerView recyclerView ;



    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {

        View view= inflater.inflate(R.layout.fragment_offer_list,container,false) ;

        recyclerView=(RecyclerView) view.findViewById(R.id.recyclerView_offerList) ;


        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        adapter=new MessageListAdapter(messgaesList) ;
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);

        prepareData();


        return view;

    }

    void prepareData(){
        Messages messages=new Messages("Ahmad","Messages","http://i.imgur.com/DvpvklR.png") ;
        messgaesList.add(messages) ;
        messgaesList.add(messages) ;
        messgaesList.add(messages) ;
    }
}
