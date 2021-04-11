package repository;

import entity.DocumentType;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;

public class DocumentTypeRepo {

    private final EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("ro.tutorial.lab.SD");

    public void insertNewDocument(DocumentType documentType) {
        EntityManager em = entityManagerFactory.createEntityManager();
        em.getTransaction().begin();
        em.persist(documentType);
        em.getTransaction().commit();
        em.close();
    }

    public DocumentType findById(String id) throws IllegalArgumentException{
        EntityManager em = entityManagerFactory.createEntityManager();
        em.getTransaction().begin( );

        CriteriaBuilder cb=em.getCriteriaBuilder();
        CriteriaQuery<DocumentType> cq=cb.createQuery(DocumentType.class);
        Root<DocumentType> documentTypeRoot=cq.from(DocumentType.class);

        cq.where(cb.equal(documentTypeRoot.get("id"), id));
        return getDocumentType(em, cq, documentTypeRoot);
    }

    public DocumentType findByName(String name) throws IllegalArgumentException{
        EntityManager em = entityManagerFactory.createEntityManager();
        em.getTransaction().begin( );

        CriteriaBuilder cb=em.getCriteriaBuilder();
        CriteriaQuery<DocumentType> cq=cb.createQuery(DocumentType.class);
        Root<DocumentType> documentTypeRoot=cq.from(DocumentType.class);

        cq.where(cb.equal(documentTypeRoot.get("name"), name));
        return getDocumentType(em, cq, documentTypeRoot);
    }

    public void removeDocumentType(String id) {
        EntityManager em = entityManagerFactory.createEntityManager();
        em.getTransaction().begin();
        DocumentType documentType = em.find(DocumentType.class, id);
        em.remove(documentType);
        em.getTransaction().commit();
        em.close();
    }

    private DocumentType getDocumentType(EntityManager em, CriteriaQuery<DocumentType> cq, Root<DocumentType> documentRoot) {
        CriteriaQuery<DocumentType> select = cq.select(documentRoot);
        TypedQuery<DocumentType> tq = em.createQuery(select);
        DocumentType resultDocumentType = tq.getSingleResult();

        em.getTransaction().commit();
        em.close();
        return resultDocumentType;
    }

    public List<DocumentType> findAllDocumentTypes()throws IllegalArgumentException{
        EntityManager em = entityManagerFactory.createEntityManager();
        em.getTransaction().begin( );

        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<DocumentType> cq = cb.createQuery(DocumentType.class);
        Root<DocumentType> documentRoot = cq.from(DocumentType.class);

        CriteriaQuery<DocumentType> select = cq.select(documentRoot);
        TypedQuery<DocumentType> tq = em.createQuery(select);
        List<DocumentType> documentTypeList = tq.getResultList();

        em.getTransaction().commit();
        em.close();
        return documentTypeList;
    }
}
