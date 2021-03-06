package com.bemobi.entity;

import javax.persistence.*;
import java.util.Calendar;
import java.util.Date;

@Entity
@Table(name = "url")
public class Url {
    @Id
    @Column(nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String url;

    @Column(unique = true)
    private String alias;

    private int customAlias = 0;

    private Long uses = Long.valueOf(0);

    private Date dataHora = Calendar.getInstance().getTime();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }

    public int getCustomAlias() {
        return customAlias;
    }

    public void setCustomAlias(int customAlias) {
        this.customAlias = customAlias;
    }

    public Long getUses() { return uses; }

    public void setUses(Long uses) {
        this.uses = uses;
    }

    public Date getDataHora() {
        return dataHora;
    }

    public void setDataHora(Date dataHora) {
        this.dataHora = dataHora;
    }
}
