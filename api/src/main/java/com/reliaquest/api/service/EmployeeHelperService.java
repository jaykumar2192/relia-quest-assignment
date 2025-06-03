package com.reliaquest.api.service;

import com.reliaquest.api.data.EmployeeEntity;
import com.reliaquest.api.data.EmployeeRequest;
import org.springframework.stereotype.Service;

@Service
public class EmployeeHelperService {

    public EmployeeRequest toDto(EmployeeEntity entity) {
        return EmployeeRequest.builder()
                .id(entity.getId())
                .name(entity.getName())
                .salary(entity.getSalary())
                .age(entity.getAge())
                .title(entity.getTitle())
                .email(entity.getEmail())
                .build();
    }

    public EmployeeEntity toEntity(EmployeeRequest dto) {
        return EmployeeEntity.builder()
                .id(dto.getId())
                .name(dto.getName())
                .salary(dto.getSalary())
                .age(dto.getAge())
                .title(dto.getTitle())
                .email(dto.getEmail())
                .build();
    }
}
