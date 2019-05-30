package com.example.demo;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Collection;

@Entity
@Table(name = "COURSE_DATA")
public class Course {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @NotNull
    @Size(min = 4)
    private String title;

    @NotNull
    @Size(min = 3)
    private String instructor;

    @NotNull
    @Size(min = 10)
    private String description;

    @NotNull
    @Min(3)
    private int credit;

    @ManyToMany(mappedBy = "courses", fetch = FetchType.LAZY)
    private Collection<User> users;



    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getInstructor() {
        return instructor;
    }

    public void setInstructor(String instructor) {
        this.instructor = instructor;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getCredit() {
        return credit;
    }

    public void setCredit(int credit) {
        this.credit = credit;
    }

    public Collection<User> getUsers() {
        return users;
    }

    public void setUsers(Collection<User> users) {
        this.users = users;
    }

    public Course() {
    }

    public Course(@NotNull @Size(min = 4, max = 8) String title, @NotNull @Size(min = 3) String instructor, @NotNull @Size(min = 10) String description, @NotNull @Min(3) int credit) {
        this.title = title;
        this.instructor = instructor;
        this.description = description;
        this.credit = credit;
    }
}
