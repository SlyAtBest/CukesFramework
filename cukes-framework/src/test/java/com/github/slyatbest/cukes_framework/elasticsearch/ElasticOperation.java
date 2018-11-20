package com.github.slyatbest.cukes_framework.elasticsearch;

import java.util.HashMap;
import java.util.Map;

/**
 * Simple POJO to store elastic operation data
 */
public class ElasticOperation
{
    private final Map<String, String> index;

    /**
     * Constructor
     * @param index String containing the index
     */
    public ElasticOperation(String index)
    {
        this.index = new HashMap<String, String>();
        this.index.put("_index", index);
    }
}
