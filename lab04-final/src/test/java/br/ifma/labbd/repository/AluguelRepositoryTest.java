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

public class AluguelRepositoryTest {

    private EntityManager manager;
    private static EntityManagerFactory factory;
    private AluguelRepository aluguelRepository;

    private static Integer locacaoId;

    @BeforeClass
    public static void inicio() {
        factory = Persistence.createEntityManagerFactory("lab04PU-test");

        EntityManager em = factory.createEntityManager();
        em.getTransaction().begin();

        TipoImovel tipo = new TipoImovel("Apartamento");
        em.persist(tipo);

        Cliente prop = new Cliente("Prop Aluguel", "111.222.333-44", "prop3@email.com");
        em.persist(prop);

        Cliente inq = new Cliente("Inq Aluguel", "444.333.222-11", "inq2@email.com");
        em.persist(inq);

        Imovel im = new Imovel();
        im.setEndereco("Rua Teste, 999");
        im.setCep("65030-000");
        im.setDormitorios(1);
        im.setBanheiros(1);
        im.setSuites(0);
        im.setMetragem(50);
        im.setValorAluguelSugerido(new BigDecimal("700.00"));
        im.setProprietario(prop);
        im.setTipoImovel(tipo);
        em.persist(im);

        Locacao loc = new Locacao();
        loc.setAtivo(1);
        loc.setDataInicio(mkDate(2025, 1, 1));
        loc.setDataFim(mkDate(2026, 12, 31));
        loc.setDiaVencimento(10);
        loc.setPercMulta(new BigDecimal("2.00"));
        loc.setValorAluguel(new BigDecimal("700.00"));
        loc.setImovel(im);
        loc.setInquilino(inq);
        em.persist(loc);

        em.getTransaction().commit();
        locacaoId = loc.getId();
        em.close();
    }

    @Before
    public void antes() {
        manager = factory.createEntityManager();
        aluguelRepository = new AluguelRepository(manager);
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

    private Aluguel novoAluguel(Date vencimento, BigDecimal valor) {
        Aluguel a = new Aluguel();
        a.setLocacao(manager.getReference(Locacao.class, locacaoId));
        a.setDtVencimento(vencimento);
        a.setValorPago(valor);
        return a;
    }

    private static Date mkDate(int ano, int mes, int dia) {
        Calendar c = Calendar.getInstance();
        c.set(ano, mes - 1, dia, 0, 0, 0);
        c.set(Calendar.MILLISECOND, 0);
        return c.getTime();
    }

    @Test
    public void deveInserirAluguel() {
        Aluguel a = novoAluguel(mkDate(2025, 2, 10), new BigDecimal("700.00"));
        aluguelRepository.salvaOuAtualiza(a);
        manager.flush();
        manager.clear();

        Aluguel doBanco = aluguelRepository.buscaPorId(a.getId());

        assertNotNull(doBanco);
        assertThat(doBanco.getValorPago(), is(new BigDecimal("700.00")));
    }

    @Test
    public void deveAtualizarAluguel() {
        Aluguel a = novoAluguel(mkDate(2025, 3, 10), new BigDecimal("700.00"));
        aluguelRepository.salvaOuAtualiza(a);
        manager.flush();

        a.setObs("Pago com atraso");
        aluguelRepository.salvaOuAtualiza(a);
        manager.flush();
        manager.clear();

        Aluguel doBanco = aluguelRepository.buscaPorId(a.getId());
        assertThat(doBanco.getObs(), is("Pago com atraso"));
    }

    @Test
    public void deveListarPorLocacaoEmOrdemDecrescente() {
        aluguelRepository.salvaOuAtualiza(novoAluguel(mkDate(2025, 2, 10), new BigDecimal("700.00")));
        aluguelRepository.salvaOuAtualiza(novoAluguel(mkDate(2025, 3, 10), new BigDecimal("700.00")));
        aluguelRepository.salvaOuAtualiza(novoAluguel(mkDate(2025, 4, 10), new BigDecimal("700.00")));
        manager.flush();
        manager.clear();

        List<Aluguel> lista = aluguelRepository.listarPorLocacao(locacaoId);

        assertThat(lista.size(), is(3));

        // Verifica ordem decrescente: cada data deve ser >= a próxima
        for (int i = 0; i < lista.size() - 1; i++) {
            assertFalse(
                lista.get(i).getDtVencimento().before(lista.get(i + 1).getDtVencimento())
            );
        }
    }
}
