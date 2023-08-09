package com.example.springsecurity5.service;

import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import com.example.springsecurity5.repository.UserRepository;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

@RequiredArgsConstructor
@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // ユーザを検索
        var user = userRepository.findByUserName(username)
                .orElseThrow(() -> new UsernameNotFoundException(username + "not Found"));
        // ユーザ情報を返す
        return new User(user.getUserName(), user.getPassword(),
                AuthorityUtils.createAuthorityList(user.getAuthority().name()));
    }
}
