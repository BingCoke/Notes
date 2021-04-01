package com.dengzhitao.notes.entity;

import com.dengzhitao.notes.service.Id;

public class User {
    private Integer id;
    private String username;
    private String password;
    private String name;
    private String gender;
    private Integer age;
    private String phoneNumber;
    private Integer power;



    public User() { }

    public User(String username, String password, String name, String gender, Integer age, String phoneNumber) {
        this.id = 0;
        this.username = username;
        this.password = password;
        this.name = name;
        this.gender = gender;
        this.age = age;
        this.phoneNumber = phoneNumber;
        this.power = 2;
    }

    public User(String username, String password, String name, String gender, Integer age, String phoneNumber, Integer power) {
        this.id = 0;
        this.username = username;
        this.password = password;
        this.name = name;
        this.gender = gender;
        this.age = age;
        this.phoneNumber = phoneNumber;
        this.power = power;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
    public int getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public Integer getPower() {
        return power;
    }

    public void setPower(Integer power) {
        this.power = power;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", name='" + name + '\'' +
                ", gender='" + gender + '\'' +
                ", age=" + age +
                ", phoneNumber=" + phoneNumber +
                ", power=" + power +
                '}';
    }


}
