package edu.fontys.horecarobot.adminappbackend.controllers;

import edu.fontys.horecarobot.adminappbackend.dtos.response.ApiResponse;
import edu.fontys.horecarobot.adminappbackend.dtos.response.OrderResponseModel;
import edu.fontys.horecarobot.adminappbackend.services.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(path = "api/order")
@CrossOrigin(origins = "http://localhost:4000")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    @GetMapping
    public ResponseEntity<?> getOrders(@RequestParam Optional<String> from, @RequestParam Optional<String> to) throws ParseException {
        List<OrderResponseModel> orders;

        if(from.isEmpty() || to.isEmpty()){
            try {
                orders = orderService.getAllOrders();
            }
            catch (Exception e) {
                e.printStackTrace();
                return ResponseEntity.internalServerError().body(ApiResponse.GENERAL_EXCEPTION_ERROR);
            }
        }
        else{
            var fromDate = new SimpleDateFormat("yyyy-MM-dd").parse(from.get());
            var toDate = new SimpleDateFormat("yyyy-MM-dd").parse(to.get());

            try {
                orders = orderService.getOrdersByDates(fromDate, toDate);
            }
            catch (Exception e) {
                e.printStackTrace();
                return ResponseEntity.internalServerError().body(ApiResponse.GENERAL_EXCEPTION_ERROR);
            }
        }

        return ResponseEntity.ok(ApiResponse.ok().addData("orders", orders));
    }
}
