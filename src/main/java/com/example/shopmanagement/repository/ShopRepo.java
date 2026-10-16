package com.example.shopmanagement.repository;

import com.example.shopmanagement.model.Shop;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

public interface ShopRepo extends JpaRepository<Shop,Long> {
}
