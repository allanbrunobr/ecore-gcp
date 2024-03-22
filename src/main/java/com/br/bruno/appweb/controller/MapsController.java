package com.br.bruno.appweb.controller;

import com.br.bruno.appweb.models.maps.PlaceSearchResult;
import com.br.bruno.appweb.models.maps.PlacesSearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

@RestController
public class MapsController {


    private final PlacesSearchService placesSearchService;

    public MapsController(PlacesSearchService placesSearchService) {
        this.placesSearchService = placesSearchService;
    }

    @PostMapping("/maps")
    public ModelAndView handleCoordinates(@RequestParam double latitude, @RequestParam double longitude, Model model)
            throws ExecutionException, InterruptedException {
        ModelAndView modelAndView = new ModelAndView("maps/maps-result");
//        String apiKey = "AIzaSyBMnDHDoSUU1t1DqEXWiF7FheEeMKHNm5A";
//        PlacesSearchService placesSearchService = new PlacesSearchService(apiKey);
//
//        PlaceSearchResult placeSearchResult = placesSearchService.searchPlaces(latitude, longitude).get(); // Wait for results
//        model.addAttribute("placeSearchResult", placeSearchResult);

        return modelAndView;
    }

}





