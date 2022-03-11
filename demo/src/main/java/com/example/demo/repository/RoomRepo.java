package com.example.demo.repository;

import com.example.demo.model.Room;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoomRepo extends JpaRepository<Room, Long>{
}
