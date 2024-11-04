package org.ktc2.cokaen.wouldyouin.member.application;

import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.ktc2.cokaen.wouldyouin.Image.application.MemberImageService;
import org.ktc2.cokaen.wouldyouin.Image.persist.MemberImage;
import org.ktc2.cokaen.wouldyouin.auth.application.dto.LocalLoginRequest;
import org.ktc2.cokaen.wouldyouin.member.application.dto.MemberResponse;
import org.ktc2.cokaen.wouldyouin.member.application.dto.request.create.HostCreateRequest;
import org.ktc2.cokaen.wouldyouin.member.application.dto.request.edit.HostEditRequest;
import org.ktc2.cokaen.wouldyouin.member.persist.Host;
import org.ktc2.cokaen.wouldyouin.member.persist.HostRepository;
import org.ktc2.cokaen.wouldyouin.member.persist.MemberType;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class HostService implements MemberServiceCommonBehavior, LikeableMemberService<Host> {

    private final HostRepository hostRepository;
    private final PasswordEncoder passwordEncoder;
    private final MemberImageService memberImageService;

    @Transactional
    public MemberResponse createHost(HostCreateRequest request) {
        String hashedPassword = passwordEncoder.encode(request.getPassword());
        MemberImage profileImage = memberImageService.getById(request.getProfileImageId());
        Host createdHost = hostRepository.save(request.toEntity(hashedPassword, profileImage));
        memberImageService.setBaseMember(profileImage, createdHost);
        return MemberResponse.from(createdHost);
    }

    @Transactional
    public MemberResponse updateHost(Long hostId, HostEditRequest request) {
        Host host = getByIdOrThrow(hostId);
        Optional.ofNullable(request.getNickname()).ifPresent(host::setNickname);
        Optional.ofNullable(request.getPhoneNumber()).ifPresent(host::setPhone);
        Optional.ofNullable(request.getProfileImageId()).map(memberImageService::getById).ifPresent(host::setProfileImage);
        Optional.ofNullable(request.getIntro()).ifPresent(host::setIntro);
        Optional.ofNullable(request.getHashtag()).ifPresent(host::setHashtag);

        return MemberResponse.from(host);
    }

    @Override
    @Transactional
    public void deleteById(Long id) {
        hostRepository.delete(getByIdOrThrow(id));
    }

    @Override
    @Transactional(readOnly = true)
    public MemberResponse getMemberResponseById(Long id) {
        return MemberResponse.from(getByIdOrThrow(id));
    }

    @Transactional(readOnly = true)
    public MemberResponse getMemberResponseBy(LocalLoginRequest loginRequest) {
        return MemberResponse.from(hostRepository
            .findByEmailAndHashedPassword(
                loginRequest.email(),
                passwordEncoder.encode(loginRequest.password()))
            .orElseThrow(RuntimeException::new));
    }

    @Transactional(readOnly = true)
    public Host getByIdOrThrow(Long id) {
        //TODO: 커스텀 예외 필요
        return hostRepository.findById(id).orElseThrow(RuntimeException::new);
    }

    @Override
    public MemberType getTargetMemberType() {
        return MemberType.host;
    }

    @Override
    public LikeableMemberService<Host> getLikeableMemberService() {
        return this;
    }
}