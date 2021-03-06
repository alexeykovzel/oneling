package com.alexeykovzel.db.models;

import com.github.pemistahl.lingua.api.Language;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import javax.persistence.*;
import java.util.Objects;

@EntityListeners(Chat.AuditTrailListener.class)
@Entity
@Table(name = "chat")

@Getter
@Setter
@NoArgsConstructor
public class Chat {

    @Id
    @Column(name = "id", length = 32)
    @Setter(AccessLevel.PROTECTED)
    private String id;

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "firstName", column = @Column(name = "first_name", length = 64, nullable = false)),
            @AttributeOverride(name = "lastName", column = @Column(name = "last_name", length = 64)),
            @AttributeOverride(name = "username", column = @Column(name = "username", length = 32, nullable = false, unique = true))
    })
    private User user;

    @Column(name = "dictionary_key")
    private String dictionaryKey = Language.ENGLISH.name();

    public Chat(String id, User user) {
        this.id = id;
        this.user = user;
    }

    @Override
    public String toString() {
        return String.format("Chat{id=%s, firstName='%s', lastName='%s', username='%s'}'",
                id, user.getFirstName(), user.getLastName(), user.getUsername());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Chat chat = (Chat) o;
        return id.equals(chat.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    // log SQL transactions
    static final class AuditTrailListener {
        private static final Log log = LogFactory.getLog(AuditTrailListener.class);

        @PrePersist
        @PreUpdate
        @PreRemove
        private void beforeAnyUpdate(Chat chat) {
            log.info("[USER AUDIT] About to add/update/delete chat: id=" + chat.getId());
        }

        @PostPersist
        @PostUpdate
        @PostRemove
        private void afterAnyUpdate(Chat chat) {
            log.info("[USER AUDIT] add/update/delete complete for chat: id=" + chat.getId());
        }

        @PostLoad
        private void afterLoad(Chat chat) {
            log.info("[USER AUDIT] chat loaded from database: id=" + chat.getId());
        }
    }
}
