package com.ohgiraffers.crud2.border.repository;

import com.ohgiraffers.crud2.border.entity.Border;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface BorderRepository extends JpaRepository<Border, Long> {

    Optional<Object> findByTitle(String title);
}
