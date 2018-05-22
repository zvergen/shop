package ru.learndev.ejb;

import ru.learndev.domain.Product;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;

@Stateless
@LocalBean
public class ProductsManagerBean {

    @PersistenceContext(unitName = "examplePU")
    private EntityManager entityManager;

    public Product createProduct(String name, int price) {
        Product product = new Product();
        product.setName(name);
        product.setPrice(price);
        entityManager.persist(product);

        return product;
    }

    public List<Product> getProducts() {
        TypedQuery<Product> query = entityManager.createQuery("select p from Product p", Product.class);
        return query.getResultList();
    }
}
