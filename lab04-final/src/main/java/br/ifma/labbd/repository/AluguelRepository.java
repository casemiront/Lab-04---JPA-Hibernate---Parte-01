package br.ifma.labbd.repository;

import br.ifma.labbd.model.Aluguel;

import javax.persistence.EntityManager;
import java.util.List;

/**
 * Repositório de Aluguel.
 * Recebe EntityManager pelo construtor (padrão do professor).
 */
public class AluguelRepository {

    private final EntityManager manager;
    private final DAOGenerico<Aluguel> dao;

    public AluguelRepository(EntityManager manager) {
        this.manager = manager;
        this.dao = new DAOGenerico<>(manager);
    }

    /** Insere ou atualiza um aluguel. */
    public Aluguel salvaOuAtualiza(Aluguel aluguel) {
        return dao.salvaOuAtualiza(aluguel);
    }

    /** Remove um aluguel. */
    public void remove(Aluguel aluguel) {
        dao.remove(aluguel);
    }

    /** Busca aluguel pelo ID. */
    public Aluguel buscaPorId(Integer id) {
        return dao.buscaPorId(Aluguel.class, id);
    }

    /**
     * Lista todos os aluguéis de uma locação,
     * ordenados de forma decrescente pela data de vencimento.
     */
    public List<Aluguel> listarPorLocacao(Integer locacaoId) {
        return manager.createQuery(
                "FROM Aluguel WHERE locacao.id = :locacaoId " +
                "ORDER BY dtVencimento DESC", Aluguel.class)
                .setParameter("locacaoId", locacaoId)
                .getResultList();
    }
}
