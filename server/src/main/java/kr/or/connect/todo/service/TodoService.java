package kr.or.connect.todo.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kr.or.connect.todo.domain.Task;
import kr.or.connect.todo.persistence.TodoDao;

@Service
public class TodoService {
	TodoDao dao = null;
	ArrayList<Task> tasks = null;

	@Autowired
	public TodoService(TodoDao dao) {
		this.dao = dao;
	}

	public Task todoInsert(Task task) {
		int id = dao.createByClass(task);
		return dao.getById(id);

	}

	public List<Task> todoList() {
		return dao.getAll();
	}

	public List<Task> filterList(String type) {
		if (type.equals("active")) {
			return dao.getByFilter(0);
		} else if (type.equals("completed")) {
			return dao.getByFilter(1);
		} else
			return dao.getAll();

	}

	public void todoComplete(int id, int completed) {
		dao.updateByClass(id, completed);
	}

	public void todoDelete(int id) {
		dao.deleteById(id);
	}

	public void deleteCompleted(String[] ids) {
		for (int i = 0; i < ids.length; i++) {
			dao.deleteById(Integer.parseInt(ids[i]));
		}

	}

}
