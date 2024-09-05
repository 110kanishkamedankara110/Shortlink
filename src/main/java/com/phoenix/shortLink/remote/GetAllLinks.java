package com.phoenix.shortLink.remote;

import com.phoenix.shortLink.model.ShortUrl;
import jakarta.ejb.Remote;

import java.util.List;

@Remote
public interface GetAllLinks {
    public List<ShortUrl> getAllLinks();
}
