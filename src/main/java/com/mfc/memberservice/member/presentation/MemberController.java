package com.mfc.memberservice.member.presentation;

import static com.mfc.memberservice.common.response.BaseResponseStatus.*;

import org.modelmapper.ModelMapper;
import org.springframework.util.StringUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mfc.memberservice.common.exception.BaseException;
import com.mfc.memberservice.common.response.BaseResponse;
import com.mfc.memberservice.member.application.MemberService;
import com.mfc.memberservice.member.dto.req.ModifyFavoriteStyleReqDto;
import com.mfc.memberservice.member.vo.req.ModifyFavoriteStyleReqVo;
import com.mfc.memberservice.member.vo.req.ModifyUserReqVo;
import com.mfc.memberservice.member.vo.resp.FavoriteStyleRespVo;
import com.mfc.memberservice.member.vo.resp.SignInRespVo;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/members")
@RequiredArgsConstructor
@Tag(name = "members", description = "회원(유저 + 파트너) 공통 서비스 컨트롤러")
public class MemberController {
	private final MemberService memberService;
	private final ModelMapper modelMapper;

	@GetMapping("/nickname/{nickname}")
	@Operation(summary = "닉네임 중복 확인 API", description = "닉네임 중복 확인")
	public BaseResponse<Boolean> verifyNickname(@PathVariable String nickname) {
		return new BaseResponse<>(memberService.verifyNickname(nickname));
	}

	@PutMapping("/nickname")
	@Operation(summary = "닉네임 수정 API", description = "헤더의 ROLE 값으로 구분하여 역할에 따라 닉네임 수정")
	public BaseResponse<Void> modifyNickname(
			@RequestHeader(name = "UUID", defaultValue = "") String uuid,
			@RequestHeader(name = "Role", defaultValue = "") String role,
			@RequestBody ModifyUserReqVo vo) {
		if(!StringUtils.hasText(uuid) || !StringUtils.hasText(role)) {
			throw new BaseException(NO_REQUIRED_HEADER);
		}

		memberService.modifyNickname(uuid, role, vo.getNickname());
		return new BaseResponse<>();
	}

	@PostMapping("/favoritestyle")
	@Operation(summary = "선호 스타일 수정 API", description = "유저/파트너 공통 적용")
	public BaseResponse<Void> modifyStyle(
			@RequestHeader(name = "UUID", defaultValue = "") String uuid,
			@RequestBody @Validated ModifyFavoriteStyleReqVo vo) {

		if(!StringUtils.hasText(uuid)) {
			throw new BaseException(NO_REQUIRED_HEADER);
		}

		memberService.modifyFavoriteStyle(uuid, modelMapper.map(vo, ModifyFavoriteStyleReqDto.class));
		return new BaseResponse<>();
	}

	@GetMapping("/favoritstyle")
	@Operation(summary = "회원 별 선호 스타일 조회 API", description = "getUUID = 조회할 회원의 UUID")
	public BaseResponse<FavoriteStyleRespVo> getFavoriteStyles(
			@RequestHeader(value = "getUUID", defaultValue = "") String uuid) {
		return new BaseResponse<>(modelMapper.map(
				memberService.getFavoriteStyle(uuid), FavoriteStyleRespVo.class));
	}

	@PutMapping("/change")
	@Operation(summary = "회원 ↔ 파트너 역할 전환 API", description = "유저 ↔ 파트너 전환 시 토큰 재발급")
	public BaseResponse<SignInRespVo> changeRole(
			@RequestHeader(value = "UUID", defaultValue = "") String uuid,
			@RequestHeader(value = "Role", defaultValue = "") String role) {
		return new BaseResponse<>(modelMapper.map(
				memberService.changeRole(uuid, role), SignInRespVo.class)
		);
	}
}
