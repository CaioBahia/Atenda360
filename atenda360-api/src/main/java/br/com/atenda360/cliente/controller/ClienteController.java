package br.com.atenda360.cliente.controller;
import br.com.atenda360.cliente.entity.Cliente;
import br.com.atenda360.cliente.repository.ClienteRepository;
import br.com.atenda360.empresa.repository.EmpresaRepository;
import br.com.atenda360.security.TenantService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDateTime;
import java.util.*;
@RestController @RequestMapping("/api/clientes")
public class ClienteController{
 private final ClienteRepository repo;private final EmpresaRepository empresas;private final TenantService tenant;
 public ClienteController(ClienteRepository repo,EmpresaRepository empresas,TenantService tenant){this.repo=repo;this.empresas=empresas;this.tenant=tenant;}
 public record Request(@NotBlank @Size(max=120) String nome,@Email String email,@Size(max=20) String telefone,@Size(max=1000) String observacoes){}
 public record Response(Long id,String nome,String email,String telefone,String observacoes,boolean ativo,LocalDateTime criadoEm){}
 @GetMapping public List<Response> listar(@RequestParam(defaultValue="") String busca){return repo.findByEmpresaIdAndNomeContainingIgnoreCaseOrderByNome(tenant.empresaId(),busca).stream().map(this::dto).toList();}
 @GetMapping("/{id}") public Response detalhe(@PathVariable Long id){return dto(find(id));}
 @PostMapping @ResponseStatus(HttpStatus.CREATED) public Response criar(@Valid @RequestBody Request r){var c=new Cliente();c.setEmpresa(empresas.getReferenceById(tenant.empresaId()));fill(c,r);return dto(repo.save(c));}
 @PutMapping("/{id}") public Response editar(@PathVariable Long id,@Valid @RequestBody Request r){var c=find(id);fill(c,r);return dto(repo.save(c));}
 @PatchMapping("/{id}/status") public Response status(@PathVariable Long id,@RequestParam boolean ativo){var c=find(id);c.setAtivo(ativo);return dto(repo.save(c));}
 private Cliente find(Long id){return repo.findByIdAndEmpresaId(id,tenant.empresaId()).orElseThrow(()->new NoSuchElementException("Cliente não encontrado"));}
 private void fill(Cliente c,Request r){c.setNome(r.nome());c.setEmail(r.email());c.setTelefone(r.telefone());c.setObservacoes(r.observacoes());}
 private Response dto(Cliente c){return new Response(c.getId(),c.getNome(),c.getEmail(),c.getTelefone(),c.getObservacoes(),c.isAtivo(),c.getCriadoEm());}
}
