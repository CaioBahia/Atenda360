package br.com.atenda360.empresa.repository;
import br.com.atenda360.empresa.entity.Empresa;
import org.springframework.data.jpa.repository.JpaRepository;
public interface EmpresaRepository extends JpaRepository<Empresa,Long>{}
