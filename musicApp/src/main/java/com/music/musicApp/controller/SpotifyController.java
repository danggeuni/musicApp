package com.music.musicApp.controller;

import com.music.musicApp.controller.dto.AlbumResponse;
import com.music.musicApp.controller.dto.SearchRequest;
import com.music.musicApp.controller.dto.ArtistResponse;
import com.music.musicApp.controller.dto.TracksResponse;
import com.music.musicApp.service.SpotifyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
public class SpotifyController {
    private final SpotifyService spotifyService;

    @Autowired
    public SpotifyController(SpotifyService spotifyService, HttpSession session) {
        this.spotifyService = spotifyService;
    }


    @GetMapping("/search")
    public String searchResult(Model model, @ModelAttribute SearchRequest request) {
        model.addAttribute("search", new SearchRequest());
        return "search";
    }

    @PostMapping("/search")
    public void searchPage(Model model, SearchRequest request) {
        String requestParam = request.getSearch();
        // 아티스트 검색
        ArtistResponse artistResult = spotifyService.findQuery(requestParam);
        model.addAttribute("artistResult", artistResult);

        // 앨범 검색
        AlbumResponse albumResult = spotifyService.findAlbum(requestParam);
        List<AlbumResponse.Albums.Items> data = albumResult.getAlbums().getItems();
        model.addAttribute("data", data);

    }

    @GetMapping("/tracks/{albumId}")
    public String showTracks(Model model, @PathVariable String albumId) {
        TracksResponse response = spotifyService.showTracks(albumId);
        List<TracksResponse.Items> data = response.getItems();
        model.addAttribute("data", data);

        return "tracks";
    }
}
