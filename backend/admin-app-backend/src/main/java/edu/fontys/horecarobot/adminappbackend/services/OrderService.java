package edu.fontys.horecarobot.adminappbackend.services;

import edu.fontys.horecarobot.adminappbackend.dtos.response.OrderResponseModel;
import edu.fontys.horecarobot.databaselibrary.models.Product;
import edu.fontys.horecarobot.databaselibrary.models.ProductOrder;
import edu.fontys.horecarobot.databaselibrary.models.RestaurantOrder;
import edu.fontys.horecarobot.databaselibrary.repositories.ProductRepository;
import edu.fontys.horecarobot.databaselibrary.repositories.RestaurantOrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final RestaurantOrderRepository orderRepository;
    private final ProductRepository productRepository;

    public List<OrderResponseModel> getAllOrders() throws ParseException {
        //addRandomRestaurantOrder();

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

    public void addRandomRestaurantOrder() throws ParseException {
        var rand = new Random();
        var products = productRepository.findAll();
        var dates = Arrays.asList(
                new SimpleDateFormat("dd-MM-yyyy").parse("19-11-2021"),
                new SimpleDateFormat("dd-MM-yyyy").parse("12-09-2021"),
                new SimpleDateFormat("dd-MM-yyyy").parse("07-06-2021"),
                new SimpleDateFormat("dd-MM-yyyy").parse("28-03-2021"),
                new SimpleDateFormat("dd-MM-yyyy").parse("21-01-2021")
        );

        var order = new RestaurantOrder();
        order.setCreatedAt(dates.get(rand.nextInt((int) dates.stream().count())));
        order.setProductOrders(createRandomProductOrder(products));
        var subTotal = Stream.concat(
                order.getProductOrders()
                    .stream()
                    .map(ProductOrder::getProduct)
                    .map(Product::getPrice),
                order.getProductOrders()
                    .stream()
                    .map(ProductOrder::getByProducts)
                    .flatMap(List::stream)
                    .map(Product::getPrice)
                ).collect(Collectors.toList());
        order.setSubTotal(subTotal.stream().mapToDouble(Double::doubleValue).sum());

        orderRepository.saveAndFlush(order);
    }

    private List<ProductOrder> createRandomProductOrder(List<Product> products){
        var rand = new Random();
        List<ProductOrder> list = new ArrayList<>();
        for(var x = 0; x < rand.nextInt(5); x++){
            var productOrder = new ProductOrder();
            productOrder.setProduct(products.get(rand.nextInt((int) products.stream().count())));
            List<Product> byproducts = new ArrayList<>();
            byproducts.add(products.get(rand.nextInt((int) products.stream().count())));
            productOrder.setByProducts(byproducts);
            list.add(productOrder);
        }
        return list;
    }
}
