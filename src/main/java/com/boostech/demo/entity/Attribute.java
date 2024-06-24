package com.boostech.demo.entity;

import com.fasterxml.jackson.annotation.*;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "attribute")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Attribute extends BaseEntity {
    @Column(name = "name", unique = false, nullable = false)
    private String attributeName;

    @JsonManagedReference
    @OneToMany(mappedBy = "attribute")
    private List<CategoryAttribute> categoryAttributes = new ArrayList<>();

    @ManyToOne(fetch = FetchType.LAZY)
    private Unit unit;

    @OneToMany(mappedBy = "attribute")
    List<PValue> values = new ArrayList<>();
}