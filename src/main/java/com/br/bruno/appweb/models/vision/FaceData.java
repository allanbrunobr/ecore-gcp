package com.br.bruno.appweb.models.vision;

import lombok.Getter;
import lombok.Setter;

/**
 * Classe que representa os dados de uma face.
 */
@Getter
@Setter
public class FaceData {
  private int id;
  private String joyLikelihood;
  private String angerLikelihood;
  private String sorrowLikelihood;
  private String surpriseLikelihood;

}
