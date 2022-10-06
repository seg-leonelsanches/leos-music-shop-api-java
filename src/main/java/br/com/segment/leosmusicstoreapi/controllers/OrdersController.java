package br.com.segment.leosmusicstoreapi.controllers;

import br.com.segment.leosmusicstoreapi.components.SegmentHelper;
import br.com.segment.leosmusicstoreapi.dtos.OrderDrumKitPostDto;
import br.com.segment.leosmusicstoreapi.dtos.OrderPostDto;
import br.com.segment.leosmusicstoreapi.dtos.outputs.UserOutput;
import br.com.segment.leosmusicstoreapi.helpers.MapsHelper;
import br.com.segment.leosmusicstoreapi.models.Customer;
import br.com.segment.leosmusicstoreapi.models.DrumKit;
import br.com.segment.leosmusicstoreapi.models.Order;
import br.com.segment.leosmusicstoreapi.models.OrderDrumKit;
import br.com.segment.leosmusicstoreapi.repositories.CustomerRepository;
import br.com.segment.leosmusicstoreapi.repositories.DrumKitRepository;
import br.com.segment.leosmusicstoreapi.repositories.OrderRepository;
import com.sun.istack.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.lang.reflect.InvocationTargetException;
import java.util.*;

@RestController
@RequestMapping(path = "/orders")
@CrossOrigin
public class OrdersController {

    @Autowired
    private DrumKitRepository drumKitRepository;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private SegmentHelper segmentHelper;

    @GetMapping("")
    public ResponseEntity<?> getAllOrders() {
        return new ResponseEntity<>(orderRepository.findAll(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getOneOrder(@PathVariable @NotNull Long id) {
        Optional<Order> order = orderRepository.findById(id);
        if (order.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(order, HttpStatus.OK);
    }

    @PostMapping("")
    public ResponseEntity<?> addOrder(@RequestBody OrderPostDto orderPostDto)
            throws IllegalAccessException, InvocationTargetException {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserOutput principal = (UserOutput) authentication.getPrincipal();

        Optional<Customer> attemptedCustomer = customerRepository.findById(principal.getId());
        if (attemptedCustomer.isEmpty()) {
            return new ResponseEntity<>("You are not authenticated as a Customer. " +
                    "Please log in or create an account to place orders.",
                    HttpStatus.PRECONDITION_FAILED);
        }

        Customer customer = attemptedCustomer.get();
        List<OrderDrumKitPostDto> orderDrumKits = orderPostDto.getOrderDrumKits();
        if (orderDrumKits == null || orderDrumKits.size() == 0) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        Order order = new Order(null, customer, new HashSet<>());
        for (OrderDrumKitPostDto orderDrumKitPostDto: orderDrumKits) {
            Optional<DrumKit> attemptedDrumKit = drumKitRepository.findById(orderDrumKitPostDto.getDrumKitId());
            if (attemptedDrumKit.isEmpty()) {
                continue;
            }

            DrumKit drumKit = attemptedDrumKit.get();
            OrderDrumKit orderDrumKit = new OrderDrumKit(
                    null,
                    orderDrumKitPostDto.getQuantity(),
                    drumKit.getPrice(),
                    null,
                    drumKit);
            order.getOrderDrumKits().add(orderDrumKit);
        }

        orderRepository.save(order);

        segmentHelper.trackEvent("Order Placed",
                principal.getId().toString(),
                MapsHelper.objectToMap(order, 0));

        return new ResponseEntity<>(order, HttpStatus.ACCEPTED);
    }
}
