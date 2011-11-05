package com.overlakehome.common.places;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

import net.sf.json.JSONArray;
import net.sf.json.JSONException;
import net.sf.json.JSONObject;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.impl.nio.client.DefaultHttpAsyncClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.nio.client.HttpAsyncClient;
import org.apache.http.nio.reactor.IOReactorException;
import org.apache.http.util.EntityUtils;
import org.apache.ws.security.util.UUIDGenerator;

import com.google.common.base.Objects;
import com.google.common.base.Splitter;
import com.google.common.base.Strings;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.Iterables;
import com.overlakehome.common.places.Place.Source;

public class Places {
    public static class Gowalla {
        private final static String GOWALLA_API_KEY = "fa574894bddc43aa96c556eb457b4009";
        private final static String GOWALLA_PLACES_SEARCH_URL = "https://api.gowalla.com/spots?";

        private final static int GOWALLA_FOOD = 7; // Food
        private final static int GOWALLA_THEATRE = 103; // Theatre

        private static HttpAsyncClient client = null;

        static {
            try {
                client = new DefaultHttpAsyncClient();
            } catch (IOReactorException e) {
            }

            client.start();
        }

        private Gowalla() {
        }

        public static Place[] findNearbyRestaurants(double latitude, double longitude, int meters, int limit, int page)
                throws InterruptedException, ExecutionException {

            return findNearby(latitude, longitude, GOWALLA_FOOD, meters, limit, page);
        }

        public static Place[] findNearbyTheaters(double latitude, double longitude, int meters, int limit, int page)
                throws InterruptedException, ExecutionException {

            return findNearby(latitude, longitude, GOWALLA_THEATRE, meters, limit, page);
        }

        public static Place[] findNearby(double latitude, double longitude, int category, int meters, int limit, int page)
                throws InterruptedException, ExecutionException {

            String search = GOWALLA_PLACES_SEARCH_URL.concat(queryOf(latitude, longitude, category, meters, limit, page));
            HttpGet get = new HttpGet(search);
            get.addHeader("Accept", "application/json");
            get.addHeader("X-Gowalla-API-Key", GOWALLA_API_KEY);
            Future<HttpResponse> future = client.execute(get, null);

            HttpResponse response = future.get();
            if (null == response) {
                return new Place[0];
            } else {
                return toPlaces(response);
            }
        }

        private static Place[] toPlaces(HttpResponse response) throws InterruptedException, ExecutionException {
            return toPlaces(toJSONObject(response).getJSONArray("spots"));
        }

        private static Place[] toPlaces(JSONArray spots) throws InterruptedException, ExecutionException {
            Place[] places = new Place[spots.size()];
            for (int i = 0; i < spots.size(); i++) {
                try {
                    JSONObject spot = spots.getJSONObject(i);
                    JSONObject address = spot.getJSONObject("address");

                    // FIXME: See if it is a good idea to add 'trending', 'photos', and 'tips' into the place model.
                    places[i] = new Place(
                        Source.Gowalla, UUIDGenerator.getUUID(), spot.getString("url"), spot.getString("name"), toClassifiers(spot.getJSONArray("spot_categories")),
                        spot.getDouble("lat"), spot.getDouble("lng"),
                        null, address.optString("locality"), address.optString("region"), "US", null,
                        "http://gowalla.com" + spot.getString("url"), null, null,
                        spot.getInt("checkins_count"), spot.getInt("users_count"), 0);
                } catch (JSONException e) {
                    throw new IllegalStateException("UNCHECKED: this bug should go unhandled; the businesses: " + spots, e);
                }
            }

            return places;
        }

        private static String[] toClassifiers(JSONArray categories) {
            String[] classifiers = new String[categories.size()];
            for (int i = 0; i < categories.size(); i++) {
                classifiers[i] = categories.getJSONObject(i).getString("name");
            }

            return classifiers;
        }


        private static JSONObject toJSONObject(HttpResponse response) {
            try {
                return JSONObject.fromObject(EntityUtils.toString(response.getEntity()));
            } catch (IOException e) {
                throw new IllegalStateException("UNCHECKED: this bug should go unhandled; the response: " + response, e);
            } catch (JSONException e) {
                throw new IllegalStateException("UNCHECKED: this bug should go unhandled; the response: " + response, e);
            }
        }

        private static String queryOf(double latitude, double longitude, int category, int meters, int limit, int page) {
            List<BasicNameValuePair> qparams;
            if (-1 == category) {
                qparams = ImmutableList.of(
                    new BasicNameValuePair("lat", Double.toString(latitude)),
                    new BasicNameValuePair("lng", Double.toString(longitude)),
                    new BasicNameValuePair("radius", Integer.toString(meters)),
                    new BasicNameValuePair("per_page", Integer.toString(limit)),
                    new BasicNameValuePair("page", Integer.toString(page)));
            } else {
                qparams = ImmutableList.of(
                    new BasicNameValuePair("lat", Double.toString(latitude)),
                    new BasicNameValuePair("lng", Double.toString(longitude)),
                    new BasicNameValuePair("radius", Integer.toString(meters)),
                    new BasicNameValuePair("category_id", Integer.toString(category)),
                    new BasicNameValuePair("per_page", Integer.toString(limit)),
                    new BasicNameValuePair("page", Integer.toString(page)));
            }

            return URLEncodedUtils.format(qparams, "UTF-8");
        }
    }

    public static class Foursquare {
        private final static String FOURSQUARE_PLACES_SEARCH_URL = "https://api.foursquare.com/v2/venues/search?";
        private final static String FOURSQUARE_PLACES_TRENDING_URL = "https://api.foursquare.com/v2/venues/trending?";

        private static HttpAsyncClient client = null;

        static {
            try {
                client = new DefaultHttpAsyncClient();
            } catch (IOReactorException e) {
            }

            client.start();
        }

        private Foursquare() {
        }

        public static Place[] findTrendingNearby(double latitude, double longitude, int limit) throws InterruptedException, ExecutionException {
            String search = FOURSQUARE_PLACES_TRENDING_URL.concat(queryOf(latitude, longitude, "", 0, limit));
            Future<HttpResponse> future = client.execute(new HttpGet(search), null);

            HttpResponse response = future.get();
            if (null == response) {
                return new Place[0];
            } else {
                JSONArray venues = toJSONObject(response).getJSONObject("response").getJSONArray("venues");
                return toPlaces(venues);
            }
        }

        public static Place[] findNearby(double latitude, double longitude, String query, int meters, int limit) throws InterruptedException, ExecutionException {

            String search = FOURSQUARE_PLACES_SEARCH_URL.concat(queryOf(latitude, longitude, query, meters, limit));
            Future<HttpResponse> future = client.execute(new HttpGet(search), null);

            HttpResponse response = future.get();
            if (null == response) {
                return new Place[0];
            } else {
                return toPlaces(response);
            }
        }

        private static Place[] toPlaces(HttpResponse response) throws InterruptedException, ExecutionException {
            JSONArray groups = toJSONObject(response).getJSONObject("response").getJSONArray("groups");
            for (int i = 0; i < groups.size(); i++) {
                JSONObject group = groups.getJSONObject(i);
                String type = group.getString("type");
                if ("nearby".equals(type) || "matches".equals(type) || "places".equals(type)) {
                    return toPlaces(group.getJSONArray("items"));
                }
            }

            throw new IllegalStateException("UNCHECKED: this bug should go unhandled.");
        }

        private static JSONObject toJSONObject(HttpResponse response) {
            try {
                return JSONObject.fromObject(EntityUtils.toString(response.getEntity()));
            } catch (IOException e) {
                throw new IllegalStateException("UNCHECKED: this bug should go unhandled; the businesses: " + response, e);
            } catch (JSONException e) {
                throw new IllegalStateException("UNCHECKED: this bug should go unhandled; the businesses: " + response, e);
            }
        }

        private static Place[] toPlaces(JSONArray items) throws InterruptedException, ExecutionException {
          Place[] places = new Place[items.size()];
          for (int i = 0; i < items.size(); i++) {
              try {
                  JSONObject item = items.getJSONObject(i);
                  JSONObject location = item.getJSONObject("location");
                  JSONObject contact = item.getJSONObject("contact");
                  JSONObject stats = item.getJSONObject("stats");
                  String address = Objects.firstNonNull(location.optString("address"), location.optString("crossStreet"));

                  // FIXME: See if it is a good idea to add 'specials', 'photos', and 'tips' into the place model.
                  places[i] = new Place(
                      Source.Foursquare, UUIDGenerator.getUUID(), item.getString("id"), item.getString("name"), toClassifiers(item.getJSONArray("categories")),
                      location.getDouble("lat"), location.getDouble("lng"),
                      address, location.optString("city"), location.optString("state"), "US", location.optString("postalCode"),
                      null, contact.optString("phone"), null,
                      stats.getInt("checkinsCount"), stats.getInt("usersCount"), item.getJSONObject("hereNow").getInt("count"));
              } catch (JSONException e) {
                  throw new IllegalStateException("UNCHECKED: this bug should go unhandled; the businesses: " + items, e);
              }
          }

          return places;
        }

        private static String[] toClassifiers(JSONArray categories) {
            String[] classifiers = new String[categories.size()];
            for (int i = 0; i < categories.size(); i++) {
                classifiers[i] = categories.getJSONObject(i).getString("name");
            }

            return classifiers;
        }

        private static String queryOf(double latitude, double longitude, String query, double miles, int limit) {
            List<BasicNameValuePair> qparams;
            if (Strings.isNullOrEmpty(query)) {
                qparams = ImmutableList.of(
                    new BasicNameValuePair("oauth_token", "LTPEYPHEF3UHHIXNIHT2WGSTPXSXVI41MEJYQTWUGRNOLGM5"),
                    new BasicNameValuePair("ll", latitude + "," + longitude),
                    new BasicNameValuePair("limit", Integer.toString(limit)),
                    new BasicNameValuePair("intent", "checkin"));
            } else {
                qparams = ImmutableList.of(
                    new BasicNameValuePair("oauth_token", "LTPEYPHEF3UHHIXNIHT2WGSTPXSXVI41MEJYQTWUGRNOLGM5"),
                    new BasicNameValuePair("ll", latitude + "," + longitude),
                    new BasicNameValuePair("query", query),
                    new BasicNameValuePair("limit", Integer.toString(limit)),
                    new BasicNameValuePair("intent", "checkin")); // 'match' intent stopped working weeks ago.
            }

            return URLEncodedUtils.format(qparams, "UTF-8");
        }
    }

    public static class CitiGrid {
        private final static String CITIGRID_PLACES_SEARCH_URL = "http://api.citygridmedia.com/content/places/v2/search/latlon?";
        private final static String CITIGRID_PLACES_DETAIL_URL = "http://api.citygridmedia.com/content/places/v2/detail?";
        private final static String CITIGRID_RESTAURANT = "restaurant";
        private final static String CITIGRID_MOVIE_THEATER = "movietheater";

        private static HttpAsyncClient citiGridClient = null;

        static {
            try {
                citiGridClient = new DefaultHttpAsyncClient();
            } catch (IOReactorException e) {
            }

            citiGridClient.start();
        }

        private CitiGrid() {
        }

        public static Place[] findNearbyRestaurants(double latitude, double longitude, String query, double miles)
                throws InterruptedException, ExecutionException {

            return findNearby(latitude, longitude, query, CITIGRID_RESTAURANT, miles, 50, 1);
        }

        public static Place[] findNearbyRestaurants(double latitude, double longitude, String query, double miles, int limit, int page)
                throws InterruptedException, ExecutionException {

            return findNearby(latitude, longitude, query, CITIGRID_RESTAURANT, miles, limit, page);
        }

        public static Place[] findNearbyMovieTheaters(double latitude, double longitude, String query, double miles)
                throws InterruptedException, ExecutionException {

            return findNearby(latitude, longitude, query, CITIGRID_MOVIE_THEATER, miles, 50, 1);
        }

        public static Place[] findNearby(double latitude, double longitude, String query, String category, double miles, int limit, int page)
                throws InterruptedException, ExecutionException {

            if (miles > 50.0) {
                throw new IllegalArgumentException("'miles' must be no greater than 50.0.");
            }

            String search = CITIGRID_PLACES_SEARCH_URL.concat(queryOf(latitude, longitude, query, category, miles, limit, page));
            Future<HttpResponse> future = citiGridClient.execute(new HttpGet(search), null);

            HttpResponse response = future.get();
            if (null == response) {
                return new Place[0];
            } else {
                return toPlaces(response);
            }
        }

        private static Place[] toPlaces(HttpResponse response) throws InterruptedException, ExecutionException {
            return toPlaces(toJSONObject(response).getJSONObject("results").getJSONArray("locations"));
        }

        private static JSONObject toJSONObject(HttpResponse response) {
            try {
                return JSONObject.fromObject(EntityUtils.toString(response.getEntity()));
            } catch (IOException e) {
                throw new IllegalStateException("UNCHECKED: this bug should go unhandled; the businesses: " + response, e);
            } catch (JSONException e) {
                throw new IllegalStateException("UNCHECKED: this bug should go unhandled; the businesses: " + response, e);
            }
        }

        private static Place[] toPlaces(JSONArray locations) throws InterruptedException, ExecutionException {
          @SuppressWarnings("unchecked")
          Future<HttpResponse>[] futures = (Future<HttpResponse>[])new Future[locations.size()];

          for (int i = 0; i < locations.size(); i++) {
              JSONObject location = locations.getJSONObject(i);
              String detail = CITIGRID_PLACES_DETAIL_URL.concat(queryOf(location.getString("id")));
              futures[i] = citiGridClient.execute(new HttpGet(detail), null);
          }

            Place[] places = new Place[locations.size()];
            for (int i = 0; i < locations.size(); i++) {
                try {
                    JSONObject location = locations.getJSONObject(i);
                    JSONObject address = location.getJSONObject("address");
                    HttpResponse response = futures[i].get();
                    String reviewUrl = null;
                    if (null == response) {
                        reviewUrl = String.format("http://seattle.citysearch.com/review/".concat(location.getString("id")));
                    } else {
                        reviewUrl = toJSONObject(futures[i].get()).getJSONArray("locations").getJSONObject(0).getJSONObject("urls").getString("reviews_url");
                    }

                    places[i] = new Place(
                        Source.CitiGrid, UUIDGenerator.getUUID(), location.getString("id"), location.getString("name"), toClassifiers(location.getString("sample_categories")),
                        location.getDouble("latitude"), location.getDouble("longitude"),
                        address.getString("street"), address.getString("city"), address.getString("state"), "US", address.getString("postal_code"),
                        location.getString("website"), location.getString("phone_number"), location.getString("fax_number"),
                        location.getInt("user_review_count"), location.optDouble("rating"), null, location.getString("profile"), reviewUrl);
                } catch (JSONException e) {
                    throw new IllegalStateException("UNCHECKED: this bug should go unhandled; the businesses: " + locations, e);
                }
            }

            return places;
        }

        private static String[] toClassifiers(String categories) {
            return Iterables.toArray(Splitter.on(',').trimResults().omitEmptyStrings().split(categories), String.class);
        }

        private static String queryOf(double latitude, double longitude, String query, String category, double miles, int limit, int page) {
            List<BasicNameValuePair> qparams = ImmutableList.of(
                new BasicNameValuePair("lat", Double.toString(latitude)),
                new BasicNameValuePair("lon", Double.toString(longitude)),
                new BasicNameValuePair("what", query),
                new BasicNameValuePair("type", category),
                new BasicNameValuePair("radius", Double.toString(miles)),
                new BasicNameValuePair("rpp", Integer.toString(limit)),
                new BasicNameValuePair("page", Integer.toString(page)),
                new BasicNameValuePair("publisher", "kihasoftware"),
                new BasicNameValuePair("format", "json"));

            return URLEncodedUtils.format(qparams, "UTF-8");
        }

        private static String queryOf(String id) {
            List<BasicNameValuePair> qparams;
            try {
                qparams = ImmutableList.of(
                    new BasicNameValuePair("listing_id", id),
                    new BasicNameValuePair("publisher", "kihasoftware"),
                    //TODO: this should be the ip address of the client we're working for
                    new BasicNameValuePair("client_ip", InetAddress.getLocalHost().getHostAddress()),
                    new BasicNameValuePair("format", "json"));
            } catch (UnknownHostException e) {
                throw new IllegalStateException("UNCHECKED: this bug should go unhandled.");
            }

            return URLEncodedUtils.format(qparams, "UTF-8");
        }
    }

    public static class Yelp {
        private final static String YELP_RESTAURANTS = "restaurants";
        private final static String YELP_MOVIE_THEATERS = "movietheaters";

        private final static String YELP_YWSID = "K1FDitsix8ROANTxeNSL6g";
        private static HttpAsyncClient yelpClient = null;

        static {
            try {
                yelpClient = new DefaultHttpAsyncClient();
            } catch (IOReactorException e) {
            }

            yelpClient.start();
        }

        private Yelp() { }

        public static Place[] findExactRestaurants(double latitude, double longitude, String query, double miles)
                throws InterruptedException, ExecutionException {

            return findNearby(latitude, longitude, query, YELP_RESTAURANTS, miles);
        }

        public static Place[] findNearbyRestaurants(double latitude, double longitude, String query, double miles)
                throws InterruptedException, ExecutionException {

            return findNearby(latitude, longitude, query, YELP_RESTAURANTS, miles);
        }

        public static Place[] findNearbyMovieTheaters(double latitude, double longitude, String query, double miles)
                throws InterruptedException, ExecutionException {

            return findNearby(latitude, longitude, query, YELP_MOVIE_THEATERS, miles);
        }

        public static Place[] findNearby(double latitude, double longitude, String query, String category, double miles)
                throws InterruptedException, ExecutionException {

            if (miles > 25.0) {
                throw new IllegalArgumentException("'miles' must be no greater than 25.0.");
            }

            String url = "http://api.yelp.com/business_review_search?".concat(queryOf(latitude, longitude, query, category, miles));
            Future<HttpResponse> future = yelpClient.execute(new HttpGet(url), null);

            return toPlaces(future.get());
        }

        private static Place[] toPlaces(HttpResponse response) {
            try {
                String responseString = EntityUtils.toString(response.getEntity());
                JSONObject json = JSONObject.fromObject(responseString);
                JSONArray businesses = json.getJSONArray("businesses");
                return toPlaces(businesses);
            } catch (IOException e) {
                throw new IllegalStateException("UNCHECKED: this bug should go unhandled; the response: " + response, e);
            } catch (JSONException e) {
                throw new IllegalStateException("UNCHECKED: this bug should go unhandled; the response: " + response, e);
            }
        }

        private static Place[] toPlaces(JSONArray businesses) {
            Place[] places = new Place[businesses.size()];
            for (int i = 0; i < businesses.size(); i++) {
                try {
                    JSONObject business = businesses.getJSONObject(i);
                    JSONArray reviews = business.getJSONArray("reviews");
                    JSONArray categories = business.getJSONArray("categories");

                    places[i] = new Place(
                        Source.Yelp, UUIDGenerator.getUUID(), business.getString("id"), business.getString("name"), toClassifiers(categories),
                        business.getDouble("latitude"), business.getDouble("longitude"),
                        business.getString("address1"), business.getString("city"), business.getString("state_code"), business.getString("country_code"), business.getString("zip"),
                        business.getString("url"), business.optString("phone"), null /* fax */,
                        business.getInt("review_count"), business.getDouble("avg_rating"), toReviews(reviews), business.getString("url"), business.getString("url"));
                } catch (JSONException e) {
                    throw new IllegalStateException("UNCHECKED: this bug should go unhandled; the businesses: " + businesses);
                }
            }

            return places;
        }

        private static String[] toClassifiers(JSONArray categories) {
            String[] classifiers = new String[categories.size()];
            for (int i = 0; i < categories.size(); i++) {
                classifiers[i] = categories.getJSONObject(i).getString("name");
            }

            return classifiers;
        }

        private static Review[] toReviews(JSONArray jsons) {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd"); // e.g. 2010-11-28
            Review[] reviews = new Review[jsons.size()];
            try {
                for (int i = 0; i < jsons.size(); i++) {
                    JSONObject json = jsons.getJSONObject(i);
                    reviews[i] = new Review(
                        json.getString("user_name"), 
                        json.getInt("rating"), 
                        sdf.parse(json.getString("date")), 
                        json.getString("text_excerpt"));
                }
            } catch (JSONException e) {
                throw new IllegalStateException("UNCHECKED: this bug should go unhandled; the review: " + jsons, e);
            } catch (ParseException e) {
                throw new IllegalStateException("UNCHECKED: this bug should go unhandled; the review: " + jsons, e);
            }

            return reviews;
        }

        private static String queryOf(double latitude, double longitude, String query, String category, double miles) {
            List<BasicNameValuePair> qparams = ImmutableList.of(
                new BasicNameValuePair("lat", Double.toString(latitude)),
                new BasicNameValuePair("long", Double.toString(longitude)),
                new BasicNameValuePair("term", query),
                new BasicNameValuePair("category", category),
                new BasicNameValuePair("radius", Double.toString(miles)),
                new BasicNameValuePair("limit", "20"), // 20 is a hard limit, reference: http://www.yelp.com/developers/documentation/search_api
                new BasicNameValuePair("ywsid", YELP_YWSID));

            return URLEncodedUtils.format(qparams, "UTF-8");
        }
    }

//    public static class YelpV2 {
//        private final static String YELP_CONSUMER_KEY = "IrQdp4Co-UnjX5kb9O4k7Q";
//        private final static String YELP_CONSUMER_SECRET = "qpAXLcleLsW6FHLs3TnYRAP2qVQ";
//        private final static String YELP_ACCESS_TOKEN = "ekOmFz7JiCoU-2U-5d3FY7Vc97qNzTd_";
//        private final static String YELP_TOKEN_SECRET = "ji06v52LwLID6Rutit8Gz3ARo9o";
//
//        private final static String YELP_RESTAURANTS = "restaurants";
//        private final static String YELP_MOVIE_THEATERS = "movietheaters";
//
//        private final static OAuthAccessor yelpAccessor = new OAuthAccessor(new OAuthConsumer(null, YELP_CONSUMER_KEY, YELP_CONSUMER_SECRET, null));
//        private final static OAuthClient yelpClient;
//
//        static {
//            yelpAccessor.accessToken = YELP_ACCESS_TOKEN;
//            yelpAccessor.tokenSecret = YELP_TOKEN_SECRET;
//            yelpClient = new OAuthClient(new HttpClient4());
//        }
//
//        private YelpV2() { }
//
//        public static Place[] findNearbyRestaurants(double latitude, double longitude, String query, int meters)
//                throws IOException, OAuthException {
//
//            return findNearby(latitude, longitude, query, YELP_RESTAURANTS, 0, 0, meters);
//        }
//
//        public static Place[] findNearbyRestaurants(double latitude, double longitude, String query, int meters, int limit, int offset)
//                throws IOException, OAuthException {
//           return findNearby(latitude, longitude, query, YELP_RESTAURANTS, limit, offset, meters);
//        }
//
//        public static Place[] findNearbyMovieTheaters(double latitude, double longitude, String query, int meters)
//                throws IOException, OAuthException {
//
//            return findNearby(latitude, longitude, query, YELP_MOVIE_THEATERS, 0, 0, meters);
//        }
//
//        public static Place[] findNearby(double latitude, double longitude, String query, String category, int limit, int offset, int meters)
//                throws IOException, OAuthException {
//
//            OAuthMessage oauthMessage = new OAuthMessage("GET", "http://api.yelp.com/v2/search", null);
//            oauthMessage.addParameter("ll", String.format("%s,%s", latitude, longitude));
//            if (!Strings.isNullOrEmpty(query)) {
//                oauthMessage.addParameter("term", query);
//            }
//
//            if (offset > 0) {
//                oauthMessage.addParameter("offset", Integer.toString(offset));
//            }
//
//            if (limit > 0) {
//                oauthMessage.addParameter("limit", Integer.toString(limit));
//            }
//
//            oauthMessage.addParameter("sort", "0"); // sort: 0=Best matched (default), 1=Distance, 2=Highest Rated. If the mode is 1 or 2, the search limit becomes 40.
//            oauthMessage.addParameter("category_filter", category);
//            oauthMessage.addParameter("radius_filter", Integer.toString(meters));
//            try {
//                oauthMessage.addRequiredParameters(yelpAccessor);
//            } catch (URISyntaxException e) {
//                throw new IllegalStateException("UNCHECKED: this bug should go unhandled.");
//            }
//
//            OAuthMessage response = yelpClient.invoke(oauthMessage, ParameterStyle.AUTHORIZATION_HEADER);
//            return toPlaces(response);
//        }
//
//        private static Place[] toPlaces(OAuthMessage response) throws IOException {
//            String responseString = new String(ByteStreams.toByteArray(response.getBodyAsStream()));
//            try {
//                JSONObject json = JSONObject.fromObject(responseString);
//                JSONArray businesses = json.getJSONArray("businesses");
//                return toPlaces(businesses);
//            } catch (JSONException e) {
//                throw new IllegalStateException("UNCHECKED: this bug should go unhandled; the businesses: " + responseString);
//            }
//        }
//
//        private static Place[] toPlaces(JSONArray businesses) {
//            Place[] places = new Place[businesses.size()];
//            for (int i = 0; i < businesses.size(); i++) {
//                try {
//                    JSONObject business = businesses.getJSONObject(i);
//                    JSONObject location = business.getJSONObject("location");
//                    JSONArray addresses = location.getJSONArray("address");
//                    JSONObject coordinate = location.getJSONObject("coordinate");
//                    JSONArray categories = business.getJSONArray("categories");
//
//                    places[i] = new Place(
//                        Source.Yelp, UUIDGenerator.getUUID(), business.getString("id"), business.getString("name"), toClassifiers(categories),
//                        coordinate.getDouble("latitude"), coordinate.getDouble("longitude"),
//                        addresses.getString(0), (String)location.get("city"), location.getString("state_code"), location.getString("country_code"), location.getString("postal_code"),
//                        business.getString("url"), business.optString("display_phone"), null /* fax */,
//                        business.getInt("review_count"), toRating(business.getString("rating_img_url")), null, null, null);
//                } catch (JSONException e) {
//                    throw new IllegalStateException("UNCHECKED: this bug should go unhandled; the businesses: " + businesses, e);
//                }
//            }
//
//            return places;
//        }
//
//        private static String[] toClassifiers(JSONArray categories) {
//            String[] classifiers = new String[categories.size()];
//            for (int i = 0; i < categories.size(); i++) {
//                classifiers[i] = categories.getJSONArray(i).getString(0);
//            }
//
//            return classifiers;
//        }
//
//        private static double toRating(String ratingImgUrl) {
//            if (ratingImgUrl.endsWith("stars_5.png")) {
//                return 5.0;
//            } else if (ratingImgUrl.endsWith("stars_4_half.png")) {
//                return 4.5;
//            } else if (ratingImgUrl.endsWith("stars_4.png")) {
//                return 4.0;
//            } else if (ratingImgUrl.endsWith("stars_3_half.png")) {
//                return 3.5;
//            } else if (ratingImgUrl.endsWith("stars_3.png")) {
//                return 3.0;
//            } else if (ratingImgUrl.endsWith("stars_2_half.png")) {
//                return 2.5;
//            } else if (ratingImgUrl.endsWith("stars_2.png")) {
//                return 2.0;
//            } else if (ratingImgUrl.endsWith("stars_1_half.png")) {
//                return 1.5;
//            } else if (ratingImgUrl.endsWith("stars_1.png")) {
//                return 1.0;
//            } else if (ratingImgUrl.endsWith("stars_0.png")) {
//                return 0.0;
//            } else {
//                return 0.0;
//            }
//        }
//    }

//    public static class SimpleGeo {
//        private final static SimpleGeoPlacesClient simpleGeoClient = SimpleGeoPlacesClient.getInstance();
//        private final static String SIMPLEGEO_OAUTH_KEY = "ckymuDcC85FRTKVK8b3yQx8CwZ8xySxF";
//        private final static String SIMPLEGEO_OAUTH_SECRET = "HRpTFQeVX8sr2wvFCxqVcyJV478xzvca";
//        private final static String SIMPLEGEO_RESTAURANT = "Restaurant";
//        private final static String SIMPLEGEO_MOVIE_THEATER = "Movie Theater";
//
//        static {
//            simpleGeoClient.getHttpClient().setToken(SIMPLEGEO_OAUTH_KEY, SIMPLEGEO_OAUTH_SECRET);
//        }
//
//        private SimpleGeo() {}
//
//        public static Place[] findNearbyMovieTheaters(String address, String query, int meters) throws IOException {
//            return findNearby(address, query, SIMPLEGEO_MOVIE_THEATER, meters);
//        }
//     
//        public static Place[] findNearbyMovieTheaters(double latitude, double longitude, String query, int meters) throws IOException {
//            return findNearby(latitude, longitude, query, SIMPLEGEO_MOVIE_THEATER, meters);
//        }
//
//        public static Place[] findNearbyRestaurants(String address, String query, int meters) throws IOException {
//            return findNearby(address, query, SIMPLEGEO_RESTAURANT, meters);
//        }
//     
//        public static Place[] findNearbyRestaurants(double latitude, double longitude, String query, int meters) throws IOException {
//            return findNearby(latitude, longitude, query, SIMPLEGEO_RESTAURANT, meters);
//        }
//
//        public static Place[] findNearby(double latitude, double longitude, String query, String category, int meters) throws IOException {
//            return toPlaces(simpleGeoClient.search(new Point(latitude, longitude), query, category, meters / 1000.0));
//        }
//
//        public static Place[] findNearby(String address, String query, String category, int meters) throws IOException {
//            return toPlaces(simpleGeoClient.searchByAddress(address, query, category, meters / 1000.0));
//        }
//
//        private static Place[] toPlaces(FeatureCollection features) {
//            Place[] places = new Place[features.getFeatures().size()];
//            for (int i = 0; i < features.getFeatures().size(); i++) {
//                Feature feature = features.getFeatures().get(i);
//                Map<String, Object> properties = feature.getProperties();
//                String[] classifiers = null;
//                try {
//                    classifiers = toStrings((org.json.JSONArray)properties.get("classifiers"));
//                } catch (org.json.JSONException e) {
//                    throw new IllegalStateException("UNCHECKED: this bug should go unhandled; the classifiers: " + properties.get("classifiers"));
//                }
//
//                places[i] = new Place(
//                    Source.SimpleGeo, UUIDGenerator.getUUID(), feature.getSimpleGeoId(), (String)properties.get("name"), classifiers,
//                    feature.getGeometry().getPoint().getLat(), feature.getGeometry().getPoint().getLon(),
//                    (String)properties.get("address"), (String)properties.get("city"), (String)properties.get("province"), (String)properties.get("country"), (String)properties.get("postcode"),
//                    (String)properties.get("website"), (String)properties.get("phone"), (String)properties.get("fax"));
//            }
//
//            return places;
//        }
//
//        private static String[] toStrings(org.json.JSONArray classifiers) throws org.json.JSONException {
//            String[] strings = new String[classifiers.length()];
//            for (int i = 0; i < classifiers.length(); i++) {
//                org.json.JSONObject classifier = classifiers.getJSONObject(i);
//                if (Strings.isNullOrEmpty((String)classifier.get("subcategory"))) {
//                    strings[i] = Joiner.on('\\').join(classifier.get("type"), classifier.get("category"));
//                } else {
//                    strings[i] = Joiner.on('\\').join(classifier.get("type"), classifier.get("category"), classifier.get("subcategory"));
//                }
//            }
//
//            return strings;
//        }
//    }
}
