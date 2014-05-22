package com.instructure.canvasapi.model;

/**
 * Created by nbutton on 5/22/14.
 */
public class KalturaConfig {

    private String domain;
    private boolean enabled;
    private long partner_id;
    private String resource_domain;
    private String rtmp_domain;

    public String getDomain() {
        return domain;
    }

    public void setDomain(String domain) {
        this.domain = domain;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public long getPartner_id() {
        return partner_id;
    }

    public void setPartner_id(long partner_id) {
        this.partner_id = partner_id;
    }

    public String getResource_domain() {
        return resource_domain;
    }

    public void setResource_domain(String resource_domain) {
        this.resource_domain = resource_domain;
    }

    public String getRtmp_domain() {
        return rtmp_domain;
    }

    public void setRtmp_domain(String rtmp_domain) {
        this.rtmp_domain = rtmp_domain;
    }

}
