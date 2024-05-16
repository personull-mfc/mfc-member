package com.mfc.memberservice.member.domain;

import java.util.UUID;

import com.mfc.memberservice.common.entity.BaseEntity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
public class User extends BaseEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private UUID uuid;
	@Column(nullable = false, length = 10)
	private String nickname;
	private String profileImage;
	private Integer height;
	@Column(length = 20)
	private String topSize;
	@Column(length = 20)
	private String bottomSize;
	private Integer shoeSize;

	public User(Long id, UUID uuid, String nickname, String profileImage, Integer height, String topSize,
			String bottomSize,
			Integer shoeSize) {
		this.id = id;
		this.uuid = uuid;
		this.nickname = nickname;
		this.profileImage = profileImage;
		this.height = height;
		this.topSize = topSize;
		this.bottomSize = bottomSize;
		this.shoeSize = shoeSize;
	}

	@Builder
	public User(UUID uuid, String nickname) {
		this.uuid = uuid;
		this.nickname = nickname;
	}
}
