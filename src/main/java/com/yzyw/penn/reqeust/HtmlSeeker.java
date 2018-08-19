package com.yzyw.penn.reqeust;

import org.apache.commons.configuration.ConfigurationException;
import org.jsoup.nodes.Document;

public class HtmlSeeker extends BaseSeeker<Document> {


    public HtmlSeeker(String configPath) throws ConfigurationException {
        super(configPath);
    }

    @Override
    protected Document getRequestResult(String url, String params) {
        return HttpRequestUtil.readHtml(url + "?" + params);
    }




}
