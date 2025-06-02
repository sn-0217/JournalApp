package com.sn_group.journalApp.controller;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sn_group.journalApp.entity.JournalEntry;
import com.sn_group.journalApp.service.JouranlEntryService;
import com.sn_group.journalApp.service.UserService;
import com.sn_group.journalApp.entity.User;

import java.util.Optional;
import java.util.function.Predicate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@RestController
@RequestMapping("/api/v1/journal")
public class JournalEntryController {

    @Autowired
    private JouranlEntryService jouranlEntryService;

    @Autowired
    private UserService userService;

    @PostMapping("{userName}")
    public ResponseEntity<?> saveJournalEntry(@RequestBody JournalEntry journalEntry, @PathVariable String userName){
        try{
            jouranlEntryService.saveEntry(journalEntry, userName);
            return new ResponseEntity<>(userService.findByUserName(userName), HttpStatus.CREATED);
        }catch(Exception e){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/all")
    public ResponseEntity<String> saveAllEntries(@RequestBody List<JournalEntry> journalEntries) {
        try {
            Predicate<String> isInvalid = str -> str == null || str.trim().isEmpty();

            Optional<JournalEntry> invalidEntry = journalEntries.stream()
                .filter(entry -> isInvalid.test(entry.getTitle()) || isInvalid.test(entry.getContent()))
                .findAny();

            if (invalidEntry.isPresent()) {
                return new ResponseEntity<>("Title or Content is missing in one or more entries", HttpStatus.BAD_REQUEST);
            }

            jouranlEntryService.saveAllEntries(journalEntries);

            return new ResponseEntity<>("Total entries added: " + journalEntries.size(), HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>("An error occurred: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/{username}")
    public ResponseEntity<?> getAllJournalEntriesOfUser(@PathVariable("username") String userName) {
        User user = userService.findByUserName(userName);
        if (user == null) {
            Map<String, String> response = new HashMap<>();
            response.put("message", "User not found: " + userName);
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }

        List<JournalEntry> journalEntries = user.getJournalEntries();
        if (journalEntries == null || journalEntries.isEmpty()) {
            Map<String, String> response = new HashMap<>();
            response.put("message", "No Journal Entries found for the user: " + userName);
            return new ResponseEntity<>(response, HttpStatus.OK); // Or NOT_FOUND
        }

        return new ResponseEntity<>(journalEntries, HttpStatus.OK);
    }



    @GetMapping("id/{myId}")
    public ResponseEntity<JournalEntry> getJournalEntryById(@PathVariable ObjectId myId){

        Optional<JournalEntry> entry = jouranlEntryService.findById(myId);
        if(entry.isPresent()){
            return new ResponseEntity<>(entry.get(), HttpStatus.OK);
        }

        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("id/{userName}/{id}")
    public ResponseEntity<?> deleteJournalEntryById(@PathVariable ObjectId id, @PathVariable String userName){
        jouranlEntryService.deleteById(id, userName);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PutMapping("id/{userName}/{id}")
    public ResponseEntity<?> updJournalEntryById(
        @PathVariable ObjectId id,
        @RequestBody JournalEntry newEntry,
        @PathVariable String userName){
        JournalEntry oldEntry = jouranlEntryService.findById(id).orElse(null);
        Predicate<String> isValid = entry -> entry != null && !entry.isEmpty();
        if(oldEntry != null){
            oldEntry.setTitle(isValid.test(newEntry.getTitle()) ? newEntry.getTitle() : oldEntry.getTitle());
            oldEntry.setContent(isValid.test(newEntry.getContent()) ? newEntry.getContent() : oldEntry.getContent());
            jouranlEntryService.saveEntry(oldEntry);
            return new ResponseEntity<>(oldEntry, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}