package repository;

import entity.Administrator;
import org.hibernate.Session;

import javax.persistence.*;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

public class AdministratorRepo {

    private final EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("ro.tutorial.lab.SD");

    public void insertNewAdministrator(Administrator administrator) {
        EntityManager em = entityManagerFactory.createEntityManager();
        em.getTransaction().begin();
        em.persist(administrator);
        em.getTransaction().commit();
        em.close();
    }

    public void updateAdministrator(Administrator admin){
        EntityManager em = entityManagerFactory.createEntityManager();
        em.getTransaction().begin();

        Administrator updatedAdministrator;
        updatedAdministrator = em.find(Administrator.class, admin.getId());
        updatedAdministrator.setFirstName(admin.getFirstName());
        updatedAdministrator.setLastName(admin.getLastName());
        updatedAdministrator.setPhoneNumber(admin.getPhoneNumber());
        updatedAdministrator.setEmailAddress(admin.getEmailAddress());
        updatedAdministrator.setPassword(admin.getPassword());
        updatedAdministrator.setUsername(admin.getUsername());

        em.unwrap(Session.class).update(updatedAdministrator);
        em.flush();
        em.getTransaction().commit();
        em.close();
    }


    public Administrator findByUsernameAndPassword(String username, String password) throws IllegalArgumentException{
        EntityManager em = entityManagerFactory.createEntityManager();
        em.getTransaction().begin( );

        CriteriaBuilder cb=em.getCriteriaBuilder();
        CriteriaQuery<Administrator> cq=cb.createQuery(Administrator.class);
        Root<Administrator> admin=cq.from(Administrator.class);

        cq.where(cb.and(cb.equal(admin.get("username"), username),cb.equal(admin.get("password"), password)));
        return getAdministrator(em, cq, admin);
    }


    public Administrator findByUsername(String username) throws IllegalArgumentException{
        EntityManager em = entityManagerFactory.createEntityManager();
        em.getTransaction().begin( );

        CriteriaBuilder cb=em.getCriteriaBuilder();
        CriteriaQuery<Administrator> cq=cb.createQuery(Administrator.class);
        Root<Administrator> admin=cq.from(Administrator.class);

        cq.where(cb.equal(admin.get("username"), username));
        return getAdministrator(em, cq, admin);
    }

    private Administrator getAdministrator(EntityManager em, CriteriaQuery<Administrator> cq, Root<Administrator> admin) throws NoResultException {
        CriteriaQuery<Administrator> select = cq.select(admin);
        TypedQuery<Administrator> tq = em.createQuery(select);

        Administrator resultAdmin = tq.getSingleResult();
        em.getTransaction().commit();
        em.close();
        return resultAdmin;
    }

}
