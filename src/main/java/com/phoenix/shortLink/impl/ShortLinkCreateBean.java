package com.phoenix.shortLink.impl;

import com.phoenix.shortLink.model.ShortUrl;
import com.phoenix.shortLink.remote.CreateShortLink;
import com.phoenix.shortLink.util.UrlUuidShortener;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

import java.util.List;


@Stateless
public class ShortLinkCreateBean implements CreateShortLink {

    @PersistenceContext(unitName = "short_url_pu")
    private EntityManager em;

    @Override
    public String create(String link) throws Exception{
        List<ShortUrl> links= em.createQuery("SELECT u FROM ShortUrl u WHERE u.link=:link").setParameter("link", link).getResultList();
        if(links.isEmpty()){
            ShortUrl sl = new ShortUrl();


                sl.setLink(link);
                sl.setShortUrlId(UrlUuidShortener.generateUniqueShortID(link));
                em.persist(sl);
                em.flush();
                return sl.getId()+"/"+sl.getShortUrlId();
        }else{
            return links.get(0).getId()+"/"+links.get(0).getShortUrlId();
        }
    }

@Override
    public String getRealLink(int id,String linkId) {
        List<ShortUrl> links=em.createQuery("SELECT u FROM ShortUrl u WHERE u.id=:id AND u.shortUrlId=:linkId", ShortUrl.class).setParameter("id", id).setParameter("linkId",linkId).getResultList();
        if(links.isEmpty()){
            return null;
        }else{
            return links.get(0).getLink();
        }
    }

}
