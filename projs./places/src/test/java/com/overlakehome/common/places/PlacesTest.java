package com.overlakehome.common.places;

import java.io.IOException;
import java.util.concurrent.ExecutionException;

import net.oauth.OAuthException;

import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;

import com.overlakehome.common.places.Place.Source;

public class PlacesTest {
    @Test
    @Ignore
    public void testFindNearbyPlacesByGowalla() throws InterruptedException, ExecutionException {
        double latitude = 47.59755;
        double longitude = -122.327752;
        Place[] restaurants = Places.Gowalla.findNearbyRestaurants(latitude, longitude, 50, 100, 1);
        Assert.assertTrue(restaurants.length > 0);
        Assert.assertEquals(Source.Gowalla, restaurants[0].getSource());
        Assert.assertEquals("Herfy's Burgers", restaurants[0].getName());
        Assert.assertEquals("Seattle, WA, US", restaurants[0].getAddress());
        Assert.assertTrue(47.5974269882 == restaurants[0].getLatitude());
        Assert.assertTrue(-122.3275791242 == restaurants[0].getLongitude());
        Assert.assertTrue(19 <= restaurants[0].getUsers());
        Assert.assertTrue(28 <= restaurants[0].getCheckins());
        Assert.assertTrue(0 <= restaurants[0].getHereNows());

        Place[] theaters = Places.Gowalla.findNearbyTheaters(latitude, longitude, 50, 100, 1);
        Assert.assertTrue(theaters.length > 0);
        Assert.assertEquals(Source.Gowalla, theaters[0].getSource());
        Assert.assertEquals("Theater Off Jackson", theaters[0].getName());
        Assert.assertEquals("Seattle, WA, US", theaters[0].getAddress());
        Assert.assertTrue(47.5990817042 == theaters[0].getLatitude());
        Assert.assertTrue(-122.3243208807 == theaters[0].getLongitude());
        Assert.assertTrue(12 <= theaters[0].getUsers());
        Assert.assertTrue(20 <= theaters[0].getCheckins());
        Assert.assertTrue(0 <= theaters[0].getHereNows());
    }

    @Test
    @Ignore
    public void testFindNearbyPlacesByFoursquare() throws InterruptedException, ExecutionException {
        double latitude = 47.59755;
        double longitude = -122.327752;
        Place[] places = Places.Foursquare.findNearby(latitude, longitude, "", 5000, 500);
        Assert.assertTrue(places.length > 0);
        Assert.assertEquals(Source.Foursquare, places[0].getSource());
        Assert.assertEquals("Uwajimaya Village", places[0].getName());
        Assert.assertTrue(47.59668613346502 == places[0].getLatitude());
        Assert.assertTrue(-122.32690572738647 == places[0].getLongitude());
        Assert.assertTrue(2024 <= places[0].getUsers());
        Assert.assertTrue(4342 <= places[0].getCheckins());
        Assert.assertTrue(0 <= places[0].getHereNows());
        Assert.assertEquals("44c14900f964a520b8351fe3", places[0].getRemoteId());

        Place[] starbucks1 = Places.Foursquare.findNearby(latitude, longitude, "Starbucks", 5000, 500);
        Assert.assertTrue(starbucks1.length > 0);
        Assert.assertEquals(Source.Foursquare, starbucks1[0].getSource());
        Assert.assertEquals("Starbucks - 505 Union Station", starbucks1[0].getName());
        Assert.assertEquals("505 5th Ave S, Seattle, WA, US", starbucks1[0].getAddress());
        Assert.assertTrue(47.5978861 == starbucks1[0].getLatitude());
        Assert.assertTrue(-122.3283594 == starbucks1[0].getLongitude());
        Assert.assertTrue(479 <= starbucks1[0].getUsers());
        Assert.assertTrue(6 <= starbucks1[0].getCheckins());
        Assert.assertTrue(0 <= starbucks1[0].getHereNows());
        Assert.assertEquals("49f9d06ef964a520876d1fe3", starbucks1[0].getRemoteId());
    }

    @Test
    @Ignore
    public void testFindNearbyRestaurantsByCitiGrid() throws IOException, OAuthException, InterruptedException, ExecutionException {
        double latitude = 47.59755;
        double longitude = -122.327752;
        Place[] starbucks1 = Places.CitiGrid.findNearbyRestaurants(latitude, longitude, "starbucks", 3, 50, 1); // 50 miles radius (the max)
        Assert.assertTrue(starbucks1.length == 50);
        Assert.assertEquals(Source.CitiGrid, starbucks1[0].getSource());
        Assert.assertEquals("505 5TH Ave S Ste 120, Seattle, WA, US", starbucks1[0].getAddress());
        Assert.assertEquals("http://seattle.citysearch.com/profile/34964722/seattle_wa/starbucks_coffee.html", starbucks1[0].getProfileUrl());
        Assert.assertEquals("http://seattle.citysearch.com/review/34964722", starbucks1[0].getReviewsUrl());

        Place[] starbucks2 = Places.CitiGrid.findNearbyRestaurants(latitude, longitude, "starbucks", 3, 50, 2); // 50 miles radius (the max), another page of 50 hits.
        Assert.assertTrue(starbucks2.length >= 0);

        Place[] cinemas = Places.CitiGrid.findNearbyMovieTheaters(latitude, longitude, "imax", 50); // 50 miles radius (the max)
        Assert.assertEquals(Source.CitiGrid, starbucks1[0].getSource());
        Assert.assertEquals("Boeing & Eames Imax Theatres", cinemas[0].getName());
        Assert.assertEquals("200 2ND Ave N, Seattle, WA, US", cinemas[0].getAddress());
        Assert.assertEquals("http://seattle.citysearch.com/profile/34927819/seattle_wa/boeing_eames_imax_theatres.html", cinemas[0].getProfileUrl());
    }

    @Test
    @Ignore
    public void testFindNearbyRestaurantAndMovieTheaterReviewsByYelpV1() throws IOException, OAuthException, InterruptedException, ExecutionException {
        // NOTE on limitation: this interface returns up to 20 businesses within a max radius of 25 miles.

        double latitude = 47.59755;
        double longitude = -122.327752;
        Place[] mexicans = Places.Yelp.findNearbyRestaurants(latitude, longitude, "burritos", 25); // 25 miles radius (the max)
        Assert.assertEquals(Source.Yelp, mexicans[0].getSource());
        Assert.assertEquals("Ooba Tooba", mexicans[0].getName());
        Assert.assertEquals("Mexican", mexicans[0].getClassifiers()[0]);
        Assert.assertTrue(mexicans[0].getReviewCount() >= 15);
        Assert.assertTrue(mexicans[0].getReviewAverage() >= 3.5);
        Assert.assertEquals("Miona H.", mexicans[0].getReviews()[0].getReviewer());
        Assert.assertEquals(4, mexicans[0].getReviews()[0].getRating());
        Assert.assertTrue(mexicans[0].getReviews()[0].getText().startsWith("Ooba Tooba is flat out awesome. I used to go to the Redmond location at least twice a month for lunch but"));
        Assert.assertEquals("http://www.yelp.com/biz/ooba-tooba-bellevue", mexicans[0].getProfileUrl());
        Assert.assertEquals("http://www.yelp.com/biz/ooba-tooba-bellevue", mexicans[0].getReviewsUrl());

        Place[] cinemas = Places.Yelp.findNearbyMovieTheaters(latitude, longitude, "imax", 25); // 25 miles radius (the max)
        Assert.assertEquals(Source.Yelp, cinemas[0].getSource());
        Assert.assertEquals("Boeing Imax Theater", cinemas[0].getName());
        Assert.assertTrue(cinemas[0].getReviewCount() >= 69);
        Assert.assertTrue(cinemas[0].getReviewAverage() >= 4.0);
        Assert.assertEquals("Bryan I.", cinemas[0].getReviews()[0].getReviewer());
        Assert.assertEquals(5, cinemas[0].getReviews()[0].getRating());
        Assert.assertTrue(cinemas[0].getReviews()[0].getText().startsWith("This IMAX puts all other IMAXs to shame; the absolute best, largest, loudest screen/system that I've seen!"));
    }

//    @Test
//    @Ignore
//    public void testFindNearbyPlacesByYelpV2() throws IOException, OAuthException {
//        // NOTE on limitation: this interface returns up to 20 businesses from the specified offset.
//
//        double latitude = 47.59755;
//        double longitude = -122.327752;
//        Place[] mexicans = Places.YelpV2.findNearbyRestaurants(latitude, longitude, "burritos", 1000);
//        Assert.assertEquals(20, mexicans.length);
//        Assert.assertEquals(Source.Yelp, mexicans[0].getSource());
//        Assert.assertEquals("Tenoch Mexican Grill", mexicans[0].getName());
//        Assert.assertEquals("Mexican", mexicans[0].getClassifiers()[0]);
//        Assert.assertTrue(mexicans[0].getReviewCount() >= 21);
//        Assert.assertTrue(mexicans[0].getReviewAverage() >= 4.0);
//
//        Place[] mexicans2 = Places.YelpV2.findNearbyRestaurants(latitude, longitude, "burritos", 1000, 20, mexicans.length / 2);
//        Assert.assertEquals(20, mexicans2.length);
//        Assert.assertEquals(Source.Yelp, mexicans[0].getSource());
//        Assert.assertEquals(mexicans[mexicans.length / 2].getName(), mexicans2[0].getName());
//        Assert.assertTrue(mexicans2[0].getReviewCount() >= 16);
//        Assert.assertTrue(mexicans2[0].getReviewAverage() >= 2.5);
//
//        Place[] cinemas = Places.YelpV2.findNearbyMovieTheaters(latitude, longitude, "imax", 10 * 1000);
//        Assert.assertEquals(Source.Yelp, cinemas[0].getSource());
//        Assert.assertEquals("Boeing Imax Theater", cinemas[0].getName());
//        Assert.assertTrue(cinemas[0].getReviewCount() >= 69);
//        Assert.assertTrue(cinemas[0].getReviewAverage() >= 4.5);
//    }

//    @Test
//    @Ignore
//    public void testFindNearbyRestaurantsBySimpleGeo() throws IOException {
//        String address = "505 5th Ave S, Suite 310, Seattle, WA, 98104";
//        Place[] thais = Places.SimpleGeo.findNearbyRestaurants(address, "Thai", 3000); // 3000 meter radius
//        Assert.assertTrue(thais[0].getName().contains("Thai"));
//
//        double latitude = 47.59755;
//        double longitude = -122.327752;
//        Place[] coffees = Places.SimpleGeo.findNearbyRestaurants(latitude, longitude, "Coffee", 3000); // 3000 meter radius
//        Assert.assertEquals(Source.SimpleGeo, coffees[0].getSource());
//        Assert.assertTrue(coffees[0].getName().contains("Coffee"));
//    }

//    @Test
//    @Ignore
//    public void testFindNearbyMovieTheatersBySimpleGeo() throws IOException {
//        String address = "505 5th Ave S, Suite 310, Seattle, WA, 98104";
//        Place[] theaters = Places.SimpleGeo.findNearbyMovieTheaters(address, "", 3000); // 3000 meter radius
//        Assert.assertTrue(theaters[0].getName().contains("Theatre"));
//        Assert.assertEquals(Source.SimpleGeo, theaters[0].getSource());
//
//        double latitude = 47.59755;
//        double longitude = -122.327752;
//        Place[] cinemas = Places.SimpleGeo.findNearbyMovieTheaters(latitude, longitude, "Fifth", 3000); // 3000 meter radius
//        Assert.assertTrue(cinemas[0].getName().contains("Fifth"));
//        Assert.assertEquals(Source.SimpleGeo, cinemas[0].getSource());
//    }
}
