package br.com.atenda360.empresa.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity @Table(name="empresas")
public class Empresa {
    @Id @GeneratedValue(strategy=GenerationType.IDENTITY) private Long id;
    @Column(nullable=false,length=120) private String nome;
    @Column(unique=true,length=18) private String cnpj;
    @Column(nullable=false) private boolean ativa=true;
    @Column(nullable=false,updatable=false) private LocalDateTime criadoEm=LocalDateTime.now();
    public Long getId(){return id;} public void setId(Long id){this.id=id;}
    public String getNome(){return nome;} public void setNome(String nome){this.nome=nome;}
    public String getCnpj(){return cnpj;} public void setCnpj(String cnpj){this.cnpj=cnpj;}
    public boolean isAtiva(){return ativa;} public void setAtiva(boolean ativa){this.ativa=ativa;}
    public LocalDateTime getCriadoEm(){return criadoEm;}
}
