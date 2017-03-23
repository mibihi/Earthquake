package com.example.android.earthquake.dummy;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Helper class for providing sample content for user interfaces created by
 * Android template wizards.
 * <p>
 * TODO: Replace all uses of this class before publishing your app.
 */
public class DummyContent {

    /**
     * An array of sample (dummy) items.
     */
    public static final List<DummyItem> ITEMS = new ArrayList<DummyItem>();

    /**
     * A map of sample (dummy) items, by ID.
     */
    public static final Map<String, DummyItem> ITEM_MAP = new HashMap<String, DummyItem>();

    static {
       // add three sample items
        for (int i=1;i <15;i++) {
            ITEMS.add(new DummyItem(i+"",4.5, "lusaka", 333333, "http://www.google.com"));
        }

    }





    /**
     * A dummy item representing a piece of content.
     */
    public static class DummyItem {
        public String getId() {
            return mId;
        }

        public String mId;
        public double mMagnitude;
        public String mLocation;
        public long mTimeinMilliseconds;
        public String mUrl;

        public double getmMagnitude() {
            return mMagnitude;
        }

        public String getmLocation() {
            return mLocation;
        }

        public long getmTimeinMilliseconds() {
            return mTimeinMilliseconds;
        }

        public String getmUrl() {
            return mUrl;
        }

        public DummyItem(String id,double Magnitude, String Location, long timeinmilliseconds, String url) {
            mMagnitude = Magnitude;
            mLocation = Location;
            mTimeinMilliseconds = timeinmilliseconds;
            mUrl = url;
            mId = id;




        }

        @Override
        public String toString() {
            return mLocation;
        }
    }
}
