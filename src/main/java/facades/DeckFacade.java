package facades;

import entities.Deck;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

public class DeckFacade {

    private static DeckFacade instance;
    private static EntityManagerFactory emf;

    private DeckFacade() {
    }

    public static DeckFacade getDeckFacade(EntityManagerFactory _emf) {
        if (instance == null) {
            emf = _emf;
            instance = new DeckFacade();
        }
        return instance;
    }

    private EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public Deck persistDeck(String deck_id) {
        EntityManager em = getEntityManager();
        Deck deck = new Deck(deck_id);

        try {
            em.getTransaction().begin();
            em.persist(deck);
            em.getTransaction().commit();
        } finally {
            em.close();
        }
        return deck;
    }

    public static void main(String[] args) {
    }
}
