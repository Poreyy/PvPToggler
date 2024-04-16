package me.porey.pvptoggler.util;

public interface CachedValues<T> {

    /**
     *
     * @param key The config key.
     * @return returns the colorized value from the config.yml.
     */
    T fromConfig(String key);

    /**
     *
     * @param toColorize The object to colorize.
     * @return returns the colorized object.
     */
    T colorize(T toColorize);

    /**
     * Clears cached keys.
     */
    void clearCaches();
}