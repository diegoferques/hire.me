package com.bemobi.entity;

import javax.persistence.*;

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

    private Long uses;

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

    public Long getUses() {
        return uses;
    }

    public void setUses(Long uses) {
        this.uses = uses;
    }
}