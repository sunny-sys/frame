package io.smallbird.common.model;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModel;
import org.apache.commons.collections.map.HashedMap;

import java.io.Serializable;
import java.util.Map;

@ApiModel(description = "所有请求和返回对象的基类")
public class BaseEntity implements Serializable {

    private static final long serialVersionUID = -1621990770162332196L;
    protected Map extraMap ;
    @JsonAnyGetter
    public Map getExtraMap() {
        return extraMap;
    }

    public void putExtraProperty(Object name, Object value) {
        if(name == null || value == null)return;
        if(extraMap == null)
            extraMap = new HashedMap();
        extraMap.put(name,value);
    }

    @JsonIgnore
    public <T> T getExtraProperty(Object name,T defaultValue) {
        T value = getExtraProperty(name);

        return value==null?defaultValue:value;
    }

    @JsonIgnore
    public <T> T getExtraProperty(Object name) {
        if(name == null || extraMap == null) return null;

        return (T)extraMap.get(name);
    }
}
