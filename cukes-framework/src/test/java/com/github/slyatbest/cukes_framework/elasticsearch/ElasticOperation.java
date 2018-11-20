package com.github.slyatbest.cukes_framework.elasticsearch;

import java.util.HashMap;
import java.util.Map;

/**
 * Simple POJO to store elastic operation data
 */
public class ElasticOperation
{
    private final Map<String, String> elasticData;

    /**
     * Constructor
     * @param index String containing the index
     * @param type String containing the type
     */
    public ElasticOperation(String index, String type)
    {
        this.elasticData = new HashMap<String, String>();
        this.elasticData.put("_index", index);
        this.elasticData.put("_type", type);
    }
}
