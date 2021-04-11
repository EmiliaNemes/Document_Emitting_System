package dto;

import entity.User;

import java.time.LocalDate;

public class RequestDto {

    private String id;
    private User user;
    private DocumentTypeDto documentTypeDto;
    private String content;
    private LocalDate requestDate;
    private ResidenceDto residenceDto;
    private boolean approved;
    private LocalDate approvalDate;

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

    public DocumentTypeDto getDocumentType() {
        return documentTypeDto;
    }

    public void setDocumentType(DocumentTypeDto documentTypeDto) {
        this.documentTypeDto = documentTypeDto;
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

    public void setResidenceDto(ResidenceDto residenceDto) {
        this.residenceDto = residenceDto;
    }

    public ResidenceDto getResidenceDto() {
        return residenceDto;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        RequestDto that = (RequestDto) o;

        if (approved != that.approved) return false;
        if (!id.equals(that.id)) return false;
        if (!user.equals(that.user)) return false;
        if (!documentTypeDto.equals(that.documentTypeDto)) return false;
        if (!content.equals(that.content)) return false;
        if (!residenceDto.equals(that.residenceDto)) return false;
        return approvalDate.equals(that.approvalDate);
    }

    @Override
    public int hashCode() {
        int result = id.hashCode();
        result = 31 * result + user.hashCode();
        result = 31 * result + documentTypeDto.hashCode();
        result = 31 * result + content.hashCode();
        result = 31 * result + residenceDto.hashCode();
        result = 31 * result + (approved ? 1 : 0);
        result = 31 * result + approvalDate.hashCode();
        return result;
    }
}
