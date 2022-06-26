package com.example.t1_smit_sgabani.models;

public class Pokemon {
    private int number;
    public String name;
    private int wins;
    private int losses;

    public Pokemon(int number, String name) {
        this.number = number;
        this.name = name;
        this.wins = 0;
        this.losses= 0;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getWins() {
        return wins;
    }

    public void setWins(int wins) {
        this.wins = wins;
    }

    public int getLosses() {
        return losses;
    }

    public void setLosses(int losses) {
        this.losses = losses;
    }

    public String toString() {
        return "Pokemon{" +
                "number=" + number +
                ", name='" + name + '\'' +
                ", wins=" + wins +
                ", losses=" + losses +
                '}';
    }
}