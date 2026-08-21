package br.com.atenda360.usuario.repository;
import br.com.atenda360.usuario.entity.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.EntityGraph;
import java.util.Optional;
public interface UsuarioRepository extends JpaRepository<Usuario,Long>{@EntityGraph(attributePaths="empresa") Optional<Usuario> findByEmailIgnoreCase(String email);}
