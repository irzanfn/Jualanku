package com.bca.bit.jualanku.service.impl;

import com.bca.bit.jualanku.model.Product;
import com.bca.bit.jualanku.model.User;
import com.bca.bit.jualanku.repository.ProductRepository;
import com.bca.bit.jualanku.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductRepository productRepository;

    @Override
    public List<Product> findAllProducts() {
        return productRepository.findAll();
    }

    @Override
    public Optional<Product> findProductById(Long id) {
        Optional<Product> product = productRepository.findById(id);
        if(product.isPresent()){
            return product;
        } else {
            throw new RuntimeException("Product not found");
        }
    }

    @Override
    public Product saveProduct(Product product) {
        product.setDateCreated(new Timestamp(System.currentTimeMillis()));
        product.setDateUpdated(new Timestamp(System.currentTimeMillis()));
        product.setStockOrdered(0);
        product.setStockSold(0);
        product.setIsDeleted("false");
        return productRepository.save(product);
    }

    @Override
    public Product updateProduct(Product product) {
        product.setDateUpdated(new Timestamp(System.currentTimeMillis()));
        return productRepository.save(product);
    }

    @Override
    public void deleteProduct(Long id) {
        Product product = findProductById(id).get();
        product.setIsDeleted("true");
        productRepository.save(product);
    }

    @Override
    public List<Product> findProductsByName(String name) {
        return productRepository.findByName(name);
    }

    @Override
    public List<Product> findProductsBySeller(User seller) {
        return productRepository.findBySeller(seller);
    }
}
