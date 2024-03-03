package com.music.musicApp.domain.repository;

import com.music.musicApp.controller.dto.music.FavoriteResponse;
import com.music.musicApp.domain.entity.FavoriteEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class SpotifyRepository {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public SpotifyRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public void addFavorite(FavoriteEntity entity) {
        jdbcTemplate.update("INSERT INTO FAVORITE (TRACK_ID, PREVIEW_URL, USERID) VALUES (?, ?, ?)", entity.getTrackId(), entity.getPreviewUrl(), entity.getUserId());
    }

    public List<FavoriteEntity> findFavorite(String userId) {
        return jdbcTemplate.query("SELECT * FROM USERS LEFT OUTER JOIN FAVORITE ON USERS.USERID = FAVORITE.USERID WHERE USERS.USERID = ?", new Object[]{userId}, favoriteEntityRowMapper());
    }

    RowMapper<FavoriteEntity> favoriteEntityRowMapper() {
        return (rs, rowNum) -> new FavoriteEntity(rs.getLong("ID"), rs.getNString("TRACK_ID")
                , rs.getString("PREVIEW_URL"),rs.getString("USERID"));
    }
}
