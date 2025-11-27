package cc.thonly.base_bridge.util;

public enum PlatformType {
    UNDEFINED("undefined"),
    FABRIC("fabric"),
    NEOFORGE("neoforge"),
    ;
    final String name;

    PlatformType(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
