package com.Majdi.Majdi_Project.Repository;

import com.Majdi.Majdi_Project.Entity.Message;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MessageRepository extends JpaRepository<Message, Long> {
}
