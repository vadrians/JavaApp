package com.bombk1n.materialpro.model;

import com.bombk1n.materialpro.dto.MovieDTO;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import java.util.List;
import java.util.stream.Collectors;


@Document(collection = "movies")
public class MovieDocument implements MovieModel {

    @Id
    private String id;

    private String title;
    private String director;
    private int releaseYear;
    private String genre;
    private int duration;
    private double rating;
    private String coverImage;
    private List<String> actors;
    private List<ShowtimeDocument> showtimes;

    public MovieDocument(String title, String director, int releaseYear, String genre, int duration, double rating, String coverImage, List<String> actors, List<ShowtimeDocument> showtimes) {
        this.title = title;
        this.director = director;
        this.releaseYear = releaseYear;
        this.genre = genre;
        this.duration = duration;
        this.rating = rating;
        this.coverImage = coverImage;
        this.actors = actors;
        this.showtimes = showtimes;
    }

    public MovieDocument(MovieDTO movieDTO) {
        this.id = movieDTO.getId();
        this.title = movieDTO.getTitle();
        this.director = movieDTO.getDirector();
        this.releaseYear = movieDTO.getReleaseYear();
        this.genre = movieDTO.getGenre();
        this.duration = movieDTO.getDuration();
        this.rating = movieDTO.getRating();
        this.coverImage = movieDTO.getCoverImage();
        this.actors = movieDTO.getActors();
        this.showtimes = movieDTO.getShowtimes().stream()
                .map(showtimeDTO -> new ShowtimeDocument(showtimeDTO.getDay(), showtimeDTO.getTimes()))
                .collect(Collectors.toList());

    }

    public MovieDocument() {}

    // Getters and setters
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDirector() {
        return director;
    }

    public void setDirector(String director) {
        this.director = director;
    }

    public int getReleaseYear() {
        return releaseYear;
    }

    public void setReleaseYear(int releaseYear) {
        this.releaseYear = releaseYear;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public double getRating() {
        return rating;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }

    public String getCoverImage() {
        return coverImage;
    }

    public void setCoverImage(String coverImage) {
        this.coverImage = coverImage;
    }

    public List<String> getActors() {
        return actors;
    }

    public void setActors(List<String> actors) {
        this.actors = actors;
    }

    public List<ShowtimeDocument> getShowtimes() {
        return showtimes;
    }

    public void setShowtimes(List<ShowtimeDocument> showtimes) {
        this.showtimes = showtimes;
    }

    public static class ShowtimeDocument {

        private String day;
        private List<String> times;

        public ShowtimeDocument(String day, List<String> times) {
            this.day = day;
            this.times = times;
        }

        public ShowtimeDocument() {}

        public String getDay() {
            return day;
        }

        public void setDay(String day) {
            this.day = day;
        }

        public List<String> getTimes() {
            return times;
        }

        public void setTimes(List<String> times) {
            this.times = times;
        }
    }
}

