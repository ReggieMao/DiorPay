package com.gene.library.exception;

import rx.Observable;
import rx.functions.Func1;

/**
 * Created by MaoLJ on 2018/7/18.
 *
 */

public class ApiExceptionFunc<T> implements Func1 <Throwable, Observable <T> > {
    @Override
    public Observable<T> call(Throwable throwable) {
        return Observable.error(ExceptionEngine.convertException(throwable));
    }
}
