package br.com.atenda360.usuario.entity;

import br.com.atenda360.empresa.entity.Empresa;
import jakarta.persistence.*;

@Entity @Table(name="usuarios",uniqueConstraints=@UniqueConstraint(columnNames={"empresa_id","email"}))
public class Usuario {
    @Id @GeneratedValue(strategy=GenerationType.IDENTITY) private Long id;
    @ManyToOne(optional=false,fetch=FetchType.LAZY) @JoinColumn(name="empresa_id") private Empresa empresa;
    @Column(nullable=false,length=120) private String nome;
    @Column(nullable=false,length=160) private String email;
    @Column(nullable=false) private String senha;
    @Enumerated(EnumType.STRING) @Column(nullable=false) private Perfil perfil=Perfil.COLABORADOR;
    @Column(nullable=false) private boolean ativo=true;
    public enum Perfil { ADMINISTRADOR, GESTOR, COLABORADOR }
    public Long getId(){return id;} public void setId(Long id){this.id=id;}
    public Empresa getEmpresa(){return empresa;} public void setEmpresa(Empresa empresa){this.empresa=empresa;}
    public String getNome(){return nome;} public void setNome(String nome){this.nome=nome;}
    public String getEmail(){return email;} public void setEmail(String email){this.email=email;}
    public String getSenha(){return senha;} public void setSenha(String senha){this.senha=senha;}
    public Perfil getPerfil(){return perfil;} public void setPerfil(Perfil perfil){this.perfil=perfil;}
    public boolean isAtivo(){return ativo;} public void setAtivo(boolean ativo){this.ativo=ativo;}
}
