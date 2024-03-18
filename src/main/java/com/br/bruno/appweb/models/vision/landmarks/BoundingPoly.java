package com.br.bruno.appweb.models.vision.landmarks;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class BoundingPoly {

    private List<Vertex> vertices;
    private List<Vertex> normalizedVertices;

}
