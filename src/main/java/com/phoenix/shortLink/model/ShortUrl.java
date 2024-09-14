package com.phoenix.shortLink.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Entity
@Getter
@Setter
@Cacheable(false)
@Table(name = "short_link")
public class ShortUrl implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name = "short_url_id", unique = true, nullable = false)
    private String shortUrlId;
    @OneToOne
    @JoinColumn(name = "link_id", referencedColumnName = "id")
    private Link link;
}
