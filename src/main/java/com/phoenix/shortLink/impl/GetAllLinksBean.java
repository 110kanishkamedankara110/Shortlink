package com.phoenix.shortLink.impl;

import com.phoenix.shortLink.model.ShortUrl;
import com.phoenix.shortLink.remote.GetAllLinks;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

import java.util.List;

@Stateless
public class GetAllLinksBean implements GetAllLinks {

    @PersistenceContext(unitName = "short_url_pu")
    private EntityManager em;

    @Override
    public List<ShortUrl> getAllLinks() {
        return em.createQuery("SELECT l FROM ShortUrl l",ShortUrl.class).getResultList();
    }
}
