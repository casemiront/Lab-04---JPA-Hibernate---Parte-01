package br.ifma.labbd.repository;

import br.ifma.labbd.model.*;
import org.junit.*;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.math.BigDecimal;
import java.util.List;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;

public class ImovelRepositoryTest {

    private EntityManager manager;
    private static EntityManagerFactory factory;
    private ImovelRepository imovelRepository;
    private ClienteRepository clienteRepository;

    // Dados fixos criados uma vez para todos os testes
    private static Integer proprietarioId;
    private static Integer tipoCasaId;
    private static Integer tipoAptoId;

    @BeforeClass
    public static void inicio() {
        factory = Persistence.createEntityManagerFactory("lab04PU-test");

        // Persiste dados de suporte (proprietário e tipos) fora da transação de teste
        EntityManager em = factory.createEntityManager();
        em.getTransaction().begin();

        TipoImovel casa = new TipoImovel("Casa");
        TipoImovel apto = new TipoImovel("Apartamento");
        em.persist(casa);
        em.persist(apto);

        Cliente prop = new Cliente("João Proprietário", "999.999.999-99", "prop@email.com");
        em.persist(prop);

        em.getTransaction().commit();

        tipoCasaId    = casa.getId();
        tipoAptoId    = apto.getId();
        proprietarioId = prop.getId();

        em.close();
    }

    @Before
    public void antes() {
        manager = factory.createEntityManager();
        imovelRepository  = new ImovelRepository(manager);
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

    private Imovel novoImovel(String endereco, String cep, BigDecimal valor, Integer tipoId) {
        Imovel i = new Imovel();
        i.setEndereco(endereco);
        i.setCep(cep);
        i.setDormitorios(3);
        i.setBanheiros(2);
        i.setSuites(1);
        i.setMetragem(100);
        i.setValorAluguelSugerido(valor);
        i.setProprietario(manager.getReference(Cliente.class, proprietarioId));
        i.setTipoImovel(manager.getReference(TipoImovel.class, tipoId));
        return i;
    }

    @Test
    public void deveInserirImovel() {
        Imovel i = novoImovel("Rua das Flores, 100", "65000-000", new BigDecimal("1500.00"), tipoCasaId);
        imovelRepository.salvaOuAtualiza(i);
        manager.flush();
        manager.clear();

        Imovel doBanco = imovelRepository.buscaPorId(i.getId());

        assertNotNull(doBanco);
        assertThat(doBanco.getEndereco(), is("Rua das Flores, 100"));
    }

    @Test
    public void deveAtualizarImovel() {
        Imovel i = novoImovel("Av. Central, 200", "65010-000", new BigDecimal("900.00"), tipoAptoId);
        imovelRepository.salvaOuAtualiza(i);
        manager.flush();

        i.setObs("Garagem dupla");
        imovelRepository.salvaOuAtualiza(i);
        manager.flush();
        manager.clear();

        Imovel doBanco = imovelRepository.buscaPorId(i.getId());
        assertThat(doBanco.getObs(), is("Garagem dupla"));
    }

    @Test
    public void deveBuscarPorCep() {
        imovelRepository.salvaOuAtualiza(
            novoImovel("Rua A, 10", "65000-000", new BigDecimal("1200.00"), tipoCasaId));
        manager.flush();
        manager.clear();

        List<Imovel> resultado = imovelRepository.buscarPorCep("65000-000");

        assertFalse(resultado.isEmpty());
        assertThat(resultado.get(0).getCep(), is("65000-000"));
    }

    @Test
    public void deveBuscarPorFaixaDePreco() {
        imovelRepository.salvaOuAtualiza(
            novoImovel("Rua B, 20", "65020-000", new BigDecimal("800.00"), tipoCasaId));
        imovelRepository.salvaOuAtualiza(
            novoImovel("Rua C, 30", "65030-000", new BigDecimal("1800.00"), tipoAptoId));
        manager.flush();
        manager.clear();

        List<Imovel> resultado = imovelRepository.buscarPorFaixaDePreco(
            new BigDecimal("500.00"), new BigDecimal("2000.00"));

        assertThat(resultado.size(), is(2));
    }

    @Test
    public void deveListarPorTipo() {
        imovelRepository.salvaOuAtualiza(
            novoImovel("Rua D, 40", "65040-000", new BigDecimal("1000.00"), tipoCasaId));
        manager.flush();
        manager.clear();

        List<Imovel> casas = imovelRepository.listarPorTipo(tipoCasaId);

        assertFalse(casas.isEmpty());
    }

    @Test
    public void deveListarPorProprietario() {
        imovelRepository.salvaOuAtualiza(
            novoImovel("Rua E, 50", "65050-000", new BigDecimal("1100.00"), tipoCasaId));
        imovelRepository.salvaOuAtualiza(
            novoImovel("Rua F, 60", "65060-000", new BigDecimal("1300.00"), tipoAptoId));
        manager.flush();
        manager.clear();

        List<Imovel> imoveis = imovelRepository.listarPorProprietario(proprietarioId);

        assertThat(imoveis.size(), is(2));
    }
}
