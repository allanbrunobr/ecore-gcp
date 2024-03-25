package com.br.bruno.appweb.controller;

import com.br.bruno.appweb.models.maps.PlaceSearchResult;
import com.br.bruno.appweb.models.maps.PlacesSearchService;
import java.util.concurrent.ExecutionException;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;


/**
 * The MapsController class is responsible for handling requests related to maps.
 * It provides methods for searching places near specified coordinates.
 */
@RestController
public class MapsController {
  /**
   * Searches for places based on the given latitude and longitude coordinates.
   *
   * @param latitude  the latitude of the location
   * @param longitude the longitude of the location
   * @param model     the Model object for rendering the view
   * @return a ModelAndView object representing the view for displaying the search results
   * @throws ExecutionException   if there is an error executing the search query
   * @throws InterruptedException if the search query is interrupted
   */
  @PostMapping("/maps")
  public ModelAndView handleCoordinates(@RequestParam double latitude,
                                        @RequestParam double longitude,
                                        Model model)
            throws ExecutionException, InterruptedException {
    ModelAndView modelAndView = new ModelAndView("maps/maps-result");
    String apiKey = "AIzaSyBMnDHDoSUU1t1DqEXWiF7FheEeMKHNm5A";
    PlacesSearchService placesSearchService = new PlacesSearchService(apiKey);
    PlaceSearchResult placeSearchResult = placesSearchService
                            .searchPlaces(latitude, longitude).get();
    model.addAttribute("placeSearchResult", placeSearchResult);
    return modelAndView;
  }
}






