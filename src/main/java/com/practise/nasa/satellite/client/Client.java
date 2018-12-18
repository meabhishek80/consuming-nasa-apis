package com.practise.nasa.satellite.client;

import com.practise.nasa.satellite.exception.ClientException;

public interface Client<K, V> {
    V get(K k1) throws ClientException;

    static String buildURL(Double latitude, Double longitude) {
        StringBuilder builder = new StringBuilder();
        builder.append(Params.URL).append(Params.QUERY_PARAM_LONGITUTDE).append(String.valueOf(longitude))
                .append(Params.QUERY_PARAM_SEPERATOR_AMPERSAND).append(Params.QUERY_PARAM_LATTITUDE)
                .append(String.valueOf(latitude)).append(Params.QUERY_PARAM_SEPERATOR_AMPERSAND)
                .append(Params.QUERY_PARAM_SKEY);
        return builder.toString();
    }
}