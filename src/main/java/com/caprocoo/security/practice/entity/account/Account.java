package com.caprocoo.security.practice.entity.account;

import lombok.*;

import javax.persistence.*;
import java.util.Set;

/**
 * packageName    : com.caprocoo.ob.repository.rdb.member
 * fileName       : Member
 * author         : caprocoo
 * date           : 2022-12-08
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2022-12-08        caprocoo       최초 생성
 */
@Getter
@Setter
@Entity
@Table(name = "OB_ACCOUNT")
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Account {

    @Id
    @Column(name = "ACCOUNT_ID", unique = true, nullable = false)
    private String memberId;
    @Column(name = "ACCOUNT_NAME")
    private String memberName;
    @Column(name = "TEL_NO_FIRST")
    private String telNoFirst;
    @Column(name = "TEL_NO_SECOND")
    private String telNoSecond;
    @Column(name = "TEL_NO_THIRD")
    private String telNoThird;
    @Column(name = "ACCOUNT_EMAIL")
    private String memberEmail;
    @Column(name = "ACCOUNT_PWD", nullable = false)
    private String memberPwd;
    @Column(name = "ACTIVATED")
    private Character activated;


    @ManyToMany
    @JoinTable(
            name = "OB_ACCOUNT_AUTHORITY",
            joinColumns = {@JoinColumn(name = "ACCOUNT_ID", referencedColumnName = "ACCOUNT_ID")},
            inverseJoinColumns = {@JoinColumn(name = "AUTHORITY_NAME", referencedColumnName = "AUTHORITY_NAME")})
    private Set<Authority> authorities;


}
