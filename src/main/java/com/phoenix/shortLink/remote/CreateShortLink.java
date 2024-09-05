package com.phoenix.shortLink.remote;

import jakarta.ejb.Remote;

@Remote
public interface CreateShortLink {
    public String create(String link) throws Exception;
    public String getRealLink(int id,String linkId);
}
