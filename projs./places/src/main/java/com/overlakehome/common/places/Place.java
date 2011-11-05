package com.overlakehome.common.places;

import com.google.common.base.Joiner;
import com.google.common.base.Objects;
import com.google.common.base.Strings;

public class Place {
    private final Source source;
    private final String streetAddress;
    private final String city;
    private final String[] classifiers;
    private final String country;
    private final String fax;
    private final String id;
    private final double latitude;
    private final double longitude;
    private final String name;
    private final String phone;
    private final String postcode;
    private final String remoteId;
    private final String state;
    private final String website;
    private final int reviewCount;
    private final double reviewAverage;
    private final Review[] reviews;
    private final String profileUrl;
    private final String reviewsUrl;
    private final int checkins;
    private final int users;
    private final int hereNows;

    public Place(
            Source source, String id, String remoteId, String name, String[] classifiers, 
            double latitude, double longitude, 
            String streetAddress, String city, String state, String country, String postcode,
            String website, String phone, String fax) {

        this.source = source;
        this.id = id;
        this.remoteId = remoteId;
        this.name = name;
        this.classifiers = classifiers;
        this.latitude = latitude;
        this.longitude = longitude;
        this.streetAddress = streetAddress;
        this.city = city;
        this.state = state;
        this.country = country;
        this.postcode = postcode;
        this.website = website;
        this.phone = phone;
        this.fax = fax;
        this.reviewCount = 0;
        this.reviewAverage = 0.0;
        this.reviews = null;
        this.profileUrl = null;
        this.reviewsUrl = null;
        this.checkins = 0;
        this.users = 0;
        this.hereNows = 0;
    }

    public Place(
            Source source, String id, String remoteId, String name, String[] classifiers,
            double latitude, double longitude, 
            String streetAddress, String city, String state, String country, String postcode,
            String website, String phone, String fax, int checkins, int users, int hereNows) {

        this.source = source;
        this.id = id;
        this.remoteId = remoteId;
        this.name = name;
        this.classifiers = classifiers;
        this.latitude = latitude;
        this.longitude = longitude;
        this.streetAddress = streetAddress;
        this.city = city;
        this.state = state;
        this.country = country;
        this.postcode = postcode;
        this.website = website;
        this.phone = phone;
        this.fax = fax;
        this.reviewCount = 0;
        this.reviewAverage = 0.0;
        this.reviews = null;
        this.profileUrl = null;
        this.reviewsUrl = null;
        this.checkins = checkins;
        this.users = users;
        this.hereNows = hereNows;
    }

    public Place(
            Source source, String id, String remoteId, String name, String[] classifiers,
            double latitude, double longitude, 
            String streetAddress, String city, String state, String country, String postcode,
            String website, String phone, String fax, int reviewCount, double reviewAverage, Review[] reviews,
            String profileUrl, String reviewsUrl) {

        this.source = source;
        this.id = id;
        this.remoteId = remoteId;
        this.name = name;
        this.classifiers = classifiers;
        this.latitude = latitude;
        this.longitude = longitude;
        this.streetAddress = streetAddress;
        this.city = city;
        this.state = state;
        this.country = country;
        this.postcode = postcode;
        this.website = website;
        this.phone = phone;
        this.fax = fax;
        this.reviewCount = reviewCount;
        this.reviewAverage = reviewAverage;
        this.reviews = reviews;
        this.profileUrl = profileUrl;
        this.reviewsUrl = reviewsUrl;
        this.checkins = 0;
        this.users = 0;
        this.hereNows = 0;
    }

    public String toString() {
        return Objects.toStringHelper(this)
            .add("name", name)
            .add("classifiers", Joiner.on(", ").join(classifiers))
            .add("address", streetAddress)
            .add("city", city)
            .add("state", state)
            .toString();
    }

    public Source getSource() {
        return source;
    }

    public String getAddress() {
        if (Strings.isNullOrEmpty(streetAddress)) {
            return String.format("%s, %s, %s", city, state, country);
        } else {
            return String.format("%s, %s, %s, %s", streetAddress, city, state, country);
        }
    }

    public String getStressAddress() {
        return streetAddress;
    }

    public String getCity() {
        return city;
    }

    public String[] getClassifiers() {
        return classifiers;
    }

    public String getCountry() {
        return country;
    }

    public String getFax() {
        return fax;
    }

    public String getId() {
        return id;
    }

    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public String getName() {
        return name;
    }

    public String getPhone() {
        return phone;
    }

    public String getPostcode() {
        return postcode;
    }

    public String getRemoteId() {
        return remoteId;
    }

    public String getState() {
        return state;
    }

    public String getWebsite() {
        return website;
    }

    public int getReviewCount() {
        return reviewCount;
    }

    public double getReviewAverage() {
        return reviewAverage;
    }

    public Review[] getReviews() {
        return reviews;
    }

    public String getProfileUrl() {
        return profileUrl;
    }

    public String getReviewsUrl() {
        return reviewsUrl;
    }

    public int getCheckins() {
        return checkins;
    }

    public int getUsers() {
        return users;
    }

    public int getHereNows() {
        return hereNows;
    }

    public static enum Source {
        CitiGrid, Google, Gowalla, Foursquare, SimpleGeo, Yelp
    }
}
