package com.wgs.experiencehub.mapping.response;

public class BaseResponse<T> {
    private BaseMetaResponse meta;

    public BaseResponse() {
        this.meta = new BaseMetaResponse();
    }

    public BaseMetaResponse getMeta() {
        return meta;
    }

    public void setMeta(BaseMetaResponse meta) {
        this.meta = meta;
    }
}
