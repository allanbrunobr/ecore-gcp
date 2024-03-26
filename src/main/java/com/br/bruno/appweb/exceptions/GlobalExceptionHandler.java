package com.br.bruno.appweb.exceptions;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

/**
 * GlobalExceptionHandler is a class that handles and manages exceptions
 * occurred throughout the application.
 */
@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

  /**
   * Handles an exception by creating a ModelAndView object with an error view,
   * setting the error message as a model attribute and returning the ModelAndView object.
   *
   * @param ex the exception to be handled
   * @return a ModelAndView object with the error view and the error message as a model attribute
   */
  @ExceptionHandler(Exception.class)
  public ModelAndView handleException(Exception ex) {
    ModelAndView modelAndView = new ModelAndView("error");
    modelAndView.addObject("errorMessage", ex.getMessage());
    return modelAndView;
  }
}
