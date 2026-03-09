package com.example.tickets;

import java.util.*;

public final class IncidentTicket {
    private final String id;
    private final String reporterEmail;
    private final String title;
    private final String description;
    private final String priority;
    private final List<String> tags;
    private final String assigneeEmail;
    private final boolean customerVisible;
    private final int slaMinutes;
    private final String source;

    // Private constructor: can only be called by the Builder
    private IncidentTicket(Builder b) {
        this.id = b.id;
        this.reporterEmail = b.reporterEmail;
        this.title = b.title;
        this.description = b.description;
        this.priority = b.priority;
        // Defensive copy to ensure immutability
        this.tags = Collections.unmodifiableList(new ArrayList<>(b.tags));
        this.assigneeEmail = b.assigneeEmail;
        this.customerVisible = b.customerVisible;
        this.slaMinutes = b.slaMinutes;
        this.source = b.source;
    }

    // Getters only - no setters
    public String getId() { return id; }
    public String getReporterEmail() { return reporterEmail; }
    public String getTitle() { return title; }
    public List<String> getTags() { return tags; }

    public Builder toBuilder() {
        return new Builder()
                .id(id).reporterEmail(reporterEmail).title(title)
                .description(description).priority(priority)
                .tags(new ArrayList<>(tags)).assigneeEmail(assigneeEmail)
                .customerVisible(customerVisible).slaMinutes(slaMinutes).source(source);
    }

    @Override
    public String toString() {
        return "Ticket[id=" + id + ", title=" + title + ", tags=" + tags + "]";
    }

    public static class Builder {
        private String id;
        private String reporterEmail;
        private String title;
        private String description = "";
        private String priority = "MEDIUM";
        private List<String> tags = new ArrayList<>();
        private String assigneeEmail;
        private boolean customerVisible = false;
        private int slaMinutes = 60;
        private String source = "WEB";

        public Builder id(String id) { this.id = id; return this; }
        public Builder reporterEmail(String email) { this.reporterEmail = email; return this; }
        public Builder title(String title) { this.title = title; return this; }
        public Builder priority(String p) { this.priority = p; return this; }
        public Builder tags(List<String> t) { this.tags = t; return this; }

        public IncidentTicket build() {

            // Centralized Validation

            Validation.check(Validation.isValidId(id), "Invalid ID format");

            Validation.check(Validation.isEmail(reporterEmail), "Invalid reporter email");
            Validation.check(title != null && !title.isEmpty() && title.length() <= 80, "Invalid title");

            List<String> validPriorities = Arrays.asList("LOW", "MEDIUM", "HIGH", "CRITICAL");
            Validation.check(validPriorities.contains(priority), "Invalid priority");


            if (slaMinutes != 0) {
                Validation.check(slaMinutes >= 5 && slaMinutes <= 7200, "SLA must be 5-7200");
            }

            return new IncidentTicket(this);
        }
    }
}