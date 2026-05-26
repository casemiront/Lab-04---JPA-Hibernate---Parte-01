package br.ifma.labbd.model;

import javax.persistence.*;

@Entity
@Table(name = "tipo_imovel")
public class TipoImovel implements EntidadeBase {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "descricao", length = 100, nullable = false)
    private String descricao;

    public TipoImovel() {}

    public TipoImovel(String descricao) {
        this.descricao = descricao;
    }

    @Override
    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }

    public String getDescricao() { return descricao; }
    public void setDescricao(String descricao) { this.descricao = descricao; }

    @Override
    public String toString() {
        return "TipoImovel{id=" + id + ", descricao='" + descricao + "'}";
    }
}
