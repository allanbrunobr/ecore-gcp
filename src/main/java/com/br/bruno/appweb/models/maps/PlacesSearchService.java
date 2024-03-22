package com.br.bruno.appweb.models.maps;

import com.google.maps.GeoApiContext;
import com.google.maps.PlacesApi;
import com.google.maps.model.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.concurrent.CompletableFuture;

/**
 * The PlacesSearchService class is responsible for searching places near the specified latitude and longitude coordinates.
 */
@Service
public class PlacesSearchService {

    private final String apiKey;
    private final GeoApiContext context;

    public PlacesSearchService(@Value("${google.api.key.places}") String apiKey) {
        this.apiKey = apiKey;
        this.context = new GeoApiContext.Builder().apiKey(apiKey).build();
    }

    public GeoApiContext getContext() {
        return context;
    }

    /**
     * Searches for places near the specified latitude and longitude coordinates.
     *
     * @param latitude  The latitude coordinate.
     * @param longitude The longitude coordinate.
     */
    public CompletableFuture<PlaceSearchResult> searchPlaces(double latitude, double longitude) {
        PlaceSearchResult result = new PlaceSearchResult();
        LatLng location = new LatLng(latitude, longitude);
        try {
            PlacesSearchResponse response = PlacesApi.nearbySearchQuery(context, location)
                    .radius(5000) // Raio de busca em metros
                    .type(PlaceType.TOURIST_ATTRACTION, PlaceType.AIRPORT, PlaceType.RESTAURANT) // Tipos de lugares (atrações turísticas, aeroportos e restaurantes)
                    .rankby(RankBy.PROMINENCE) // Ordenar por relevância
                    .await(); // Executar a consulta

            // Realize as consultas para diferentes tipos de lugares
            PlacesSearchResult[] restaurants = PlacesApi.nearbySearchQuery(context, location)
                    .radius(5000)
                    .type(PlaceType.RESTAURANT)
                    .rankby(RankBy.PROMINENCE)
                    .await()
                    .results;

            PlacesSearchResult[] airports = PlacesApi.nearbySearchQuery(context, location)
                    .radius(5000)
                    .type(PlaceType.AIRPORT)
                    .rankby(RankBy.PROMINENCE)
                    .await()
                    .results;

            PlacesSearchResult[] touristAttractions = PlacesApi.nearbySearchQuery(context, location)
                    .radius(5000)
                    .type(PlaceType.TOURIST_ATTRACTION)
                    .rankby(RankBy.PROMINENCE)
                    .await()
                    .results;

            PlacesSearchResult[] carSearchResults = PlacesApi.nearbySearchQuery(context, location)
                    .radius(5000)
                    .type(PlaceType.CAR_RENTAL)
                    .rankby(RankBy.PROMINENCE)
                    .await()
                    .results;

            result.setRestaurants(Arrays.asList(response.results));
            result.setAirports(Arrays.asList(airports));
            result.setTouristAttractions(Arrays.asList(touristAttractions));
            result.setCarSearchResults(Arrays.asList(carSearchResults));
        } catch (Exception e) {
            e.printStackTrace();
        }

        return CompletableFuture.completedFuture(result);

    }

    /**
     *  public void searchTouristAttractions(double latitude, double longitude) {
     *         LatLng location = new LatLng(latitude, longitude);
     *         try {
     *             PlacesSearchResponse response = PlacesApi.nearbySearchQuery(context, location)
     *                     .radius(5000) // Raio de busca em metros
     *                     .type(PlaceType.TOURIST_ATTRACTION, PlaceType.AIRPORT, PlaceType.RESTAURANT) // Tipo de lugar (neste caso, atrações turísticas)
     *                     .rankby(RankBy.PROMINENCE) // Ordenar por relevância
     *                     .await(); // Executar a consulta
     *
     *             // Processar os resultados
     *             for (int i = 0; i < response.results.length; i++) {
     *                 System.out.println(response.results[i].name); // Nome do lugar
     *                 System.out.println(response.results[i].vicinity); // Endereço do lugar
     *                 System.out.println();
     *             }
     *         } catch (Exception e) {
     *             e.printStackTrace();
     *         }
     *     }
     * **/
}
