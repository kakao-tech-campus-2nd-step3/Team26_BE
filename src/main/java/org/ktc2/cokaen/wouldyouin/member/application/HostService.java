package org.ktc2.cokaen.wouldyouin.member.application;

import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.ktc2.cokaen.wouldyouin.member.application.dto.request.create.HostCreateRequest;
import org.ktc2.cokaen.wouldyouin.member.application.dto.request.edit.HostEditRequest;
import org.ktc2.cokaen.wouldyouin.member.application.dto.MemberResponse;
import org.ktc2.cokaen.wouldyouin.member.persist.Host;
import org.ktc2.cokaen.wouldyouin.member.persist.HostRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class HostService implements MemberServiceCommonBehavior {

    private final HostRepository hostRepository;

    @Transactional
    public MemberResponse createHost(HostCreateRequest request) {
        return MemberResponse.from(hostRepository.save(request.toEntity()));
    }

    @Transactional
    public MemberResponse updateHost(Long hostId, HostEditRequest request) {
        Host host = getHostOrThrow(hostId);
        Optional.ofNullable(request.getNickname()).ifPresent(host::setNickname);
        Optional.ofNullable(request.getPhoneNumber()).ifPresent(host::setPhone);
        Optional.ofNullable(request.getProfileUrl()).ifPresent(host::setProfileImageUrl);
        Optional.ofNullable(request.getIntro()).ifPresent(host::setIntro);
        Optional.ofNullable(request.getHashtag()).ifPresent(host::setHashtag);

        return MemberResponse.from(host);
    }

    @Override
    @Transactional
    public void deleteById(Long id) {
        hostRepository.delete(getHostOrThrow(id));
    }

    @Override
    @Transactional(readOnly = true)
    public MemberResponse getMemberResponseById(Long id) {
        return MemberResponse.from(getHostOrThrow(id));
    }

    @Transactional(readOnly = true)
    public Host getHostOrThrow(Long id) {
        //TODO: 커스텀 예외 필요
        return hostRepository.findById(id).orElseThrow(RuntimeException::new);
    }
}
