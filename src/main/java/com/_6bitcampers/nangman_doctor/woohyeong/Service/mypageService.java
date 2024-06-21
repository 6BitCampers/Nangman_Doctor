package com._6bitcampers.nangman_doctor.woohyeong.Service;

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

    public UserDTO getUser(String user_email){
        return mypageMapper.getUser(user_email);
    }

    public EmployeeDTO getEmployee(String user_email){
        return mypageMapper.getEmployee(user_email);
    }

    public List<ReceiptDTO> getReceipt(int user_no){
        return mypageMapper.getReceipt(user_no);
    }

    public void updateUser(Map<String, Object> map){
        mypageMapper.updateUser(map);
    }
}
