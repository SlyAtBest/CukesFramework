package com.github.slyatbest.cukes_framework.elasticsearch;

import java.util.HashMap;

/**
 * Simple POJO to store elastic operation data
 */
public class ElasticOperation
{
    private final HashMap<String, String> index;

    /**
     * Constructor
     * @param index String containing the index
     * @param type String containing the type
     */
    public ElasticOperation(String index, String type)
    {
        this.index = new HashMap<String, String>();
        this.index.put("_index", index);
        this.index.put("_type", type);
    }
}
