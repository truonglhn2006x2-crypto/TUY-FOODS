package com.example.demo.service;

import com.example.demo.model.*;
import com.example.demo.repository.OrderRepository;
import com.example.demo.repository.ProductRepository;
import com.example.demo.repository.VoucherRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private MembershipService membershipService;

    @Autowired
    private VoucherRepository voucherRepository;

    private static final BigDecimal SHIPPING_FEE =
            BigDecimal.valueOf(20_000);

    public Order createOrder(
            Long userId,
            String address,
            String phone,
            String note,
            List<OrderItem> items,
            BigDecimal orderDiscount,
            BigDecimal shippingDiscount,
            String orderVoucherCode,
            String shippingVoucherCode
    ) {

        Order order = new Order();

        User user = new User();
        user.setId(userId);

        order.setUser(user);
        order.setDeliveryAddress(address);
        order.setPhone(phone);
        order.setNote(note);

        BigDecimal subtotal = BigDecimal.ZERO;

        for (OrderItem item : items) {

            item.setOrder(order);

            subtotal = subtotal.add(
                    item.getPrice().multiply(
                            BigDecimal.valueOf(item.getQuantity())
                    )
            );
        }

        /*
         * ==============================
         * MEMBERSHIP DISCOUNT
         * ==============================
         *
         * Member   = 0%
         * Veteran  = 5%
         * VIP      = 10%
         */
        Membership membership =
                membershipService.getMembership(userId);

        BigDecimal membershipDiscount =
                BigDecimal.ZERO;

        if ("Veteran".equalsIgnoreCase(
                membership.getLevel()
        )) {

            membershipDiscount =
                    subtotal
                            .multiply(BigDecimal.valueOf(5))
                            .divide(BigDecimal.valueOf(100));

        } else if ("VIP".equalsIgnoreCase(
                membership.getLevel()
        )) {

            membershipDiscount =
                    subtotal
                            .multiply(BigDecimal.valueOf(10))
                            .divide(BigDecimal.valueOf(100));
        }

        /*
         * ==============================
         * ORDER VOUCHER
         * ==============================
         */
        BigDecimal voucherDiscount =
                BigDecimal.ZERO;

        if (orderVoucherCode != null &&
                !orderVoucherCode.trim().isEmpty()) {

            String code =
                    orderVoucherCode
                            .trim()
                            .toUpperCase();

            Voucher voucher =
                    voucherRepository
                            .findByCode(code)
                            .orElseThrow(
                                    () -> new RuntimeException(
                                            "Voucher không tồn tại"
                                    )
                            );

            validateVoucher(
                    voucher,
                    subtotal
            );

            /*
             * TUYFOOD10:
             * giảm 10%, tối đa 50.000đ
             */
            if ("TUYFOOD10".equals(code)) {

                voucherDiscount =
                        subtotal
                                .multiply(
                                        BigDecimal.valueOf(10)
                                )
                                .divide(
                                        BigDecimal.valueOf(100)
                                );

                voucherDiscount =
                        voucherDiscount.min(
                                BigDecimal.valueOf(50_000)
                        );

                /*
                 * GIAM20K:
                 * giảm cố định 20.000đ
                 */
            } else if ("GIAM20K".equals(code)) {

                voucherDiscount =
                        BigDecimal.valueOf(20_000)
                                .min(subtotal);

            } else {

                /*
                 * Voucher DB khác:
                 * discount được hiểu là %
                 */
                if (voucher.getDiscount() != null) {

                    voucherDiscount =
                            subtotal
                                    .multiply(
                                            voucher.getDiscount()
                                    )
                                    .divide(
                                            BigDecimal.valueOf(100)
                                    );
                }
            }
        }

        /*
         * ==============================
         * SHIPPING VOUCHER
         * ==============================
         */
        BigDecimal calculatedShippingDiscount =
                BigDecimal.ZERO;

        if (shippingVoucherCode != null &&
                !shippingVoucherCode.trim().isEmpty()) {

            String code =
                    shippingVoucherCode
                            .trim()
                            .toUpperCase();

            Voucher voucher =
                    voucherRepository
                            .findByCode(code)
                            .orElseThrow(
                                    () -> new RuntimeException(
                                            "Voucher vận chuyển không tồn tại"
                                    )
                            );

            validateVoucher(
                    voucher,
                    subtotal
            );

            /*
             * FREESHIP:
             * giảm toàn bộ phí vận chuyển.
             */
            if ("FREESHIP".equals(code)) {

                calculatedShippingDiscount =
                        SHIPPING_FEE;
            }
        }

        /*
         * ==============================
         * TỔNG DISCOUNT
         * ==============================
         *
         * Membership
         * + Voucher đơn hàng
         * + Voucher vận chuyển
         */
        BigDecimal totalDiscount =
                membershipDiscount
                        .add(voucherDiscount)
                        .add(calculatedShippingDiscount);

        order.setDiscountAmount(
                totalDiscount
        );

        /*
         * ==============================
         * TỔNG TIỀN
         * ==============================
         *
         * Tiền món
         * - Membership
         * - Voucher đơn hàng
         * + Phí ship
         * - Voucher ship
         */
        BigDecimal total =
                subtotal
                        .subtract(membershipDiscount)
                        .subtract(voucherDiscount)
                        .add(SHIPPING_FEE)
                        .subtract(calculatedShippingDiscount);

        if (total.compareTo(
                BigDecimal.ZERO
        ) < 0) {

            total = BigDecimal.ZERO;
        }

        order.setTotalAmount(total);

        order.setItems(items);

        return orderRepository.save(order);
    }

    /*
     * ==============================
     * VALIDATE VOUCHER
     * ==============================
     */
    private void validateVoucher(
            Voucher voucher,
            BigDecimal subtotal
    ) {

        if (!Boolean.TRUE.equals(
                voucher.getStatus()
        )) {

            throw new RuntimeException(
                    "Voucher đã bị khóa"
            );
        }

        LocalDateTime now =
                LocalDateTime.now();

        if (voucher.getStartDate() != null &&
                now.isBefore(
                        voucher.getStartDate()
                )) {

            throw new RuntimeException(
                    "Voucher chưa có hiệu lực"
            );
        }

        if (voucher.getEndDate() != null &&
                now.isAfter(
                        voucher.getEndDate()
                )) {

            throw new RuntimeException(
                    "Voucher đã hết hạn"
            );
        }

        BigDecimal minOrder =
                voucher.getMinOrder() != null
                        ? voucher.getMinOrder()
                        : BigDecimal.ZERO;

        if (subtotal.compareTo(
                minOrder
        ) < 0) {

            throw new RuntimeException(
                    "Đơn hàng chưa đạt giá trị tối thiểu"
            );
        }
    }

    public List<Order> getByUser(Long userId) {
        return orderRepository.findByUserId(userId);
    }

    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }

    public Order getById(Long id) {

        return orderRepository.findById(id)
                .orElseThrow(
                        () -> new RuntimeException(
                                "Không tìm thấy đơn hàng"
                        )
                );
    }

    public Order updateStatus(
            Long id,
            Order.Status status
    ) {

        Order order = getById(id);

        order.setStatus(status);

        return orderRepository.save(order);
    }
}