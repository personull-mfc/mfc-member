package com.mfc.memberservice.member.presentation;

import static com.mfc.memberservice.common.response.BaseResponseStatus.*;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Pageable;
import org.springframework.util.StringUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.mfc.memberservice.common.exception.BaseException;
import com.mfc.memberservice.common.response.BaseResponse;
import com.mfc.memberservice.member.application.PartnerService;
import com.mfc.memberservice.member.dto.req.CareerReqDto;
import com.mfc.memberservice.member.dto.req.ModifyPartnerReqDto;
import com.mfc.memberservice.member.dto.req.UpdateSnsReqDto;
import com.mfc.memberservice.member.vo.req.CareerReqVo;
import com.mfc.memberservice.member.vo.req.ModifyPartnerReqVo;
import com.mfc.memberservice.member.vo.req.UpdateSnsReqVo;
import com.mfc.memberservice.member.vo.resp.CareerListRespVo;
import com.mfc.memberservice.member.vo.resp.PartnerAccountRespVo;
import com.mfc.memberservice.member.vo.resp.PartnerPortfolioRespVo;
import com.mfc.memberservice.member.vo.resp.PartnerProfileListRespVo;
import com.mfc.memberservice.member.vo.resp.PartnerRankingRespVo;
import com.mfc.memberservice.member.vo.resp.PartnersByStyleRespVo;
import com.mfc.memberservice.member.vo.resp.ProfileRespVo;
import com.mfc.memberservice.member.vo.resp.SnsListRespVo;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/partners")
@RequiredArgsConstructor
@Tag(name = "partners", description = "파트너 서비스 컨트롤러")
public class PartnerController {
	private final PartnerService partnerService;
	private final ModelMapper modelMapper;

	@PutMapping("/profileimage")
	@Operation(summary = "파트너 프로필 사진 수정 API", description = "profileImage만 수정 가능")
	public BaseResponse<Void> updateProfileImage(
			@RequestHeader(name = "UUID", defaultValue = "") String uuid,
			@RequestBody ModifyPartnerReqVo vo) {
		checkUuid(uuid);
		partnerService.updateProfileImage(uuid, modelMapper.map(vo, ModifyPartnerReqDto.class));
		return new BaseResponse<>();
	}

	@PostMapping("/sns")
	@Operation(summary = "파트너 SNS 등록 API", description = "파트너 포트폴리오 : sns 등록")
	public BaseResponse<Void> updateSns(
			@RequestHeader(name = "UUID", defaultValue = "") String uuid,
			@RequestBody UpdateSnsReqVo vo) {
		checkUuid(uuid);
		partnerService.updateSns(uuid, modelMapper.map(vo, UpdateSnsReqDto.class));
		return new BaseResponse<>();
	}

	@GetMapping("/sns")
	@Operation(summary = "파트너 SNS 조회 API", description = "파트너 포트폴리오 : sns 목록 조회")
	public BaseResponse<SnsListRespVo> updateSns(
			@RequestHeader(value = "partnerId", defaultValue = "") String partnerId) {
		return new BaseResponse<>(modelMapper.map(
				partnerService.getSnsList(partnerId), SnsListRespVo.class));
	}

	@PostMapping("/career")
	@Operation(summary = "파트너 경력 등록 API", description = "파트너 포트폴리오 : 경력 등록")
	public BaseResponse<Void> createCareer(
			@RequestHeader(value = "UUID", defaultValue = "") String uuid,
			@RequestBody @Validated CareerReqVo vo) {
		checkUuid(uuid);
		partnerService.createCareer(uuid, modelMapper.map(vo, CareerReqDto.class));
		return new BaseResponse<>();
	}

	@PutMapping("/career/{careerId}")
	@Operation(summary = "파트너 경력 수정 API", description = "파트너 포트폴리오 : 경력 수정")
	public BaseResponse<Void> modifyCareer(@PathVariable Long careerId,
			@RequestHeader(value = "UUID", defaultValue = "") String uuid,
			@RequestBody @Validated CareerReqVo vo) {
		checkUuid(uuid);
		partnerService.updateCareer(uuid, careerId, modelMapper.map(vo, CareerReqDto.class));
		return new BaseResponse<>();
	}

	@DeleteMapping("/career/{careerId}")
	@Operation(summary = "파트너 경력 삭제 API", description = "파트너 포트폴리오 : 경력 삭제")
	public BaseResponse<Void> deleteCareer(@PathVariable Long careerId,
			@RequestHeader(value = "UUID", defaultValue = "") String uuid) {
		checkUuid(uuid);
		partnerService.deleteCareer(uuid, careerId);
		return new BaseResponse<>();
	}

	@GetMapping("/career")
	@Operation(summary = "파트너 경력 조회 API", description = "파트너 포트폴리오 : 경력 목록 조회")
	public BaseResponse<CareerListRespVo> getCareerList(
			@RequestHeader(value = "partnerId", defaultValue = "") String partnerId) {
		return new BaseResponse<>(modelMapper.map(partnerService.getCareerList(partnerId), CareerListRespVo.class));
	}

	@PutMapping("/description")
	@Operation(summary = "파트너 한 줄 소개 수정 API", description = "파트너 소개 수정 : description만 수정 가능")
	public BaseResponse<Void> updateDescription(
			@RequestHeader(value = "UUID", defaultValue = "") String uuid,
			@RequestBody ModifyPartnerReqVo vo) {
		checkUuid(uuid);
		partnerService.updateDescription(uuid, modelMapper.map(vo, ModifyPartnerReqDto.class));
		return new BaseResponse<>();
	}

	@PutMapping("/account")
	@Operation(summary = "파트너 계좌 수정 API", description = "파트너 계좌 수정 : bank, account만 수정 가능")
	public BaseResponse<Void> updateAccount(
			@RequestHeader(value = "UUID", defaultValue = "") String uuid,
			@RequestBody ModifyPartnerReqVo vo) {
		checkUuid(uuid);
		partnerService.updateAccount(uuid, modelMapper.map(vo, ModifyPartnerReqDto.class));
		return new BaseResponse<>();
	}

	@PutMapping("/chattime")
	@Operation(summary = "파트너 채팅 시간 수정 API", description = "파트너 채팅 시간 수정 : startTime, endTime만 수정 가능")
	public BaseResponse<Void> updateChatTime(
			@RequestHeader(value = "UUID", defaultValue = "") String uuid,
			@RequestBody ModifyPartnerReqVo vo) {
		checkUuid(uuid);
		partnerService.updateChatTime(uuid, modelMapper.map(vo, ModifyPartnerReqDto.class));
		return new BaseResponse<>();
	}

	@PutMapping("/averageDate")
	@Operation(summary = "파트너 코디 평균 소요 시간 수정 API", description = "파트너 소개 수정 : averageDate만 수정 가능")
	public BaseResponse<Void> updateAverageDate(
			@RequestHeader(value = "UUID", defaultValue = "") String uuid,
			@RequestBody ModifyPartnerReqVo vo) {
		checkUuid(uuid);
		partnerService.updateAverageTime(uuid, modelMapper.map(vo, ModifyPartnerReqDto.class));
		return new BaseResponse<>();
	}

	@PutMapping("/averagePrice")
	@Operation(summary = "파트너 코디 평균 가격 수정 API", description = "파트너 평균 가격 수정 : averagePrice만 수정 가능")
	public BaseResponse<Void> updateAveragePrice(
			@RequestHeader(value = "UUID", defaultValue = "") String uuid,
			@RequestBody ModifyPartnerReqVo vo) {
		checkUuid(uuid);
		partnerService.updateAveragePrice(uuid, modelMapper.map(vo, ModifyPartnerReqDto.class));
		return new BaseResponse<>();
	}

	@GetMapping
	@Operation(summary = "파트너 포트폴리오 조회 API", description = "한 줄 소개, 채팅 가능 시간, 평균 소요 기간 조회")
	public BaseResponse<PartnerPortfolioRespVo> getPortfolio(
			@RequestHeader(value = "partnerId", defaultValue = "") String partnerId) {
		return new BaseResponse<>(modelMapper.map(
				partnerService.getPortfolio(partnerId), PartnerPortfolioRespVo.class));
	}

	@GetMapping("/account")
	@Operation(summary = "파트너 계좌 정보 조회 API", description = "은행, 계좌정보 조회")
	public BaseResponse<PartnerAccountRespVo> getAccount(
			@RequestHeader(value = "UUID", defaultValue = "") String partnerId) {
		checkUuid(partnerId);
		return new BaseResponse<>(modelMapper.map(
				partnerService.getAccount(partnerId), PartnerAccountRespVo.class));
	}

	@GetMapping("/profile")
	@Operation(summary = "파트너 기본 프로필 조회 API", description = "닉네임, 프로필 이미지 조회")
	public BaseResponse<ProfileRespVo> getProfile(
			@RequestHeader(value = "partnerId", defaultValue = "") String partnerId) {
		return new BaseResponse<>(modelMapper.map(
				partnerService.getProfile(partnerId), ProfileRespVo.class));
	}

	@GetMapping("/{styleId}")
	@Operation(summary = "스타일 별 파트너 목록 조회 API", description = "스타일 별 파트너 id 조회")
	public BaseResponse<PartnersByStyleRespVo> getPartnersByStyle(@PathVariable Long styleId) {
		return new BaseResponse<>(modelMapper.map(
				partnerService.getPartnersByStyle(styleId), PartnersByStyleRespVo.class));
	}

	@GetMapping("/profiles")
	@Operation(summary = "파트너 프로필 목록 조회 API", description = "포스팅 목록에 해당하는 파트너 프로필 목록 조회")
	public BaseResponse<PartnerProfileListRespVo> getPartnerProfiles(@RequestParam List<String> partnerIds) {
		return new BaseResponse<>(modelMapper.map(
				partnerService.getPartnerProfiles(partnerIds), PartnerProfileListRespVo.class));
	}

	@GetMapping("/styles/{userId}")
	@Operation(summary = "스타일 목록 별 파트너 목록 조회 API", description = "스타일 목록에 해당하는 파트너 목록 조회 (ID)")
	public BaseResponse<PartnersByStyleRespVo> getPartnersByStyles(@PathVariable String userId) {
		return new BaseResponse<>(modelMapper.map(
				partnerService.getPartnersByStyles(userId), PartnersByStyleRespVo.class));
	}

	@GetMapping("/ranking")
	@Operation(summary = "파트너 랭킹 조회 API", description = "파트너 랭킹 목록 조회 (30명)")
	public BaseResponse<PartnerRankingRespVo> getPartnerRanking() {
		return new BaseResponse<>(modelMapper.map(
				partnerService.getPartnerRanking(), PartnerRankingRespVo.class));
	}

	private void checkUuid(String uuid) {
		if(!StringUtils.hasText(uuid)) {
			throw new BaseException(NO_REQUIRED_HEADER);
		}
	}
}
