package entity;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Date;
import java.util.UUID;

@Entity
@Table(name = "request")
public class Request {

    @Id
    private String id;

    @ManyToOne
    @JoinColumn(name = "fk_user")
    private User user;

    @ManyToOne
    @JoinColumn(name = "fk_document_type")
    private DocumentType documentType;

    @Column(name = "content")
    private String content;

    @Column(name = "request_date")
    private LocalDate requestDate;

    @ManyToOne
    @JoinColumn(name = "fk_residence")
    private Residence residence;

    @Column(name = "approved")
    private boolean approved;

    @Column(name = "approval_date", nullable = true)
    private LocalDate approvalDate;


    public Request(User user, DocumentType documentType, String content, Residence residence) {
        this.id = UUID.randomUUID().toString();
        this.user = user;
        this.documentType = documentType;
        this.content = content;
        this.requestDate = LocalDate.now();
        this.residence = residence;
        this.approved = false;
        this.approvalDate = null;
    }

    public Request(){}

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public DocumentType getDocumentType() {
        return documentType;
    }

    public void setDocumentType(DocumentType documentType) {
        this.documentType = documentType;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public LocalDate getRequestDate() {
        return requestDate;
    }

    public void setRequestDate(LocalDate requestDate) {
        this.requestDate = requestDate;
    }

    public Residence getResidence() {
        return residence;
    }

    public void setResidence(Residence residence) {
        this.residence = residence;
    }

    public boolean isApproved() {
        return approved;
    }

    public void setApproved(boolean approved) {
        this.approved = approved;
    }

    public LocalDate getApprovalDate() {
        return approvalDate;
    }

    public void setApprovalDate(LocalDate approvalDate) {
        this.approvalDate = approvalDate;
    }
}
