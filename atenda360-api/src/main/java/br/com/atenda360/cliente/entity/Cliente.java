package br.com.atenda360.cliente.entity;

import br.com.atenda360.empresa.entity.Empresa;
import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity @Table(name="clientes",indexes={@Index(name="idx_cliente_empresa_nome",columnList="empresa_id,nome")})
public class Cliente {
    @Id @GeneratedValue(strategy=GenerationType.IDENTITY) private Long id;
    @ManyToOne(optional=false,fetch=FetchType.LAZY) @JoinColumn(name="empresa_id") private Empresa empresa;
    @Column(nullable=false,length=120) private String nome;
    @Column(length=160) private String email;
    @Column(length=20) private String telefone;
    @Column(length=1000) private String observacoes;
    @Column(nullable=false) private boolean ativo=true;
    @Column(nullable=false,updatable=false) private LocalDateTime criadoEm=LocalDateTime.now();
    public Long getId(){return id;} public void setId(Long id){this.id=id;}
    public Empresa getEmpresa(){return empresa;} public void setEmpresa(Empresa empresa){this.empresa=empresa;}
    public String getNome(){return nome;} public void setNome(String nome){this.nome=nome;}
    public String getEmail(){return email;} public void setEmail(String email){this.email=email;}
    public String getTelefone(){return telefone;} public void setTelefone(String telefone){this.telefone=telefone;}
    public String getObservacoes(){return observacoes;} public void setObservacoes(String observacoes){this.observacoes=observacoes;}
    public boolean isAtivo(){return ativo;} public void setAtivo(boolean ativo){this.ativo=ativo;}
    public LocalDateTime getCriadoEm(){return criadoEm;}
}
