package com.phoenix.shortLink.impl;

import com.phoenix.shortLink.model.Link;
import com.phoenix.shortLink.model.ShortUrl;
import com.phoenix.shortLink.remote.CreateShortLink;
import com.phoenix.shortLink.util.UrlUuidShortener;
import jakarta.ejb.Stateless;
import jakarta.ejb.TransactionAttribute;
import jakarta.ejb.TransactionAttributeType;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

import java.util.List;


@Stateless
public class ShortLinkCreateBean implements CreateShortLink {

    @PersistenceContext(unitName = "short_url_pu")
    private EntityManager em;

    @Override
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public String create(String link) throws Exception {
        List<Link> links = em.createQuery("SELECT u FROM Link u WHERE u.link=:link").setParameter("link", link).getResultList();
        if (links.isEmpty()) {
            ShortUrl sl = new ShortUrl();

            Link l = new Link();
            l.setLink(link);
            em.persist(l);
            em.flush();
            System.out.println();

            sl.setLink(l);

            String id=UrlUuidShortener.generateUniqueShortID(new Long(l.getId()));

            List<ShortUrl> u = em.createQuery("SELECT u FROM ShortUrl u WHERE u.shortUrlId=:link").setParameter("link", id).getResultList();

            while (!u.isEmpty()) {
                id=UrlUuidShortener.generateUniqueShortID(new Long(l.getId()));
                u = em.createQuery("SELECT u FROM ShortUrl u WHERE u.shortUrlId=:link").setParameter("link", id).getResultList();
            }

            sl.setShortUrlId(id);
            em.persist(sl);
            em.flush();
            return sl.getShortUrlId();
        } else {
            return links.get(0).getShortUrl().getShortUrlId();
        }
    }

    @Override
    public String getRealLink(String linkId) {
        List<ShortUrl> links = em.createQuery("SELECT u FROM ShortUrl u WHERE u.shortUrlId=:id", ShortUrl.class).setParameter("id", linkId).getResultList();
        if (links.isEmpty()) {
            return null;
        } else {
            return links.get(0).getLink().getLink();
        }
    }

}
