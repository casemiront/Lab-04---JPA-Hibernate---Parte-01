package br.ifma.labbd.repository;

import br.ifma.labbd.model.Cliente;
import org.junit.*;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;

public class ClienteRepositoryTest {

    private EntityManager manager;
    private static EntityManagerFactory factory;
    private ClienteRepository clienteRepository;

    @BeforeClass
    public static void inicio() {
        factory = Persistence.createEntityManagerFactory("lab04PU-test");
    }

    @Before
    public void antes() {
        manager = factory.createEntityManager();
        clienteRepository = new ClienteRepository(manager);
        manager.getTransaction().begin();
    }

    @After
    public void depois() {
        manager.getTransaction().rollback();
        manager.close();
    }

    @AfterClass
    public static void fim() {
        factory.close();
    }

    // ────────────────────────────────────────────────

    @Test
    public void deveInserirCliente() {
        Cliente c = new Cliente("Maria Silva", "111.111.111-11", "maria@email.com");
        clienteRepository.salvaOuAtualiza(c);
        manager.flush();
        manager.clear();

        Cliente doBanco = clienteRepository.buscarPorCpf("111.111.111-11");

        assertNotNull(doBanco);
        assertThat(doBanco.getNomeCliente(), is("Maria Silva"));
    }

    @Test
    public void deveAtualizarCliente() {
        Cliente c = new Cliente("João Pedro", "222.222.222-22", "joao@email.com");
        clienteRepository.salvaOuAtualiza(c);
        manager.flush();

        c.setNomeCliente("João Pedro Atualizado");
        clienteRepository.salvaOuAtualiza(c);
        manager.flush();
        manager.clear();

        Cliente doBanco = clienteRepository.buscarPorCpf("222.222.222-22");
        assertThat(doBanco.getNomeCliente(), is("João Pedro Atualizado"));
    }

    @Test
    public void deveBuscarPorCpf() {
        Cliente c = new Cliente("Ana Lima", "333.333.333-33", "ana@email.com");
        clienteRepository.salvaOuAtualiza(c);
        manager.flush();
        manager.clear();

        Cliente resultado = clienteRepository.buscarPorCpf("333.333.333-33");

        assertNotNull(resultado);
        assertThat(resultado.getCpf(), is("333.333.333-33"));
    }

    @Test
    public void deveBuscarPorEmail() {
        Cliente c = new Cliente("Carlos Melo", "444.444.444-44", "carlos@email.com");
        clienteRepository.salvaOuAtualiza(c);
        manager.flush();
        manager.clear();

        Cliente resultado = clienteRepository.buscarPorEmail("carlos@email.com");

        assertNotNull(resultado);
        assertThat(resultado.getEmail(), is("carlos@email.com"));
    }

    @Test
    public void deveRetornarNullParaCpfInexistente() {
        Cliente resultado = clienteRepository.buscarPorCpf("000.000.000-00");
        assertNull(resultado);
    }

    @Test
    public void deveRetornarNullParaEmailInexistente() {
        Cliente resultado = clienteRepository.buscarPorEmail("naoexiste@email.com");
        assertNull(resultado);
    }
}
