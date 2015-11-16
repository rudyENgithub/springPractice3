
package com.spring_cookbook.dao;

import com.spring_cookbook.domain.Post;
import com.spring_cookbook.domain.Users;
import com.spring_cookbook.domain.UsersJdbc;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public class UserDAO {

    @Autowired
    private JdbcTemplate jdbcTemplate;
    
    /*HIBERNATE*/
    @Autowired
	SessionFactory sessionFactory;

	protected Session getSession() {
		return sessionFactory.getCurrentSession();
	}
	
	@Transactional
	public void addHb(Users user) {
		getSession().saveOrUpdate(user);
	}
    
    /*HIBERNATE*/
    

	public void add(UsersJdbc user) {
		String sql = "insert into users (first_name, age) values (?, ?)";
		jdbcTemplate.update(sql, user.getFirstName(), user.getAge());
	}
        
        
        public UsersJdbc findById(Long id) {
		String sql = "select * from users where id=?";
		UsersJdbc user = jdbcTemplate.queryForObject(sql, new Object[]{id}, new UserMapper());
		return user;
	}
        
        public List<UsersJdbc> findAll() {
		String sql = "select * from users";
		List<UsersJdbc> userList = jdbcTemplate.query(sql, new BeanPropertyRowMapper<UsersJdbc>(UsersJdbc.class));
		return userList;
	}

	private class UserMapper implements RowMapper<UsersJdbc> {
		public UsersJdbc mapRow(ResultSet row, int rowNum) throws SQLException {
			UsersJdbc user = new UsersJdbc();

			user.setId(row.getLong("id"));
			user.setFirstName(row.getString("first_name"));
			user.setAge(row.getInt("age"));
			
			return user;
		}
	}
        
        
        public List<UsersJdbc> findAllDepen() {
		String sql = "select u.id, u.first_name, u.age, p.id as p_id, p.title as p_title, p.date as p_date from users u left join post p on p.user_id = u.id order by u.id asc, p.date desc";
		return jdbcTemplate.query(sql, new UserWithPosts());
	}
        
        
	private class UserWithPosts implements ResultSetExtractor<List<UsersJdbc>> {

		public List<UsersJdbc> extractData(ResultSet rs) throws SQLException,
				DataAccessException {
			
			Map<Long, UsersJdbc> userMap = new ConcurrentHashMap<Long, UsersJdbc>();
			UsersJdbc u = null;
			while (rs.next()) {
				// user already in map?
				Long id = rs.getLong("id");
				u = userMap.get(id);

				// if not, add it
				if(u == null) {
					u = new UsersJdbc();
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
			
		
                        return new LinkedList<UsersJdbc>(userMap.values());
		}
	}
        
        
    public void update(UsersJdbc user) {
        String sql = "update users set first_name=?, age=? where id=?";
        jdbcTemplate.update(sql, user.getFirstName(), user.getAge(),
                user.getId());
    }
    
    public void delete(UsersJdbc user) {
		String sql = "delete  from users where id=?";
		jdbcTemplate.update(sql, user.getId());
	}
    
    public long countMinorUsers() {
		String sql = "select count(*) from age < 18";
		return jdbcTemplate.queryForObject(sql, Long.class);
	}
}

