package com.example.demo.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.UUID;

@Data
@Entity
@Table(name = "modules")
public class Modules extends BaseEntity {
    @Id
    @GeneratedValue
    private UUID id;

    private String name;
    private String description;
}
