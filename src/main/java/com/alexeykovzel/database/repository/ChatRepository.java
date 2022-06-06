package com.alexeykovzel.database.repository;

import com.alexeykovzel.database.entity.Chat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ChatRepository extends JpaRepository<Chat, String> {
    List<Chat> findByUserFirstName(String firstName);

    @Modifying(flushAutomatically = true)
    @Query("UPDATE Chat c SET c.dictionaryKey = :key WHERE c.id = :id")
    void setDictionaryKey(@Param("key") int key, @Param("id") String chatId);

    @Query("SELECT c.dictionaryKey FROM Chat c WHERE c.id = :id")
    Integer findDictionaryKeyByChatId(@Param("id") String chatId);
}