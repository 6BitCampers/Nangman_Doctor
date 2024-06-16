package com._6bitcampers.nangman_doctor.leegahyun.management.managementService;

import com._6bitcampers.nangman_doctor.leegahyun.management.managementDto.EmpDto;
import com._6bitcampers.nangman_doctor.leegahyun.management.managementMapper.empMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmpService {
    @Autowired
    private empMapper empMapper;

    public List<EmpDto> getEmployeeLikeCounts(String email) {
        System.out.println(email);
        return empMapper.findLikecountByInfoNo(email);
    }


}
