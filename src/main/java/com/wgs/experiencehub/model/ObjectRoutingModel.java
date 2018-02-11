package com.wgs.experiencehub.model;

import java.util.LinkedHashMap;

/**
 * Created by zer0 , The Maverick Hunter
 * On 16/03/17 - 21:23
 */
public class ObjectRoutingModel {
    private LinkedHashMap<String, LinkedHashMap<String, String>> ae;

    public LinkedHashMap<String, LinkedHashMap<String, String>> getAe() {
        return ae;
    }

    public void setAn(LinkedHashMap<String, LinkedHashMap<String, String>> ae) {
        this.ae = ae;
    }
}
