package repository;

import entity.*;
import mapper.UserMapper;
import org.hibernate.Session;

import javax.persistence.*;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.Date;
import java.util.List;

public class RequestRepo {

    private final EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("ro.tutorial.lab.SD");

    public void insertNewRequest(Request request) {
        EntityManager em = entityManagerFactory.createEntityManager();
        em.getTransaction().begin();
        em.persist(request);
        em.getTransaction().commit();
        em.close();
    }

    public void removeRequest(String id) {
        EntityManager em = entityManagerFactory.createEntityManager();
        em.getTransaction().begin();
        Request request = em.find(Request.class, id);
        em.remove(request);
        em.getTransaction().commit();
        em.close();
    }

    public void updateRequest(Request request){
        EntityManager em = entityManagerFactory.createEntityManager();
        em.getTransaction().begin();

        Request updatedRequest;
        updatedRequest = em.find(Request.class, request.getId());
        updatedRequest.setUser(request.getUser());
        updatedRequest.setDocumentType(request.getDocumentType());
        updatedRequest.setContent(request.getContent());
        updatedRequest.setRequestDate(request.getRequestDate());
        updatedRequest.setApproved(request.isApproved());
        updatedRequest.setApprovalDate(request.getApprovalDate());
        updatedRequest.setResidence(request.getResidence());
        em.unwrap(Session.class).update(updatedRequest);
        em.flush();
        em.getTransaction().commit();
        em.close();
    }

    public Request getRequestById(String id){
        EntityManager em = entityManagerFactory.createEntityManager();
        em.getTransaction().begin( );

        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Request> cq = cb.createQuery(Request.class);
        Root<Request> requestRoot = cq.from(Request.class);

        cq.where(cb.equal(requestRoot.get("id"), id));
        CriteriaQuery<Request> select = cq.select(requestRoot);
        TypedQuery<Request> tq = em.createQuery(select);
        Request result = tq.getSingleResult();

        em.getTransaction().commit();
        em.close();
        return result;
    }


    public List<Request> getRequestsByUser(User user){
        EntityManager em = entityManagerFactory.createEntityManager();
        em.getTransaction().begin( );

        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Request> cq = cb.createQuery(Request.class);
        Root<Request> requestRoot = cq.from(Request.class);

        cq.where(cb.equal(requestRoot.get("user"), user));
        return getRequests(em, cq, requestRoot);
    }

    public List<Request> getRequestsByUserAndResidenceAndDocumentType(User user, Residence residence, DocumentType documentType) {
        EntityManager em = entityManagerFactory.createEntityManager();
        em.getTransaction().begin( );

        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Request> cq = cb.createQuery(Request.class);
        Root<Request> requestRoot = cq.from(Request.class);

        cq.where(cb.and(cb.equal(requestRoot.get("user"), user), cb.equal(requestRoot.get("residence"), residence), cb.equal(requestRoot.get("documentType"), documentType)));
        return getRequests(em, cq, requestRoot);
    }

    public List<Request> findAllRequests()throws IllegalArgumentException{
        EntityManager em = entityManagerFactory.createEntityManager();
        em.getTransaction().begin( );

        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Request> cq = cb.createQuery(Request.class);
        Root<Request> requestRoot = cq.from(Request.class);

        return getRequests(em, cq, requestRoot);
    }

    private List<Request> getRequests(EntityManager em, CriteriaQuery<Request> cq, Root<Request> requestRoot) {
        CriteriaQuery<Request> select = cq.select(requestRoot);
        TypedQuery<Request> tq = em.createQuery(select);
        List<Request> resultList = tq.getResultList();

        em.getTransaction().commit();
        em.close();
        return resultList;
    }

    public List<Request> getRequestsByDocumentType(DocumentType documentType) {
        EntityManager em = entityManagerFactory.createEntityManager();
        em.getTransaction().begin( );

        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Request> cq = cb.createQuery(Request.class);
        Root<Request> requestRoot = cq.from(Request.class);

        cq.where(cb.equal(requestRoot.get("documentType"), documentType));
        return getRequests(em, cq, requestRoot);
    }

    public List<Request> getRequestsByApproval(boolean approval) {
        EntityManager em = entityManagerFactory.createEntityManager();
        em.getTransaction().begin( );

        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Request> cq = cb.createQuery(Request.class);
        Root<Request> requestRoot = cq.from(Request.class);

        cq.where(cb.equal(requestRoot.get("approved"), approval));
        return getRequests(em, cq, requestRoot);
    }
}
