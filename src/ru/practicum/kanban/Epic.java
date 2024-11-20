package ru.practicum.kanban;

import java.util.ArrayList;

public class Epic extends Task {
    private ArrayList<Integer> linkedSubtasksId;


    public Epic(String title, String description) {
        super(title, description, TaskStatus.NEW);

        linkedSubtasksId = new ArrayList<>();
    }

    public ArrayList<Integer> getLinkedSubtasksId() {
        return linkedSubtasksId;
    }

    public void setLinkedSubtasksId(ArrayList<Integer> linkedSubtasksId) {
        this.linkedSubtasksId = linkedSubtasksId;
    }


    @Override
    public String toString() {
        String result = "Epic{" +
                "title='" + title + '\'' +
                ", id='" + id + '\'' +
                ", status='" + status + '\'';

        if(description != null) {
            result = result + ", description.length='" + description.length() + '\'';
        } else {
            result = result + ", description=null";
        }

        return result + ", linkedSubtasksId=" + linkedSubtasksId.toString() +
                '}';
    }
}
