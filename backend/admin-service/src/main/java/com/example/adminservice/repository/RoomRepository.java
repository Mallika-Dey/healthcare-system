package com.example.adminservice.repository;

import com.example.adminservice.entity.Room;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface RoomRepository extends JpaRepository<Room, Integer> {
    @Query(value = "SELECT max(roomNumber) FROM Room")
    public Integer getLastRoomNo();

    public Optional<Room> findByRoomNumber(Integer roomNumber);
}
