package com._6bitcampers.nangman_doctor.servingPackage.jangwoo.join.joinService;

import com._6bitcampers.nangman_doctor.servingPackage.jangwoo.join.joinDto.joinRequestDto;
import com._6bitcampers.nangman_doctor.servingPackage.jangwoo.join.joinMapper.joinMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class joinService {
    private final joinMapper mapper;
    private final BCryptPasswordEncoder passwordEncoder;

    public void joinService(joinRequestDto dto) {
        String row_pw = dto.getPassword();
        dto.setPassword(passwordEncoder.encode(row_pw));

        if (dto.getType().equals("general")) {
            mapper.insertNormalUserDefault(dto);
        } else {
            mapper.insertEmployeeUserDefault(dto);
        }
    }
}
