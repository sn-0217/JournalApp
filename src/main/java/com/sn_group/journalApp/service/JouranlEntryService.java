package com.sn_group.journalApp.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sn_group.journalApp.entity.JournalEntry;
import com.sn_group.journalApp.entity.User;
import com.sn_group.journalApp.repository.JournalEntryRepository;

@Service
public class JouranlEntryService {

    @Autowired
    private JournalEntryRepository journalEntryRepository;
    @Autowired
    private UserService userService;

    public void saveEntry(JournalEntry journalEntry, String userName){
        User user = userService.findByUserName(userName);
        journalEntry.setDate(LocalDateTime.now());
        JournalEntry saved = journalEntryRepository.save(journalEntry);
        user.getJournalEntries().add(saved);
        userService.saveEntry(user);
    }

    public void saveAllEntries(List<JournalEntry> journalEntries){
        journalEntries.forEach(entry -> entry.setDate(LocalDateTime.now()));
        journalEntryRepository.saveAll(journalEntries);
    }

    public List<JournalEntry> getAll() {
        return journalEntryRepository.findAll();
    }

    public Optional<JournalEntry> findById(ObjectId oId){
        return journalEntryRepository.findById(oId);
    }

    public void deleteById(ObjectId oId, String userName){
        User user  = userService.findByUserName(userName);
        user.getJournalEntries().removeIf(entry -> entry.getId().equals(oId));
        journalEntryRepository.deleteById(oId);
        userService.saveEntry(user);
    }

    public void saveEntry(JournalEntry journalEntry) {
        journalEntryRepository.save(journalEntry);
    }
}
