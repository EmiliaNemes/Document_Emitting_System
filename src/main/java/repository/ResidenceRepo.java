package repository;

import dto.UserDto;
import entity.Residence;
import entity.User;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;

public class ResidenceRepo {

    private final EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("ro.tutorial.lab.SD");

    public void insertNewResidence(Residence residence) {
        EntityManager em = entityManagerFactory.createEntityManager();
        em.getTransaction().begin();
        em.persist(residence);
        em.getTransaction().commit();
        em.close();
    }

    public Residence findById(String id) throws IllegalArgumentException{
        EntityManager em = entityManagerFactory.createEntityManager();
        em.getTransaction().begin( );

        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Residence> cq = cb.createQuery(Residence.class);
        Root<Residence> residenceRoot = cq.from(Residence.class);

        cq.where(cb.equal(residenceRoot.get("id"), id));
        CriteriaQuery<Residence> select = cq.select(residenceRoot);
        TypedQuery<Residence> tq = em.createQuery(select);
        Residence resultResidence = tq.getSingleResult();

        em.getTransaction().commit();
        em.close();
        return resultResidence;
    }

    public List<Residence> findByUser(User user) throws IllegalArgumentException{
        EntityManager em = entityManagerFactory.createEntityManager();
        em.getTransaction().begin( );

        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Residence> cq = cb.createQuery(Residence.class);
        Root<Residence> residenceRoot = cq.from(Residence.class);

        cq.where(cb.equal(residenceRoot.get("user"), user));
        CriteriaQuery<Residence> select = cq.select(residenceRoot);
        TypedQuery<Residence> tq = em.createQuery(select);
        List<Residence> resultResidences = tq.getResultList();

        em.getTransaction().commit();
        em.close();
        return resultResidences;
    }

    public void removeResidence(String id) {
        EntityManager em = entityManagerFactory.createEntityManager();
        em.getTransaction().begin();
        Residence residence = em.find(Residence.class, id);
        em.remove(residence);
        em.getTransaction().commit();
        em.close();
    }

    public Residence findByCityStreetNumberUser(String city, String street, String number, User user) {
        EntityManager em = entityManagerFactory.createEntityManager();
        em.getTransaction().begin( );

        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Residence> cq = cb.createQuery(Residence.class);
        Root<Residence> residenceRoot=cq.from(Residence.class);

        cq.where(cb.and(cb.equal(residenceRoot.get("city"), city),cb.equal(residenceRoot.get("street"), street), cb.equal(residenceRoot.get("number"), number), cb.equal(residenceRoot.get("user"), user)));
        return getResidence(em, cq, residenceRoot);
    }

    private Residence getResidence(EntityManager em, CriteriaQuery<Residence> cq, Root<Residence> residenceRoot) {
        CriteriaQuery<Residence> select = cq.select(residenceRoot);
        TypedQuery<Residence> tq = em.createQuery(select);
        Residence resultResidence = tq.getSingleResult();

        em.getTransaction().commit();
        em.close();
        return resultResidence;
    }
}
