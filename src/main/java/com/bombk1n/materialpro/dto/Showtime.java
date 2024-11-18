package com.bombk1n.materialpro.dto;

import jakarta.persistence.*;

import java.util.List;

@Entity
public class Showtime {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String day;

    @ElementCollection
    @CollectionTable(name = "showtime_times", joinColumns = @JoinColumn(name = "showtime_id"))
    @Column(name = "time")
    private List<String> times;


    public Showtime(String day, List<String> times) {
        this.day = day;
        this.times = times;
    }

    public Showtime() {
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

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }
}