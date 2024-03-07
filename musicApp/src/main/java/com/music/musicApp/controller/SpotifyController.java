package com.music.musicApp.controller;

import com.music.musicApp.controller.dto.music.*;
import com.music.musicApp.domain.entity.FavoriteEntity;
import com.music.musicApp.service.SpotifyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/music")
public class SpotifyController {
    private final SpotifyService spotifyService;
    private final HttpSession session;

    @Autowired
    public SpotifyController(SpotifyService spotifyService, HttpSession session) {
        this.spotifyService = spotifyService;
        this.session = session;
    }

    @GetMapping("/main")
    public String mainPage(Model model, @ModelAttribute SearchRequest request) {
        model.addAttribute("search", new SearchRequest());
        model.addAttribute("userId", session.getAttribute("userId"));
        return "music/main";
    }


    @GetMapping("/search")
    public String searchResult(Model model, @ModelAttribute SearchRequest request) {
        model.addAttribute("search", new SearchRequest());
        return "music/search";
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

        model.addAttribute("userId", session.getAttribute("userId"));
    }

    @GetMapping("/tracks/{albumId}")
    public String showTracks(Model model, @PathVariable String albumId) {
        TracksResponse response = spotifyService.showTracks(albumId);

        String userId = null;
        if (session.getAttribute("userId") != null) {
            userId = session.getAttribute("userId").toString();
        }

        List<TracksResponse.Items> data = response.getItems();

        model.addAttribute("data", data);
        model.addAttribute("userId", userId);
        model.addAttribute("albumId", albumId);

        // 찜 여부
        List<FavoriteResponse> trackId = spotifyService.findFavorite(userId).stream()
                .map(FavoriteResponse::new)
                .collect(Collectors.toList());
        model.addAttribute("trackId", trackId);

        return "music/tracks";
    }

    @PostMapping("/tracks/{albumId}")
    public String addFavorite(@ModelAttribute FavoriteRequest request, @PathVariable String albumId, RedirectAttributes attributes) {
        String userId = session.getAttribute("userId").toString();
        spotifyService.addFavorite(request, userId);
        attributes.addAttribute("albumId", albumId);

        return "redirect:/music/tracks/{albumId}";
    }

    @GetMapping("/favorite")
    public String myFavorite(Model model) {
        String userId = session.getAttribute("userId").toString();
        model.addAttribute("userId", userId);

        List<FavoriteEntity> data = spotifyService.findFavorite(userId);
        List<FavoriteResponse> responses = data.stream().map(FavoriteResponse::new).collect(Collectors.toList());

        model.addAttribute("data", responses);

        return "music/favorite";
    }
}
