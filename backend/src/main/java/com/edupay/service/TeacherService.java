package com.edupay.service;

import com.edupay.dto.response.TeacherResponse;
import com.edupay.dto.request.CreateTeacherRequest;
import java.util.List;
import java.util.UUID;

public interface TeacherService {
    TeacherResponse createTeacher(CreateTeacherRequest request);
    TeacherResponse getTeacher(UUID id);
    List<TeacherResponse> getAllTeachers();
    TeacherResponse updateTeacher(UUID id, CreateTeacherRequest request);
    void deleteTeacher(UUID id);
}
