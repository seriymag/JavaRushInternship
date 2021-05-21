package com.game.entity;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.lang.NonNull;

import javax.persistence.*;
import java.util.Date;
import java.util.Objects;

@Entity
@Table(name = "player")
public class PlayerEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    public Long id;

    @Column (name = "name", length = 12)
    @NonNull
    public String name;

    @Column (name = "title", length = 30)
    @NonNull
    public String title;

    @Column (name = "race")
    @Enumerated(EnumType.STRING)
    @NonNull
    public Race race;

    @Column (name = "profession")
    @Enumerated(EnumType.STRING)
    @NonNull
    public Profession profession;

    @Column (name = "birthday")
    @DateTimeFormat
    @NonNull
    @Temporal(TemporalType.DATE) // transfer only date to db
    public Date birthday;

    @Column (name = "banned")
    public Boolean banned;

    @Column (name = "experience")
    @NonNull
    public Integer experience;

    @Column (name = "level")
    public Integer level;

    @Column (name = "untilNextLevel")
    public Integer untilNextLevel;

    public PlayerEntity() {
    }

    //    Constructor for jsp form without id, level & untilNextLevel
    public PlayerEntity(String name, String title, Race race, Profession profession, Date birthday, Boolean banned, Integer experience) {
        this.name = name;
        this.title = title;
        this.race = race;
        this.profession = profession;
        this.birthday = birthday;
        this.experience = experience;

        if (banned == null) this.banned = false;
        else this.banned = banned;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Race getRace() {
        return race;
    }

    public void setRace(Race race) {
        this.race = race;
    }

    public Profession getProfession() {
        return profession;
    }

    public void setProfession(Profession profession) {
        this.profession = profession;
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public Boolean getBanned() {
        return banned;
    }

    public void setBanned(Boolean banned) {
        this.banned = banned;
    }

    public Integer getExperience() {
        return experience;
    }

    public void setExperience(Integer experience) {
        this.experience = experience;
    }

    public Integer getLevel() {
        return level;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }

    public Integer getUntilNextLevel() {
        return untilNextLevel;
    }

    public void setUntilNextLevel(Integer untilNextLevel) {
        this.untilNextLevel = untilNextLevel;
    }

//    Также должна присутствовать бизнес-логика:
//Перед сохранением персонажа в базу данных (при добавлении нового или при апдейте характеристик существующего), должны высчитываться:
//- текущий уровень персонажа
//- опыт необходимый для достижения следующего уровня
//и сохраняться в БД. Текущий уровень персонажа рассчитывается по формуле:
//√2500 + 200·exp − 50 𝐿= 100 ,
//где:
//exp — опыт персонажа.
//Опыт до следующего уровня рассчитывается по формуле:
//𝑁 = 50 ∙ (𝑙𝑣𝑙 + 1) ∙ (𝑙𝑣𝑙 + 2) − 𝑒𝑥𝑝 ,
//где:
//lvl — текущий уровень персонажа; exp — опыт персонажа.

    @PrePersist
    private void logicPreLoad() {
        this.level = Math.toIntExact((long) ((Math.sqrt(2500 + 200 * experience) - 50) / 100.0));
        this.untilNextLevel = 50 * (level + 1) * (level + 2) - experience;
    }

//    @PostLoad
//    private void logicPostLoad() {}

    public void updateNonNullFields(PlayerEntity source) {
        if (source.getName() != null) {
            this.setName(source.getName());
        }
        if (source.getTitle() != null) {
            this.setTitle(source.getTitle());
        }
        if (source.getExperience() != null) {
            this.setExperience(source.getExperience());
        }
        if (source.getBanned() != null) {
            this.setBanned(source.getBanned());
        }
        if (source.getBirthday() != null) {
            this.setBirthday(source.getBirthday());
        }
        if (source.getProfession() != null) {
            this.setProfession(source.getProfession());
        }
        if (source.getRace() != null) {
            this.setRace(source.getRace());
        }
        if (source.getLevel() != null) {
            this.setLevel(source.getLevel());
        }
        if (source.getUntilNextLevel() != null) {
            this.setUntilNextLevel(source.getUntilNextLevel());
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PlayerEntity player = (PlayerEntity) o;
        return Objects.equals(id, player.id) &&
                name.equals(player.name) &&
                Objects.equals(title, player.title) &&
                race == player.race &&
                profession == player.profession &&
                Objects.equals(birthday, player.birthday) &&
                Objects.equals(banned, player.banned) &&
                Objects.equals(experience, player.experience) &&
                Objects.equals(level, player.level) &&
                Objects.equals(untilNextLevel, player.untilNextLevel);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, title, race, profession, birthday, banned, experience, level, untilNextLevel);
    }

    @Override
    public String toString() {
        return "Player{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", title='" + title + '\'' +
                ", race=" + race +
                ", profession=" + profession +
                ", birthday=" + birthday +
                ", banned=" + banned +
                ", experience=" + experience +
                ", level=" + level +
                ", untilNextLevel=" + untilNextLevel +
                '}';
    }
}