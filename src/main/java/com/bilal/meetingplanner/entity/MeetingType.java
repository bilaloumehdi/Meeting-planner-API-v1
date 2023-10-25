package com.bilal.meetingplanner.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@Table(uniqueConstraints = @UniqueConstraint(
            name = "meeting_name_unique",
        columnNames = "name"
))
public class MeetingType {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;
    @Column(nullable = false)
    private String name;

    @ManyToMany(
            cascade = CascadeType.MERGE,
            fetch = FetchType.EAGER
    )
    @JoinTable(
            name="meeting_type_equipment_map",
            joinColumns = @JoinColumn(
                    name = "meeting_type_id",
                    referencedColumnName = "id"
            ),
            inverseJoinColumns = @JoinColumn(
                    name = "equipment_id",
                    referencedColumnName = "id"
            )
    )
    private Set<Equipment> requiredEquipments;
}
