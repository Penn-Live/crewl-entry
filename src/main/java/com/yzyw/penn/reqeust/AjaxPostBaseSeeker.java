package com.yzyw.penn.reqeust;

import com.google.gson.JsonObject;
import org.apache.commons.configuration.ConfigurationException;

import java.util.List;

public class AjaxPostBaseSeeker extends BaseSeeker<JsonObject> {

    public AjaxPostBaseSeeker(String configPath) throws ConfigurationException {
        super(configPath);
    }

    @Override
    protected JsonObject getRequestResult(String url, String params) {
        return HttpRequestUtil.postDownloadJson(url, params);
    }


}
