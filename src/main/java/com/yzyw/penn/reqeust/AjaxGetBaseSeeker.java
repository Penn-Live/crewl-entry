package com.yzyw.penn.reqeust;

import com.google.gson.JsonObject;
import org.apache.commons.configuration.ConfigurationException;

public class AjaxGetBaseSeeker extends BaseSeeker<JsonObject> {


    public AjaxGetBaseSeeker(String configPath) throws ConfigurationException {
        super(configPath);

    }


    @Override
    protected JsonObject getRequestResult(String url, String params) {
        return HttpRequestUtil.readContentFromGet(url + "?" + params);
    }


}
