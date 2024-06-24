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
        return hospitalMapper.findTopRatedByKeyword(keyword).stream().limit(10).toList();
    }
    public HospitalDto findHospitalById(Long hospitalId) {
        return hospitalMapper.findById(hospitalId);
    }

}