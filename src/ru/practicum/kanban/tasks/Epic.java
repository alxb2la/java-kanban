package ru.practicum.kanban.tasks;

import java.util.ArrayList;
import java.util.List;

public class Epic extends Task {
    private List<Integer> linkedSubtasksId;

    public Epic(String title, String description) {
        super(title, description, TaskStatus.NEW);
        linkedSubtasksId = new ArrayList<>();
    }


    public List<Integer> getLinkedSubtasksId() {
        return linkedSubtasksId;
    }

    public void setLinkedSubtasksId(List<Integer> linkedSubtasksId) {
        this.linkedSubtasksId = linkedSubtasksId;
    }

    @Override
    public String toString() {
        String result = "Epic{" +
                "title='" + title + '\'' +
                ", id='" + id + '\'' +
                ", status='" + status + '\'';

        if (description != null) {
            result = result + ", description.length='" + description.length() + '\'';
        } else {
            result = result + ", description=null";
        }
        return result + ", linkedSubtasksId=" + linkedSubtasksId.toString() +
                '}';
    }
}