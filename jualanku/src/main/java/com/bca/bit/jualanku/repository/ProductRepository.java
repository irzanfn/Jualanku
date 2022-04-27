package com.bca.bit.jualanku.repository;

import com.bca.bit.jualanku.model.Product;
import com.bca.bit.jualanku.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.web.bind.annotation.RequestAttribute;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long> {
    @Query(value = "SELECT * FROM T_PRODUCT WHERE NAME = :name AND IS_DELETED = 'false'", nativeQuery = true)
    List<Product> findByName(@RequestAttribute("name") String name);

    @Query(value = "SELECT * FROM T_PRODUCT WHERE SELLER_ID = :seller AND IS_DELETED = 'false'", nativeQuery = true)
    List<Product> findBySeller(@RequestAttribute("seller") User seller);

    @Query(value = "SELECT * FROM T_PRODUCT WHERE IS_DELETED = 'false'", nativeQuery = true)
    List<Product> findAll();
}