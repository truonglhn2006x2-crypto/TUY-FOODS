package com.example.demo.controller;

import com.example.demo.model.Order;
import com.example.demo.model.OrderItem;
import com.example.demo.model.Product;
import com.example.demo.repository.ProductRepository;
import com.example.demo.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.HashMap;

@RestController
@RequestMapping("/api/orders")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @Autowired
    private ProductRepository productRepository;

    @PostMapping
    public ResponseEntity<?> createOrder(@RequestBody Map<String, Object> body) {
        try {
            Long userId = Long.valueOf(body.get("userId").toString());
            String address = body.get("deliveryAddress").toString();
            String phone = body.get("phone") != null ? body.get("phone").toString() : "";
            String note = body.get("note") != null ? body.get("note").toString() : "";

            List<Map<String, Object>> rawItems = (List<Map<String, Object>>) body.get("items");
            List<OrderItem> orderItems = new ArrayList<>();

            for (Map<String, Object> raw : rawItems) {
                Long productId = Long.valueOf(raw.get("productId").toString());
                Integer quantity = Integer.valueOf(raw.get("quantity").toString());

                Product product = productRepository.findById(productId)
                        .orElseThrow(() -> new RuntimeException("Không tìm thấy sản phẩm " + productId));

                if (!Boolean.TRUE.equals(product.getActive())) {
                    throw new RuntimeException(
                            "Sản phẩm \"" + product.getName() + "\" không còn kinh doanh"
                    );
                }

                if (!Boolean.TRUE.equals(product.getAvailable())) {
                    throw new RuntimeException(
                            "Sản phẩm \"" + product.getName() + "\" đã hết hàng"
                    );
                }

                OrderItem item = new OrderItem();
                item.setProduct(product);
                item.setProductName(product.getName());
                item.setPrice(product.getPrice());
                item.setQuantity(quantity);
                orderItems.add(item);
            }

            BigDecimal orderDiscount =
                    body.get("orderDiscount") != null
                            ? new BigDecimal(body.get("orderDiscount").toString())
                            : BigDecimal.ZERO;

            BigDecimal shippingDiscount =
                    body.get("shippingDiscount") != null
                            ? new BigDecimal(body.get("shippingDiscount").toString())
                            : BigDecimal.ZERO;

            Order order = orderService.createOrder(
                    userId,
                    address,
                    phone,
                    note,
                    orderItems,
                    orderDiscount,
                    shippingDiscount,
                    body.get("orderVoucherCode") != null
                            ? body.get("orderVoucherCode").toString()
                            : null,
                    body.get("shippingVoucherCode") != null
                            ? body.get("shippingVoucherCode").toString()
                            : null
            );

            Map<String, Object> response = new HashMap<>();
            response.put("id", order.getId());
            response.put("totalAmount", order.getTotalAmount());
            response.put("discountAmount", order.getDiscountAmount());
            response.put("status", order.getStatus().name());

            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }

    @GetMapping("/user/{userId}")
    public List<Order> getByUser(@PathVariable Long userId) {
        return orderService.getByUser(userId);
    }

    @GetMapping
    public List<Order> getAllOrders() {
        return orderService.getAllOrders();
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable Long id) {
        try {
            return ResponseEntity.ok(orderService.getById(id));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }

    @PatchMapping("/{id}/status")
    public ResponseEntity<?> updateStatus(@PathVariable Long id, @RequestParam String status) {
        try {
            return ResponseEntity.ok(orderService.updateStatus(id, Order.Status.valueOf(status)));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }
}