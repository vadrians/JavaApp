package com.bombk1n.materialpro.model;

import de.huxhorn.sulky.ulid.ULID;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.*;
import org.springframework.data.mongodb.core.mapping.Document;
import java.util.List;


@Document(collection = "movies")
@Entity
public class MovieEntity{

    @org.springframework.data.annotation.Id
    @Id
    @Column(unique = true, nullable = false)
    private String id;

    private String title;
    private String director;
    private int releaseYear;
    private String genre;
    private int duration;
    private double rating;
    private String coverImage;

    @ElementCollection
    @CollectionTable(name = "movie_actors", joinColumns = @JoinColumn(name = "movie_id"))
    @Column(name = "actor")
    private List<String> actors;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "movie_id")
    private List<ShowtimeEntity> showtimeEntities;

    public MovieEntity() {

    }

    public MovieEntity(String title, String director, int releaseYear, String genre, int duration, double rating, String coverImage, List<String> actors, List<ShowtimeEntity> showtimeEntities) {
        this.title = title;
        this.director = director;
        this.releaseYear = releaseYear;
        this.genre = genre;
        this.duration = duration;
        this.rating = rating;
        this.coverImage = coverImage;
        this.actors = actors;
        this.showtimeEntities = showtimeEntities;
    }

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

    public List<ShowtimeEntity> getShowtimes() {
        return showtimeEntities;
    }

    public void setShowtimes(List<ShowtimeEntity> showtimeEntities) {
        this.showtimeEntities = showtimeEntities;
    }

    @PrePersist
    public void onPrePersist() {
        if (this.id == null) {
            this.id = new ULID().nextULID();
        }
    }

    @Entity
    public static class ShowtimeEntity {

        @Id
        @Column(unique = true, nullable = false)
        private String id;

        private String day;

        @ElementCollection
        @CollectionTable(name = "showtime_times", joinColumns = @JoinColumn(name = "showtime_id"))
        @Column(name = "time")
        private List<String> times;


        public ShowtimeEntity(String day, List<String> times) {
            this();
            this.day = day;
            this.times = times;
        }

        public ShowtimeEntity() {
            this.id = new ULID().nextULID();
        }

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

        public void setId(String id) {
            this.id = id;
        }

        public String getId() {
            return id;
        }
    }
}
