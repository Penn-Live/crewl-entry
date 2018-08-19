package com.yzyw.penn.reqeust.seekers;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.yzyw.penn.reqeust.AjaxGetBaseSeek;
import org.apache.commons.configuration.ConfigurationException;

import java.util.HashSet;
import java.util.Set;

//site: "feelunique.com"
public class FeeluniqueSkuSeeker extends AjaxGetBaseSeek {


    public FeeluniqueSkuSeeker(String confiPath) throws ConfigurationException {
        super(confiPath);
    }

    public static void main(String[] args) throws ConfigurationException {
        Set<String> resultIds=
                new FeeluniqueSkuSeeker("sconf/feeluique.properties")
                .doSeek(jsonObject -> {
            HashSet<String> ids = new HashSet<>();
            JsonArray skuArr = jsonObject.getAsJsonArray("data");
            //System.out.println(skuArr.size());
            for (int j = 0; j <skuArr.size(); j++) {
                JsonElement current = skuArr.get(j);
                ids.add(current.getAsJsonObject().get("entity_id").toString());
                //处理里面的
                JsonArray innerSkuArr =  current.getAsJsonObject().get("inner_hits").getAsJsonObject().getAsJsonArray("skus");
                for (int i1 = 0; i1 < innerSkuArr.size(); i1++) {
                    ids.add(innerSkuArr.get(i1).getAsJsonObject().get("entity_id").toString());
                }
            }
            return ids;
        });

        System.out.println(transforSetToSingStr(resultIds));

    }


}
