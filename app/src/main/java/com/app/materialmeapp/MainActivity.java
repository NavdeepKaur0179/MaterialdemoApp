package com.app.materialmeapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.res.TypedArray;
import android.graphics.Movie;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import java.util.ArrayList;
import java.util.Collections;


/***
 * Main Activity for the Material Me app, a mock sports news application
 * with poor design choices.
 */
public class MainActivity extends AppCompatActivity {

    // Member variables.
    private RecyclerView mRecyclerView;
    private ArrayList<SportNew> mSportsData;
    private SportsAdapter mAdapter;

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelableArrayList("sportsData", mSportsData);
        Log.e("TAG1", "onSaveInstanceState: "+mSportsData );
    }

//    @Override
//    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
//        super.onRestoreInstanceState(savedInstanceState);
//        mSportsData =savedInstanceState.getParcelableArrayList("sportsData");
//        Log.e("TAG1", "onRestoreInstanceState: "+mSportsData);
//
//    }

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//
        // Initialize the RecyclerView.
        mRecyclerView = findViewById(R.id.recyclerView);

        // Set the Layout Manager.
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Initialize the ArrayList that will contain the data.
        mSportsData = new ArrayList<>();

        // Initialize the adapter and set it to the RecyclerView.
        mAdapter = new SportsAdapter(this, mSportsData);
        mRecyclerView.setAdapter(mAdapter);

        // Get the data.
        if (savedInstanceState != null) {
            mSportsData = savedInstanceState.getParcelableArrayList("sportsData");
            Log.e("TAG1", "onCreate: "+mSportsData.toString() );
            mAdapter = new SportsAdapter(this, mSportsData);
            mRecyclerView.setAdapter(mAdapter);
        }else {
            initializeData();
        }
        /*
        *
        //To delete views on swipe left or right dragdir=0
        ItemTouchHelper helper=new ItemTouchHelper((new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT |
                ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }
            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                mSportsData.remove(viewHolder.getAbsoluteAdapterPosition());
                mAdapter.notifyItemRemoved(viewHolder.getAbsoluteAdapterPosition());
            }
        }));
        helper.attachToRecyclerView(mRecyclerView);
         */
        //To delete views on swipe left or right

        //Now Drag directions are also added
        ItemTouchHelper helper = new ItemTouchHelper(new ItemTouchHelper
                .SimpleCallback(ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT |
                ItemTouchHelper.DOWN | ItemTouchHelper.UP, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                int from = viewHolder.getAbsoluteAdapterPosition();
                int to = target.getAbsoluteAdapterPosition();
                //Swap the items in the dataset by calling Collections.swap() and pass in the dataset, and the initial and final indexes:
                Collections.swap(mSportsData, from, to);
                mAdapter.notifyItemMoved(from, to);
                return true;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                mSportsData.remove(viewHolder.getAbsoluteAdapterPosition());
                mAdapter.notifyItemRemoved(viewHolder.getAbsoluteAdapterPosition());
            }
        });
        helper.attachToRecyclerView(mRecyclerView);
    }


    /**
     * Initialize the sports data from resources.
     */
    private void initializeData() {
        // Get the resources from the XML file.
        String[] sportsList = getResources()
                .getStringArray(R.array.sports_titles);
        String[] sportsInfo = getResources()
                .getStringArray(R.array.sports_info);

        // Clear the existing data (to avoid duplication).
        mSportsData.clear();
        // A TypedArray allows you to store an array of other XML resources.
        TypedArray sportsImageResources =
                getResources().obtainTypedArray(R.array.sports_images);
        // Create the ArrayList of Sports objects with titles and
        // information about each sport.
        for (int i = 0; i < sportsList.length; i++) {
            mSportsData.add(new SportNew(sportsList[i], sportsInfo[i], sportsImageResources.getResourceId(i, 0)));
        }
        //Clean up the data in the typed array once you have created the Sport data ArrayList
        sportsImageResources.recycle();

        // Notify the adapter of the change.
        mAdapter.notifyDataSetChanged();
    }

    public void resetSports(View view) {
        initializeData();
    }
}