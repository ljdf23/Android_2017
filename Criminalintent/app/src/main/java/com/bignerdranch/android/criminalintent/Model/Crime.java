package com.bignerdranch.android.criminalintent.Model;

import java.util.UUID;

/**
 * Created by Luis on 22/01/2017.
 */

public class Crime {

    private UUID mId;
    private String mTitle;

    public Crime() {
        mId=UUID.randomUUID();
    }

    public UUID getId() {
        return mId;
    }

    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String title) {
        mTitle = title;
    }
}
