package com.mt_analytics.mt_analytics.mapper;

import com.mt_analytics.mt_analytics.dto.request.GeneralUserDto;
import com.mt_analytics.mt_analytics.entity.GeneralUser;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class GeneralUserMapper {
    private final ModelMapper modelMapper;

    public GeneralUser toEntity(GeneralUserDto generalUserDto){

        return modelMapper.map(generalUserDto, GeneralUser.class);
    }
}
