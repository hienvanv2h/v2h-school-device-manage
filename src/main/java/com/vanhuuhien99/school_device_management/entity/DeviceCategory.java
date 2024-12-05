package com.vanhuuhien99.school_device_management.entity;

import jakarta.persistence.*;
import lombok.*;

@Builder
@Getter @Setter @NoArgsConstructor @AllArgsConstructor
@ToString(callSuper = true)
@Entity
@Table(name = "DeviceCategories")
public class DeviceCategory extends BaseEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "CategoryID")
    private Long categoryId;

    @Column(name = "CategoryName", length = 100, nullable = false, unique = true)
    private String categoryName;

    @Column(name = "Description")
    private String description;

    @Column(name = "Unit", length = 20, nullable = false)
    private String unit;

    @Column(name = "UnitPrice", nullable = false)
    private Double unitPrice;
}
