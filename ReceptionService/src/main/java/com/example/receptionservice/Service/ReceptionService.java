package com.example.receptionservice.Service;

import com.example.receptionservice.Entity.GuestStay;
import com.example.receptionservice.Entity.Room;
import com.example.receptionservice.Entity.RoomRate;
import com.example.receptionservice.Enums.GuestStayStatus;
import com.example.receptionservice.Enums.RoomStatus;
import com.example.receptionservice.Repository.GuestStayRepository;
import com.example.receptionservice.Repository.RoomRateRepository;
import com.example.receptionservice.Repository.RoomRepository;
import com.example.receptionservice.dto.CheckInRequest;
import com.example.receptionservice.dto.CheckInResponse;
import com.example.receptionservice.dto.CheckOutResponse;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class ReceptionService {

    private final RestTemplate  restTemplate;
    private final RoomRepository roomRepository;
    private final RoomVacatedPublisher roomVacatedPublisher;
    private final GuestStayRepository guestStayRepository;
    private final RoomRateRepository  roomRateRepository;
    private final RoomAssignmentService roomAssignmentService;

    public ReceptionService(RestTemplate restTemplate,
            RoomRepository roomRepository,  GuestStayRepository guestStayRepository,
                            RoomAssignmentService roomAssignmentService,  RoomRateRepository roomRateRepository,
                            RoomVacatedPublisher roomVacatedPublisher) {
        this.roomRepository = roomRepository;
        this.guestStayRepository = guestStayRepository;
        this.roomAssignmentService = roomAssignmentService;
        this.roomRateRepository = roomRateRepository;
        this.restTemplate = restTemplate;
        this.roomVacatedPublisher = roomVacatedPublisher;
    }

    public CheckInResponse checkIn(CheckInRequest checkInRequest) {
        Optional<GuestStay> checkDoubleCheckIn =
                guestStayRepository.findByGuestNameAndStatus(
                        checkInRequest.getGuestName(),
                        GuestStayStatus.CHECKED_IN
                );

        if (checkDoubleCheckIn.isPresent()) {
            throw new RuntimeException(
                    "Guest already checked in"
            );
        }

        Room room = roomAssignmentService.assignRoom(checkInRequest);

        RoomRate roomRate = roomRateRepository.findByRoomType(checkInRequest.getRoomType())
                .orElseThrow();
        room.setStatus(RoomStatus.OCCUPIED);
        roomRepository.save(room);

        GuestStay guestStay = new GuestStay();
        guestStay.setGuestName(checkInRequest.getGuestName());
        guestStay.setRoomNumber(room.getRoomNumber());
        guestStay.setRoomType(room.getRoomType());
        guestStay.setNightlyRate(roomRate.getNightlyRate());
        guestStay.setNumberOfNights(checkInRequest.getNumberOfNights());
        guestStay.setCheckInDate(LocalDateTime.now());
        guestStay.setTotalBill(BigDecimal.ZERO);
        guestStay.setStatus(GuestStayStatus.CHECKED_IN);

        guestStayRepository.save(guestStay);

        return new CheckInResponse(checkInRequest.getGuestName(), room.getRoomNumber(), "Check-in successful");
    }

    public CheckOutResponse checkOut(Integer roomNumber) {
        GuestStay guestStay = guestStayRepository.findByRoomNumberAndStatus(roomNumber, GuestStayStatus.CHECKED_IN)
                .orElseThrow(() -> new RuntimeException("No active guests found"));
        Room room = roomRepository.findByRoomNumber(roomNumber).orElseThrow(() -> new RuntimeException("Room not found"));

        BigDecimal roomCharge =
                guestStay.getNightlyRate()
                        .multiply(
                                BigDecimal.valueOf(
                                        guestStay.getNumberOfNights()
                                )
                        );

        String url =
                "http://localhost:8080/room-service/rooms/"
                        + roomNumber
                        + "/total";

        BigDecimal foodCharge =
                restTemplate.getForObject(
                        url,
                        BigDecimal.class
                );

        if (foodCharge == null) {
            foodCharge = BigDecimal.ZERO;
        }

        BigDecimal totalBill =
                roomCharge.add(foodCharge);
        guestStay.setTotalBill(totalBill);
        guestStay.setCheckOutDate(LocalDateTime.now());
        guestStay.setStatus(GuestStayStatus.CHECKED_OUT);
        guestStayRepository.save(guestStay);

        room.setStatus(RoomStatus.DIRTY);
        roomRepository.save(room);

        roomVacatedPublisher.publishRoomVacated(roomNumber);

        return new CheckOutResponse(
                guestStay.getGuestName(),
                roomNumber,
                totalBill,
                "Check-out successful"
        );
    }

    public List<Room> getRooms() {
        return roomRepository.findAll();
    }
}
