package br.com.atenda360.atendimento.entity;

import br.com.atenda360.agendamento.entity.Agendamento;
import br.com.atenda360.empresa.entity.Empresa;
import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity @Table(name="atendimentos")
public class Atendimento {
    @Id @GeneratedValue(strategy=GenerationType.IDENTITY) private Long id;
    @ManyToOne(optional=false,fetch=FetchType.LAZY) @JoinColumn(name="empresa_id") private Empresa empresa;
    @OneToOne(optional=false,fetch=FetchType.LAZY) @JoinColumn(name="agendamento_id",unique=true) private Agendamento agendamento;
    private LocalDateTime iniciadoEm;
    private LocalDateTime finalizadoEm;
    @Column(length=3000) private String observacoes;
    @Column(length=2000) private String resultado;
    public Long getId(){return id;} public void setId(Long id){this.id=id;}
    public Empresa getEmpresa(){return empresa;} public void setEmpresa(Empresa empresa){this.empresa=empresa;}
    public Agendamento getAgendamento(){return agendamento;} public void setAgendamento(Agendamento agendamento){this.agendamento=agendamento;}
    public LocalDateTime getIniciadoEm(){return iniciadoEm;} public void setIniciadoEm(LocalDateTime iniciadoEm){this.iniciadoEm=iniciadoEm;}
    public LocalDateTime getFinalizadoEm(){return finalizadoEm;} public void setFinalizadoEm(LocalDateTime finalizadoEm){this.finalizadoEm=finalizadoEm;}
    public String getObservacoes(){return observacoes;} public void setObservacoes(String observacoes){this.observacoes=observacoes;}
    public String getResultado(){return resultado;} public void setResultado(String resultado){this.resultado=resultado;}
}
