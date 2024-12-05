package com.vanhuuhien99.school_device_management.entity;

import jakarta.persistence.*;
import lombok.*;

@Builder
@Getter @Setter @NoArgsConstructor @AllArgsConstructor
@ToString(callSuper = true)
@Entity
@Table(name = "Devices")
public class Device extends BaseEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "DeviceID")
    private Long deviceId;

    @Column(name = "DeviceName", length = 100, nullable = false)
    private String deviceName;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CategoryID")
    private DeviceCategory deviceCategory;

    @Column(name = "Description")
    private String description;

    @Column(name = "Status", nullable = false)
    private String status;
}
