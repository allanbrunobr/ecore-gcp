package com.br.bruno.appweb.models.maps;


import com.google.maps.GeoApiContext;
import com.google.maps.PlacesApi;
import com.google.maps.model.LatLng;
import com.google.maps.model.PlaceType;
import com.google.maps.model.PlacesSearchResult;
import com.google.maps.model.RankBy;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;


/**
 * The PlacesSearchService class is responsible for searching places
 * near the specified latitude and longitude coordinates.
 */
@Service
public class PlacesSearchService {

  private final String apiKey;
  @Getter
  private final GeoApiContext context;


  /**
   * Constructs a new PlacesSearchService with the provided API key.
   *
   * @param apiKey The API key for accessing the Google Places API.
   */
  public PlacesSearchService(@Value("${google.api.key.places}") String apiKey) {
    this.apiKey = apiKey;
    this.context = new GeoApiContext.Builder().apiKey(apiKey).build();
  }

  /**
   * Searches for places based on the given latitude and longitude coordinates.
   *
   * @param latitude  the latitude of the location
   * @param longitude the longitude of the location
   * @return a CompletableFuture that will eventually complete with a PlaceSearchResult object.
   */
  public CompletableFuture<PlaceSearchResult> searchPlaces(double latitude, double longitude) {
    LatLng location = new LatLng(latitude, longitude);
    return CompletableFuture.supplyAsync(() -> {
      PlaceSearchResult result = new PlaceSearchResult();
      try {
        result.setRestaurants(getTopPlaces(PlaceType.RESTAURANT, 5, location, "restaurant"));
        result.setAirports(getTopPlaces(PlaceType.AIRPORT, 2, location, "airport"));
        result.setTouristAttractions(getTopPlaces(
                PlaceType.TOURIST_ATTRACTION, 7, location, "tourist"));
        result.setShoppingMalls(getTopPlaces(PlaceType.SHOPPING_MALL, 5, location, "shopping"));
        result.setHotels(getTopPlaces(PlaceType.LODGING, 5, location, "hotel"));
      } catch (Exception e) {
        throw new RuntimeException("Ocorreu um erro durante o processamento da solicitação.");
      }
      return result;
    });
  }

  /**
   * Retrieves the top places of a given place type within a specified
   * limit around a given location.
   *
   * @param placeType the type of place to retrieve
   * @param limit     the maximum number of places to retrieve
   * @param location  the location around which to retrieve the places
   * @return a list of PlaceInfo objects representing the top places
   * @throws Exception if there is an error retrieving the places
   */
  private List<PlaceInfo> getTopPlaces(PlaceType placeType,
                                         int limit,
                                         LatLng location,
                                         String keyword) throws Exception {
    PlacesSearchResult[] places = PlacesApi
                .nearbySearchQuery(context, location)
                .radius(50000)
                .type(placeType)
                .rankby(RankBy.PROMINENCE)
                .keyword(keyword)
                .await()
                .results;

    Arrays.sort(places, Comparator.comparingInt(place ->
                Optional.ofNullable(place.userRatingsTotal).orElse(0)));
    Collections.reverse(Arrays.asList(places));

    return createPlaceInfos(Arrays.copyOfRange(places, 0, limit));
  }

  /**
   * Creates PlaceInfo objects based on the given PlacesSearchResult array.
   *
   * @param placesSearchResults an array of PlacesSearchResult objects
   *                           representing the search results.
   * @return a List of PlaceInfo objects containing information about the places.
   */
  private List<PlaceInfo> createPlaceInfos(PlacesSearchResult[] placesSearchResults) {
    List<PlaceInfo> placeInfos = new ArrayList<>();
    for (PlacesSearchResult searchResult : placesSearchResults) {
      PlaceInfo placeInfo = new PlaceInfo();
      placeInfo.setName(searchResult.name);
      placeInfo.setVicinity(searchResult.vicinity);

      // Crie URLs personalizadas para o site e o Google Maps para cada lugar.
      String placeId = searchResult.placeId;
      String websiteUrl = "https://www.example.com/place/" + placeId; // URL personalizado para o site
      String googleMapsUrl = "https://maps.google.com/?q=" + searchResult.geometry.location.lat + "," + searchResult.geometry.location.lng; // URL personalizado para o Google Maps
      placeInfo.setWebsiteUrl(websiteUrl);
      placeInfo.setGoogleMapsUrl(googleMapsUrl);
      placeInfos.add(placeInfo);
    }

    return placeInfos;
  }
}

