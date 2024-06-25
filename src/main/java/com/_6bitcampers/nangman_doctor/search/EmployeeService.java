package com._6bitcampers.nangman_doctor.search;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmployeeService {

    private final EmployeeMapper employeeMapper;
    private final HospitalMapper hospitalMapper;

    @Autowired
    public EmployeeService(EmployeeMapper employeeMapper, HospitalMapper hospitalMapper) {
        System.out.println(employeeMapper);
        this.employeeMapper = employeeMapper;
        this.hospitalMapper = hospitalMapper;
    }

    public List<EmployeeDto> getEmployeesByInfoNo(Long infoNo) {
        return employeeMapper.findByInfoNo(infoNo);
    }

    public List<EmployeeDto> getTop10EmployeesByLikeCount() {
        return employeeMapper.findTop10ByRoleAndLikeCount();
    }

    public String getHospitalNameByInfoNo(Long infoNo) {
        return employeeMapper.findHospitalNameByInfoNo(infoNo);
    }
}
