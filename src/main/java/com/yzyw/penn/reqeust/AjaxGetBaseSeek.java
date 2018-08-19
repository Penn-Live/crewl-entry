package com.yzyw.penn.reqeust;

import com.google.gson.JsonObject;
import org.apache.commons.configuration.ConfigurationException;

public class AjaxGetBaseSeek extends BaseSeeker<JsonObject> {


    public AjaxGetBaseSeek(String configPath) throws ConfigurationException {
        super(configPath);

    }


    @Override
    protected JsonObject getRequestResult(String url, String params) {
        return HttpRequestUtil.readContentFromGet(url + "?" + params);
    }


}
