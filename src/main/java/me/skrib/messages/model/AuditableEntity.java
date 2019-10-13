package me.skrib.messages.model;

import lombok.Getter;

import javax.persistence.MappedSuperclass;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;

@MappedSuperclass
@Getter
public class AuditableEntity {

    private OffsetDateTime createdDate;
    private OffsetDateTime lastModifiedDate;

    @PrePersist
    public void prePersist() {
        createdDate = OffsetDateTime.now(ZoneOffset.UTC);
    }

    @PreUpdate
    public void preUpdate() {
        lastModifiedDate = OffsetDateTime.now(ZoneOffset.UTC);
    }

}
