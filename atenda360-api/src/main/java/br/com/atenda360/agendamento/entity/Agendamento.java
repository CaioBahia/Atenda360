package br.com.atenda360.agendamento.entity;

import br.com.atenda360.cliente.entity.Cliente;
import br.com.atenda360.empresa.entity.Empresa;
import br.com.atenda360.usuario.entity.Usuario;
import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity @Table(name="agendamentos",indexes={@Index(name="idx_agendamento_empresa_inicio",columnList="empresa_id,inicio")})
public class Agendamento {
    @Id @GeneratedValue(strategy=GenerationType.IDENTITY) private Long id;
    @ManyToOne(optional=false,fetch=FetchType.LAZY) @JoinColumn(name="empresa_id") private Empresa empresa;
    @ManyToOne(optional=false,fetch=FetchType.LAZY) @JoinColumn(name="cliente_id") private Cliente cliente;
    @ManyToOne(optional=false,fetch=FetchType.LAZY) @JoinColumn(name="responsavel_id") private Usuario responsavel;
    @Column(nullable=false,length=80) private String tipoAtendimento;
    @Column(nullable=false) private LocalDateTime inicio;
    @Column(nullable=false) private LocalDateTime fim;
    @Enumerated(EnumType.STRING) @Column(nullable=false,length=25) private StatusAgendamento status=StatusAgendamento.AGENDADO;
    @Column(length=1000) private String observacoes;
    public Long getId(){return id;} public void setId(Long id){this.id=id;}
    public Empresa getEmpresa(){return empresa;} public void setEmpresa(Empresa empresa){this.empresa=empresa;}
    public Cliente getCliente(){return cliente;} public void setCliente(Cliente cliente){this.cliente=cliente;}
    public Usuario getResponsavel(){return responsavel;} public void setResponsavel(Usuario responsavel){this.responsavel=responsavel;}
    public String getTipoAtendimento(){return tipoAtendimento;} public void setTipoAtendimento(String tipoAtendimento){this.tipoAtendimento=tipoAtendimento;}
    public LocalDateTime getInicio(){return inicio;} public void setInicio(LocalDateTime inicio){this.inicio=inicio;}
    public LocalDateTime getFim(){return fim;} public void setFim(LocalDateTime fim){this.fim=fim;}
    public StatusAgendamento getStatus(){return status;} public void setStatus(StatusAgendamento status){this.status=status;}
    public String getObservacoes(){return observacoes;} public void setObservacoes(String observacoes){this.observacoes=observacoes;}
}
