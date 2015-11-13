
package com.spring_cookbook.dao;

import com.spring_cookbook.domain.Post;
import com.spring_cookbook.domain.Users;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

@Repository
public class UserDAO {

    @Autowired
    private JdbcTemplate jdbcTemplate;

	public void add(Users user) {
		String sql = "insert into users (first_name, age) values (?, ?)";
		jdbcTemplate.update(sql, user.getFirstName(), user.getAge());
	}
        
        
        public Users findById(Long id) {
		String sql = "select * from users where id=?";
		Users user = jdbcTemplate.queryForObject(sql, new Object[]{id}, new UserMapper());
		return user;
	}
        
        public List<Users> findAll() {
		String sql = "select * from users";
		List<Users> userList = jdbcTemplate.query(sql, new BeanPropertyRowMapper<Users>(Users.class));
		return userList;
	}

	private class UserMapper implements RowMapper<Users> {
		public Users mapRow(ResultSet row, int rowNum) throws SQLException {
			Users user = new Users();

			user.setId(row.getLong("id"));
			user.setFirstName(row.getString("first_name"));
			user.setAge(row.getInt("age"));
			
			return user;
		}
	}
        
        
        public List<Users> findAllDepen() {
		String sql = "select u.id, u.first_name, u.age, p.id as p_id, p.title as p_title, p.date as p_date from users u left join post p on p.user_id = u.id order by u.id asc, p.date desc";
		return jdbcTemplate.query(sql, new UserWithPosts());
	}
        
        
	private class UserWithPosts implements ResultSetExtractor<List<Users>> {

		public List<Users> extractData(ResultSet rs) throws SQLException,
				DataAccessException {
			
			Map<Long, Users> userMap = new ConcurrentHashMap<Long, Users>();
			Users u = null;
			while (rs.next()) {
				// user already in map?
				Long id = rs.getLong("id");
				u = userMap.get(id);

				// if not, add it
				if(u == null) {
					u = new Users();
					u.setId(id);
					u.setFirstName(rs.getString("first_name"));
					u.setAge(rs.getInt("age"));
					userMap.put(id, u);
				}
				
				// create post if there's one
				Long postId = rs.getLong("p_id");
				if (postId > 0) {
					System.out.println("add post id=" + postId);
					Post p = new Post();
					p.setId(postId);
					p.setTitle(rs.getString("p_title"));
					p.setDate(rs.getDate("p_date"));
					p.setUser(u);
					u.getPosts().add(p);
				}
			}
			
		
                        return new LinkedList<Users>(userMap.values());
		}
	}
        
        
    public void update(Users user) {
        String sql = "update users set first_name=?, age=? where id=?";
        jdbcTemplate.update(sql, user.getFirstName(), user.getAge(),
                user.getId());
    }
    
    public void delete(Users user) {
		String sql = "delete  from users where id=?";
		jdbcTemplate.update(sql, user.getId());
	}
}

