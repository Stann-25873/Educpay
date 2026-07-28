package com.edupay.mapper;

import com.edupay.dto.response.TeacherResponse;
import com.edupay.entity.Teacher;
import org.springframework.stereotype.Component;

@Component
public class TeacherMapper {

    public TeacherResponse toResponse(Teacher teacher) {
        TeacherResponse response = new TeacherResponse();
        response.setId(teacher.getId());
        response.setFirstName(teacher.getFirstName());
        response.setLastName(teacher.getLastName());
        response.setEmail(teacher.getEmail());
        response.setPhone(teacher.getPhone());
        response.setSpecialization(teacher.getSpecialization());
        response.setCreatedAt(teacher.getCreatedAt());
        
        if (teacher.getInstitution() != null) {
            response.setInstitutionId(teacher.getInstitution().getId());
            response.setInstitutionName(teacher.getInstitution().getName());
        }
        
        return response;
    }
}