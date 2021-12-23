package edu.fontys.horecarobot.adminappbackend.dtos.response;

import edu.fontys.horecarobot.databaselibrary.models.ProductOrder;
import edu.fontys.horecarobot.databaselibrary.models.RestaurantOrder;
import lombok.Data;

import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Data
public class OrderResponseModel {

    public OrderResponseModel(RestaurantOrder order){
        id = order.getId();
        createdAt = order.getCreatedAt();
        subTotal = order.getSubTotal();
        products = Stream.concat(
            order.getProductOrders()
                .stream()
                .map(ProductOrder::getProduct)
                .map(ProductResponseModel::new),
            order.getProductOrders()
                .stream()
                .map(ProductOrder::getByProducts)
                .flatMap(List::stream)
                .map(ProductResponseModel::new)
            ).collect(Collectors.toList());
    }

    private final UUID id;
    private final Date createdAt;
    private final double subTotal;
    private final List<ProductResponseModel> products;
}
