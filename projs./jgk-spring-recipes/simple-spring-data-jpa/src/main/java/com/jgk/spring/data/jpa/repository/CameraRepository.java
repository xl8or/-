package com.jgk.spring.data.jpa.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.jgk.spring.data.jpa.domain.Camera;

public interface CameraRepository extends JpaRepository<Camera, Long> {
    List<Camera> findAllOrdered();
}
