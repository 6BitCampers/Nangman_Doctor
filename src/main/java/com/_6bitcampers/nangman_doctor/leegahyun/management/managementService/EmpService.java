package com._6bitcampers.nangman_doctor.leegahyun.management.managementService;

import com._6bitcampers.nangman_doctor.leegahyun.management.managementDto.EmpDto;
import com._6bitcampers.nangman_doctor.leegahyun.management.managementMapper.empMapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmpService {
    @Autowired
    private empMapper empMapper;

    public List<EmpDto> getEmployeeLikeCounts(String email) {
        return empMapper.findLikecountByInfoNo(email);
    }


    public List<EmpDto> getEmpList(String email){
        return empMapper.getEmpList(email);
    }

    public void registerRole(String email, String role) {
        empMapper.updateRoleByEmail(email, role);
        // 이메일로 식별된 사용자의 역할을 업데이트하는 empMapper의 메서드가 있다고 가정합니다.
    }

}
