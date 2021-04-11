package entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.UUID;

@Entity
@Table(name = "document_type")
public class DocumentType {

    @Id
    private String id;

    @Column(name = "name", nullable = false)
    private String name;

    public DocumentType(String name) {
        this.id = UUID.randomUUID().toString();
        this.name = name;
    }

    public DocumentType(){}

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
