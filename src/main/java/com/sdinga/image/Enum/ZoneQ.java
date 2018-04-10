package com.sdinga.image.Enum;

import com.qiniu.common.Zone;
import org.apache.commons.lang3.StringUtils;

public enum ZoneQ {
    //华东
    ZONE_0("zone0", Zone.zone0()),
    //华北
    ZONE_1("zone0", Zone.zone1()),
    //华南
    ZONE_2("zone0", Zone.zone2()),
    //北美
    ZONE_NA_0("zone0", Zone.zoneNa0());

    private final String zoneName;
    private final Zone zone;

    ZoneQ(String zoneName, Zone zone) {
        this.zoneName = zoneName;
        this.zone = zone;
    }

    public String getZoneName() {
        return zoneName;
    }

    public Zone getZone() {
        return zone;
    }

    public static ZoneQ getZone(String zoneName) {
        for (ZoneQ zoneQ : values()) {
            if (StringUtils.equals(zoneQ.getZoneName(), zoneName)) {
                return zoneQ;
            }
        }
        return ZONE_0;
    }
}