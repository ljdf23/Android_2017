package com.bignerdranch.android.criminalintent;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import com.bignerdranch.android.criminalintent.Model.Crime;
import com.bignerdranch.android.criminalintent.Model.CrimeLab;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by schizophrenia on 7/02/17.
 */

public class CrimeListFragment extends Fragment {

    private static final int REQUEST_CRIME = 1;
    private RecyclerView mCrimeRecyclerView;
    private CrimeAdapter mAdapter;
    private Unbinder unbinder;
    private int position=0;

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        if(resultCode == REQUEST_CRIME) {

        }
        super.onActivityResult(requestCode, resultCode, data);

    }

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

    /**
     * Why override onResume() to update the RecyclerView and not onStart() ? You cannot assume that
     your activity will be stopped when another activity is in front of it. If the other activity is transparent,
     your activity may just be paused. If your activity is paused and your update code is in onStart() ,
     then the list will not be reloaded. In general, onResume() is the safest place to take action to update a
     fragment’s view
     */
    @Override
    public void onResume() {
        super.onResume();
        updateUI();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        //We have to unbind beacuse the lifeciycle of a fragment//
        unbinder.unbind();
    }

    private void updateUI() {
        CrimeLab crimeLab = CrimeLab.getInstance(getActivity());
        List<Crime> crimes = crimeLab.getCrimes();

        if(mAdapter == null) {
            mAdapter = new CrimeAdapter(crimes);
            mCrimeRecyclerView.setAdapter(mAdapter);
        }
        else
        {
            mAdapter.notifyItemChanged(position);
        }
    }

    //The ViewHolder does one thing: it holds on to a View
    protected class CrimeHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        @BindView(R.id.list_item_crime_title_text_view) TextView mTitleTextView;
        @BindView(R.id.list_item_crime_date_text_view) TextView mDateTextView;
        @BindView(R.id.list_item_crime_solved_check_box) CheckBox mSolvedCheckBox;

        //private TextView mTitleTextView;
        //private TextView mDateTextView;
        //private CheckBox mSolvedCheckBox;

        private Crime mCrime;

        //The itemView field is your ViewHolder ’s reason for existing: it holds a reference to the entire View you passed in to super(view) .
        public CrimeHolder(View itemView)
        {
            super(itemView);
            //mTitleTextView = (TextView) itemView;

            /*
            mTitleTextView = (TextView)
                    itemView.findViewById(R.id.list_item_crime_title_text_view);
            mDateTextView = (TextView)
                    itemView.findViewById(R.id.list_item_crime_date_text_view);
            mSolvedCheckBox = (CheckBox)
                    itemView.findViewById(R.id.list_item_crime_solved_check_box);

            */

            //replace the finviewbyid with butterknife

            unbinder = ButterKnife.bind(this, itemView);
            itemView.setOnClickListener(this);
        }

        public void bindCrime(Crime crime)
        {
            mCrime = crime;
            mTitleTextView.setText(mCrime.getTitle());
            mDateTextView.setText(mCrime.getDate().toString());
            mSolvedCheckBox.setChecked(mCrime.isSolved());
        }

        @Override
        public void onClick(View v) {
            Toast.makeText(getActivity(), mCrime.getTitle() + " clicked!", Toast.LENGTH_SHORT).show();
            //animation for changed items
            //mAdapter.notifyItemChanged(6);

            position = this.getLayoutPosition();
            Intent intent = CrimeActivity.newIntent(getActivity(), mCrime.getId());
            startActivityForResult(intent, REQUEST_CRIME);
            //Intent intent = new Intent(getActivity(), CrimeActivity.class);
            //startActivity(intent);
        }
    }

    /*
    * The RecyclerView will communicate with this adapter when a ViewHolder needs to be created or
    * connected with a Crime object. The RecyclerView itself will not know anything about the Crime
    * object, but the Adapter will know all of Crime ’s intimate and personal details
    */

    private class CrimeAdapter extends RecyclerView.Adapter<CrimeHolder> {
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
            View view = layoutInflater.inflate(R.layout.list_item_crime, parent, false);
            return new CrimeHolder(view);
        }

        /*This method will bind a ViewHolder ’s View to my model object*/
        @Override
        public void onBindViewHolder(CrimeHolder holder, int position) {
            Crime crime = mCrimes.get(position);
            //holder.mTitleTextView.setText(crime.getTitle());
            holder.bindCrime(crime);
        }

        @Override
        public int getItemCount() {
            return mCrimes.size();
        }
    }
}
