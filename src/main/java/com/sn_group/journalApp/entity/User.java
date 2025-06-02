package com.sn_group.journalApp.entity;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import io.swagger.v3.oas.annotations.media.Schema; // ✅ Correct import

import com.mongodb.lang.NonNull;
import java.util.*;

import lombok.Data;

@Document(collection = "users")
@Data
public class User {

    @Schema(type = "string", example = "60c72b2f9b1d8a001c8e4b4d")
    @Id
    private ObjectId id;

    @Indexed(unique = true)
    @NonNull
    private String userName;

    @NonNull
    private String password;

    @DBRef
    private List<JournalEntry> JournalEntries = new ArrayList<>();
}