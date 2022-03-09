package com.example.demo.service;

import com.example.demo.dto.RoomDto;
import com.example.demo.exception.CustomGlobalExceptionHandler;
import com.example.demo.model.Room;
import com.example.demo.repository.RoomRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

@Service
public class RoomService {
    @Autowired
    private RoomRepository roomRepository;

    @Autowired
    private ModelMapper modelMapper;

    //Main functions
    public RoomDto get(Long id){
        return modelMapper.map(getEntity(id), RoomDto.class);
    }

    public List<RoomDto> getAll(){
        List<Room> roomList = roomRepository.findAllAndDeletedAtNull();
        List<Room> list = new LinkedList<>();
        if(!roomList.isEmpty()){
            for (Room room : roomList){
                if(room.getDeletedAt() == null){
                    list.add(room);
                }
            }
            List<RoomDto> roomDtoList = list.stream().map(room -> modelMapper.map(room, RoomDto.class)).toList();
            return roomDtoList;
        }
        throw new CustomGlobalExceptionHandler("Room list not found");
    }

    public void create(RoomDto dto){
        Room room = modelMapper.map(dto, Room.class);
        room.setCreatedAt(LocalDateTime.now());
        room.setStatus(true);
        roomRepository.save(room);
    }

    public void update(Long id, RoomDto dto){
        Room room = modelMapper.map(dto, Room.class);
        Room entity = getEntity(id);
        room.setId(id);
        room.setUpdatedAt(LocalDateTime.now());
        room.setCreatedAt(entity.getCreatedAt());
        room.setStatus(entity.getStatus());
        roomRepository.save(room);
    }

    public void delete(Long id){
        Room room = getEntity(id);
        room.setDeletedAt(LocalDateTime.now());
        roomRepository.save(room);
    }

    //Secondary functions
    private Room getEntity(Long id) {
        Optional<Room> optional = roomRepository.findByIdAndDeletedAtNull(id);
        if(optional.isEmpty()){
            throw new CustomGlobalExceptionHandler("Room not found");
        }
        return optional.get();
    }
}
