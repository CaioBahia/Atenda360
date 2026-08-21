package br.com.atenda360.security;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
@Service public class TenantService { public Long empresaId(){var a=SecurityContextHolder.getContext().getAuthentication();if(a==null||!(a.getDetails() instanceof Long id))throw new IllegalStateException("Empresa não identificada");return id;} }
