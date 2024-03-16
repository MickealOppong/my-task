package com.opp.todo.model;

import java.time.LocalDate;

public record TodoEditRequest(String title, String description, LocalDate dueDate,
                              String priority, String project) {
}
