package com.finance_management.mappers;

import com.account.entities.User;
import com.account.mappers.UserMapper;
import com.account.utils.dto.UserDto;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;


class UserMapperTest {


    UserMapper userMapper = new UserMapper();

    @Test
    void shouldReturnUser(){
        UserDto userDto = UserDto.builder().username("toto").externalId(1L).build();

        var result = userMapper.fromUserDto(userDto);

        assertEquals(userDto.getUsername(), result.getUsername());
        assertEquals(userDto.getExternalId(), result.getExternalId());
    }

    @Test
    void shouldReturnUserDto(){
        User user = User.builder().username("toto").externalId(1L).internalId(2L).build();

        var result = userMapper.fromUser(user);

        assertEquals(user.getUsername(), result.getUsername());
        assertEquals(user.getExternalId(), result.getExternalId());
    }

}