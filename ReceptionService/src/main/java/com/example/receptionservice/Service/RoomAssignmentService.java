package com.example.receptionservice.Service;

import com.example.receptionservice.Entity.Room;
import com.example.receptionservice.Enums.RoomStatus;
import com.example.receptionservice.Repository.RoomRepository;
import com.example.receptionservice.dto.CheckInRequest;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;

@Service
public class RoomAssignmentService {

    private final RoomRepository roomRepository;
    public RoomAssignmentService(RoomRepository roomRepository) {
        this.roomRepository = roomRepository;
    }

    public Room assignRoom(CheckInRequest checkInRequest) {
        //Checking available rooms
        List<Room> availableRooms = roomRepository.findByRoomTypeAndStatus(checkInRequest.getRoomType(), RoomStatus.AVAILABLE);

        if(availableRooms.isEmpty()) {
            throw new RuntimeException("No room available for this type" + checkInRequest.getRoomType());
        }

        //Preferred floor
        if (checkInRequest.getPreferredFloor() != null) {
            List<Room> floorRooms = availableRooms.stream()
                    .filter(room -> room.getFloorNumber().equals(checkInRequest.getPreferredFloor())).toList();

            if (!floorRooms.isEmpty()) {
                availableRooms = floorRooms;
            }
        }

        //Elevator preference
        if(checkInRequest.getNearElevator() != null) {
            List<Room> elevatorRooms = availableRooms.stream()
                    .filter(room -> room.getNearElevator().equals(checkInRequest.getNearElevator())).toList();

            if (!elevatorRooms.isEmpty()) {
                availableRooms = elevatorRooms;
            }
        }

        // Stair preferences
        if (checkInRequest.getNearStairs() != null) {
            List<Room> stairRooms = availableRooms.stream()
                    .filter(room -> room.getNearStairs().equals(checkInRequest.getNearStairs()))
                    .toList();

            if (!stairRooms.isEmpty()) {
                availableRooms = stairRooms;
            }
        }

        //Longest clean room
        return availableRooms.stream()
                .sorted(Comparator.comparing(Room::getLastCleaned))
                .findFirst()
                .orElseThrow();
    }

}
