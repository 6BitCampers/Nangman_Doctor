package com._6bitcampers.nangman_doctor.search;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class HospitalService {

    @Autowired
    private HospitalMapper hospitalMapper;

    public List<HospitalDto> searchHospitals(String keyword, int page, int size) {
        int offset = (page - 1) * size;
        return hospitalMapper.findByNameContaining(keyword, size, offset);
    }

    public List<HospitalDto> searchTopRatedHospitals(String keyword, int page, int size) {
        int offset = (page - 1) * size;
        return hospitalMapper.findTopRatedByKeyword(keyword, size, offset);
    }

    public long countHospitals(String keyword) {
        return hospitalMapper.countByNameContaining(keyword);
    }

    public HospitalDto findHospitalById(Long hospitalId) {
        return hospitalMapper.findById(hospitalId);
    }
}
