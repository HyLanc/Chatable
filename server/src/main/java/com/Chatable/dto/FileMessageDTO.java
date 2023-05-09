package com.Chatable.dto;

/**
 * @author 杨杨吖
 * @QQ 823208782
 * @WX yjqi12345678
 * @create 2022-04-08 17:38
 */
public class FileMessageDTO {

    private String id;

    private String name;

    private Long size;

    private String strSize;

    private String suffix;

    private String messageId;

    private String url;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getSize() {
        return size;
    }

    public void setSize(Long size) {
        this.size = size;
    }

    public String getSuffix() {
        return suffix;
    }

    public void setSuffix(String suffix) {
        this.suffix = suffix;
    }

    public String getMessageId() {
        return messageId;
    }

    public void setMessageId(String messageId) {
        this.messageId = messageId;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getStrSize() {
        return strSize;
    }

    public void setStrSize(String strSize) {
        this.strSize = strSize;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", name=").append(name);
        sb.append(", size=").append(size);
        sb.append(", suffix=").append(suffix);
        sb.append(", messageId=").append(messageId);
        sb.append(", url=").append(url);
        sb.append(", strSize=").append(strSize);
        sb.append("]");
        return sb.toString();
    }
}
