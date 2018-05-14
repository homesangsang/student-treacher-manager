package cn.edu.qlu.studentteachermanager.filter;

import cn.edu.qlu.studentteachermanager.entity.JwtUser;
import cn.edu.qlu.studentteachermanager.entity.MyUser;
import cn.edu.qlu.studentteachermanager.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;

public class JWTLoginFilter extends UsernamePasswordAuthenticationFilter{
    @Autowired
    private UserService userService;

    private AuthenticationManager authenticationManager;

    public JWTLoginFilter(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
    }

    // 接收并解析用户凭证
    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        try {
            MyUser myUser = new ObjectMapper().readValue(request.getInputStream(), MyUser.class);
            return authenticationManager.authenticate( // 2. 将生成的对象提交给Authentication进行验证
                    new UsernamePasswordAuthenticationToken( //1. 将用户名和密码封装成一个UsernamePasswordAuthenticationToken对象
                            myUser.getUsername(),
                            myUser.getPassword(),
                            new ArrayList<>()
                    )
            );
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    // 用户登录成功后，调用该方法，在该方法中生成token，并返回给客户端
    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {
        String token = Jwts.builder()
//                .setSubject(((JwtUser)authResult.getPrincipal()).getUsername())
                .setSubject(((JwtUser)authResult.getPrincipal()).getUsername())
                .setExpiration(new Date(System.currentTimeMillis() + 60 * 60 * 24 * 1000))
                .signWith(SignatureAlgorithm.HS512, "MyJwtSecret")
                .compact();
        response.addHeader("Authorization", "Bearer" + token);
    }
}
