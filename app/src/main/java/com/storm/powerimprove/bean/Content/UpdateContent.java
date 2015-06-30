
package com.storm.powerimprove.bean.Content;

import com.google.gson.annotations.Expose;

import java.util.ArrayList;
import java.util.List;

public class UpdateContent {

    @Expose
    private Integer minVersion;
    @Expose
    private Integer maxVersion;
    @Expose
    private Integer toVersion;
    @Expose
    private String channel;
    @Expose
    private Integer type;
    @Expose
    private String title;
    @Expose
    private List<String> content = new ArrayList<String>();
    @Expose
    private String url;
    @Expose
    private String language;

    /**
     * @return The minVersion
     */
    public Integer getMinVersion() {
        return minVersion;
    }

    /**
     * @param minVersion
     *         The minVersion
     */
    public void setMinVersion(Integer minVersion) {
        this.minVersion = minVersion;
    }

    public UpdateContent withMinVersion(Integer minVersion) {
        this.minVersion = minVersion;
        return this;
    }

    /**
     * @return The maxVersion
     */
    public Integer getMaxVersion() {
        return maxVersion;
    }

    /**
     * @param maxVersion
     *         The maxVersion
     */
    public void setMaxVersion(Integer maxVersion) {
        this.maxVersion = maxVersion;
    }

    public UpdateContent withMaxVersion(Integer maxVersion) {
        this.maxVersion = maxVersion;
        return this;
    }

    /**
     * @return The toVersion
     */
    public Integer getToVersion() {
        return toVersion;
    }

    /**
     * @param toVersion
     *         The toVersion
     */
    public void setToVersion(Integer toVersion) {
        this.toVersion = toVersion;
    }

    public UpdateContent withToVersion(Integer toVersion) {
        this.toVersion = toVersion;
        return this;
    }

    /**
     * @return The channel
     */
    public String getChannel() {
        return channel;
    }

    /**
     * @param channel
     *         The channel
     */
    public void setChannel(String channel) {
        this.channel = channel;
    }

    public UpdateContent withChannel(String channel) {
        this.channel = channel;
        return this;
    }

    /**
     * @return The type
     */
    public Integer getType() {
        return type;
    }

    /**
     * @param type
     *         The type
     */
    public void setType(Integer type) {
        this.type = type;
    }

    public UpdateContent withType(Integer type) {
        this.type = type;
        return this;
    }

    /**
     * @return The title
     */
    public String getTitle() {
        return title;
    }

    /**
     * @param title
     *         The title
     */
    public void setTitle(String title) {
        this.title = title;
    }

    public UpdateContent withTitle(String title) {
        this.title = title;
        return this;
    }

    /**
     * @return The content
     */
    public List<String> getContent() {
        return content;
    }

    /**
     * @param content
     *         The content
     */
    public void setContent(List<String> content) {
        this.content = content;
    }

    public UpdateContent withContent(List<String> content) {
        this.content = content;
        return this;
    }

    /**
     * @return The url
     */
    public String getUrl() {
        return url;
    }

    /**
     * @param url
     *         The url
     */
    public void setUrl(String url) {
        this.url = url;
    }

    public UpdateContent withUrl(String url) {
        this.url = url;
        return this;
    }

    /**
     * @return The language
     */
    public String getLanguage() {
        return language;
    }

    /**
     * @param language
     *         The language
     */
    public void setLanguage(String language) {
        this.language = language;
    }

    public UpdateContent withLanguage(String language) {
        this.language = language;
        return this;
    }


}
