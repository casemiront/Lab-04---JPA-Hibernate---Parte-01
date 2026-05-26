package br.ifma.labbd.model;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "imoveis")
public class Imovel implements EntidadeBase {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "endereco", length = 255)
    private String endereco;

    @Column(name = "cep", length = 10)
    private String cep;

    @Column(name = "dormitorios")
    private Integer dormitorios;

    @Column(name = "banheiros")
    private Integer banheiros;

    @Column(name = "suites")
    private Integer suites;

    @Column(name = "metragem")
    private Integer metragem;

    @Column(name = "valor_aluguel_sugerido", precision = 10, scale = 2)
    private BigDecimal valorAluguelSugerido;

    @Column(name = "obs", columnDefinition = "TEXT")
    private String obs;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "proprietario_id", nullable = false)
    private Cliente proprietario;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "tipo_imovel_id", nullable = false)
    private TipoImovel tipoImovel;

    public Imovel() {}

    @Override
    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }

    public String getEndereco() { return endereco; }
    public void setEndereco(String endereco) { this.endereco = endereco; }

    public String getCep() { return cep; }
    public void setCep(String cep) { this.cep = cep; }

    public Integer getDormitorios() { return dormitorios; }
    public void setDormitorios(Integer dormitorios) { this.dormitorios = dormitorios; }

    public Integer getBanheiros() { return banheiros; }
    public void setBanheiros(Integer banheiros) { this.banheiros = banheiros; }

    public Integer getSuites() { return suites; }
    public void setSuites(Integer suites) { this.suites = suites; }

    public Integer getMetragem() { return metragem; }
    public void setMetragem(Integer metragem) { this.metragem = metragem; }

    public BigDecimal getValorAluguelSugerido() { return valorAluguelSugerido; }
    public void setValorAluguelSugerido(BigDecimal valor) { this.valorAluguelSugerido = valor; }

    public String getObs() { return obs; }
    public void setObs(String obs) { this.obs = obs; }

    public Cliente getProprietario() { return proprietario; }
    public void setProprietario(Cliente proprietario) { this.proprietario = proprietario; }

    public TipoImovel getTipoImovel() { return tipoImovel; }
    public void setTipoImovel(TipoImovel tipoImovel) { this.tipoImovel = tipoImovel; }

    @Override
    public String toString() {
        return "Imovel{id=" + id + ", endereco='" + endereco + "', cep='" + cep + "', valor=" + valorAluguelSugerido + "}";
    }
}
