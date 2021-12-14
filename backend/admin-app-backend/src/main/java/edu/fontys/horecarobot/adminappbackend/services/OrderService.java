package edu.fontys.horecarobot.adminappbackend.services;

import edu.fontys.horecarobot.adminappbackend.dtos.response.OrderResponseModel;
import edu.fontys.horecarobot.databaselibrary.repositories.RestaurantOrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final RestaurantOrderRepository orderRepository;

    public List<OrderResponseModel> getAllOrders(){
        return orderRepository.findAll()
                .stream()
                .map(OrderResponseModel::new)
                .collect(Collectors.toList());
    }

    public List<OrderResponseModel> getOrdersByDates(Date from, Date to){
        return orderRepository.findAll()
                .stream()
                .filter(i -> i.getCreatedAt().after(from) && i.getCreatedAt().before(to))
                .map(OrderResponseModel::new)
                .collect(Collectors.toList());
    }
}
