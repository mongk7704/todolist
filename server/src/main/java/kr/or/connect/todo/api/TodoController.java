package kr.or.connect.todo.api;

import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import kr.or.connect.todo.domain.Task;
import kr.or.connect.todo.service.TodoService;

@RestController

public class TodoController {
	TodoService service = null;

	@Autowired
	public TodoController(TodoService service) {
		this.service = service;
	}

	@GetMapping("/tasks")
	List<Task> list() {
		return service.todoList();
	}

	@GetMapping("/filter/{type}")
	@ResponseStatus(HttpStatus.CREATED)
	List<Task> filter_todo(@PathVariable String type) {
		return service.filterList(type);
	}

	@PostMapping("/task")
	@ResponseStatus(HttpStatus.CREATED)
	Task create(@RequestBody Task task) {

		return service.todoInsert(task);

	}

	@DeleteMapping("task/{id}")
	void delete(@PathVariable int id) {
		service.todoDelete(id);
	}

	@DeleteMapping("/completed")
	void deleteCompleted(@RequestBody String[] ids) {
		service.deleteCompleted(ids);
	}

	@PutMapping("task/{id}")
	void update(@RequestBody HashMap<String, String> map, @PathVariable int id) {
		service.todoComplete(id, Integer.parseInt(map.get("completed")));
	}

}
