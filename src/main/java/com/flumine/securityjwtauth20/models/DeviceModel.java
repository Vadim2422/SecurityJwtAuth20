package com.flumine.securityjwtauth20.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "devices")
public class DeviceModel {

    @Id
    @GeneratedValue
    private Long id;
    @Column(nullable = false)
    private String name;
    @ElementCollection
    @Column
    private List<String> types;

    @Column
    private Long userid;
}

