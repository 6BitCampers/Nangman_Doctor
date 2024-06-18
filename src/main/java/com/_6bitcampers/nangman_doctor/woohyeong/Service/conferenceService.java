package com._6bitcampers.nangman_doctor.woohyeong.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com._6bitcampers.nangman_doctor.woohyeong.Mapper.conferenceMapper;

@Service
public class conferenceService {

    private final conferenceMapper conferenceMapper;

    @Autowired
    public conferenceService(conferenceMapper conferenceMapper){
        this.conferenceMapper = conferenceMapper;
    }

    public int getUserNo(String user_email){
        return conferenceMapper.getUserNo(user_email);
    }

}
