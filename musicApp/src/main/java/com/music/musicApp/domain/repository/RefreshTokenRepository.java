package com.music.musicApp.domain.repository;

import com.music.musicApp.domain.entity.RefreshToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

@Repository
public class RefreshTokenRepository {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public RefreshTokenRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public RefreshToken findByUserId(String userId) {
        return jdbcTemplate.queryForObject("SELECT * FROM RT WHERE USERID = ?", new Object[]{userId}, refreshTokenRowMapper());
    }

    public RefreshToken findByRefreshToken(String refreshToken) {
        return jdbcTemplate.queryForObject("SELECT * FROM RT WHERE REFRESH_TOKEN = ?", new Object[]{refreshToken}, refreshTokenRowMapper());
    }

    RowMapper<RefreshToken> refreshTokenRowMapper (){
        return ((rs, rowNum) -> new RefreshToken(
                rs.getLong("ID"),
                rs.getString("USERID"),
                rs.getString("REFRESH_TOKEN")
        ));
    }
}
