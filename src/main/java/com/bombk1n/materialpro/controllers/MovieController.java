package com.bombk1n.materialpro.controllers;

import com.bombk1n.materialpro.dto.MovieDTO;
import com.bombk1n.materialpro.service.MovieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/movies")
@CrossOrigin(origins = "http://localhost:4270")
public class MovieController {

    private MovieService movieService;
    @Autowired
    public MovieController(MovieService movieService) {
        this.movieService = movieService;
    }

    @GetMapping
    public List<MovieDTO> getAllMovies() {
        return movieService.getAllMovies();
    }

    @GetMapping("/{id}")
    public Optional<MovieDTO> getMovie(@PathVariable String id) {
        Optional<MovieDTO> movie = movieService.getMovieById(id);
        return movie;
    }

    @PostMapping
    public MovieDTO addMovie(@RequestBody MovieDTO movie) {
        return movieService.saveMovie(movie);
    }
    @PostMapping("/bulk")
    public List<MovieDTO> addMovies(@RequestBody List<MovieDTO> movies) {
        return movieService.saveMovies(movies);
    }

    @PutMapping("/{id}")
    public MovieDTO updateMovie(@PathVariable String id, @RequestBody MovieDTO movie) {
        return movieService.updateMovie(id,movie);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMovie(@PathVariable String id) {
        movieService.deleteMovie(id);
        return ResponseEntity.noContent().build();
    }
}
