package com._6bitcampers.nangman_doctor.hayoon.Service;

import com._6bitcampers.nangman_doctor.hayoon.Mapper.StatusMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class StatusService {
    private StatusMapper statusMapper;

    @Autowired
    public StatusService(StatusMapper statusMapper) {
        this.statusMapper = statusMapper;
    }

    public void updateStatus(int reservationNo){
        statusMapper.updateStatus(reservationNo);
    }
}
