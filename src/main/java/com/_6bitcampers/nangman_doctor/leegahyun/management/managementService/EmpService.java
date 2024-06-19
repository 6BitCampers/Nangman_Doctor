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
    }

    public void registerPhoto(String email, String photo) {
        empMapper.updatePhotoByEmail(email, photo);
    }
    public void registerDescription(String email, String description) {
        empMapper.updateDescriptionByEmail(email, description);
    }

    public void registerAddress(String email, String addr) {
        empMapper.updateAddrByEmail(email, addr);
    }
    public void registerPlus(String email, String plus) {
        empMapper.updatePlusByEmail(email, plus);
    }
    public void registerHp(String email, String hp) {
        empMapper.updateHpByEmail(email, hp);
    }



}
