package com.account.mappers;


import com.account.entities.User;
import com.account.utils.dto.UserDto;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
public class UserMapper {

    private final ModelMapper modelMapper = new ModelMapper();

    public User fromUserDto(UserDto userDto){
        return modelMapper.map(userDto, User.class);
    }

    public UserDto fromUser(User user){
        return modelMapper.map(user, UserDto.class);
    }
}
