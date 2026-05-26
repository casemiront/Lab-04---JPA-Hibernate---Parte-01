package br.ifma.labbd.repository;

import br.ifma.labbd.model.*;
import org.junit.*;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;

public class LocacaoRepositoryTest {

    private EntityManager manager;
    private static EntityManagerFactory factory;
    private LocacaoRepository locacaoRepository;

    private static Integer inquilinoId;
    private static Integer imovelId;

    @BeforeClass
    public static void inicio() {
        factory = Persistence.createEntityManagerFactory("lab04PU-test");

        EntityManager em = factory.createEntityManager();
        em.getTransaction().begin();

        TipoImovel tipo = new TipoImovel("Casa");
        em.persist(tipo);

        Cliente prop = new Cliente("Proprietário Teste", "777.777.777-77", "prop2@email.com");
        em.persist(prop);

        Cliente inq = new Cliente("Inquilino Teste", "888.888.888-88", "inq@email.com");
        em.persist(inq);

        Imovel im = new Imovel();
        im.setEndereco("Travessa Boa Vista, 50");
        im.setCep("65020-000");
        im.setDormitorios(2);
        im.setBanheiros(1);
        im.setSuites(0);
        im.setMetragem(80);
        im.setValorAluguelSugerido(new BigDecimal("1200.00"));
        im.setProprietario(prop);
        im.setTipoImovel(tipo);
        em.persist(im);

        em.getTransaction().commit();

        inquilinoId = inq.getId();
        imovelId    = im.getId();

        em.close();
    }

    @Before
    public void antes() {
        manager = factory.createEntityManager();
        locacaoRepository = new LocacaoRepository(manager);
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

    private Locacao novaLocacao(int ativo, Date inicio, Date fim, BigDecimal valor) {
        Locacao l = new Locacao();
        l.setAtivo(ativo);
        l.setDataInicio(inicio);
        l.setDataFim(fim);
        l.setDiaVencimento(5);
        l.setPercMulta(new BigDecimal("10.00"));
        l.setValorAluguel(valor);
        l.setImovel(manager.getReference(Imovel.class, imovelId));
        l.setInquilino(manager.getReference(Cliente.class, inquilinoId));
        return l;
    }

    private Date daqui(int anos) {
        Calendar c = Calendar.getInstance();
        c.add(Calendar.YEAR, anos);
        return c.getTime();
    }

    @Test
    public void deveInserirLocacaoAtiva() {
        Locacao l = novaLocacao(1, new Date(), daqui(1), new BigDecimal("1200.00"));
        locacaoRepository.salvaOuAtualiza(l);
        manager.flush();
        manager.clear();

        Locacao doBanco = locacaoRepository.buscaPorId(l.getId());

        assertNotNull(doBanco);
        assertThat(doBanco.getAtivo(), is(1));
    }

    @Test
    public void deveAtualizarLocacao() {
        Locacao l = novaLocacao(1, new Date(), daqui(1), new BigDecimal("1200.00"));
        locacaoRepository.salvaOuAtualiza(l);
        manager.flush();

        l.setObs("Renovada em 2026");
        locacaoRepository.salvaOuAtualiza(l);
        manager.flush();
        manager.clear();

        Locacao doBanco = locacaoRepository.buscaPorId(l.getId());
        assertThat(doBanco.getObs(), is("Renovada em 2026"));
    }

    @Test
    public void deveListarSomenteLocacoesAtivas() {
        // Insere uma ativa e uma inativa
        locacaoRepository.salvaOuAtualiza(novaLocacao(1, new Date(), daqui(1), new BigDecimal("1200.00")));
        locacaoRepository.salvaOuAtualiza(novaLocacao(0, daqui(-2), daqui(-1), new BigDecimal("800.00")));
        manager.flush();
        manager.clear();

        List<Locacao> ativas = locacaoRepository.listarAtivas();

        assertFalse(ativas.isEmpty());
        ativas.forEach(loc -> assertTrue(loc.isAtivo()));
    }

    @Test
    public void deveListarLocacoesPorInquilino() {
        locacaoRepository.salvaOuAtualiza(novaLocacao(1, new Date(), daqui(1), new BigDecimal("1200.00")));
        locacaoRepository.salvaOuAtualiza(novaLocacao(0, daqui(-2), daqui(-1), new BigDecimal("800.00")));
        manager.flush();
        manager.clear();

        List<Locacao> locacoes = locacaoRepository.listarPorInquilino(inquilinoId);

        assertThat(locacoes.size(), is(2));
    }
}
