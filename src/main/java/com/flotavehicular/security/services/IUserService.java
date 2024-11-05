package com.flotavehicular.security.services;

import com.flotavehicular.security.dto.UserDto;
import com.flotavehicular.security.utils.PageResponse;
import com.proyecto.flotavehicular_webapp.models.Security.User;

public interface IUserService {
    PageResponse<UserDto> getAllUsers(int pageNumber, int pageSize);
}
