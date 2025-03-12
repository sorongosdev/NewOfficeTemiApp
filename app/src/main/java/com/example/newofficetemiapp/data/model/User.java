package com.example.newofficetemiapp.data.model;

public class User {
    private String id;
    private String name;
    private String teamName;
    private String cardId;

    // 기본 생성자
    public User() {
    }

    public User(String id, String name, String teamName, String cardId) {
        this.id = id;
        this.name = name;
        this.teamName = teamName;
        this.cardId = cardId;
    }

    // Getters and Setters
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTeamName() {
        return teamName;
    }

    public void setTeamName(String teamName) {
        this.teamName = teamName;
    }

    public String getCardId() {
        return cardId;
    }

    public void setCardId(String cardId) {
        this.cardId = cardId;
    }
}