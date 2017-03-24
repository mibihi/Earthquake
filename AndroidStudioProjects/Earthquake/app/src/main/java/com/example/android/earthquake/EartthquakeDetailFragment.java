package com.example.android.earthquake;

import android.app.Activity;
import android.support.design.widget.CollapsingToolbarLayout;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.TextView;
import com.example.android.earthquake.dummy.DummyContent;
import android.webkit.WebView;

/**
 * A fragment representing a single Eartthquake detail screen.
 * This fragment is either contained in a {@link EartthquakeListActivity}
 * in two-pane mode (on tablets) or a {@link EartthquakeDetailActivity}
 * on handsets.
 */
public class EartthquakeDetailFragment extends Fragment {
    public String LOG_TAG = EartthquakeDetailFragment.class.getName();
    /**
     * The fragment argument representing the item ID that this fragment
     * represents.
     */
    public static final String ARG_ITEM_ID = "item_id";

    /**
     * The dummy content this fragment is presenting.
     */
    private DummyContent.DummyItem mItem;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public EartthquakeDetailFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments().containsKey(ARG_ITEM_ID)) {
            // Load the dummy content specified by the fragment
            // arguments. In a real-world scenario, use a Loader
            // to load content from a content provider.
            mItem = DummyContent.ITEM_MAP.get(getArguments().getString(ARG_ITEM_ID));

            Activity activity = this.getActivity();
            CollapsingToolbarLayout appBarLayout = (CollapsingToolbarLayout) activity.findViewById(R.id.toolbar_layout);
            if (appBarLayout != null) {
                appBarLayout.setTitle(mItem.website_name);
            }
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.eartthquake_detail, container, false);

        // Show the dummy content as text in a TextView.
        if (mItem != null) {
           // ((TextView) rootView.findViewById(R.id.eartthquake_detail)).setText(mItem.details);
           ((WebView) rootView.findViewById(R.id.eartthquake_detail)).loadUrl(mItem.website_url);

        }

        return rootView;
    }
}
