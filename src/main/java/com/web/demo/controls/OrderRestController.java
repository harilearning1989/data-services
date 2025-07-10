package com.web.demo.controls;

import com.web.demo.models.order.Order;
import com.web.demo.models.order.OrderItem;
import com.web.demo.repos.order.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashSet;
import java.util.Set;

@RestController
@RequestMapping("order")
public class OrderRestController {

    @Autowired
    private OrderRepository orderRepository;

    @GetMapping(name = "Save Order", value = "saveOrder")
    public void saveOrder() {
        //Order order = new Order();

        Set<OrderItem> orderItemSet = new HashSet<>();
       /* OrderItem orderItem1 = new OrderItem();
        orderItem1.setQuantity(10);
        orderItemSet.add(orderItem1);

        OrderItem orderItem2 = new OrderItem();
        orderItem2.setQuantity(20);
        orderItemSet.add(orderItem2);

        order.setOrderName("hari");
        order.setOrderItems(orderItemSet);
        */

        OrderItem orderItem = OrderItem.builder()
                .quantity(20)
                .build();
        orderItemSet.add(orderItem);

        Order order = Order.builder()
                .orderName("Hari")
                .orderItems(orderItemSet)
                .build();

        orderRepository.save(order);
    }

    @GetMapping(name = "Update Order", value = "updateOrder")
    void updateOrder() {
        Order order = orderRepository.findById(1L).get();
        order.setOrderName("DELIVERED");
        orderRepository.save(order);
    }

    /*@GetMapping(name = "Save Order", value = "allOrders")
    void allOrders() {
        List<Order> orders = orderRepository.findAll();
        orders.forEach((o) -> {
            System.out.println("order id :: " + o.getOrderId());
            o.getOrderItems().forEach((orderItem -> {
                System.out.println("orderItem :: " + orderItem.getId());
            }));
        });
    }*/

    @GetMapping(name = "Find Order", value = "findOrder")
    void findByOrderTrackingNumber() {
        // add fetch type as EAGER
//        order.getOrderItems().forEach((o) -> {
//            System.out.println(o.getId());
//        });
    }

    @GetMapping(name = "Delete Order", value = "deleteOrder")
    void deleteOrder() {
        orderRepository.deleteById(1L);
    }
}
