package by.mikemladinskiy.pzz.core.backend.internal

import by.mikemladinskiy.pzz.core.backend.internal.dto.PzzResponseDto
import com.besmartmobile.result.annimon.ResultExt.fail
import com.besmartmobile.result.annimon.ResultExt.ok
import com.besmartmobile.result.annimon.Unit.unit
import com.squareup.moshi.*
import com.squareup.moshi.internal.Util
import okhttp3.MediaType
import okhttp3.RequestBody
import okhttp3.ResponseBody
import okio.Buffer
import retrofit2.Converter
import retrofit2.Retrofit
import java.io.IOException
import java.lang.IllegalStateException
import java.lang.reflect.ParameterizedType
import java.lang.reflect.Type
import java.lang.reflect.WildcardType
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

    override fun requestBodyConverter(
        type: Type,
        parameterAnnotations: Array<Annotation>,
        methodAnnotations: Array<Annotation>,
        retrofit: Retrofit
    ): Converter<*, RequestBody>? {
        var adapter = moshi.adapter<Any>(type, Util.jsonAnnotations(parameterAnnotations))
        adapter = adapter.failOnUnknown()
        return RequestBodyConverter<Any>(adapter)
    }
}

internal class ResponseBodyConverter<T>(private val type: Type,
                                        private val moshi: Moshi) : Converter<ResponseBody, T> {

    override fun convert(value: ResponseBody): T {
        if (type !is ParameterizedType) {
            throw IllegalStateException()
        }
        val valueType = getParameterUpperBound(0, type)

        val adapter = moshi.adapter<PzzResponseDto<Any>>(
            Types.newParameterizedType(
                PzzResponseDto::class.java,
                valueType
            )
        )

        val source = value.source()
        try {
            val reader = JsonReader.of(source)


            val result: PzzResponseDto<Any>? = adapter.fromJson(reader)
            if (reader.peek() != JsonReader.Token.END_DOCUMENT) {
                throw JsonDataException("JSON document was not fully consumed.")
            }

            return ok<Any, Any>(result!!.response.data) as T
        } catch (e: Exception) {
            return fail<Any, Any>(unit()) as T
        } finally {
            value.close()
        }
    }
}

private val JSON_MEDIA_TYPE = MediaType.get("application/json; charset=UTF-8")

internal class RequestBodyConverter<T>(private val adapter: JsonAdapter<T>) : Converter<T, RequestBody> {

    @Throws(IOException::class)
    override fun convert(value: T): RequestBody {
        val buffer = Buffer()
        val writer = JsonWriter.of(buffer)
        adapter.toJson(writer, value)
        return RequestBody.create(JSON_MEDIA_TYPE, buffer.readByteString())
    }
}


private fun getParameterUpperBound(index: Int, type: ParameterizedType): Type {
    val types = type.actualTypeArguments
    if (index < 0 || index >= types.size) {
        throw IllegalArgumentException(
            "Index " + index + " not in range [0," + types.size + ") for " + type
        )
    }
    val paramType = types[index]
    return if (paramType is WildcardType) {
        paramType.upperBounds[0]
    } else paramType
}