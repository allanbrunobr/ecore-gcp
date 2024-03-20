package com.br.bruno.appweb.models.vision.landmarks;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

/**
 * BoundingPoly represents a polygon with the bounding coordinates.
 */
@Getter
@Setter
 public class BoundingPoly {
    private List<Vertex> vertices;
    private List<NormalizedVertex> normalizedVertices;
  }
