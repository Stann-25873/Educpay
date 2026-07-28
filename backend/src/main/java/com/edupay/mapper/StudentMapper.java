package com.edupay.mapper;

import com.edupay.dto.response.ParentResponse;
import com.edupay.dto.response.StudentResponse;
import com.edupay.entity.Student;
import org.springframework.stereotype.Component;

@Component
public class StudentMapper {

    public StudentResponse toResponse(Student student) {
        StudentResponse response = new StudentResponse();
        response.setId(student.getId());
        response.setFirstName(student.getFirstName());
        response.setLastName(student.getLastName());
        response.setExternalRef(student.getExternalRef());
        response.setLevel(student.getLevel());
        response.setStatus(student.getStatus());
        response.setCreatedAt(student.getCreatedAt());
        if (student.getInstitution() != null) {
            response.setInstitutionId(student.getInstitution().getId());
            response.setInstitutionName(student.getInstitution().getName());
        }
        return response;
    }
}

