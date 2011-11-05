package com.overlakehome.common.places;

import java.util.Date;

public class Review {
    private final String reviewer;
    private final int rating;
    private final Date date;
    private final String text;

    public Review(String reviewer, int rating, Date date, String excerpt) {
        this.reviewer = reviewer;
        this.rating = rating;
        this.date = date;
        this.text = excerpt;
    }

    public String getReviewer() {
        return reviewer;
    }

    public int getRating() {
        return rating;
    }

    public Date getDate() {
        return date;
    }

    public String getText() {
        return text;
    }
}
