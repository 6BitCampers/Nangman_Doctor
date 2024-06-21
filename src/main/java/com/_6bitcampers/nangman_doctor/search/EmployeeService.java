package com._6bitcampers.nangman_doctor.search;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmployeeService {

    private final EmployeeMapper employeeMapper;

    @Autowired
    public EmployeeService(EmployeeMapper employeeMapper) {
        System.out.println(employeeMapper);
        this.employeeMapper = employeeMapper;
    }

    public List<EmployeeDto> getEmployeesByInfoNo(Long infoNo) {
        return employeeMapper.findByInfoNo(infoNo);
    }
}
