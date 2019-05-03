package by.mikemladinskiy.pzz.core.backend.internal

import by.mikemladinskiy.pzz.core.backend.internal.dto.PzzResponseDto
import com.besmartmobile.result.annimon.Result
import com.besmartmobile.result.annimon.ResultExt.fail
import com.besmartmobile.result.annimon.ResultExt.ok
import com.besmartmobile.result.annimon.Unit
import com.besmartmobile.result.annimon.Unit.unit
import com.squareup.moshi.JsonReader
import com.squareup.moshi.Moshi
import com.squareup.moshi.Types
import okhttp3.ResponseBody
import retrofit2.Converter
import retrofit2.Retrofit
import java.lang.reflect.ParameterizedType
import java.lang.reflect.Type
import javax.inject.Inject

internal class ResultConverterFactory @Inject constructor(private val moshi: Moshi)
    : Converter.Factory() {

    override fun responseBodyConverter(
        type: Type,
        annotations: Array<Annotation>,
        retrofit: Retrofit
    ): Converter<ResponseBody, *>? {
        return ResponseBodyConverter<Any>(type, moshi)
    }
}

internal class ResponseBodyConverter<T>(private val resultType: Type,
                                        private val moshi: Moshi) : Converter<ResponseBody, Result<T, Unit>> {

    override fun convert(responseBody: ResponseBody): Result<T, Unit> {
        if (resultType !is ParameterizedType) {
            throw IllegalArgumentException()
        }
        if (resultType.rawType != Result::class.java) {
            throw IllegalArgumentException()
        }
        val valueType = resultType.actualTypeArguments[0]

        val adapter = moshi.adapter<PzzResponseDto<Any>>(
            Types.newParameterizedType(
                PzzResponseDto::class.java,
                valueType
            )
        )

        try {
            val source = responseBody.source()
            val reader = JsonReader.of(source)

            val result: PzzResponseDto<T> = adapter.fromJson(reader) as PzzResponseDto<T>

            return ok(result.response.data)
        } catch (e: Exception) {
            return fail(unit())
        } finally {
            responseBody.close()
        }
    }
}