package com.caprocoo.security.practice.dto.jwt;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class LoginDto {

   private String memberId;

   private String memberPwd;
}
