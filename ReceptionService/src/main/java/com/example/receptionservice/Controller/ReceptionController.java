package com.example.receptionservice.Controller;

import com.example.receptionservice.Entity.Room;
import com.example.receptionservice.Repository.RoomRepository;
import com.example.receptionservice.Service.ReceptionService;
import com.example.receptionservice.dto.CheckInRequest;
import com.example.receptionservice.dto.CheckInResponse;
import com.example.receptionservice.dto.CheckOutResponse;
import com.example.receptionservice.dto.RoomStatusResponse;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/reception")
public class ReceptionController {

    private final ReceptionService receptionService;
    private final RoomRepository  roomRepository;
    public ReceptionController(ReceptionService receptionService, RoomRepository roomRepository) {
        this.receptionService = receptionService;
        this.roomRepository = roomRepository;
    }

    @PostMapping("/checkin")
    public CheckInResponse checkIn(@Valid @RequestBody CheckInRequest checkInRequest) {
        return receptionService.checkIn(checkInRequest);
    }

    @PostMapping("/checkout/{roomNumber}")
    public CheckOutResponse checkOut(@PathVariable Integer roomNumber) {
        return receptionService.checkOut(roomNumber);
    }

    @GetMapping("/rooms")
    public List<Room> getRooms() {
        return receptionService.getRooms();
    }

    @GetMapping("/rooms/{roomNumber}/status")
    public RoomStatusResponse getRoomStatus(
            @PathVariable Integer roomNumber
    ) {

        Room room =
                roomRepository
                        .findByRoomNumber(roomNumber)
                        .orElseThrow(
                                () -> new RuntimeException(
                                        "Room not found"
                                )
                        );

        return new RoomStatusResponse(
                room.getRoomNumber(),
                room.getStatus()
        );
    }
}
