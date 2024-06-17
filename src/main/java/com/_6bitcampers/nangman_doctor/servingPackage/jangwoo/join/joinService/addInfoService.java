package com._6bitcampers.nangman_doctor.servingPackage.jangwoo.join.joinService;

import com._6bitcampers.nangman_doctor.servingPackage.jangwoo.join.joinDto.joinRequestDto;
import com._6bitcampers.nangman_doctor.servingPackage.jangwoo.join.joinMapper.joinMapper;
import com._6bitcampers.nangman_doctor.servingPackage.jangwoo.login.loginEntity.userEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class addInfoService {
    private final joinMapper mapper;


    public void addinfo(String type, String email, joinRequestDto dto) {
        userEntity userEntity = mapper.findByEmailAndType(type, email);
        //drop
        mapper.deleteByEmailAndType(type, email);

        if (type.equals("google")) {
            dto.setName(userEntity.getUser_name());
            dto.setEmail(userEntity.getUser_email());
        } else if (type.equals("naver")) {
            dto.setName(userEntity.getUser_name());
            dto.setEmail(userEntity.getUser_email());
            dto.setGender(userEntity.getUser_gender());
            dto.setHp(userEntity.getUser_hp());
            dto.setAge(userEntity.getUser_age());
            dto.setNickname(userEntity.getUser_nickname());
        }
        else {
            dto.setName(userEntity.getUser_name());
            dto.setEmail(userEntity.getUser_email());
            dto.setGender(userEntity.getUser_gender());
            dto.setHp(userEntity.getUser_hp());
            dto.setAge(userEntity.getUser_age());
        }

        if (dto.getType().equals("general"))
            mapper.insertNormalUserNormal(dto,type);
        else
            mapper.insertEmployeeUserNormal(dto, type);
    }
}
