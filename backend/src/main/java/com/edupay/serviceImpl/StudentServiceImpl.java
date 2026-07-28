package com.edupay.serviceImpl;

import com.edupay.dto.request.CreateStudentRequest;
import com.edupay.dto.response.StudentResponse;
import com.edupay.entity.Institution;
import com.edupay.entity.Parent;
import com.edupay.entity.Student;
import com.edupay.exception.ResourceNotFound;
import com.edupay.exception.UnauthorizedTenantAccess;
import com.edupay.mapper.StudentMapper;
import com.edupay.repository.InstitutionRepository;
import com.edupay.repository.ParentRepository;
import com.edupay.repository.StudentRepository;
import com.edupay.security.SecurityUtils;
import com.edupay.service.StudentService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.UUID;

@Service
public class StudentServiceImpl implements StudentService {

    private final StudentRepository studentRepository;
    private final InstitutionRepository institutionRepository;
    private final ParentRepository parentRepository;
    private final StudentMapper studentMapper;

    public StudentServiceImpl(StudentRepository studentRepository,
                              InstitutionRepository institutionRepository,
                              ParentRepository parentRepository,
                              StudentMapper studentMapper) {
        this.studentRepository = studentRepository;
        this.institutionRepository = institutionRepository;
        this.parentRepository = parentRepository;
        this.studentMapper = studentMapper;
    }

    @Override
    @Transactional
    public StudentResponse createStudent(CreateStudentRequest request) {
        UUID tenantId = UUID.fromString(SecurityUtils.getRequiredTenantId());
        Institution institution = institutionRepository.findById(tenantId)
                .orElseThrow(() -> new ResourceNotFound("Institution not found"));

        Student student = new Student();
        student.setInstitution(institution);
        student.setFirstName(request.getFirstName());
        student.setLastName(request.getLastName());
        student.setExternalRef(request.getExternalRef());
        student.setLevel(request.getLevel());
        student.setStatus("ACTIVE");
        student.setParents(new HashSet<>());

        if (request.getParentIds() != null && !request.getParentIds().isEmpty()) {
            for (UUID parentId : request.getParentIds()) {
                Parent parent = parentRepository.findById(parentId)
                        .orElseThrow(() -> new ResourceNotFound("Parent not found: " + parentId));
                if (!parent.getTenantId().equals(tenantId)) {
                    throw new UnauthorizedTenantAccess("Parent belongs to different tenant");
                }
                student.getParents().add(parent);
                parent.getStudents().add(student);
            }
        }

        student = studentRepository.save(student);
        return studentMapper.toResponse(student);
    }

    @Override
    @Transactional(readOnly = true)
    public StudentResponse getStudent(UUID id) {
        UUID tenantId = UUID.fromString(SecurityUtils.getRequiredTenantId());
        Student student = studentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFound("Student not found: " + id));
        if (!student.getTenantId().equals(tenantId)) {
            throw new UnauthorizedTenantAccess("Cross-tenant access denied");
        }
        return studentMapper.toResponse(student);
    }

    @Override
    @Transactional(readOnly = true)
    public List<StudentResponse> getAllStudents() {
        UUID tenantId = UUID.fromString(SecurityUtils.getRequiredTenantId());
        return studentRepository.findAll().stream()
                .filter(s -> s.getTenantId().equals(tenantId))
                .map(studentMapper::toResponse)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<StudentResponse> getStudentsByLevel(String level) {
        UUID tenantId = UUID.fromString(SecurityUtils.getRequiredTenantId());
        return studentRepository.findAll().stream()
                .filter(s -> s.getTenantId().equals(tenantId) && level.equals(s.getLevel()))
                .map(studentMapper::toResponse)
                .toList();
    }

    @Override
    @Transactional
    public StudentResponse updateStudent(UUID id, CreateStudentRequest request) {
        UUID tenantId = UUID.fromString(SecurityUtils.getRequiredTenantId());
        Student student = studentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFound("Student not found: " + id));
        if (!student.getTenantId().equals(tenantId)) {
            throw new UnauthorizedTenantAccess("Cross-tenant access denied");
        }
        student.setFirstName(request.getFirstName());
        student.setLastName(request.getLastName());
        student.setExternalRef(request.getExternalRef());
        student.setLevel(request.getLevel());
        student = studentRepository.save(student);
        return studentMapper.toResponse(student);
    }

    @Override
    @Transactional
    public void deleteStudent(UUID id) {
        UUID tenantId = UUID.fromString(SecurityUtils.getRequiredTenantId());
        Student student = studentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFound("Student not found: " + id));
        if (!student.getTenantId().equals(tenantId)) {
            throw new UnauthorizedTenantAccess("Cross-tenant access denied");
        }
        studentRepository.delete(student);
    }

    @Override
    @Transactional
    public void linkParent(UUID studentId, UUID parentId) {
        UUID tenantId = UUID.fromString(SecurityUtils.getRequiredTenantId());
        Student student = studentRepository.findById(studentId)
                .orElseThrow(() -> new ResourceNotFound("Student not found: " + studentId));
        Parent parent = parentRepository.findById(parentId)
                .orElseThrow(() -> new ResourceNotFound("Parent not found: " + parentId));
        if (!student.getTenantId().equals(tenantId) || !parent.getTenantId().equals(tenantId)) {
            throw new UnauthorizedTenantAccess("Cross-tenant access denied");
        }
        student.getParents().add(parent);
        parent.getStudents().add(student);
        studentRepository.save(student);
    }

    @Override
    @Transactional
    public void unlinkParent(UUID studentId, UUID parentId) {
        UUID tenantId = UUID.fromString(SecurityUtils.getRequiredTenantId());
        Student student = studentRepository.findById(studentId)
                .orElseThrow(() -> new ResourceNotFound("Student not found: " + studentId));
        Parent parent = parentRepository.findById(parentId)
                .orElseThrow(() -> new ResourceNotFound("Parent not found: " + parentId));
        if (!student.getTenantId().equals(tenantId) || !parent.getTenantId().equals(tenantId)) {
            throw new UnauthorizedTenantAccess("Cross-tenant access denied");
        }
        student.getParents().remove(parent);
        parent.getStudents().remove(student);
        studentRepository.save(student);
    }
}
