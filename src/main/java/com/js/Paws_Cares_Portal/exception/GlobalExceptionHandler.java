package com.js.Paws_Cares_Portal.exception;

import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.MissingServletRequestParameterException;
 import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;


@ControllerAdvice
public class GlobalExceptionHandler {

    // Handles missing required parameters (like empty form fields)
    @ExceptionHandler(MissingServletRequestParameterException.class)
    public String handleMissingParams(MissingServletRequestParameterException ex, ModelMap model) {
        model.put("error", "Please fill all the required fields before confirming payment.");
        return "user/PayDonation"; // redirect back to same page with error
    }

    // Handles wrong data type (e.g., blank value for a double amount)
    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public String handleTypeMismatch(MethodArgumentTypeMismatchException ex, ModelMap model) {
        model.put("error", "Invalid data entered. Please check the fields and try again.");
        return "user/PayDonation";
    }

    // Catch-all fallback (for any unexpected error)
    @ExceptionHandler(Exception.class)
    public String handleGeneralException(Exception ex, ModelMap model) {
        model.put("error", "Something went wrong. Please try again later.");
        return "error"; // optional: create error.html if you want a generic error page
    }
}
