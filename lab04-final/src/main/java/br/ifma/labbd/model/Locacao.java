package br.ifma.labbd.model;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

@Entity
@Table(name = "locacao")
public class Locacao implements EntidadeBase {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "ativo")
    private Integer ativo; // 1 = ativo, 0 = inativo

    @Temporal(TemporalType.DATE)
    @Column(name = "data_inicio")
    private Date dataInicio;

    @Temporal(TemporalType.DATE)
    @Column(name = "data_fim")
    private Date dataFim;

    @Column(name = "dia_vencimento")
    private Integer diaVencimento;

    @Column(name = "perc_multa", precision = 10, scale = 2)
    private BigDecimal percMulta;

    @Column(name = "valor_aluguel", precision = 10, scale = 2)
    private BigDecimal valorAluguel;

    @Column(name = "obs", columnDefinition = "TEXT")
    private String obs;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_imovel", nullable = false)
    private Imovel imovel;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_inquilino", nullable = false)
    private Cliente inquilino;

    public Locacao() {}

    public boolean isAtivo() { return Integer.valueOf(1).equals(ativo); }

    @Override
    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }

    public Integer getAtivo() { return ativo; }
    public void setAtivo(Integer ativo) { this.ativo = ativo; }

    public Date getDataInicio() { return dataInicio; }
    public void setDataInicio(Date dataInicio) { this.dataInicio = dataInicio; }

    public Date getDataFim() { return dataFim; }
    public void setDataFim(Date dataFim) { this.dataFim = dataFim; }

    public Integer getDiaVencimento() { return diaVencimento; }
    public void setDiaVencimento(Integer diaVencimento) { this.diaVencimento = diaVencimento; }

    public BigDecimal getPercMulta() { return percMulta; }
    public void setPercMulta(BigDecimal percMulta) { this.percMulta = percMulta; }

    public BigDecimal getValorAluguel() { return valorAluguel; }
    public void setValorAluguel(BigDecimal valorAluguel) { this.valorAluguel = valorAluguel; }

    public String getObs() { return obs; }
    public void setObs(String obs) { this.obs = obs; }

    public Imovel getImovel() { return imovel; }
    public void setImovel(Imovel imovel) { this.imovel = imovel; }

    public Cliente getInquilino() { return inquilino; }
    public void setInquilino(Cliente inquilino) { this.inquilino = inquilino; }

    @Override
    public String toString() {
        return "Locacao{id=" + id + ", ativo=" + ativo + ", valorAluguel=" + valorAluguel + "}";
    }
}
