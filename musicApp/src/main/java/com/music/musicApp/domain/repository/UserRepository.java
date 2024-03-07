package com.music.musicApp.domain.repository;

import com.music.musicApp.domain.entity.UserEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

@Repository
public class UserRepository {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public UserRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public UserEntity joinUser(UserEntity entity) {
        jdbcTemplate.update("INSERT INTO USERS (USERID, NAME, PASSWORD) VALUES (?, ?, ?)",
                entity.getUserId(), entity.getName(), entity.getPassword());

        return entity;
    }

    public UserEntity findById(String userId) {
        try {
            return jdbcTemplate.queryForObject("SELECT * FROM USERS WHERE USERID = ?", new Object[]{userId}, userEntityRowMapper());
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    RowMapper<UserEntity> userEntityRowMapper() {
        return (rs, rowNum) -> new UserEntity(rs.getString("USERID"), rs.getString("NAME"), rs.getNString("PASSWORD"));
    }
}
