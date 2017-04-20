package com.app.mvc.dataBaseDomainModel;

import org.codehaus.jackson.annotate.JsonIgnore;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by Катерина on 11.04.2017.
 */

@Entity
@Table(schema = "ont", name = "thirdparty_resources")
public class ThirdPartyResource implements Serializable{

    private Long id;
    private String url;
    private String apikey;
    private String acceptData;
    private String acceptCharset;
    private String resourceName;
    private boolean queryString;
    @JsonIgnore
    private Set<ResourceColumns> resourceColumns = new HashSet<ResourceColumns>();

    public ThirdPartyResource() {}

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    public Long getId(){
        return this.id;
    }
    public void setId(Long id){
        this.id = id;
    }

    @Column(name = "url")
    public String getUrl() { return this.url; }
    public void setUrl(String url) { this.url = url; }

    @Column(name = "apikey")
    public String getApikey() { return this.apikey; }
    public void setApikey(String apikey) { this.apikey = apikey; }

    @Column(name = "accept_data")
    public String getAcceptData() { return this.acceptData; }
    public void setAcceptData(String acceptData) { this.acceptData = acceptData; }

    @Column(name = "accept_charset")
    public String getAcceptCharset() { return this.acceptCharset; }
    public void setAcceptCharset(String acceptCharset) { this.acceptCharset = acceptCharset; }

    @Column(name = "resource_name")
    public String getResourceName() { return this.resourceName; }
    public void setResourceName(String resourceName) { this.resourceName = resourceName; }

    @Column(name = "query_string")
    public boolean getQueryString() { return this.queryString; }
    public void setQueryString(boolean queryString) { this.queryString = queryString; }

    @OneToMany(mappedBy = "resourceNumber", cascade = CascadeType.ALL,
            orphanRemoval = true)
    public Set<ResourceColumns> getResourceColumns(){
        return this.resourceColumns;
    }
    public void setResourceColumns(Set<ResourceColumns> resourceColumns){
        this.resourceColumns = resourceColumns;
    }
}
