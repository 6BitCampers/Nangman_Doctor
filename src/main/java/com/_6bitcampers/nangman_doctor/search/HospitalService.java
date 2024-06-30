package com._6bitcampers.nangman_doctor.search;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.time.LocalDate;
import java.time.DayOfWeek;
import java.util.ArrayList;

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

    public List<HospitalDto> findTop(){
        return hospitalMapper.findTop();
    }

    public String convertTimeFormat(String time) {
        if(!time.contains(":")){
            return "09:00 ~ 14:00";
        }
        if(time.length() == 8){
            time="0"+time;
        }
        String[] times = time.split(":");
        String start = times[0].substring(0, 2) + ":" + times[0].substring(2, 4);
        String end = times[1].substring(0, 2) + ":" + times[1].substring(2, 4);
        return start + " ~ " + end;
    }

    public List<String> getReorderedList(long hospitalId) {
        LocalDate today = LocalDate.now();
        DayOfWeek dayOfWeek = today.getDayOfWeek();

        HospitalDto dto=hospitalMapper.findById(hospitalId);

        List<String> hoursList = new ArrayList<>();
        hoursList.add("Monday "+convertTimeFormat(dto.getMonday()));
        hoursList.add("Tuesday "+convertTimeFormat(dto.getTuesday()));
        hoursList.add("Wednesday "+convertTimeFormat(dto.getWednesday()));
        hoursList.add("Thursday "+convertTimeFormat(dto.getThursday()));
        hoursList.add("Friday "+convertTimeFormat(dto.getFriday()));
        hoursList.add("Saturday "+convertTimeFormat(dto.getSaturday()));
        hoursList.add("Sunday "+convertTimeFormat(dto.getSunday()));

        List<String> reorderedHoursList = new ArrayList<>();
        int startIndex = dayOfWeek.getValue() - 1;  // MONDAY is 1, SUNDAY is 7
        for (int i = 0; i < hoursList.size(); i++) {
            reorderedHoursList.add(hoursList.get((startIndex + i) % hoursList.size()));
        }
        return reorderedHoursList;
    }
    public String getHospitalNameById(Long hospitalId) {
        // Example: Fetch hospital name from database or other source
        if (hospitalId == 1L) {
            return "Sample Hospital";
        }
        // Add more logic as needed
        return "Unknown Hospital";
    }
}
