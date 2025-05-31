package config;

import game.protocol.Protocol;
import game.protocol.ProtocolVersionHandler;

public class VersionReporter {
    private final Protocol protocol;

    public VersionReporter(int protocolVersion) {
        this.protocol = ProtocolVersionHandler.getInstance().getProtocolByProtocolVersion(protocolVersion);
    }

    public int getDataVersion() {
        return protocol.getDataVersion();
    }

    public Protocol getProtocol() {
        return protocol;
    }
    
    /**
	 * Selects the correction version based on the provided dataVersion, this goes in order of specified versions finding the first possible match
	 * @param dataVersion the dataVersion <a href="https://minecraft.wiki/w/Java_Edition_1.21.5">Data Version</a>
	 * @param type the tye of class this returns
	 * @param opts all options to pick from
	 * @return The Matching Option or null
	 * @param <T>
	 */
    public static <T> T select(int dataVersion, Class<T> type, Option... opts) {
        for (Option opt : opts) {
            if (dataVersion >= opt.v.dataVersion) {
                return type.cast(opt.obj.get());
            }
        }
        return null;
    }
    
    /**
     * Selects the correction version based on the dataVersion from {@link #getDataVersion()} this goes in order of specified versions finding the first possible match
     * @param type the tye of class this returns
     * @param opts all options to pick from
     * @return The Matching Option or null
     * @param <T>
     */
    public <T> T select(Class<T> type, Option... opts) {
        for (Option opt : opts) {
            if (isAtLeast(opt.v)) {
                return type.cast(opt.obj.get());
            }
        }
        return null;
    }

    public boolean isAtLeast(Version v) {
        return getDataVersion() >= v.dataVersion;
    }
}
