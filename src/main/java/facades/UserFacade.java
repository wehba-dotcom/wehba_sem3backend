package facades;

import entities.Role;
import entities.User;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

import security.errorhandling.AuthenticationException;

import java.util.ArrayList;
import java.util.List;

public class UserFacade {

    private static EntityManagerFactory emf;
    private static UserFacade instance;

    private UserFacade() {
    }

    /**
     * @param _emf
     * @return the instance of this facade.
     */
    public static UserFacade getUserFacade(EntityManagerFactory _emf) {
        if (instance == null) {
            emf = _emf;
            instance = new UserFacade();
        }
        return instance;
    }

    public User getVeryfiedUser(String username, String password) throws AuthenticationException {
        EntityManager em = emf.createEntityManager();
        User user;
        try {
            user = em.find(User.class, username);
            if (user == null || !user.verifyPassword(password)) {
                throw new AuthenticationException("Invalid user name or password");
            }
        } finally {
            em.close();
        }
        return user;
    }

    public User registerNewUser(String username, String password){
        EntityManager em = emf.createEntityManager();
        Role role = new Role("user");
        User user = new User(username,password);
        user.addRole(role);
        try {
            if(em.find(User.class, username) == null){
                em.getTransaction().begin();
                em.persist(user);
                em.getTransaction().commit();
            }
            else throw new Exception("Brugeren med " + username + " eksistere allerede!");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            em.close();
        }

        return user;
    }

    public List<User> initDB() throws Exception {
        EntityManager em = emf.createEntityManager();
        if(em.find(User.class,"user") == null) {


            User user = new User("user", "test");
            User admin = new User("admin", "test");
            User both = new User("user_admin", "test");

            em.getTransaction().begin();
            Role userRole = new Role("user");
            Role adminRole = new Role("admin");
            user.addRole(userRole);
            admin.addRole(adminRole);
            both.addRole(userRole);
            both.addRole(adminRole);
            em.persist(userRole);
            em.persist(adminRole);
            em.persist(user);
            em.persist(admin);
            em.persist(both);
            em.getTransaction().commit();
            List<User> userlist = new ArrayList<>();
            userlist.add(user);
            userlist.add(admin);
            userlist.add(both);
            return userlist;
        }
        else throw new Exception("Init already happend!");
    }
}
