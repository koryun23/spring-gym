package org.example.facade.core;

import org.example.dto.RestResponse;
import org.example.dto.request.UserChangePasswordRequestDto;
import org.example.dto.request.UserRetrievalRequestDto;
import org.example.dto.response.UserChangePasswordResponseDto;
import org.example.dto.response.UserRetrievalResponseDto;

public interface UserFacade {

    RestResponse<UserRetrievalResponseDto> select(UserRetrievalRequestDto requestDto);

    RestResponse<UserChangePasswordResponseDto> changePassword(UserChangePasswordRequestDto requestDto);

}
