package com.caprocoo.security.practice.dto.account;

import com.caprocoo.security.practice.entity.account.Account;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import javax.validation.constraints.NotNull;
import java.util.Set;
import java.util.stream.Collectors;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AccountDto {

    @NotNull
    private String memberId;
    private String memberName;
    private String telNoFirst;
    private String telNoSecond;
    private String telNoThird;
    private String memberEmail;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @NotNull
    private String memberPwd;
    private Set<AuthorityDto> authorityDtoSet;

    public static AccountDto from(Account account) {
        if (account == null) return null;

        return AccountDto.builder()
                .memberId(account.getMemberId())
                .memberPwd(account.getMemberPwd())
                .memberName(account.getMemberName())
                .memberEmail(account.getMemberEmail())
                .telNoFirst(account.getTelNoFirst())
                .telNoSecond(account.getTelNoSecond())
                .telNoThird(account.getTelNoThird())
                .authorityDtoSet(account.getAuthorities().stream()
                        .map(authority -> AuthorityDto.builder().authorityName(authority.getAuthorityName()).build())
                        .collect(Collectors.toSet()))
                .build();
    }
}