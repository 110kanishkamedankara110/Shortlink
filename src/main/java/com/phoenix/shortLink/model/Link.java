package com.phoenix.shortLink.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Entity
@Getter
@Setter
@Table(name = "link")
@Cacheable(false)
public class Link implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name = "link", unique = true, nullable = false)
    private String link;
    @OneToOne(cascade = CascadeType.ALL,mappedBy = "link")
    private ShortUrl shortUrl;
}
