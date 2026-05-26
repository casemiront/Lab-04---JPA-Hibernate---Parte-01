package br.ifma.labbd.repository;

import br.ifma.labbd.model.Cliente;

import javax.persistence.EntityManager;
import java.util.List;

/**
 * Repositório de Cliente.
 * Recebe EntityManager pelo construtor (padrão do professor).
 */
public class ClienteRepository {

    private final EntityManager manager;
    private final DAOGenerico<Cliente> dao;

    public ClienteRepository(EntityManager manager) {
        this.manager = manager;
        this.dao = new DAOGenerico<>(manager);
    }

    /** Insere ou atualiza um cliente. */
    public Cliente salvaOuAtualiza(Cliente cliente) {
        return dao.salvaOuAtualiza(cliente);
    }

    /** Remove um cliente. */
    public void remove(Cliente cliente) {
        dao.remove(cliente);
    }

    /** Busca cliente pelo ID. */
    public Cliente buscaPorId(Integer id) {
        return dao.buscaPorId(Cliente.class, id);
    }

    /** Busca cliente pelo CPF. Retorna null se não encontrado. */
    public Cliente buscarPorCpf(String cpf) {
        List<Cliente> r = manager.createQuery(
                "FROM Cliente WHERE cpf = :cpf", Cliente.class)
                .setParameter("cpf", cpf)
                .getResultList();
        return r.isEmpty() ? null : r.get(0);
    }

    /** Busca cliente pelo e-mail. Retorna null se não encontrado. */
    public Cliente buscarPorEmail(String email) {
        List<Cliente> r = manager.createQuery(
                "FROM Cliente WHERE email = :email", Cliente.class)
                .setParameter("email", email)
                .getResultList();
        return r.isEmpty() ? null : r.get(0);
    }
}
