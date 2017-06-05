package kr.or.connect.todo.persistence;

public class TodoSqls {
	static final String DELETE_BY_ID = "DELETE FROM todo WHERE id= :id";
	static final String LIST_ALL_TASKS = "SELECT * FROM todo";

	static final String SELECT_BY_ID = "SELECT id, todo, completed, date FROM todo where id=:id";
	static final String SELECT_ALL = "SELECT * FROM todo";
	static final String UPDATE_COMPLETE_TASK = "UPDATE todo SET completed=:completed where id=:id";
	static final String SELECT_COMPLETED = " SELECT count(*) FROM todo WHERE completed=1";
	static final String SELECT_BY_FILTER = "SELECT * FROM todo WHERE completed = :type";
}
