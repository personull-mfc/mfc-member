package com.mfc.memberservice.member.application;

import static com.mfc.memberservice.common.response.BaseResponseStatus.*;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mfc.memberservice.common.exception.BaseException;
import com.mfc.memberservice.member.domain.User;
import com.mfc.memberservice.member.dto.req.ModifyUserReqDto;
import com.mfc.memberservice.member.infrastructure.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
	private final UserRepository userRepository;

	@Override
	public void updateProfile(String uuid, ModifyUserReqDto dto) {
		User user = isExist(uuid);

		userRepository.save(User.builder()
				.id(user.getId())
				.nickname(user.getNickname())
				.profileImage(user.getProfileImage())
				.imageAlt(user.getImageAlt())
				.height(dto.getHeight())
				.weight(dto.getWeight())
				.bottomSize(dto.getBottomSize())
				.topSize(dto.getTopSize())
				.shoeSize(dto.getShoeSize())
				.bodyType(dto.getBodyType())
				.build()
		);
	}

	@Override
	public void updateSize(String uuid, ModifyUserReqDto dto) {
		User user = isExist(uuid);

		userRepository.save(User.builder()
				.id(user.getId())
				.nickname(user.getNickname())
				.profileImage(user.getProfileImage())
				.imageAlt(user.getImageAlt())
				.height(user.getHeight())
				.weight(user.getWeight())
				.bottomSize(dto.getBottomSize()) // 수정
				.topSize(dto.getTopSize()) // 수정
				.shoeSize(dto.getShoeSize()) // 수정
				.bodyType(user.getBodyType())
				.build()
		);
	}

	@Override
	public void updateProfileImage(String uuid, ModifyUserReqDto dto) {
		User user = isExist(uuid);

		userRepository.save(User.builder()
				.id(user.getId())
				.nickname(user.getNickname())
				.profileImage(dto.getProfileImage()) // 수정
				.imageAlt("Profile Image") // 수정
				.height(user.getHeight())
				.weight(user.getWeight())
				.bottomSize(user.getBottomSize())
				.topSize(user.getTopSize())
				.shoeSize(user.getShoeSize())
				.bodyType(user.getBodyType())
				.build()
		);
	}

	@Override
	public void updateBodyType(String uuid, ModifyUserReqDto dto) {
		User user = isExist(uuid);

		userRepository.save(User.builder()
				.id(user.getId())
				.nickname(user.getNickname())
				.profileImage(user.getProfileImage())
				.imageAlt(user.getImageAlt())
				.height(dto.getHeight()) // 수정
				.weight(dto.getWeight()) // 수정
				.bottomSize(user.getBottomSize())
				.topSize(user.getTopSize())
				.shoeSize(user.getShoeSize())
				.bodyType(dto.getBodyType()) // 수정
				.build()
		);
	}

	private User isExist(String uuid) {
		return userRepository.findByUuid(uuid)
				.orElseThrow(() -> new BaseException(MEMBER_NOT_FOUND));
	}
}
