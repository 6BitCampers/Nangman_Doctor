package com._6bitcampers.nangman_doctor.leegahyun.management.managementService;

import com._6bitcampers.nangman_doctor.leegahyun.management.managementDto.EmpDto;
import com._6bitcampers.nangman_doctor.leegahyun.management.managementMapper.empMapper;
import com._6bitcampers.nangman_doctor.minio.service.storageService;
import lombok.RequiredArgsConstructor;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class EmpService {
    private final empMapper empMapper;
    private final storageService storageService;

    public List<EmpDto> getEmployeeLikeCounts(String email) {
        return empMapper.findLikecountByInfoNo(email);
    }


    public List<EmpDto> getEmpList(String email){
        return empMapper.getEmpList(email);
    }

    public void registerRole(String email, String role) {
        empMapper.updateRoleByEmail(email, role);
    }

    public void registerPhoto(String email, MultipartFile photo) {
        String filename = null;

        if (photo != null && !photo.isEmpty()) {
            filename = UUID.randomUUID().toString();
            try {
                storageService.uploadFile("nangmandoctor", "/Hospital_info/" + filename, photo.getInputStream(), photo.getContentType());
            } catch (IOException e) {
                throw new RuntimeException("Failed to upload photo", e);
            }
        }

        empMapper.updatePhotoByEmail(email, filename);
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


    public List<EmpDto> getAllHospitalNames() {
        return empMapper.getAllHospitalNames();
    }

    public void registerName(String email, String hname) {
        empMapper.updateNameByEmail(email, hname);
    }


}
