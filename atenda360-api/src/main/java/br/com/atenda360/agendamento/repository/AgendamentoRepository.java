package br.com.atenda360.agendamento.repository;
import br.com.atenda360.agendamento.entity.*;
import org.springframework.data.jpa.repository.JpaRepository;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
public interface AgendamentoRepository extends JpaRepository<Agendamento,Long>{
 List<Agendamento> findByEmpresaIdAndInicioBetweenOrderByInicio(Long empresaId,LocalDateTime inicio,LocalDateTime fim);
 Optional<Agendamento> findByIdAndEmpresaId(Long id,Long empresaId);
 long countByEmpresaIdAndInicioBetween(Long empresaId,LocalDateTime inicio,LocalDateTime fim);
 long countByEmpresaIdAndStatusAndInicioBetween(Long empresaId,StatusAgendamento status,LocalDateTime inicio,LocalDateTime fim);
}
