package com.br.bruno.appweb.models.maps;

import com.google.maps.model.PlacesSearchResult;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class PlaceSearchResult {
    private List<PlacesSearchResult> restaurants;
    private List<PlacesSearchResult> airports;
    private List<PlacesSearchResult> touristAttractions;
    private List<PlacesSearchResult> carSearchResults;
}
