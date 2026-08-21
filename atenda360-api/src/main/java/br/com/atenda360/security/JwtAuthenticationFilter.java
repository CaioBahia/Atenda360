package br.com.atenda360.security;

import io.jsonwebtoken.JwtException;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import java.io.IOException;
import java.util.List;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    private final JwtService jwtService;
    public JwtAuthenticationFilter(JwtService jwtService){this.jwtService=jwtService;}
    @Override protected void doFilterInternal(HttpServletRequest req,HttpServletResponse res,FilterChain chain)throws ServletException,IOException{
        var header=req.getHeader("Authorization");
        if(header!=null&&header.startsWith("Bearer "))try{
            var claims=jwtService.validar(header.substring(7));
            var auth=new UsernamePasswordAuthenticationToken(claims.getSubject(),null,List.of(new SimpleGrantedAuthority("ROLE_"+claims.get("perfil",String.class))));
            auth.setDetails(claims.get("empresaId",Long.class));SecurityContextHolder.getContext().setAuthentication(auth);
        }catch(JwtException ignored){SecurityContextHolder.clearContext();}
        chain.doFilter(req,res);
    }
}
