package kr.or.connect.todo.persistence;

import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import kr.or.connect.todo.domain.Task;

@Repository
public class TodoDao {
	private NamedParameterJdbcTemplate jdbc;
	private SimpleJdbcInsert insertAction;

	private static class compare implements Comparator<Task> {

		@Override
		public int compare(Task o1, Task o2) {
			// TODO Auto-generated method stub
			return (o1.getDate().compareTo(o2.getDate()));

		}

	}

	public TodoDao(DataSource dataSource) {
		this.jdbc = new NamedParameterJdbcTemplate(dataSource);
		this.insertAction = new SimpleJdbcInsert(dataSource).withTableName("todo").usingGeneratedKeyColumns("id");
	}

	public List<Task> getAll() {
		List<Task> list = null;
		RowMapper<Task> mapper = BeanPropertyRowMapper.newInstance(Task.class);
		list = jdbc.query(TodoSqls.SELECT_ALL, mapper);
		list.sort(new compare());
		Collections.reverse(list);
		return list;
	}

	public List<Task> getByFilter(int type) {
		List<Task> list = null;
		RowMapper<Task> mapper = BeanPropertyRowMapper.newInstance(Task.class);
		Map<String, Integer> map = new HashMap<String, Integer>();
		map.put("type", type);
		list = jdbc.query(TodoSqls.SELECT_BY_FILTER, map, mapper);
		list.sort(new compare());
		Collections.reverse(list);
		return list;
	}

	public Task getById(int id) {
		RowMapper<Task> mapper = BeanPropertyRowMapper.newInstance(Task.class);
		Map<String, Integer> map = new HashMap<String, Integer>();
		map.put("id", id);
		return jdbc.queryForObject(TodoSqls.SELECT_BY_ID, map, mapper);
	}

	public int createByClass(Task task) {

		SqlParameterSource param = new BeanPropertySqlParameterSource(task);
		return insertAction.executeAndReturnKey(param).intValue();
	}

	public void updateByClass(int id, int completed) {
		Map<String, Integer> map = new HashMap<String, Integer>();
		map.put("id", id);
		map.put("completed", completed);
		jdbc.update(TodoSqls.UPDATE_COMPLETE_TASK, map);
	}

	public void deleteById(int id) {
		Map<String, Integer> map = new HashMap<String, Integer>();
		map.put("id", id);
		jdbc.update(TodoSqls.DELETE_BY_ID, map);
	}

}
