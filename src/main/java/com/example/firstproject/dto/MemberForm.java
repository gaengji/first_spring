package com.example.firstproject.dto;

import com.example.firstproject.entity.Member;
import lombok.AllArgsConstructor;
import lombok.ToString;

@AllArgsConstructor
@ToString
public class MemberForm {

    private Long id;
    private String mail;
    private String pw;

    public Member toEntity() {
        return new Member(id, mail, pw);
    }
}
