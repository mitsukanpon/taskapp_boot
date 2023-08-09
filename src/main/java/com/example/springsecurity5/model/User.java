package com.example.springsecurity5.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import com.example.springsecurity5.util.Authority;
import com.example.springsecurity5.validatoir.UniqueLogin;

@Getter
@Setter
@Entity
@Table(name = "user")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @NotNull
    @Size(min = 6, max = 60, message = "ユーザ名は６字以上６０字以内で入力してください")
    @UniqueLogin
    @Column(name = "username")
    private String userName;

    @NotNull(message = "パスワードが設定されていません")
    @Size(min = 6, message = "パスワードは６字以上で入力してください")
    @Column(name = "password")
    private String password;

    @NotNull
    @Size(min = 6, max = 50, message = "メールアドレスは５０文字以内で入力してください")
    @Email
    private String email;

    private int gender;
    private boolean admin;
    private Authority authority;

}
