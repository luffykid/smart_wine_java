package com.changfa.frame.data.entity.message;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "message_detail", schema = "winery", catalog = "")
public class MessageDetail {
    private Integer id;
    private Integer messageId;
    private Integer wineryId;
    private Integer smsTempParaId;
    private String content;

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Basic
    @Column(name = "message_id")
    public Integer getMessageId() {
        return messageId;
    }

    public void setMessageId(Integer messageId) {
        this.messageId = messageId;
    }

    @Basic
    @Column(name = "winery_id")
    public Integer getWineryId() {
        return wineryId;
    }

    public void setWineryId(Integer wineryId) {
        this.wineryId = wineryId;
    }

    @Basic
    @Column(name = "sms_temp_para_id")
    public Integer getSmsTempParaId() {
        return smsTempParaId;
    }

    public void setSmsTempParaId(Integer smsTempParaId) {
        this.smsTempParaId = smsTempParaId;
    }

    @Basic
    @Column(name = "content")
    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MessageDetail that = (MessageDetail) o;
        return id == that.id &&
                Objects.equals(messageId, that.messageId) &&
                Objects.equals(wineryId, that.wineryId) &&
                Objects.equals(smsTempParaId, that.smsTempParaId) &&
                Objects.equals(content, that.content);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id, messageId, wineryId, smsTempParaId, content);
    }
}
