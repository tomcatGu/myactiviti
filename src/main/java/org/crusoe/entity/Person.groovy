package com.example.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
@Table(name = "ss_person")
public class Person {

    @Id
    @GeneratedValue
    Integer id

    String firstName

    String lastName

}