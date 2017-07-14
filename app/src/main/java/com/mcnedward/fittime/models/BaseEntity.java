package com.mcnedward.fittime.models;

import java.io.Serializable;

/**
 * Created by Edward on 2/13/2016.
 */
public abstract class BaseEntity implements Serializable {

    protected Integer id = null;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}
