package com.thoughtclan.taskhub.model;

import lombok.*;
import jakarta.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "developers")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Developer {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable=false)
    private String name;

    @Column(nullable=false, unique = true)
    private String email;

    @ManyToMany
    @JoinTable(name = "developer_project",
            joinColumns = @JoinColumn(name = "developer_id"),
            inverseJoinColumns = @JoinColumn(name = "project_id"))
    @ToString.Exclude @EqualsAndHashCode.Exclude
    private Set<Project> projects = new HashSet<>();
}
