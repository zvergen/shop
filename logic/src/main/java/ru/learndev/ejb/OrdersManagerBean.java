package ru.learndev.ejb;

import ru.learndev.domain.Order;
import ru.learndev.domain.Product;
import ru.learndev.domain.ProductInOrder;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Stateless
@LocalBean
public class OrdersManagerBean {

    @PersistenceContext(unitName = "examplePU")
    private EntityManager entityManager;

    public Order createOrder() {
        Order order = new Order();
        entityManager.persist(order);

        return order;
    }

    public boolean addToOrder(long productId, long orderId) {
        Product product = entityManager.find(Product.class, productId);
        if (product == null) {
            return false;
        }

        Order order = entityManager.find(Order.class, orderId);
        if (order == null) {
            return false;
        }

        ProductInOrder productInOrder = new ProductInOrder();
        productInOrder.setOrder(order);
        productInOrder.setProduct(product);
        productInOrder.setQuantity(1);

        entityManager.persist(productInOrder);

        return true;
    }

    public List<Product> getProductsInOrder(long orderId) {
        Order order = entityManager.find(Order.class, orderId);
        if (order == null) {
            return Collections.emptyList();
        }

        List<ProductInOrder> productInOrders = order.getProductInOrders();
        List<Product> result = new ArrayList<>();
        for (ProductInOrder productInOrder : productInOrders) {
            result.add(productInOrder.getProduct());
        }

        return result;
    }
}
