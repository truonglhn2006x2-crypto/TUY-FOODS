package com.example.demo.service;

import com.example.demo.model.*;
import com.example.demo.repository.OrderRepository;
import com.example.demo.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private ProductRepository productRepository;

    public Order createOrder(Long userId, String address, String phone,
                             String note, List<CartItem> items) {
        Order order = new Order();
        User user = new User();
        user.setId(userId);
        order.setUser(user);
        order.setDeliveryAddress(address);
        order.setPhone(phone);
        order.setNote(note);

        BigDecimal total = BigDecimal.ZERO;
        for (CartItem item : items) {
            Product product = productRepository.findById(item.getProduct().getId())
                    .orElseThrow(() -> new RuntimeException("Không tìm thấy sản phẩm"));
            OrderItem orderItem = new OrderItem();
            orderItem.setOrder(order);
            orderItem.setProduct(product);
            orderItem.setProductName(product.getName());
            orderItem.setPrice(product.getPrice());
            orderItem.setQuantity(item.getQuantity());
            total = total.add(product.getPrice().multiply(BigDecimal.valueOf(item.getQuantity())));
        }

        order.setTotalAmount(total);
        return orderRepository.save(order);
    }

    public List<Order> getByUser(Long userId) {
        return orderRepository.findByUserId(userId);
    }

    public Order getById(Long id) {
        return orderRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy đơn hàng"));
    }

    public Order updateStatus(Long id, Order.Status status) {
        Order order = getById(id);
        order.setStatus(status);
        return orderRepository.save(order);
    }
}
