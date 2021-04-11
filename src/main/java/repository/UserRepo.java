package repository;

import entity.User;
import org.hibernate.Session;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;

public class UserRepo {

    private final EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("ro.tutorial.lab.SD");

    public void insertNewUser(User user) {
        EntityManager em = entityManagerFactory.createEntityManager();
        em.getTransaction().begin();
        em.persist(user);
        em.getTransaction().commit();
        em.close();
    }

    public void updateUser(User user){
        EntityManager em = entityManagerFactory.createEntityManager();
        em.getTransaction().begin();

        User updatedUser;
        updatedUser = em.find(User.class, user.getId());
        updatedUser.setFirstName(user.getFirstName());
        updatedUser.setLastName(user.getLastName());
        updatedUser.setEmailAddress(user.getEmailAddress());
        updatedUser.setPhoneNumber(user.getPhoneNumber());
        updatedUser.setUsername(user.getUsername());
        updatedUser.setPassword(user.getPassword());
        //updatedUser.setResidences(user.getResidences());

        em.unwrap(Session.class).update(updatedUser);
        em.flush();
        em.getTransaction().commit();
        em.close();
    }

    public User findByUsernameAndPassword(String username, String password) throws IllegalArgumentException{
        EntityManager em = entityManagerFactory.createEntityManager();
        em.getTransaction().begin( );

        CriteriaBuilder cb=em.getCriteriaBuilder();
        CriteriaQuery<User> cq=cb.createQuery(User.class);
        Root<User> user=cq.from(User.class);

        cq.where(cb.and(cb.equal(user.get("username"), username),cb.equal(user.get("password"), password)));
        return getUser(em, cq, user);
    }

    public User findByUsername(String username) throws IllegalArgumentException{
        EntityManager em = entityManagerFactory.createEntityManager();
        em.getTransaction().begin( );

        CriteriaBuilder cb=em.getCriteriaBuilder();
        CriteriaQuery<User> cq=cb.createQuery(User.class);
        Root<User> user=cq.from(User.class);

        cq.where(cb.equal(user.get("username"), username));
        return getUser(em, cq, user);
    }

    private User getUser(EntityManager em, CriteriaQuery<User> cq, Root<User> user) {
        CriteriaQuery<User> select = cq.select(user);
        TypedQuery<User> tq = em.createQuery(select);
        User resultUser = tq.getSingleResult();

        em.getTransaction().commit();
        em.close();
        return resultUser;
    }

    public void removeUser(String id) {
        EntityManager em = entityManagerFactory.createEntityManager();
        em.getTransaction().begin();
        User user = em.find(User.class, id);
        em.remove(user);
        em.getTransaction().commit();
        em.close();
    }

    public List<User> findAllUsers()throws IllegalArgumentException{
        EntityManager em = entityManagerFactory.createEntityManager();
        em.getTransaction().begin( );

        CriteriaBuilder cb=em.getCriteriaBuilder();
        CriteriaQuery<User> cq=cb.createQuery(User.class);
        Root<User> userRoot=cq.from(User.class);

        CriteriaQuery<User> select = cq.select(userRoot);
        TypedQuery<User> tq = em.createQuery(select);
        List<User> userList = tq.getResultList();

        em.getTransaction().commit();
        em.close();
        return userList;
    }

    public User findByFirstNameAndLastName(String firstName, String lastName) {
        EntityManager em = entityManagerFactory.createEntityManager();
        em.getTransaction().begin( );

        CriteriaBuilder cb=em.getCriteriaBuilder();
        CriteriaQuery<User> cq=cb.createQuery(User.class);
        Root<User> user=cq.from(User.class);

        cq.where(cb.and(cb.equal(user.get("firstName"), firstName),cb.equal(user.get("lastName"), lastName)));
        return getUser(em, cq, user);
    }
}

