package com.example.roomservice.Repository;

import com.example.roomservice.Entity.MenuItem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MenuItemRepository extends JpaRepository<MenuItem, Long> {

    Optional<MenuItem> findByItemName(String itemName);
}
