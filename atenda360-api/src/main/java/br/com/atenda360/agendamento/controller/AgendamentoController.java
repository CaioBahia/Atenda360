package br.com.atenda360.agendamento.controller;
import br.com.atenda360.agendamento.entity.*;
import br.com.atenda360.agendamento.repository.AgendamentoRepository;
import br.com.atenda360.cliente.repository.ClienteRepository;
import br.com.atenda360.empresa.repository.EmpresaRepository;
import br.com.atenda360.security.TenantService;
import br.com.atenda360.usuario.repository.UsuarioRepository;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import java.time.*;
import java.util.*;
@RestController @RequestMapping("/api/agendamentos")
public class AgendamentoController{
 private final AgendamentoRepository repo;private final ClienteRepository clientes;private final UsuarioRepository usuarios;private final EmpresaRepository empresas;private final TenantService tenant;
 public AgendamentoController(AgendamentoRepository r,ClienteRepository c,UsuarioRepository u,EmpresaRepository e,TenantService t){repo=r;clientes=c;usuarios=u;empresas=e;tenant=t;}
 public record Request(@NotNull Long clienteId,@NotNull Long responsavelId,@NotBlank String tipoAtendimento,@NotNull LocalDateTime inicio,@NotNull LocalDateTime fim,String observacoes){}
 public record Response(Long id,Long clienteId,String cliente,Long responsavelId,String responsavel,String tipoAtendimento,LocalDateTime inicio,LocalDateTime fim,StatusAgendamento status,String observacoes){}
 @GetMapping public List<Response> listar(@RequestParam LocalDate inicio,@RequestParam LocalDate fim){return repo.findByEmpresaIdAndInicioBetweenOrderByInicio(tenant.empresaId(),inicio.atStartOfDay(),fim.plusDays(1).atStartOfDay()).stream().map(this::dto).toList();}
 @PostMapping @ResponseStatus(HttpStatus.CREATED) public Response criar(@Valid @RequestBody Request r){if(!r.fim().isAfter(r.inicio()))throw new IllegalArgumentException("O fim deve ser posterior ao início");var a=new Agendamento();a.setEmpresa(empresas.getReferenceById(tenant.empresaId()));a.setCliente(clientes.findByIdAndEmpresaId(r.clienteId(),tenant.empresaId()).orElseThrow(()->new NoSuchElementException("Cliente não encontrado")));a.setResponsavel(usuarios.findById(r.responsavelId()).filter(u->u.getEmpresa().getId().equals(tenant.empresaId())).orElseThrow(()->new NoSuchElementException("Responsável não encontrado")));fill(a,r);return dto(repo.save(a));}
 @PutMapping("/{id}") public Response editar(@PathVariable Long id,@Valid @RequestBody Request r){var a=find(id);fill(a,r);return dto(repo.save(a));}
 @PatchMapping("/{id}/status") public Response status(@PathVariable Long id,@RequestParam StatusAgendamento status){var a=find(id);a.setStatus(status);return dto(repo.save(a));}
 private Agendamento find(Long id){return repo.findByIdAndEmpresaId(id,tenant.empresaId()).orElseThrow(()->new NoSuchElementException("Agendamento não encontrado"));}
 private void fill(Agendamento a,Request r){a.setTipoAtendimento(r.tipoAtendimento());a.setInicio(r.inicio());a.setFim(r.fim());a.setObservacoes(r.observacoes());}
 private Response dto(Agendamento a){return new Response(a.getId(),a.getCliente().getId(),a.getCliente().getNome(),a.getResponsavel().getId(),a.getResponsavel().getNome(),a.getTipoAtendimento(),a.getInicio(),a.getFim(),a.getStatus(),a.getObservacoes());}
}
