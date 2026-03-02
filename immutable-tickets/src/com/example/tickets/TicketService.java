package com.example.tickets;

import java.util.List;

public class TicketService {
    public IncidentTicket createInitial(String id, String email, String title) {
        return new IncidentTicket.Builder()
                .id(id)
                .reporterEmail(email)
                .title(title)
                .build();
    }

    public IncidentTicket addTag(IncidentTicket original, String newTag) {
        // Create a new list for the builder
        java.util.List<String> newTags = new java.util.ArrayList<>(original.getTags());
        newTags.add(newTag);

        // Return a NEW instance using the builder
        return original.toBuilder()
                .tags(newTags)
                .build();
    }
}