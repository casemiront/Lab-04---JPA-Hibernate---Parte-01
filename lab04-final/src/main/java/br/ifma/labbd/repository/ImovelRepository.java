package br.ifma.labbd.repository;

import br.ifma.labbd.model.Imovel;

import javax.persistence.EntityManager;
import java.math.BigDecimal;
import java.util.List;

/**
 * Repositório de Imóvel.
 * Recebe EntityManager pelo construtor (padrão do professor).
 */
public class ImovelRepository {

    private final EntityManager manager;
    private final DAOGenerico<Imovel> dao;

    public ImovelRepository(EntityManager manager) {
        this.manager = manager;
        this.dao = new DAOGenerico<>(manager);
    }

    /** Insere ou atualiza um imóvel. */
    public Imovel salvaOuAtualiza(Imovel imovel) {
        return dao.salvaOuAtualiza(imovel);
    }

    /** Remove um imóvel. */
    public void remove(Imovel imovel) {
        dao.remove(imovel);
    }

    /** Busca imóvel pelo ID. */
    public Imovel buscaPorId(Integer id) {
        return dao.buscaPorId(Imovel.class, id);
    }

    /** Lista todos os imóveis. */
    public List<Imovel> listarTodos() {
        return manager.createQuery(
                "SELECT i FROM Imovel i JOIN FETCH i.tipoImovel JOIN FETCH i.proprietario",
                Imovel.class).getResultList();
    }

    /** Busca imóveis pelo CEP. */
    public List<Imovel> buscarPorCep(String cep) {
        return manager.createQuery(
                "FROM Imovel WHERE cep = :cep", Imovel.class)
                .setParameter("cep", cep)
                .getResultList();
    }

    /** Busca imóveis dentro de uma faixa de valor de aluguel. */
    public List<Imovel> buscarPorFaixaDePreco(BigDecimal minimo, BigDecimal maximo) {
        return manager.createQuery(
                "FROM Imovel WHERE valorAluguelSugerido >= :min " +
                "AND valorAluguelSugerido <= :max " +
                "ORDER BY valorAluguelSugerido", Imovel.class)
                .setParameter("min", minimo)
                .setParameter("max", maximo)
                .getResultList();
    }

    /** Lista imóveis por tipo (ex: Casa, Apartamento). */
    public List<Imovel> listarPorTipo(Integer tipoImovelId) {
        return manager.createQuery(
                "FROM Imovel WHERE tipoImovel.id = :tipoId", Imovel.class)
                .setParameter("tipoId", tipoImovelId)
                .getResultList();
    }

    /** Lista todos os imóveis de um proprietário. */
    public List<Imovel> listarPorProprietario(Integer clienteId) {
        return manager.createQuery(
                "FROM Imovel WHERE proprietario.id = :clienteId", Imovel.class)
                .setParameter("clienteId", clienteId)
                .getResultList();
    }
}
