package by.mikemladinskiy.pzz.core.common

import com.besmartmobile.result.annimon.Result
import com.besmartmobile.result.annimon.ResultExt.fail
import io.reactivex.Maybe
import io.reactivex.functions.BiFunction
import io.reactivex.functions.Function3
import io.reactivex.functions.Function4
import io.reactivex.functions.Function5

private typealias MaybeResult<T, E> = Maybe<Result<T, E>>

fun <R, T, E> MaybeResult<T, E>.chain(chainFunction: (T) -> MaybeResult<R, E>)
    : MaybeResult<R, E> {
    return this.flatMap { result ->
        result.match(
                chainFunction,
                { Maybe.just(fail(it)) }
        )
    }
}

fun <R, T, E> MaybeResult<T, E>.mapResult(mapper: (T) -> R)
        : MaybeResult<R, E> {
    return this.map { result -> result.map(mapper) }
}

fun <R, T, E> MaybeResult<T, E>.flatMapResult(mapper: (T) -> Result<R, E>)
        : MaybeResult<R, E> {
    return this.map { result -> result.flatMap(mapper) }
}

fun <T, E> MaybeResult<T, E>.doOnSuccessfulResult(onSuccess: (T) -> kotlin.Unit)
        : MaybeResult<T, E> {
    return this.doOnSuccess { result -> result.onSuccess(onSuccess) }
}

fun <T, E> MaybeResult<T, E>.doOnFailedResult(onFailure: (E) -> kotlin.Unit)
        : MaybeResult<T, E> {
    return this.doOnSuccess { result -> result.onFailure(onFailure) }
}

fun <T1, T2, TError, R> zipResults(
        source1: MaybeResult<T1, TError>,
        source2: MaybeResult<T2, TError>,
        zipper: (T1, T2) -> R): MaybeResult<R, TError> {
    return Maybe.zip(source1, source2,
            BiFunction { r1, r2 -> r1.combine(r2, zipper) })
}

fun <T1, T2, T3, TError, R> zipResults(
        source1: MaybeResult<T1, TError>,
        source2: MaybeResult<T2, TError>,
        source3: MaybeResult<T3, TError>,
        zipper: (T1, T2, T3) -> R): MaybeResult<R, TError> {
    return Maybe.zip(source1, source2, source3,
            Function3 { r1, r2, r3 -> r1.combine(r2, r3, zipper) })
}

fun <T1, T2, T3, T4, TError, R> zipResults(
        source1: MaybeResult<T1, TError>,
        source2: MaybeResult<T2, TError>,
        source3: MaybeResult<T3, TError>,
        source4: MaybeResult<T4, TError>,
        zipper: (T1, T2, T3, T4) -> R): MaybeResult<R, TError> {
    return Maybe.zip(source1, source2, source3, source4,
            Function4 { r1, r2, r3, r4 -> r1.combine(r2, r3, r4, zipper) })
}

fun <T1, T2, T3, T4, T5, TError, R> zipResults(
        source1: MaybeResult<T1, TError>,
        source2: MaybeResult<T2, TError>,
        source3: MaybeResult<T3, TError>,
        source4: MaybeResult<T4, TError>,
        source5: MaybeResult<T5, TError>,
        zipper: (T1, T2, T3, T4, T5) -> R): MaybeResult<R, TError> {
    return Maybe.zip(source1, source2, source3, source4, source5,
            Function5 { r1, r2, r3, r4, r5 -> r1.combine(r2, r3, r4, r5, zipper) })
}

