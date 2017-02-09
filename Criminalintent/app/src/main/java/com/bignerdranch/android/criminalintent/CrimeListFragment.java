package com.bignerdranch.android.criminalintent;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.TextView;

import com.bignerdranch.android.criminalintent.Model.Crime;
import com.bignerdranch.android.criminalintent.Model.CrimeLab;

import java.util.List;

/**
 * Created by schizophrenia on 7/02/17.
 */

public class CrimeListFragment extends Fragment {

    private RecyclerView mCrimeRecyclerView;
    private CrimeAdapter mAdapter;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.fragment_crime_list, container, false);
        mCrimeRecyclerView  = (RecyclerView) view.findViewById(R.id.crime_recycler_view);

        //the LayoutManager handles the positioning of items and also defines the scrolling behavior.

        //we will use the LinearLayoutManager , which will position the items in the list vertically
        mCrimeRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        updateUI();

        return view;
        //return super.onCreateView(inflater, container, savedInstanceState);
    }

    private void updateUI() {
        CrimeLab crimeLab = CrimeLab.getInstance(getActivity());
        List<Crime> crimes = crimeLab.getCrimes();

        mAdapter = new CrimeAdapter(crimes);
        mCrimeRecyclerView.setAdapter(mAdapter);
    }

    //The ViewHolder does one thing: it holds on to a View
    private class CrimeHolder extends RecyclerView.ViewHolder{
        public TextView mTitleTextView;

        //The itemView field is your ViewHolder ’s reason for existing: it holds a reference to the entire View you passed in to super(view) .
        public CrimeHolder(View itemView)
        {
            super(itemView);
            mTitleTextView = (TextView) itemView;
        }
    }

    /*
    * The RecyclerView will communicate with this adapter when a ViewHolder needs to be created or
    * connected with a Crime object. The RecyclerView itself will not know anything about the Crime
    * object, but the Adapter will know all of Crime ’s intimate and personal details
    */

    private class CrimeAdapter extends RecyclerView.Adapter<CrimeHolder>
    {
        private List<Crime> mCrimes;
        public CrimeAdapter(List<Crime> crimes)
        {
            mCrimes = crimes;
        }

        /*
        * is called by the RecyclerView when it needs a new View to display an item. In
        * this method, you create the View and wrap it in a ViewHolder .
        */
        @Override
        public CrimeHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater = LayoutInflater.from(getActivity());
            View view = layoutInflater.inflate(android.R.layout.simple_expandable_list_item_1, parent, false);
            return new CrimeHolder(view);
        }

        /*This method will bind a ViewHolder ’s View to my model object*/
        @Override
        public void onBindViewHolder(CrimeHolder holder, int position) {
            Crime crime = mCrimes.get(position);
            holder.mTitleTextView.setText(crime.getTitle());
        }

        @Override
        public int getItemCount() {
            return mCrimes.size();
        }
    }
}
