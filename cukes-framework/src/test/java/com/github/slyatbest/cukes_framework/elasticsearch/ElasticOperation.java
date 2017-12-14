package com.github.slyatbest.cukes_framework.elasticsearch;

import java.util.HashMap;
import java.util.Map;

public class ElasticOperation
{
    Map<String, String> index;

    public ElasticOperation(String index, String type)
    {
        this.index = new HashMap<String, String>();
        this.index.put("_index", index);
        this.index.put("_type", type);
    }
}
