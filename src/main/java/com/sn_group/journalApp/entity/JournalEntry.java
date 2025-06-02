package com.sn_group.journalApp.entity;

import java.time.LocalDateTime;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import com.mongodb.lang.NonNull;

import lombok.Data;
import lombok.NoArgsConstructor;
import io.swagger.v3.oas.annotations.media.Schema; // ✅ Correct import


@Document(collection = "journal-entries")
@Data
@NoArgsConstructor
public class JournalEntry {

    @Schema(type = "string", example = "60c72b2f9b1d8a001c8e4b4d")
    @Id
    private ObjectId id;

    @NonNull
    private String title;
    @NonNull
    private String content;
    private LocalDateTime date;
}
