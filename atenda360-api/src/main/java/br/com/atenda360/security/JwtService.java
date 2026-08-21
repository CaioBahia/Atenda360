package br.com.atenda360.security;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;

@Service
public class JwtService {
    private final SecretKey key; private final long expiration;
    public JwtService(@Value("${atenda360.jwt.secret}") String secret,@Value("${atenda360.jwt.expiration}") long expiration){this.key=Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));this.expiration=expiration;}
    public String gerar(String email,Long empresaId,String perfil){var now=new Date();return Jwts.builder().subject(email).claim("empresaId",empresaId).claim("perfil",perfil).issuedAt(now).expiration(new Date(now.getTime()+expiration)).signWith(key).compact();}
    public Claims validar(String token){return Jwts.parser().verifyWith(key).build().parseSignedClaims(token).getPayload();}
}
