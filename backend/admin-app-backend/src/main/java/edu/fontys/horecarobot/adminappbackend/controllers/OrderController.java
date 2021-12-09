package edu.fontys.horecarobot.adminappbackend.controllers;

import edu.fontys.horecarobot.adminappbackend.dtos.response.ApiResponse;
import edu.fontys.horecarobot.adminappbackend.dtos.response.OrderResponseModel;
import edu.fontys.horecarobot.adminappbackend.services.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@RestController
@RequestMapping(path = "api/order")
@CrossOrigin(origins = "http://localhost:4000")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    @GetMapping
    public ResponseEntity<?> getOrders(
            @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") Date from,
            @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") Date to
    ){
        List<OrderResponseModel> orders;
        System.out.println(from);
        System.out.println(to);

        if(from == null || to == null){
            try {
                orders = orderService.getAllOrders();
            }
            catch (Exception e) {
                e.printStackTrace();
                return ResponseEntity.internalServerError().body(ApiResponse.GENERAL_EXCEPTION_ERROR);
            }
        }
        else{
            try {
                orders = orderService.getOrdersByDates(from, to);
            }
            catch (Exception e) {
                e.printStackTrace();
                return ResponseEntity.internalServerError().body(ApiResponse.GENERAL_EXCEPTION_ERROR);
            }
        }

        return ResponseEntity.ok(ApiResponse.ok().addData("orders", orders));
    }
}
