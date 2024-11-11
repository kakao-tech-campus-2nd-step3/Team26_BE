package org.ktc2.cokaen.wouldyouin._common.util;

import org.springframework.util.MultiValueMap;
import org.springframework.web.util.UriComponentsBuilder;

public class UriUtil {

    public static String buildUrl(String scheme, String host, String path, MultiValueMap<String, String> params) {
        return UriComponentsBuilder.newInstance()
            .scheme(scheme)
            .host(host)
            .path(path)
            .queryParams(params)
            .build()
            .toString();
    }

    public static String buildUrl(String scheme, String host, String path) {
        return UriComponentsBuilder.newInstance()
            .scheme(scheme)
            .host(host)
            .path(path)
            .build()
            .toString();
    }

    public static String assembleFullUrl(String baseUrl, String... paths) {
        String joinedPath = String.join("/", paths);
        return UriComponentsBuilder.fromHttpUrl(baseUrl)
            .path("/" + joinedPath)
            .build()
            .toString();
    }
}