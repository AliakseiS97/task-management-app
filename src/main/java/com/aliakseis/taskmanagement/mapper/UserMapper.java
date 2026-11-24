package com.aliakseis.taskmanagement.mapper;

import com.aliakseis.taskmanagement.dto.user.UserPatchRequestDto;
import com.aliakseis.taskmanagement.dto.user.UserRegistrationRequestDto;
import com.aliakseis.taskmanagement.dto.user.UserResponseDto;
import com.aliakseis.taskmanagement.dto.user.UserUpdateRequestDto;
import com.aliakseis.taskmanagement.dto.user.UserWithRolesResponseDto;
import com.aliakseis.taskmanagement.model.Role;
import com.aliakseis.taskmanagement.model.User;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.ERROR)
public interface UserMapper {
    UserResponseDto toDto(User user);

    UserWithRolesResponseDto toDtoWithRoles(User user);

    default Role.RoleName toRoleName(Role role) {
        return role.getName();
    }

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "password", ignore = true)
    @Mapping(target = "roles", ignore = true)
    @Mapping(target = "deleted", ignore = true)
    User toModel(UserRegistrationRequestDto requestDto);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "email", ignore = true)
    @Mapping(target = "password", ignore = true)
    @Mapping(target = "roles", ignore = true)
    @Mapping(target = "deleted", ignore = true)
    void updateUser(UserUpdateRequestDto requestDto, @MappingTarget User user);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "email", ignore = true)
    @Mapping(target = "password", ignore = true)
    @Mapping(target = "roles", ignore = true)
    @Mapping(target = "deleted", ignore = true)
    void patchUser(UserPatchRequestDto requestDto, @MappingTarget User user);
}
