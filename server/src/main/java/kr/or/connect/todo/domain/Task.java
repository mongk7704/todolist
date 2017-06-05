package kr.or.connect.todo.domain;

import java.sql.Timestamp;

public class Task {
	private Integer id;
	private String todo;
	private Integer completed;
	private Timestamp date;

	public Task() {

	}

	public Task(String todo, Integer completed, Timestamp date) {
		this.todo = todo;
		this.completed = completed;
		this.date = date;

	}

	public Task(Integer id, String todo, Integer completed, Timestamp date) {

		this.id = id;
		this.todo = todo;
		this.completed = completed;
		this.date = date;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getTodo() {
		return todo;
	}

	public void setTodo(String todo) {
		this.todo = todo;
	}

	public Integer getCompleted() {
		return completed;
	}

	public void setCompleted(Integer completed) {
		this.completed = completed;
	}

	public Timestamp getDate() {
		return date;
	}

	public void setDate(Timestamp date) {
		this.date = date;
	}

}
