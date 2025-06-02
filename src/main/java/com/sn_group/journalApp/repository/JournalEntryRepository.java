package com.sn_group.journalApp.repository;

import java.util.Optional;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

import com.sn_group.journalApp.entity.JournalEntry;

public interface JournalEntryRepository extends MongoRepository<JournalEntry, Long>{

    Optional<JournalEntry> findById(ObjectId id);
    Optional<JournalEntry> deleteById(ObjectId id);

}
