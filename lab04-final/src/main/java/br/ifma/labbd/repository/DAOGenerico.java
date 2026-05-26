package br.ifma.labbd.repository;

import br.ifma.labbd.model.EntidadeBase;

import javax.persistence.EntityManager;
import java.util.Objects;

/**
 * DAO genérico reutilizável por todos os repositórios.
 * Padrão do professor: recebe EntityManager pelo construtor.
 */
class DAOGenerico<T extends EntidadeBase> {

    private final EntityManager manager;

    DAOGenerico(EntityManager manager) {
        this.manager = manager;
    }

    T buscaPorId(Class<T> clazz, Integer id) {
        return manager.find(clazz, id);
    }

    T salvaOuAtualiza(T t) {
        if (Objects.isNull(t.getId()))
            manager.persist(t);
        else
            t = manager.merge(t);
        return t;
    }

    void remove(T t) {
        manager.remove(t);
        manager.flush();
    }
}
