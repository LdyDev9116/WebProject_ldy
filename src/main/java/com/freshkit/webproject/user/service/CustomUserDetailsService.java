package com.freshkit.webproject.user.service;

import com.freshkit.webproject.user.dto.CustomUserDetails;
import com.freshkit.webproject.user.dto.UserEntity;
import com.freshkit.webproject.user.mapper.UserMapper;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final UserMapper userMapper;

    public CustomUserDetailsService(UserMapper userMapper) {
        this.userMapper = userMapper;
    }

    @Override
    public UserDetails loadUserByUsername(String memberId) throws UsernameNotFoundException {
        UserEntity userData = userMapper.findByUsername(memberId);

        if (userData == null) {
            throw new UsernameNotFoundException("User not found with username: " + memberId);
        }

        return new CustomUserDetails(userData);
    }
}
