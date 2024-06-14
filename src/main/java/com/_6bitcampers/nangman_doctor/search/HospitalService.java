package com._6bitcampers.nangman_doctor.search;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class HospitalService {

    @Autowired
    private HospitalMapper hospitalMapper;

    public List<HospitalDto> searchHospitals(String keyword) {
        return hospitalMapper.findByNameContaining(keyword);
    }

    public List<HospitalDto> searchTopRatedHospitals(String keyword) {
        System.out.println("실행");
        return hospitalMapper.findTopRatedByKeyword(keyword);

    }
}