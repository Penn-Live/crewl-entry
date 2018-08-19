package com.yzyw.penn.reqeust.seeks;

import com.google.common.collect.Sets;
import com.yzyw.penn.reqeust.HtmlSeeker;
import org.apache.commons.configuration.ConfigurationException;


//fake htmlSeeker
public class FakeHtmlSeeker extends HtmlSeeker {
    public FakeHtmlSeeker(String configPath) throws ConfigurationException {
        super(configPath);
    }


    public static void main(String[] args) throws ConfigurationException {
        new FakeHtmlSeeker("sconf/html.properties")
                .doSeek((document -> {
                    System.out.println(document);
                    return Sets.newHashSet();
                }));
    }
}
