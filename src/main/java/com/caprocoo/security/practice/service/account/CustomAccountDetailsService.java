package com.caprocoo.security.practice.service.account;

import com.caprocoo.security.practice.entity.account.Account;
import com.caprocoo.security.practice.repository.AccountRepository;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Component("accountDetailsService")
public class CustomAccountDetailsService implements UserDetailsService {
   private final AccountRepository accountRepository;

   public CustomAccountDetailsService(AccountRepository accountRepository) {
      this.accountRepository = accountRepository;
   }

   @Override
   @Transactional
   public UserDetails loadUserByUsername(final String memberId) {
      return accountRepository.findByMemberId(memberId)
         .map(user -> createUser(memberId, user))
         .orElseThrow(() -> new UsernameNotFoundException(memberId + " -> 데이터베이스에서 찾을 수 없습니다."));
   }

   private org.springframework.security.core.userdetails.User createUser(String username, Account account) {
      if (!(account.getActivated()).equals('1')) {
         throw new RuntimeException(username + " -> 활성화되어 있지 않습니다.");
      }

      List<GrantedAuthority> grantedAuthorities = account.getAuthorities().stream()
              .map(authority -> new SimpleGrantedAuthority(authority.getAuthorityName()))
              .collect(Collectors.toList());

      return new org.springframework.security.core.userdetails.User(account.getMemberId(),
              account.getMemberPwd(),
              grantedAuthorities);
   }
}
