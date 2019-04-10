package com.gene.library.exception;

import android.net.ParseException;

import com.gene.library.constant.DPCode;
import com.gene.library.constant.DPStrings;
import com.google.gson.JsonParseException;
import org.json.JSONException;
import java.net.ConnectException;

import retrofit2.adapter.rxjava.HttpException;

/**
 * Created by MaoLJ on 2018/7/18.
 *
 */

public class ExceptionEngine {

    public static ApiException convertException(Throwable e) {
        ApiException ex = null;
        if (e instanceof HttpException) { //HTTP错误
            HttpException httpException = (HttpException) e;
            ex = new ApiException(e, DPCode.HTTP_ERROR);
            switch (httpException.code()) {
                case DPCode.UNAUTHORIZED:
                case DPCode.FORBIDDEN:
                case DPCode.NOT_FOUND:
                case DPCode.REQUEST_TIMEOUT:
                case DPCode.GATEWAY_TIMEOUT:
                case DPCode.INTERNAL_SERVER_ERROR:
                case DPCode.BAD_GATEWAY:
                case DPCode.SERVICE_UNAVAILABLE:
                default:
                    ex.setMessage(DPStrings.NETWORK_ERROR);
                    break;
            }
            return ex;

        } else if (e instanceof ServerException) {//服务器返回的错误
            ServerException resultException = (ServerException) e;
            ex = new ApiException(resultException, resultException.getCode());
            ex.setMessage(resultException.getMessage());
            return ex;

        } else if (e instanceof JsonParseException || e instanceof JSONException || e instanceof ParseException) {//均视为解析错误
            ex = new ApiException(e, DPCode.PARSE_ERROR);
            ex.setMessage(DPStrings.PARSE_ERROR);
            return ex;

        } else if (e instanceof ConnectException) {//均视为网络错误
            ex = new ApiException(e, DPCode.NETWORD_ERROR);
            ex.setMessage(DPStrings.NETWORK_ERROR);
            return ex;

        } else if (e instanceof javax.net.ssl.SSLHandshakeException) {
            ex = new ApiException(e, DPCode.SSL_ERROR);
            ex.setMessage(DPStrings.SSL_ERROR);
            return ex;

        } else {//未知错误
            ex = new ApiException(e, DPCode.UNKNOWN);
            ex.setMessage(DPStrings.UNKNOWN_ERROR);
            return ex;
        }
    }

}
