package com.example.android.earthquake;


import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;

import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;

import android.support.v4.app.LoaderManager;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;



import com.example.android.earthquake.dummy.Earthquake;
import com.example.android.earthquake.dummy.EarthquakeLoader;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;



/**
 * An activity representing a list of Earthquakes. This activity
 * has different presentations for handset and tablet-size devices. On
 * handsets, the activity presents a list of items, which when touched,
 * lead to a {@link EartthquakeDetailActivity} representing
 * item details. On tablets, the activity presents the list of items and
 * item details side-by-side using two vertical panes.
 */
public class EartthquakeListActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<List<Earthquake>> {

    public String LOG_TAG = EartthquakeListActivity.class.getName();
    private static final String LOCATION_SEPARATOR = " of ";
    /** URL for earthquake data from the USGS dataset */
    private static final String USGS_REQUEST_URL =
            "https://earthquake.usgs.gov/fdsnws/event/1/query?format=geojson&starttime=2014-01-01&endtime=2014-01-02&limit=15";
    /**
     * Constant value for the earthquake loader ID. We can choose any integer.
     * This really only comes into play if you're using multiple loaders.
     */
    private static final int EARTHQUAKE_LOADER_ID = 1;
    /** TextView that is displayed when the list is empty */
    private TextView mEmptyStateTextView;
    private List<Earthquake> mEarthquake;
    View recyclerView;
    private String primaryloc,offsetloc;

    /**
     * Whether or not the activity is in two-pane mode, i.e. running on a tablet
     * device.
     */
    private boolean mTwoPane;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_eartthquake_list);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitle(getTitle());

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        LoaderManager loaderManager = getSupportLoaderManager();
        loaderManager.initLoader(EARTHQUAKE_LOADER_ID, null, this);

         recyclerView = findViewById(R.id.eartthquake_list);
        assert recyclerView != null;
       // setupRecyclerView((RecyclerView) recyclerView,mEarthquake  );

        if (findViewById(R.id.eartthquake_detail_container) != null) {
            // The detail container view will be present only in the
            // large-screen layouts (res/values-w900dp).
            // If this view is present, then the
            // activity should be in two-pane mode.
            mTwoPane = true;
        }
    }

    private void setupRecyclerView( RecyclerView recyclerView, List<Earthquake> eq) {

        recyclerView.setAdapter(new SimpleItemRecyclerViewAdapter(eq));
    }

    @Override
    public Loader<List<Earthquake>> onCreateLoader(int id, Bundle args) {
        return new EarthquakeLoader(this, USGS_REQUEST_URL);
    }

    @Override
    public void onLoadFinished(Loader<List<Earthquake>> loader, List<Earthquake> data) {
        ProgressDialog progress = new ProgressDialog(this);
        progress.setMessage("Downloading Music :) ");
        progress.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        progress.setIndeterminate(true);
        setupRecyclerView((RecyclerView) recyclerView,data  );
        progress.dismiss();

    }

    @Override
    public void onLoaderReset(Loader<List<Earthquake>> loader) {

    }


    public class SimpleItemRecyclerViewAdapter
            extends RecyclerView.Adapter<SimpleItemRecyclerViewAdapter.ViewHolder> {


        private final List<Earthquake> mValues;

        public SimpleItemRecyclerViewAdapter(List<Earthquake> data) {
            mValues = data;

        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.eartthquake_list_content, parent, false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(final ViewHolder holder, int position) {
            holder.mItem = mValues.get(position);
            holder.mMagnitude.setText(getFormattedMagnitude(mValues.get(position).getMagnitude()));
            String original = mValues.get(position).getLocation();
            GradientDrawable magnitudeCircle = (GradientDrawable) holder.mMagnitude.getBackground();
            // Get the appropriate background color based on the current earthquake magnitude
            int magnitudeColor = getMagnitudeColor(getFormattedMagnitude(mValues.get(position).getMagnitude()));
            // Set the color on the magnitude circle
            magnitudeCircle.setColor(magnitudeColor);
            if (original.contains(LOCATION_SEPARATOR))
            { String[] parts = original.split(LOCATION_SEPARATOR);
                // Location offset should be "5km N " + " of " --> "5km N of"
                offsetloc = parts[0] + LOCATION_SEPARATOR;
                primaryloc = parts[1];
            }
            else
            {    primaryloc = original; offsetloc= "near ";
            }
            holder.mPrimary_location.setText(primaryloc);
            holder.mlocationOffset.setText(offsetloc);
            // Create a new Date object from the time in milliseconds of the earthquake
            Date dateObject = new Date(mValues.get(position).getTimeInMilliseconds());
            holder.mDate.setText(getFormattedDate(dateObject));
            holder.mTime.setText(getFormattedTime(dateObject));

            holder.mView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mTwoPane) {
                        Bundle arguments = new Bundle();
                        arguments.putString(EartthquakeDetailFragment.ARG_ITEM_TITLE, holder.mItem.getLocation());
                        arguments.putString(EartthquakeDetailFragment.ARG_ITEM_URL, holder.mItem.getUrl());
                        EartthquakeDetailFragment fragment = new EartthquakeDetailFragment();
                        fragment.setArguments(arguments);
                        getSupportFragmentManager().beginTransaction()
                                .replace(R.id.eartthquake_detail_container, fragment)
                                .commit();
                    } else {
                        Context context = v.getContext();
                        Intent intent = new Intent(context, EartthquakeDetailActivity.class);
                        intent.putExtra(EartthquakeDetailFragment.ARG_ITEM_TITLE, holder.mItem.getLocation());
                        intent.putExtra(EartthquakeDetailFragment.ARG_ITEM_URL, holder.mItem.getUrl());


                        context.startActivity(intent);
                    }
                }
            });
        }

        private int getMagnitudeColor(String formattedMagnitude) {
            int magnitudeColorResourceId;
            int magnitudeFloor = (int) Math.floor(Double.parseDouble(formattedMagnitude));
            switch (magnitudeFloor)
            {
                case 0:
                case 1:
                    magnitudeColorResourceId = R.color.magnitude1;
                    break;
                case 2:
                    magnitudeColorResourceId = R.color.magnitude2;
                    break;
                case 3:
                    magnitudeColorResourceId = R.color.magnitude3;
                    break;
                case 4:
                    magnitudeColorResourceId = R.color.magnitude4;
                    break;
                case 5:
                    magnitudeColorResourceId = R.color.magnitude5;
                    break;
                case 6:
                    magnitudeColorResourceId = R.color.magnitude6;
                    break;
                case 7:
                    magnitudeColorResourceId = R.color.magnitude7;
                    break;
                case 8:
                    magnitudeColorResourceId = R.color.magnitude8;
                    break;
                case 9:
                    magnitudeColorResourceId = R.color.magnitude9;
                    break;
                default:
                    magnitudeColorResourceId = R.color.magnitude10plus;
                    break;

            }
            return ContextCompat.getColor(recyclerView.getContext(), magnitudeColorResourceId);
        }

        private String getFormattedTime(Date dateObject) {
            SimpleDateFormat timeFormat = new SimpleDateFormat("h:mm a");
            return timeFormat.format(dateObject);
        }

        private String getFormattedDate(Date dateObject) {
            SimpleDateFormat dateFormat = new SimpleDateFormat("LLL dd, yyyy");
            return dateFormat.format(dateObject);
        }

        private String getFormattedMagnitude(double magnitude) {
            DecimalFormat magnitudeFormat = new DecimalFormat("0.0");
            return  magnitudeFormat.format(magnitude);
        }

        @Override
        public int getItemCount() {
            return mValues.size();
        }

        public class ViewHolder extends RecyclerView.ViewHolder {
            public final View mView;
            public final TextView mMagnitude;
            public final TextView mPrimary_location;
            public final TextView mlocationOffset;
            public final TextView mDate;
            public final TextView mTime;
            public Earthquake mItem;

            public ViewHolder(View view) {
                super(view);
                mView = view;
                mMagnitude = (TextView) view.findViewById(R.id.magnitude);
                mPrimary_location = (TextView) view.findViewById(R.id.primary_location);
                mDate = (TextView) view.findViewById(R.id.date);
                mTime = (TextView) view.findViewById(R.id.timeInMilliseconds);
                mlocationOffset = (TextView) view.findViewById(R.id.secondary_location);
            }

            @Override
            public String toString() {
                return super.toString() + " '" + mPrimary_location.getText() + "'";
            }
        }
    }
}
