package com.android.netconnect.bean;


/**
 * 回复
 * <p/>
 * ZhaoRuYang
 * 7/8/16 4:53 PM
 */
public class Response {
    private final Request request;
    private final int code;
    private final String message;

    private Response(Builder builder) {
        request = builder.request;
        code = builder.code;
        message = builder.message;
    }

    public static IBuild builder(String message, int code, Request request) {
        return new Builder(message, code, request);
    }


    interface IBuild {
        Response build();
    }

    public static final class Builder implements IBuild {
        private final String message;
        private final int code;
        private final Request request;

        private Builder(String message, int code, Request request) {
            this.message = message;
            this.code = code;
            this.request = request;
        }

        public Response build() {
            return new Response(this);
        }
    }
}
