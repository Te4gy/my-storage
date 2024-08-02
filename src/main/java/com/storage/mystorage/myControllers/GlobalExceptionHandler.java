//package com.storage.mystorage.myControllers;
//
//
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.ControllerAdvice;
//import org.springframework.web.bind.annotation.ExceptionHandler;
//import org.springframework.web.context.request.WebRequest;
//
////Класс, который перехватывает исключения и позволяет обрабатывать их
//@ControllerAdvice
//public class GlobalExceptionHandler {
//
//
//    //Метод в котором мы описываем что перехватывать и что делать
//    @ExceptionHandler(Exception.class)
//    //Перехватывает запрос, который пришел к нам и вызвал ошибку
//    public ResponseEntity<String> handleGlobalException(Exception ex, WebRequest request) {
//
//        // false - не будет включать информацию о клиенте
//        String requestDetails = request.getDescription(false);
//        String errorDetails = "Internal server error: " + ex.getMessage() + " at " + requestDetails;
//
//        //оформление ответа
//        return new ResponseEntity<>(errorDetails, HttpStatus.INTERNAL_SERVER_ERROR);
//    }
//}
