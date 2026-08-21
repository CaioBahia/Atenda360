package br.com.atenda360.atendimento.repository;
import br.com.atenda360.atendimento.entity.Atendimento;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
public interface AtendimentoRepository extends JpaRepository<Atendimento,Long>{List<Atendimento> findByEmpresaIdAndAgendamentoClienteIdOrderByIniciadoEmDesc(Long empresaId,Long clienteId);}
