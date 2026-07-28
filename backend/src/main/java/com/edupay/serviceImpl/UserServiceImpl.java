package com.edupay.serviceImpl;

import com.edupay.dto.request.CreateUserRequest;
import com.edupay.dto.response.UserResponse;
import com.edupay.entity.Institution;
import com.edupay.entity.Role;
import com.edupay.entity.User;
import com.edupay.exception.ResourceNotFound;
import com.edupay.mapper.UserMapper;
import com.edupay.repository.InstitutionRepository;
import com.edupay.repository.RoleRepository;
import com.edupay.repository.UserRepository;
import com.edupay.security.SecurityUtils;
import com.edupay.service.UserService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final InstitutionRepository institutionRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserMapper userMapper;

    public UserServiceImpl(UserRepository userRepository,
                           InstitutionRepository institutionRepository,
                           RoleRepository roleRepository,
                           PasswordEncoder passwordEncoder,
                           UserMapper userMapper) {
        this.userRepository = userRepository;
        this.institutionRepository = institutionRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
        this.userMapper = userMapper;
    }

    @Override
    @Transactional
    public UserResponse createUser(CreateUserRequest request) {
        UUID tenantId = UUID.fromString(SecurityUtils.getRequiredTenantId());

        Institution institution = institutionRepository.findById(tenantId)
                .orElseThrow(() -> new ResourceNotFound("Institution not found with id: " + tenantId));

        if (userRepository.findByEmail(request.getEmail()).isPresent()) {
            throw new IllegalArgumentException("Email already in use: " + request.getEmail());
        }

        User user = new User();
        user.setInstitution(institution);
        user.setEmail(request.getEmail().toLowerCase().trim());
        user.setPasswordHash(passwordEncoder.encode(request.getPassword()));
        user.setFirstName(request.getFirstName());
        user.setLastName(request.getLastName());
        user.setPhone(request.getPhone());
        user.setIsActive(true);

        if (request.getRoleId() != null) {
            Role role = roleRepository.findById(request.getRoleId())
                    .orElseThrow(() -> new ResourceNotFound("Role not found with id: " + request.getRoleId()));
            user.setRole(role);
        }

        user = userRepository.save(user);
        return userMapper.toResponse(user);
    }

    @Override
    @Transactional(readOnly = true)
    public UserResponse getUserById(UUID id) {
        UUID tenantId = UUID.fromString(SecurityUtils.getRequiredTenantId());
        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFound("User not found with id: " + id));

        if (!user.getTenantId().equals(tenantId)) {
            throw new com.edupay.exception.UnauthorizedTenantAccess("Cross-tenant access denied");
        }

        return userMapper.toResponse(user);
    }

    @Override
    @Transactional(readOnly = true)
    public UserResponse getCurrentUser () {
        String email = SecurityUtils.getCurrentUsername()
                .orElseThrow(() -> new ResourceNotFound("No authenticated user"));
        UUID tenantId = UUID.fromString(SecurityUtils.getRequiredTenantId());

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFound("User not found with email: " + email));
                
                UUID userTenantId = user.getTenantId();
        if (!tenantId.equals(userTenantId)) {
            throw new com.edupay.exception.UnauthorizedTenantAccess("Cross-tenant access denied");
        }
        return userMapper.toResponse(user);
    }

    @Override
    @Transactional(readOnly = true)
    public List<UserResponse> getAllUsersByTenant() {
        UUID tenantId = UUID.fromString(SecurityUtils.getRequiredTenantId());
        return userRepository.findAll().stream()
                .filter(u -> u.getTenantId().equals(tenantId))
                .map(userMapper::toResponse)
                .toList();
    }

    @Override
    @Transactional
    public UserResponse updateUser(UUID id, CreateUserRequest request) {
        UUID tenantId = UUID.fromString(SecurityUtils.getRequiredTenantId());
        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFound("User not found with id: " + id));

        if (!user.getTenantId().equals(tenantId)) {
            throw new com.edupay.exception.UnauthorizedTenantAccess("Cross-tenant access denied");
        }

        user.setFirstName(request.getFirstName());
        user.setLastName(request.getLastName());
        user.setPhone(request.getPhone());

        if (request.getPassword() != null && !request.getPassword().isBlank()) {
            user.setPasswordHash(passwordEncoder.encode(request.getPassword()));
        }

        if (request.getRoleId() != null) {
            Role role = roleRepository.findById(request.getRoleId())
                    .orElseThrow(() -> new ResourceNotFound("Role not found with id: " + request.getRoleId()));
            user.setRole(role);
        }

        user = userRepository.save(user);
        return userMapper.toResponse(user);
    }

    @Override
    @Transactional
    public void deleteUser(UUID id) {
        UUID tenantId = UUID.fromString(SecurityUtils.getRequiredTenantId());
        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFound("User not found with id: " + id));

        if (!user.getTenantId().equals(tenantId)) {
            throw new com.edupay.exception.UnauthorizedTenantAccess("Cross-tenant access denied");
        }

        userRepository.delete(user);
    }

    @Override
    @Transactional
    public void toggleUserStatus(UUID id, boolean active) {
        UUID tenantId = UUID.fromString(SecurityUtils.getRequiredTenantId());
        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFound("User not found with id: " + id));

        if (!user.getTenantId().equals(tenantId)) {
            throw new com.edupay.exception.UnauthorizedTenantAccess("Cross-tenant access denied");
        }

        user.setIsActive(active);
        userRepository.save(user);
    }
}
