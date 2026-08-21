package br.com.atenda360.config;
import br.com.atenda360.cliente.entity.Cliente;
import br.com.atenda360.cliente.repository.ClienteRepository;
import br.com.atenda360.empresa.entity.Empresa;
import br.com.atenda360.empresa.repository.EmpresaRepository;
import br.com.atenda360.usuario.entity.Usuario;
import br.com.atenda360.usuario.repository.UsuarioRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.*;
import org.springframework.security.crypto.password.PasswordEncoder;
@Configuration @Profile({"dev","demo"})
 public class DevDataInitializer{
 @Bean CommandLineRunner seed(EmpresaRepository empresas,UsuarioRepository usuarios,ClienteRepository clientes,PasswordEncoder encoder){return args->{if(empresas.count()>0)return;var e=new Empresa();e.setNome("Clínica Plena");e.setCnpj("12.345.678/0001-90");empresas.save(e);novoUsuario(usuarios,encoder,e,"Camila Souza","admin@clinicaplena.com.br",Usuario.Perfil.ADMINISTRADOR);novoUsuario(usuarios,encoder,e,"Bruno Martins","atendente@clinicaplena.com.br",Usuario.Perfil.COLABORADOR);novoCliente(clientes,e,"Mariana Ferreira","mariana.ferreira@email.com","(11) 98765-1020");novoCliente(clientes,e,"Rafael Barros","rafael.barros@email.com","(11) 99214-7741");novoCliente(clientes,e,"Carolina Lima","carolina.lima@email.com","(11) 97881-2304");};}
 private void novoUsuario(UsuarioRepository r,PasswordEncoder encoder,Empresa e,String nome,String email,Usuario.Perfil perfil){var u=new Usuario();u.setEmpresa(e);u.setNome(nome);u.setEmail(email);u.setSenha(encoder.encode("123456"));u.setPerfil(perfil);r.save(u);}
 private void novoCliente(ClienteRepository r,Empresa e,String nome,String email,String tel){var c=new Cliente();c.setEmpresa(e);c.setNome(nome);c.setEmail(email);c.setTelefone(tel);r.save(c);}
}
