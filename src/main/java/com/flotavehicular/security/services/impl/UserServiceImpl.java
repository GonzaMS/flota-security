package com.flotavehicular.security.services.impl;

import com.flotavehicular.security.repositories.IUserRepository;
import com.flotavehicular.security.services.IUserService;
import com.flotavehicular.security.utils.PageResponse;
import com.proyecto.flotavehicular_webapp.dto.security.UserDto;
import com.proyecto.flotavehicular_webapp.models.Security.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements IUserService {
    private final IUserRepository userRepository;

    public UserServiceImpl(IUserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public PageResponse<UserDto> getAllUsers(int pageNumber, int pageSize) {
        PageRequest pageRequest = PageRequest.of(pageNumber, pageSize);

        // Assuming `findAllByEnabled` is a method in IUserRepository that fetches only enabled users
        Page<User> activeUsersPage = userRepository.findAllByEnabled(true, pageRequest);

        return mapToPageResponse(activeUsersPage);
    }

    private UserDto mapToDto(User user) {
        return UserDto.builder()
                .userId(user.getId())
                .fullName(user.getFullName())
                .roles(user.getRoles())
                .active(user.isEnabled())
                .build();
    }

    private PageResponse<UserDto> mapToPageResponse(Page<User> userPage) {
        List<UserDto> userDtos = userPage.getContent().stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());

        return PageResponse.of(
                userDtos,
                userPage.getNumber(),
                userPage.getSize(),
                userPage.getTotalElements(),
                userPage.getTotalPages(),
                userPage.isLast());
    }
}
