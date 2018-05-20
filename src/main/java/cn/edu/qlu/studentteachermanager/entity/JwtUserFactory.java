package cn.edu.qlu.studentteachermanager.entity;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Description:
 */
public class JwtUserFactory {
    private JwtUserFactory() {
    }

    public static JwtUser create(MyUser user) {
        return new JwtUser(
                user.getId(),
                user.getUsername(),
                user.getPassword(),
                mapToGrantAuthorities(user.getAuthorities()),
                user.getIfFirstLogin()

        );

    }
    private static List<GrantedAuthority> mapToGrantAuthorities(List<Authority> authorities) {
        return authorities.stream()
                .map(authority -> new SimpleGrantedAuthority(authority.getAuthorityName()))
                .collect(Collectors.toList());
    }
}
