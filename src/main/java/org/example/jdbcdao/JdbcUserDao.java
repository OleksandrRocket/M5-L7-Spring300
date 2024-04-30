package org.example.jdbcdao;

import org.example.entity.User;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import org.springframework.jdbc.core.support.SqlLobValue;
import org.springframework.stereotype.Component;

import java.sql.Types;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
@Component
public class JdbcUserDao implements UserDao {

    private final JdbcTemplate jdbcTemplate;
    private final RowMapper<User> mapper;

    private final NamedParameterJdbcTemplate namedParameter;

    public JdbcUserDao(JdbcTemplate jdbcTemplate, RowMapper<User> mapper) {
        this.jdbcTemplate = jdbcTemplate;
        this.mapper = mapper;
        this.namedParameter = new NamedParameterJdbcTemplate(jdbcTemplate);
    }

    @Override
    public Optional<User> findById(String id) {
        String findQuery = "select * from users where id = ?";
        List<User> query = jdbcTemplate.query(findQuery, mapper, id);
        return Optional.ofNullable(query == null ? null : query.get(0));
    }

    @Override
    public Optional<User> findById2(String id) {
        SimpleJdbcCall simpleJdbcCall = new SimpleJdbcCall(jdbcTemplate)
                .withProcedureName("findByIdUserProcedure");
        MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource();
        mapSqlParameterSource.addValue("user_id", id);
        Map<String, Object> result = simpleJdbcCall.execute(mapSqlParameterSource);
        User user = new User();
        user.setId((String) result.get("u_id"));
        user.setId((String) result.get("u_email"));
        user.setId((String) result.get("u_password"));
        return Optional.of(user);
    }

    @Override
    public User save(User user) {
        user.setId(UUID.randomUUID().toString());
        String saveQuery = "insert into users (id, email, password) values (?,?,?)";
        jdbcTemplate.update(saveQuery, user.getId(), user.getEmail(), user.getPassword());
        return user;
    }

    @Override
    public void update(User user) {
        String updateQuery = "update users set email=? and password = ? where id = ?";
        jdbcTemplate.update(updateQuery, user.getEmail(), user.getPassword(), user.getId());
    }

    @Override
    public void deleteById(String id) {
        String deleteQuery = "delete from users where id = ?";
        jdbcTemplate.update(deleteQuery, id);

    }

    @Override
    public void saveUserFile(String id, byte[] files) {
        MapSqlParameterSource mapper = new MapSqlParameterSource();
        mapper.addValue("userId", id);
        SqlLobValue value = new SqlLobValue(files);
        mapper.addValue("userFile", value, Types.BLOB);
//        String querySaveFile = "insert into user_files (id, file ) values (?,?)";
//        jdbcTemplate.update(querySaveFile,mapper);
        String querySaveFile = "insert into user_files (id, file ) values (:userId,:userFile)";
        namedParameter.update(querySaveFile, mapper);
        jdbcTemplate.update(querySaveFile, mapper);
    }
}
