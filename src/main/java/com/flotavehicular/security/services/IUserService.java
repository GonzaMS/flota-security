package com.flotavehicular.security.services;

import com.flotavehicular.security.utils.PageResponse;
import com.proyecto.flotavehicular_webapp.dto.security.UserDto;

public interface IUserService {
    PageResponse<UserDto> getAllUsers(int pageNumber, int pageSize);
}
