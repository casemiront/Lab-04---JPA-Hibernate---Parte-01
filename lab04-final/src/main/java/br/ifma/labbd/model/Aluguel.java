package br.ifma.labbd.model;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

@Entity
@Table(name = "alugueis")
public class Aluguel implements EntidadeBase {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_locacao", nullable = false)
    private Locacao locacao;

    @Temporal(TemporalType.DATE)
    @Column(name = "dt_vencimento")
    private Date dtVencimento;

    @Column(name = "valor_pago", precision = 10, scale = 2)
    private BigDecimal valorPago;

    @Column(name = "obs", columnDefinition = "TEXT")
    private String obs;

    public Aluguel() {}

    @Override
    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }

    public Locacao getLocacao() { return locacao; }
    public void setLocacao(Locacao locacao) { this.locacao = locacao; }

    public Date getDtVencimento() { return dtVencimento; }
    public void setDtVencimento(Date dtVencimento) { this.dtVencimento = dtVencimento; }

    public BigDecimal getValorPago() { return valorPago; }
    public void setValorPago(BigDecimal valorPago) { this.valorPago = valorPago; }

    public String getObs() { return obs; }
    public void setObs(String obs) { this.obs = obs; }

    @Override
    public String toString() {
        return "Aluguel{id=" + id + ", dtVencimento=" + dtVencimento + ", valorPago=" + valorPago + "}";
    }
}
