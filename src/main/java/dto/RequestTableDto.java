package dto;

import entity.User;

import java.time.LocalDate;

public class RequestTableDto {

    private String id;
    private String user;
    private String documentType;
    private String documentContent;
    private LocalDate requestDate;
    private String residence;
    private String approved;
    private LocalDate approvalDate;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getDocumentType() {
        return documentType;
    }

    public void setDocumentType(String documentType) {
        this.documentType = documentType;
    }

    public String getDocumentContent() {
        return documentContent;
    }

    public void setDocumentContent(String documentContent) {
        this.documentContent = documentContent;
    }

    public LocalDate getRequestDate() {
        return requestDate;
    }

    public void setRequestDate(LocalDate requestDate) {
        this.requestDate = requestDate;
    }

    public String getResidence() {
        return residence;
    }

    public void setResidence(String residence) {
        this.residence = residence;
    }

    public String getApproved() {
        return approved;
    }

    public void setApproved(String approved) {
        this.approved = approved;
    }

    public LocalDate getApprovalDate() {
        return approvalDate;
    }

    public void setApprovalDate(LocalDate approvalDate) {
        this.approvalDate = approvalDate;
    }

    @Override
    public String toString() {
        String approval;
        if(approved.equals("Yes")){
            approval = "Approved";
        } else {
            approval = "Not Approved";
        }
        if(approvalDate == null){
            return  requestDate + " // " +
                    user + " // " +
                    documentType +  " // " +
                    documentContent + " // " +
                    residence + " // " +
                    approval;
        }
        return requestDate + " // " +
                user + " // " +
                documentType +  " // " +
                documentContent + " // " +
                residence + " // " +
                approval + " // " +
                approvalDate;
    }
}
