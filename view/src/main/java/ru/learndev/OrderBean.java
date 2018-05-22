package ru.learndev;

import ru.learndev.domain.Order;
import ru.learndev.domain.Product;
import ru.learndev.ejb.OrdersManagerBean;
import ru.learndev.ejb.ProductsManagerBean;

import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;
import java.io.Serializable;
import java.util.Collections;
import java.util.List;

@Named
@SessionScoped
public class OrderBean implements Serializable {

    private Order order;
    private String name;
    private int price;

    @EJB
    private OrdersManagerBean ordersManagerBean;

    @EJB
    private ProductsManagerBean productsManagerBean;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public void createOrder() {
        if (order == null) {
            order = ordersManagerBean.createOrder();
        }
    }

    public void createProduct() {
        productsManagerBean.createProduct(name, price);
    }

    public List<Product> getProducts() {
        return productsManagerBean.getProducts();
    }

    public void addProduct(Product product) {
        if (order != null) {
            ordersManagerBean.addToOrder(product.getId(), order.getId());
        }
    }

    public List<Product> getProductsInOrder() {
        if (order == null) {
            return Collections.emptyList();
        }

        return ordersManagerBean.getProductsInOrder(order.getId());
    }
}
