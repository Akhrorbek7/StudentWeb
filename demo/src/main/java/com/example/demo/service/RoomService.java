package com.example.demo.service;

import com.example.demo.dto.RoomDto;
import com.example.demo.exception.CustomGlobalExceptionHandler;
import com.example.demo.model.Room;
import com.example.demo.repository.RoomRepo;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

@Service
public class RoomService {
    private final RoomRepo roomRepo;
    private final ModelMapper modelMapper;

    public RoomService(RoomRepo roomRepo, ModelMapper modelMapper) {
        this.roomRepo = roomRepo;
        this.modelMapper = modelMapper;
    }

    //Main functions
    public RoomDto get(Long id){
        return modelMapper.map(getEntity(id), RoomDto.class);
    }

    public List<RoomDto> getAll(){
        List<Room> roomList = roomRepo.findAllAndDeletedAtNull();
        List<Room> list = new LinkedList<>();
        if(!roomList.isEmpty()){
            for (Room room : roomList){
                if(room.getDeletedAt() == null){
                    list.add(room);
                }
            }
            return list.stream().map(room -> modelMapper.map(room, RoomDto.class)).toList();
        }
        throw new CustomGlobalExceptionHandler("Room list not found");
    }

    public void create(RoomDto dto){
        Room room = modelMapper.map(dto, Room.class);
        room.setCreatedAt(LocalDateTime.now());
        room.setStatus(true);
        roomRepo.save(room);
    }

    public void update(Long id, RoomDto dto){
        Room room = modelMapper.map(dto, Room.class);
        Room entity = getEntity(id);
        room.setId(id);
        room.setUpdatedAt(LocalDateTime.now());
        room.setCreatedAt(entity.getCreatedAt());
        room.setStatus(entity.getStatus());
        roomRepo.save(room);
    }

    public void delete(Long id){
        Room room = getEntity(id);
        room.setDeletedAt(LocalDateTime.now());
        roomRepo.save(room);
    }

    //Secondary functions
    public Room getEntity(Long id) {
        Optional<Room> optional = roomRepo.findByIdAndDeletedAtNull(id);
        if(optional.isEmpty()){
            throw new CustomGlobalExceptionHandler("Room not found");
        }
        return optional.get();
    }
}
