package facades;

import entities.Deck;
import entities.RenameMe;
import org.junit.jupiter.api.*;
import utils.EMF_Creator;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

import static org.junit.jupiter.api.Assertions.*;

class DeckFacadeTest {

    private static EntityManagerFactory emf;
    private static FacadeExample facade;
    private Deck d1;

    public DeckFacadeTest() {
    }

    @BeforeAll
    public static void setUpClass() {
        emf = EMF_Creator.createEntityManagerFactoryForTest();
        facade = FacadeExample.getFacadeExample(emf);
    }

    @BeforeEach
    void setUp() {
        EntityManager em = emf.createEntityManager();
        d1 = new Deck("sdy98aWhd62d");

        try {
            em.getTransaction().begin();
            em.createNamedQuery("Deck.deleteAllRows").executeUpdate();
            em.persist(d1);
            em.getTransaction().commit();
        } finally {
            em.close();
        }
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void persistDeck() {
        String expected = d1.getDeck_id();
        String actual = "sdy98aWhd62d";
        assertEquals(expected, actual);
    }
}