package com.caprocoo.security.practice.service.account;

import com.caprocoo.security.practice.dto.account.AccountDto;
import com.caprocoo.security.practice.entity.account.Account;
import com.caprocoo.security.practice.entity.account.Authority;
import com.caprocoo.security.practice.exception.DuplicateMemberException;
import com.caprocoo.security.practice.exception.NotFoundMemberException;
import com.caprocoo.security.practice.repository.AccountRepository;
import com.caprocoo.security.practice.util.SecurityUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;

/**
 * packageName    : com.caprocoo.ob.service
 * fileName       : MemberService
 * author         : caprocoo
 * date           : 2022-12-08
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2022-12-08        caprocoo       최초 생성
 */
@Transactional
@Service
@Slf4j
@RequiredArgsConstructor
public class AccountService {


    private final AccountRepository accountRepository;

    private final PasswordEncoder passwordEncoder;

    @Transactional
    public AccountDto signup(AccountDto accountDto) {
        if (accountRepository.findByMemberId(accountDto.getMemberId()).orElse(null) != null) {
            throw new DuplicateMemberException("이미 가입되어 있는 유저입니다.");
        }

        Authority authority = Authority.builder()
                .authorityName("ROLE_USER")
                .build();

        Account account = Account.builder()
                .memberId(accountDto.getMemberId())
                .memberPwd(passwordEncoder.encode(accountDto.getMemberPwd()))
                .telNoFirst(accountDto.getTelNoFirst())
                .telNoSecond(accountDto.getTelNoSecond())
                .telNoThird(accountDto.getTelNoThird())
                .memberEmail(accountDto.getMemberEmail())
                .authorities(Collections.singleton(authority))
                .activated('1')
                .build();

        return AccountDto.from(accountRepository.save(account));
    }

    @Transactional(readOnly = true)
    public AccountDto getUserWithAuthorities(String memberId) {
        return AccountDto.from(accountRepository.findByMemberId(memberId).orElse(null));
    }

    @Transactional(readOnly = true)
    public AccountDto getMyUserWithAuthorities() {
        return AccountDto.from(
                SecurityUtil.getCurrentUsername()
                        .flatMap(accountRepository::findByMemberId)
                        .orElseThrow(() -> new NotFoundMemberException("Member not found"))
        );
    }





}
