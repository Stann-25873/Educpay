package com.edupay.service;

import com.edupay.dto.request.CreateStudentRequest;
import com.edupay.dto.response.StudentResponse;
import java.util.List;
import java.util.UUID;

public interface StudentService {
    StudentResponse createStudent(CreateStudentRequest request);
    StudentResponse getStudent(UUID id);
    List<StudentResponse> getAllStudents();
    List<StudentResponse> getStudentsByLevel(String level);
    StudentResponse updateStudent(UUID id, CreateStudentRequest request);
    void deleteStudent(UUID id);
    void linkParent(UUID studentId, UUID parentId);
    void unlinkParent(UUID studentId, UUID parentId);
}
