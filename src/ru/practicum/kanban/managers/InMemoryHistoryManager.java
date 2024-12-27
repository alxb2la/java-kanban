package ru.practicum.kanban.managers;

import ru.practicum.kanban.tasks.*;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.ArrayList;
import java.util.HashMap;


public class InMemoryHistoryManager implements HistoryManager {
	private final Map<Integer, Node> tasksViewHistory;
    // Ref to fist element of list
    private Node headOfList;
    // Ref to last element of list
    private Node tailOfList;
    // Size of list
    private int sizeOfList;

    public InMemoryHistoryManager() {
        tasksViewHistory = new HashMap<>();
        this.headOfList = null;
        this.tailOfList = null;
        this.sizeOfList = 0;
    }

    @Override
    public boolean add(Task task) {
        if (task == null) {
            return false;
        } else {
            Integer id = task.getId();
            if (tasksViewHistory.containsKey(id)) {
                removeNode(tasksViewHistory.get(id));
                tasksViewHistory.remove(id);
            }
            linkLast(task);
            return true;
        }
    }

    @Override
    public boolean remove(int id) {
    	if (tasksViewHistory.containsKey(id)) {
    		removeNode(tasksViewHistory.get(id));
    		tasksViewHistory.remove(id);
			return true;
    	} else {
    		return false;
    	}
    }

    @Override
    public List<Task> getHistory() {
    	return getTasks();
    }

    class Node {
        public Task task;
        public Node next;
        public Node prev;

        public Node() {
        	this.task = null;
            this.next = null;
            this.prev = null;
        }

        public Node(Node prev, Task task, Node next) {
            this.task = task;
            this.next = next;
            this.prev = prev;
        }
    }

    private void linkLast(Task task) {
    	final Node oldTail = tailOfList;
        final Node newNode = new Node(oldTail, task, null);

        tailOfList = newNode;
        if (oldTail == null) {
        	headOfList = newNode;
        } else {
        	oldTail.next = newNode;
        }
        ++sizeOfList;
        tasksViewHistory.put(task.getId(), newNode);
    }

    private List<Task> getTasks() {
    	if (sizeOfList == 0) {
    		return Collections.emptyList();
    	} else {
    		List<Task> tasks = new ArrayList<>();
    		Node node = headOfList;
    		for (int i = 0; i < sizeOfList; i++) {
    			tasks.add(node.task);
    			node = node.next;
    		}
    		return tasks;
    	}
    }

    private boolean removeNode(Node node) {
    	// Empty ref or empty list
        if (node == null || sizeOfList == 0) {
    		return false;
    	} else {
    		// 1 element in list
            if (sizeOfList == 1) {
                sizeOfList = 0;
                headOfList = null;
                tailOfList = null;
                return true;
            // fist element in list
            } else if (node == headOfList) {
                headOfList = node.next;
                node.next.prev = null;
                --sizeOfList;
                return true;
            // last element in list
            } else if (node == tailOfList) {
                tailOfList = node.prev;
                node.prev.next = null;
                --sizeOfList;
                return true;
            // element between other elements
            } else {
                node.prev.next = node.next;
                node.next.prev = node.prev;
                --sizeOfList;
                return true;
            }
    	}
    }
}