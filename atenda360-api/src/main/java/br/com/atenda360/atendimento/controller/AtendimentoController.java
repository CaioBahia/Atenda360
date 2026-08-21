package br.com.atenda360.atendimento.controller;
import br.com.atenda360.agendamento.entity.StatusAgendamento;
import br.com.atenda360.agendamento.repository.AgendamentoRepository;
import br.com.atenda360.atendimento.entity.Atendimento;
import br.com.atenda360.atendimento.repository.AtendimentoRepository;
import br.com.atenda360.security.TenantService;
import jakarta.transaction.Transactional;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDateTime;
import java.util.*;
@RestController @RequestMapping("/api/atendimentos")
public class AtendimentoController{
 private final AtendimentoRepository repo;private final AgendamentoRepository agendas;private final TenantService tenant;
 public AtendimentoController(AtendimentoRepository r,AgendamentoRepository a,TenantService t){repo=r;agendas=a;tenant=t;}
 public record Finalizacao(String observacoes,String resultado){}
 @PostMapping("/agendamento/{id}/iniciar") @Transactional public Map<String,Object> iniciar(@PathVariable Long id){var a=agendas.findByIdAndEmpresaId(id,tenant.empresaId()).orElseThrow(()->new NoSuchElementException("Agendamento não encontrado"));a.setStatus(StatusAgendamento.EM_ANDAMENTO);var at=new Atendimento();at.setEmpresa(a.getEmpresa());at.setAgendamento(a);at.setIniciadoEm(LocalDateTime.now());repo.save(at);return Map.of("id",at.getId(),"status",a.getStatus(),"iniciadoEm",at.getIniciadoEm());}
 @PatchMapping("/{id}/concluir") @Transactional public Map<String,Object> concluir(@PathVariable Long id,@RequestBody Finalizacao r){var at=repo.findById(id).filter(x->x.getEmpresa().getId().equals(tenant.empresaId())).orElseThrow(()->new NoSuchElementException("Atendimento não encontrado"));at.setObservacoes(r.observacoes());at.setResultado(r.resultado());at.setFinalizadoEm(LocalDateTime.now());at.getAgendamento().setStatus(StatusAgendamento.CONCLUIDO);return Map.of("id",at.getId(),"status","CONCLUIDO","finalizadoEm",at.getFinalizadoEm());}
 @GetMapping("/cliente/{clienteId}") public List<Map<String,Object>> historico(@PathVariable Long clienteId){return repo.findByEmpresaIdAndAgendamentoClienteIdOrderByIniciadoEmDesc(tenant.empresaId(),clienteId).stream().map(a->{Map<String,Object> m=new LinkedHashMap<>();m.put("id",a.getId());m.put("tipo",a.getAgendamento().getTipoAtendimento());m.put("responsavel",a.getAgendamento().getResponsavel().getNome());m.put("inicio",a.getIniciadoEm());m.put("fim",a.getFinalizadoEm());m.put("resultado",a.getResultado());return m;}).toList();}
}
