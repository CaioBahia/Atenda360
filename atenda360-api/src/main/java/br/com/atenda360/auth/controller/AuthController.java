package br.com.atenda360.auth.controller;
import br.com.atenda360.security.JwtService;
import br.com.atenda360.usuario.repository.UsuarioRepository;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import org.springframework.http.*;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import java.util.Map;
@RestController @RequestMapping("/api/auth")
public class AuthController{
 private final UsuarioRepository usuarios;private final PasswordEncoder encoder;private final JwtService jwt;
 public AuthController(UsuarioRepository usuarios,PasswordEncoder encoder,JwtService jwt){this.usuarios=usuarios;this.encoder=encoder;this.jwt=jwt;}
 public record LoginRequest(@Email @NotBlank String email,@NotBlank String senha){}
 @PostMapping("/login") public ResponseEntity<?> login(@Valid @RequestBody LoginRequest req){return usuarios.findByEmailIgnoreCase(req.email()).filter(u->u.isAtivo()&&encoder.matches(req.senha(),u.getSenha())).<ResponseEntity<?>>map(u->ResponseEntity.ok(Map.of("token",jwt.gerar(u.getEmail(),u.getEmpresa().getId(),u.getPerfil().name()),"nome",u.getNome(),"empresa",u.getEmpresa().getNome()))).orElseGet(()->ResponseEntity.status(401).body(Map.of("mensagem","E-mail ou senha inválidos")));}
}
