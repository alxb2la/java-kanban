package ru.practicum.kanban;

import ru.practicum.kanban.managers.*;
import ru.practicum.kanban.tasks.*;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;

import java.util.ArrayList;
import java.util.List;

class InMemoryTaskManagerTest {
    private static InMemoryTaskManager manager;

    @BeforeEach
    public void beforeEach() {
        manager = new InMemoryTaskManager();
    }

    @Test
    void shouldAddTaskObjectToTasksMapAndGetListOfTasks() {
        manager.addTask(new Task("Title1","Description1", TaskStatus.NEW));
        manager.addTask(new Task("Title2","Description2", TaskStatus.IN_PROGRESS));
        List<Task> gTasks = manager.getListOfTasks();

        Assertions.assertEquals("Title1", gTasks.get(0).getTitle());
        Assertions.assertEquals("Description1", gTasks.get(0).getDescription());
        Assertions.assertEquals(TaskStatus.NEW, gTasks.get(0).getStatus());
        Assertions.assertEquals(1, gTasks.get(0).getId());

        Assertions.assertEquals("Title2", gTasks.get(1).getTitle());
        Assertions.assertEquals("Description2", gTasks.get(1).getDescription());
        Assertions.assertEquals(TaskStatus.IN_PROGRESS, gTasks.get(1).getStatus());
        Assertions.assertEquals(2, gTasks.get(1).getId());
    }

    @Test
    void shouldAddEpicObjectToEpicsMapAndGetListOfEpics() {
        manager.addEpic(new Epic("Title1","Description1"));
        manager.addEpic(new Epic("Title2","Description2"));
        List<Epic> gEpics = manager.getListOfEpics();

        Assertions.assertEquals("Title1", gEpics.get(0).getTitle());
        Assertions.assertEquals("Description1", gEpics.get(0).getDescription());
        Assertions.assertEquals(TaskStatus.NEW, gEpics.get(0).getStatus());
        Assertions.assertEquals(1, gEpics.get(0).getId());

        Assertions.assertEquals("Title2", gEpics.get(1).getTitle());
        Assertions.assertEquals("Description2", gEpics.get(1).getDescription());
        Assertions.assertEquals(TaskStatus.NEW, gEpics.get(1).getStatus());
        Assertions.assertEquals(2, gEpics.get(1).getId());
    }

    @Test
    void shouldAddSubtaskObjectToSubtasksMapAndGetListOfSubtasks() {
        manager.addEpic(new Epic("Title1","Description1"));
        manager.addEpic(new Epic("Title2","Description2"));

        manager.addSubtask(new Subtask("Title3","Description3", TaskStatus.NEW, 1));
        manager.addSubtask(new Subtask("Title4","Description4", TaskStatus.IN_PROGRESS, 2));
        List<Subtask> gSubtasks = manager.getListOfSubtasks();

        Assertions.assertEquals("Title3", gSubtasks.get(0).getTitle());
        Assertions.assertEquals("Description3", gSubtasks.get(0).getDescription());
        Assertions.assertEquals(TaskStatus.NEW, gSubtasks.get(0).getStatus());
        Assertions.assertEquals(3, gSubtasks.get(0).getId());

        Assertions.assertEquals("Title4", gSubtasks.get(1).getTitle());
        Assertions.assertEquals("Description4", gSubtasks.get(1).getDescription());
        Assertions.assertEquals(TaskStatus.IN_PROGRESS, gSubtasks.get(1).getStatus());
        Assertions.assertEquals(4, gSubtasks.get(1).getId());
    }

    @Test
    void shouldGetListOfEpicSubtasks() {
        Epic epic = manager.addEpic(new Epic("Title1","Description1"));
        Subtask subtask1 = manager.addSubtask(new Subtask("Title2","Description2", TaskStatus.NEW, epic.getId()));
        Subtask subtask2 = manager.addSubtask(new Subtask("Title3","Description3", TaskStatus.IN_PROGRESS, epic.getId()));
        List<Integer> epicSubtasksId = epic.getLinkedSubtasksId();

        Assertions.assertEquals(subtask1.getId(), epicSubtasksId.get(0));
        Assertions.assertEquals(subtask2.getId(), epicSubtasksId.get(1));
    }

    @Test
    void shouldRefreshTaskObjectInTasksMapAndGetTaskById() {
        manager.addTask(new Task("Title1","Description1", TaskStatus.NEW));
        List<Task> gTasks = manager.getListOfTasks();
        Task rTask = new Task("Title2","Description1", TaskStatus.IN_PROGRESS);
        int gTaskId = gTasks.get(0).getId();
        rTask.setId(gTaskId);
        manager.refreshTask(rTask);

        Assertions.assertEquals("Title2", manager.getTask(gTaskId).getTitle());
        Assertions.assertEquals("Description1", manager.getTask(gTaskId).getDescription());
        Assertions.assertEquals(TaskStatus.IN_PROGRESS, manager.getTask(gTaskId).getStatus());
        Assertions.assertEquals(gTaskId, manager.getTask(gTaskId).getId());
    }

    @Test
    void shouldRefreshEpicObjectInEpicsMapAndGetEpicById() {
        manager.addEpic(new Epic("Title1","Description1"));
        List<Epic> gEpics = manager.getListOfEpics();
        Epic rEpic = new Epic("Title2","Description1");
        int gEpicId = gEpics.get(0).getId();
        rEpic.setId(gEpicId);
        manager.refreshEpic(rEpic);

        Assertions.assertEquals("Title2", manager.getEpic(gEpicId).getTitle());
        Assertions.assertEquals("Description1", manager.getEpic(gEpicId).getDescription());
        Assertions.assertEquals(gEpicId, manager.getEpic(gEpicId).getId());
    }

    @Test
    void shouldRefreshSubtaskObjectInSubtasksMapAndGetSubtaskById() {
        manager.addEpic(new Epic("Title1","Description1"));
        manager.addSubtask(new Subtask("Title2","Description2", TaskStatus.NEW, 1));
        List<Subtask> gSubtasks = manager.getListOfSubtasks();
        int gSubtaskId = gSubtasks.get(0).getId();
        Subtask rSubtask = new Subtask("Title3","Description3", TaskStatus.IN_PROGRESS, manager.getSubtask(gSubtaskId).getLinkedEpicId());
        rSubtask.setId(gSubtaskId);
        manager.refreshSubtask(rSubtask);

        Assertions.assertEquals("Title3", manager.getSubtask(gSubtaskId).getTitle());
        Assertions.assertEquals("Description3", manager.getSubtask(gSubtaskId).getDescription());
        Assertions.assertEquals(TaskStatus.IN_PROGRESS, manager.getSubtask(gSubtaskId).getStatus());
        Assertions.assertEquals(gSubtaskId, manager.getSubtask(gSubtaskId).getId());
    }

    @Test
    void shouldRemoveTaskObjectFromTasksMapAndRemoveAllTasks() {
        Task task1 = manager.addTask(new Task("Title1","Description1", TaskStatus.NEW));
        Task task2 = manager.addTask(new Task("Title2","Description2", TaskStatus.IN_PROGRESS));
        Task task3 = manager.addTask(new Task("Title3","Description3", TaskStatus.IN_PROGRESS));

        manager.removeTask(task2.getId());
        List<Task> gTasks = manager.getListOfTasks();
        Assertions.assertEquals(2, gTasks.size());

        manager.removeAllTasks();
        gTasks = manager.getListOfTasks();
        Assertions.assertEquals(true, gTasks.isEmpty());
    }

    @Test
    void shouldRemoveEpicObjectFromEpicsMapAndRemoveAllEpics() {
        Epic epic1 = manager.addEpic(new Epic("Title1","Description1"));
        Epic epic2 = manager.addEpic(new Epic("Title2","Description2"));
        Epic epic3 = manager.addEpic(new Epic("Title3","Description3"));

        manager.removeEpic(epic3.getId());
        List<Epic> gEpics = manager.getListOfEpics();
        Assertions.assertEquals(2, gEpics.size());

        manager.removeAllEpics();
        gEpics = manager.getListOfEpics();
        Assertions.assertEquals(true, gEpics.isEmpty());
    }

    @Test
    void shouldRemoveSubtaskObjectFromSubtasksMapAndRemoveAllSubtasks() {
        Epic epic1 = manager.addEpic(new Epic("Title","Description"));

        Subtask subtask1 = manager.addSubtask(new Subtask("Title1","Description1", TaskStatus.NEW, epic1.getId()));
        Subtask subtask2 = manager.addSubtask(new Subtask("Title2","Description2", TaskStatus.IN_PROGRESS, epic1.getId()));
        Subtask subtask3 = manager.addSubtask(new Subtask("Title3","Description3", TaskStatus.IN_PROGRESS, epic1.getId()));

        manager.removeSubtask(subtask3.getId());
        List<Subtask> gSubtasks = manager.getListOfSubtasks();
        Assertions.assertEquals(2, gSubtasks.size());

        manager.removeAllSubtasks();
        gSubtasks = manager.getListOfSubtasks();
        Assertions.assertEquals(true, gSubtasks.isEmpty());
    }

    @Test
    void shouldNotAddEpicInTheSameEpicLikeSubtask() {
        Epic epic = manager.addEpic(new Epic("Title","Description"));
        Epic equalEpic = new Epic("Title2","Description2");
        equalEpic.setId(epic.getId());
        equalEpic.setLinkedSubtasksId(new ArrayList<Integer>(List.of(epic.getId())));
        manager.refreshEpic(equalEpic);

        Assertions.assertEquals(List.of(), epic.getLinkedSubtasksId());
        Assertions.assertEquals("Title2", epic.getTitle());
        Assertions.assertEquals("Description2", epic.getDescription());
    }

    @Test
    void shouldNotAddSubtaskInTheSameSubtaskLikeEpicSubtask() {
        Epic epic = manager.addEpic(new Epic("Title","Description"));
        Subtask subtask = manager.addSubtask(new Subtask("Title1","Description1", TaskStatus.NEW, epic.getId()));
        Subtask equalSubtask = manager.addSubtask(new Subtask("Title2","Description2", TaskStatus.NEW, subtask.getId()));

        Assertions.assertNull(equalSubtask);
    }

    @Test
    void shouldNotConflictBetweenExternalIdsAndInnerIds() {
        Task task = manager.addTask(new Task("Title1","Description1", TaskStatus.NEW));
        Epic epic = manager.addEpic(new Epic("Title2","Description2"));
        Subtask subtask = manager.addSubtask(new Subtask("Title3","Description3", TaskStatus.IN_PROGRESS, epic.getId()));

        Task task1 = new Task("Title10","Description10", TaskStatus.NEW);
        Epic epic1 = new Epic("Title11","Description11");
        Subtask subtask1 = new Subtask("Title12","Description12", TaskStatus.IN_PROGRESS, epic1.getId());
        task1.setId(10);
        epic1.setId(11);
        subtask1.setId(12);

        Assertions.assertNull(manager.addTask(task1));
        Assertions.assertNull(manager.addEpic(epic1));
        Assertions.assertNull(manager.addSubtask(subtask1));

        Assertions.assertFalse(manager.refreshTask(task1));
        Assertions.assertFalse(manager.refreshEpic(epic1));
        Assertions.assertFalse(manager.refreshSubtask(subtask1));

        Assertions.assertFalse(manager.removeTask(task1.getId()));
        Assertions.assertFalse(manager.removeEpic(epic1.getId()));
        Assertions.assertFalse(manager.removeSubtask(subtask1.getId()));

        Assertions.assertEquals(1, manager.getListOfTasks().get(0).getId());
        Assertions.assertEquals(2, manager.getListOfEpics().get(0).getId());
        Assertions.assertEquals(3, manager.getListOfSubtasks().get(0).getId());
    }

    @Test
    void tasksShouldStayUnchangedAfterAddToManager() {
        Task task = manager.addTask(new Task("Title1","Description1", TaskStatus.NEW));
        Epic epic = manager.addEpic(new Epic("Title2","Description2"));
        Subtask subtask = manager.addSubtask(new Subtask("Title3","Description3", TaskStatus.IN_PROGRESS, epic.getId()));

        Assertions.assertEquals("Title1", task.getTitle());
        Assertions.assertEquals("Description1", task.getDescription());
        Assertions.assertEquals(TaskStatus.NEW, task.getStatus());
        Assertions.assertEquals(1, task.getId());

        Assertions.assertEquals("Title2", epic.getTitle());
        Assertions.assertEquals("Description2", epic.getDescription());
        Assertions.assertEquals(TaskStatus.IN_PROGRESS, epic.getStatus());
        Assertions.assertEquals(2, epic.getId());

        Assertions.assertEquals("Title3", subtask.getTitle());
        Assertions.assertEquals("Description3", subtask.getDescription());
        Assertions.assertEquals(TaskStatus.IN_PROGRESS, subtask.getStatus());
        Assertions.assertEquals(3, subtask.getId());
    }
    
    @Test
    void ShouldBeActualListOfSubtasksInEpicTask() {	
        Epic epic = manager.addEpic(new Epic("Title1","Description1"));
        Subtask subtask1 = manager.addSubtask(new Subtask("Title2","Description2", TaskStatus.IN_PROGRESS, epic.getId()));
        Subtask subtask2 = manager.addSubtask(new Subtask("Title3","Description3", TaskStatus.NEW, epic.getId()));
        Subtask subtask3 = manager.addSubtask(new Subtask("Title4","Description4", TaskStatus.IN_PROGRESS, epic.getId()));
        
        List<Integer> subtasksIds = epic.getLinkedSubtasksId();
        
        Assertions.assertEquals(subtask1.getId(), subtasksIds.get(0));
        Assertions.assertEquals(subtask2.getId(), subtasksIds.get(1));
        Assertions.assertEquals(subtask3.getId(), subtasksIds.get(2));
        
        manager.removeSubtask(subtask1.getId());
        manager.removeSubtask(subtask3.getId());
        subtasksIds = epic.getLinkedSubtasksId();
        
        Assertions.assertEquals(subtask2.getId(), subtasksIds.get(0));
    }
    
    @Test
    void CheckCorruptDataOfTasksInsideManagerAfterUsingSetMethods() {	
    	Task task = manager.addTask(new Task("Title1","Description1", TaskStatus.NEW));
    	
    	task.setTitle("Title12345");
    	task.setDescription("Description12345");
    	task.setStatus(TaskStatus.DONE);
    	List<Task> tasks = manager.getListOfTasks();
    	
    	Assertions.assertEquals("Title12345", tasks.get(0).getTitle());
    	Assertions.assertEquals("Description12345", tasks.get(0).getDescription());
    	Assertions.assertEquals(TaskStatus.DONE, tasks.get(0).getStatus());
    }
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
}