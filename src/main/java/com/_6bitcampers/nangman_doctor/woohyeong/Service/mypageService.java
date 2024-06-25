package com._6bitcampers.nangman_doctor.woohyeong.Service;

import com._6bitcampers.nangman_doctor.leegahyun.management.managementDto.EmpDto;
import com._6bitcampers.nangman_doctor.woohyeong.Mapper.mypageMapper;
import com._6bitcampers.nangman_doctor.woohyeong.dto.EmployeeDTO;
import com._6bitcampers.nangman_doctor.woohyeong.dto.ReceiptDTO;
import com._6bitcampers.nangman_doctor.woohyeong.dto.UserDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class mypageService {
    private final mypageMapper mypageMapper;

    @Autowired
    public mypageService(mypageMapper mypageMapper) {
        this.mypageMapper = mypageMapper;
    }

    public UserDTO getUser(String user_email, String user_type){
        return mypageMapper.getUser(user_email, user_type);
    }

    public EmployeeDTO getEmployee(String user_email){
        return mypageMapper.getEmployee(user_email);
    }

    public List<ReceiptDTO> getReceipt(Map<String, Object> map){
        return mypageMapper.getReceipt(map);
    }

    public void updateUser(Map<String, Object> map){
        mypageMapper.updateUser(map);
    }
    public String getInfoName(int info_no){
        return mypageMapper.getInfoName(info_no);
    };

    public void updateReservation(int reservation_no){
        mypageMapper.updateReservation(reservation_no);
    }
}
