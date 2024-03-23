package com.br.bruno.appweb.models.maps;


import java.util.List;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * This class represents the result of a place search, containing lists
 * of different types of places.
 */
@Getter
@Setter
@NoArgsConstructor
public class PlaceSearchResult {

  private List<PlaceInfo> restaurants;
  private List<PlaceInfo> airports;
  private List<PlaceInfo> touristAttractions;
  private List<PlaceInfo> shoppingMalls;
  private List<PlaceInfo> hotels;
}
