package com.cache.manager.repository;

import com.cache.manager.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
* Repository interface for Product entity.
*/
@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
}
