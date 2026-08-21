package br.com.atenda360.dashboard.controller;
import br.com.atenda360.agendamento.entity.StatusAgendamento;
import br.com.atenda360.agendamento.repository.AgendamentoRepository;
import br.com.atenda360.cliente.repository.ClienteRepository;
import br.com.atenda360.security.TenantService;
import org.springframework.web.bind.annotation.*;
import java.time.*;
import java.util.Map;
@RestController @RequestMapping("/api/dashboard")
public class DashboardController{
 private final AgendamentoRepository agendas;private final ClienteRepository clientes;private final TenantService tenant;
 public DashboardController(AgendamentoRepository a,ClienteRepository c,TenantService t){agendas=a;clientes=c;tenant=t;}
 @GetMapping public Map<String,Long> resumo(@RequestParam(required=false) YearMonth mes){var ref=mes==null?YearMonth.now():mes;var ini=ref.atDay(1).atStartOfDay();var fim=ref.plusMonths(1).atDay(1).atStartOfDay();var id=tenant.empresaId();return Map.of("total",agendas.countByEmpresaIdAndInicioBetween(id,ini,fim),"concluidos",agendas.countByEmpresaIdAndStatusAndInicioBetween(id,StatusAgendamento.CONCLUIDO,ini,fim),"cancelados",agendas.countByEmpresaIdAndStatusAndInicioBetween(id,StatusAgendamento.CANCELADO,ini,fim),"naoCompareceram",agendas.countByEmpresaIdAndStatusAndInicioBetween(id,StatusAgendamento.NAO_COMPARECEU,ini,fim),"clientesAtivos",clientes.countByEmpresaIdAndAtivoTrue(id));}
}
