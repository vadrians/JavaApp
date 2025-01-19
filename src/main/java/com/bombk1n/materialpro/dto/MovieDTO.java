package com.bombk1n.materialpro.dto;

import java.util.List;

public class MovieDTO {

    private String id;
    private String title;
    private String director;
    private int releaseYear;
    private String genre;
    private int duration;
    private double rating;
    private String coverImage;
    private List<String> actors;
    private List<ShowtimeDTO> showtimes;

    public MovieDTO(String id, String title, String director, int releaseYear, String genre, int duration, double rating, String coverImage, List<String> actors, List<ShowtimeDTO> showtimes) {
        this.id = id;
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

    public MovieDTO() {}

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

    public List<ShowtimeDTO> getShowtimes() {
        return showtimes;
    }

    public void setShowtimes(List<ShowtimeDTO> showtimes) {
        this.showtimes = showtimes;
    }
    public static class ShowtimeDTO {

        private String day;
        private List<String> times;

        public ShowtimeDTO(String day, List<String> times) {
            this.day = day;
            this.times = times;
        }

        public ShowtimeDTO() {}

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

        @Override
        public String toString() {
            return "ShowtimeDTO{" +
                    "day='" + day + '\'' +
                    ", times=" + times +
                    '}';
        }
    }
}
