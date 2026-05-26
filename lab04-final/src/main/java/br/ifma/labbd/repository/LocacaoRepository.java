package br.ifma.labbd.repository;

import br.ifma.labbd.model.Locacao;

import javax.persistence.EntityManager;
import java.util.List;

/**
 * Repositório de Locação.
 * Recebe EntityManager pelo construtor (padrão do professor).
 */
public class LocacaoRepository {

    private final EntityManager manager;
    private final DAOGenerico<Locacao> dao;

    public LocacaoRepository(EntityManager manager) {
        this.manager = manager;
        this.dao = new DAOGenerico<>(manager);
    }

    /** Insere ou atualiza uma locação. */
    public Locacao salvaOuAtualiza(Locacao locacao) {
        return dao.salvaOuAtualiza(locacao);
    }

    /** Remove uma locação. */
    public void remove(Locacao locacao) {
        dao.remove(locacao);
    }

    /** Busca locação pelo ID. */
    public Locacao buscaPorId(Integer id) {
        return dao.buscaPorId(Locacao.class, id);
    }

    /** Lista todas as locações ativas (ativo = 1). */
    public List<Locacao> listarAtivas() {
        return manager.createQuery(
                "SELECT l FROM Locacao l " +
                "JOIN FETCH l.imovel JOIN FETCH l.inquilino " +
                "WHERE l.ativo = 1", Locacao.class)
                .getResultList();
    }

    /** Lista todas as locações de um cliente (inquilino). */
    public List<Locacao> listarPorInquilino(Integer inquilinoId) {
        return manager.createQuery(
                "SELECT l FROM Locacao l JOIN FETCH l.imovel " +
                "WHERE l.inquilino.id = :id", Locacao.class)
                .setParameter("id", inquilinoId)
                .getResultList();
    }
}
