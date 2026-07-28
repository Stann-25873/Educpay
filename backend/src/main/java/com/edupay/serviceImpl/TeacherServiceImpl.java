package com.edupay.serviceImpl;

import com.edupay.dto.request.CreateTeacherRequest;
import com.edupay.dto.response.TeacherResponse;
import com.edupay.entity.Institution;
import com.edupay.entity.Teacher;
import com.edupay.exception.ResourceNotFound;
import com.edupay.exception.UnauthorizedTenantAccess;
import com.edupay.mapper.TeacherMapper;
import com.edupay.repository.InstitutionRepository;
import com.edupay.repository.TeacherRepository;
import com.edupay.security.SecurityUtils;
import com.edupay.service.TeacherService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
public class TeacherServiceImpl implements TeacherService {

    private final TeacherRepository teacherRepository;
    private final InstitutionRepository institutionRepository;
    private final TeacherMapper teacherMapper;

    public TeacherServiceImpl(TeacherRepository teacherRepository,
                              InstitutionRepository institutionRepository,
                              TeacherMapper teacherMapper) {
        this.teacherRepository = teacherRepository;
        this.institutionRepository = institutionRepository;
        this.teacherMapper = teacherMapper;
    }

    @Override
    @Transactional
    public TeacherResponse createTeacher(CreateTeacherRequest request) {
        UUID tenantId = UUID.fromString(SecurityUtils.getRequiredTenantId());
        Institution institution = institutionRepository.findById(tenantId)
                .orElseThrow(() -> new ResourceNotFound("Institution not found"));

        Teacher teacher = new Teacher();
        teacher.setInstitution(institution);
        teacher.setFirstName(request.getFirstName());
        teacher.setLastName(request.getLastName());
        teacher.setEmail(request.getEmail());
        teacher.setPhone(request.getPhone());
        teacher.setSpecialization(request.getSpecialization());

        teacher = teacherRepository.save(teacher);
        return teacherMapper.toResponse(teacher);
    }

    @Override
    @Transactional(readOnly = true)
    public TeacherResponse getTeacher(UUID id) {
        UUID tenantId = UUID.fromString(SecurityUtils.getRequiredTenantId());
        Teacher teacher = teacherRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFound("Teacher not found: " + id));
        if (!teacher.getTenantId().equals(tenantId)) {
            throw new UnauthorizedTenantAccess("Cross-tenant access denied");
        }
        return teacherMapper.toResponse(teacher);
    }

    @Override
    @Transactional(readOnly = true)
    public List<TeacherResponse> getAllTeachers() {
        UUID tenantId = UUID.fromString(SecurityUtils.getRequiredTenantId());
        return teacherRepository.findAll().stream()
                .filter(t -> t.getTenantId().equals(tenantId))
                .map(teacherMapper::toResponse)
                .toList();
    }

    @Override
    @Transactional
    public TeacherResponse updateTeacher(UUID id, CreateTeacherRequest request) {
        UUID tenantId = UUID.fromString(SecurityUtils.getRequiredTenantId());
        Teacher teacher = teacherRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFound("Teacher not found: " + id));
        if (!teacher.getTenantId().equals(tenantId)) {
            throw new UnauthorizedTenantAccess("Cross-tenant access denied");
        }
        teacher.setFirstName(request.getFirstName());
        teacher.setLastName(request.getLastName());
        teacher.setEmail(request.getEmail());
        teacher.setPhone(request.getPhone());
        teacher.setSpecialization(request.getSpecialization());
        teacher = teacherRepository.save(teacher);
        return teacherMapper.toResponse(teacher);
    }

    @Override
    @Transactional
    public void deleteTeacher(UUID id) {
        UUID tenantId = UUID.fromString(SecurityUtils.getRequiredTenantId());
        Teacher teacher = teacherRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFound("Teacher not found: " + id));
        if (!teacher.getTenantId().equals(tenantId)) {
            throw new UnauthorizedTenantAccess("Cross-tenant access denied");
        }
        teacherRepository.delete(teacher);
    }
}