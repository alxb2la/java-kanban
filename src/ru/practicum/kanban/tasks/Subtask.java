package ru.practicum.kanban.tasks;

public class Subtask extends Task {
    private int linkedEpicId;

    public Subtask(String title, String description, TaskStatus status, int linkedEpicId) {
        super(title, description, status);
        this.linkedEpicId = linkedEpicId;
    }


    public int getLinkedEpicId() {
        return linkedEpicId;
    }

    public void setLinkedEpicId(int linkedEpicId) {
        this.linkedEpicId = linkedEpicId;
    }

    @Override
    public String toString() {
        String result = "Subtask{" +
                "title='" + title + '\'' +
                ", id='" + id + '\'' +
                ", status='" + status + '\'';

        if (description != null) {
            result = result + ", description.length='" + description.length() + '\'';
        } else {
            result = result + ", description=null";
        }
        return result + ", linkedEpicId='" + linkedEpicId + '\'' + '}';
    }
}