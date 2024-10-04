package org.example.facade.core;

import org.example.dto.request.UserChangePasswordRequestDto;
import org.example.dto.request.UserRetrievalRequestDto;
import org.example.dto.response.UserChangePasswordResponseDto;
import org.example.dto.response.UserRetrievalResponseDto;

public interface UserFacade {

    UserRetrievalResponseDto select(UserRetrievalRequestDto requestDto);

    UserChangePasswordResponseDto changePassword(UserChangePasswordRequestDto requestDto);

}
