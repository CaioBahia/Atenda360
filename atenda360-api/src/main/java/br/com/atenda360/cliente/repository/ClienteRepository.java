package br.com.atenda360.cliente.repository;
import br.com.atenda360.cliente.entity.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;
public interface ClienteRepository extends JpaRepository<Cliente,Long>{
 List<Cliente> findByEmpresaIdAndNomeContainingIgnoreCaseOrderByNome(Long empresaId,String busca);
 Optional<Cliente> findByIdAndEmpresaId(Long id,Long empresaId);
 long countByEmpresaIdAndAtivoTrue(Long empresaId);
}
