package com.bca.bit.jualanku.service;

import com.bca.bit.jualanku.model.Product;
import com.bca.bit.jualanku.model.User;

import java.util.List;
import java.util.Optional;

public interface ProductService {
    // Find all products
    List<Product> findAllProducts();

    // Find product by id
    Optional<Product> findProductById(Long id);

    // Save product
    Product saveProduct(Product product);

    // Delete product
    void deleteProduct(Long id);

    // Update product
    Product updateProduct(Product product);

    // Find product by name
    List<Product> findProductsByName(String name);

    // Find product by seller
    List<Product> findProductsBySeller(User seller);
}
