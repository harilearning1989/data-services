package com.web.demo.models.order;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.math.BigDecimal;
import java.util.Date;

@Entity
@Table(name = "ORDER_ITEMS")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrderItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="ID")
    private long id;

    @Column(name="QUANTITY")
    private int quantity;

    @Column(name="DATE_CREATED")
    @CreationTimestamp
    private Date dateCreated;

    @Column(name="LAST_UPDATED")
    @UpdateTimestamp
    private Date lastUpdated;
}
