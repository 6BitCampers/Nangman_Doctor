package com._6bitcampers.nangman_doctor.baedongwoo.controller.payment;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.servlet.ModelAndView;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(IllegalArgumentException.class)
    public ModelAndView handleIllegalArgumentException(IllegalArgumentException ex) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("error", ex.getMessage());
        modelAndView.setViewName("paymentError");
        return modelAndView;
    }

    @ExceptionHandler(MissingServletRequestParameterException.class)
    public ModelAndView handleMissingParams(MissingServletRequestParameterException ex) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("error", "필수 파라미터가 누락되었습니다: " + ex.getParameterName());
        modelAndView.setViewName("paymentError");
        return modelAndView;
    }
}