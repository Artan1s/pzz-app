package by.mikemladinskiy.pzz.core.unit.backend.internal

import by.mikemladinskiy.pzz.core.backend.internal.ResponseBodyConverter
import com.besmartmobile.result.annimon.Result
import com.besmartmobile.result.annimon.ResultExt.fail
import com.besmartmobile.result.annimon.Unit
import com.squareup.moshi.Moshi
import okhttp3.MediaType
import okhttp3.ResponseBody
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test

class ResponseBodyConverterTest {

    private val moshi = Moshi.Builder()
        .build()
    private val dummyResultOfTestDataAndUnit: Result<TestData, Unit> = fail()
    private val dummyResultOfTestDataAndUnitType = ResponseBodyConverterTest::class.java
        .getDeclaredField("dummyResultOfTestDataAndUnit")
        .genericType

    private val dummyListOfStrings: List<String> = listOf()
    private val dummyListOfStringsType = ResponseBodyConverterTest::class.java
        .getDeclaredField("dummyListOfStrings")
        .genericType

    private val validResponseBody = jsonResponseBody("""{"response":{"data":{"n":20,"s":"abc"}}}""")

    @Test
    fun can_convert() {
        // given
        val responseBodyContent = """{"response":{"data":{"n":20,"s":"abc"}}}"""
        val responseBody = jsonResponseBody(responseBodyContent)

        val converter = ResponseBodyConverter<TestData>(dummyResultOfTestDataAndUnitType, moshi)

        // when
        val result = converter.convert(responseBody)


        // then
        assertTrue(result.isSuccess)
        assertEquals(20, result.value.n)
        assertEquals("abc", result.value.s)
    }

    @Test(expected = IllegalArgumentException::class)
    fun throws_on_non_generic_type() {
        val converter = ResponseBodyConverter<TestData>(String::class.java, moshi)

        // when
        val result = converter.convert(emptyResponseBody())
    }

    @Test(expected = IllegalArgumentException::class)
    fun throws_on_non_Result_type() {
        val converter = ResponseBodyConverter<TestData>(dummyListOfStringsType, moshi)

        // when
        val result = converter.convert(emptyResponseBody())
    }

    @Test
    fun fails_on_invalid_json() {
        // given
        val responseBody = jsonResponseBody("{{")
        val converter = ResponseBodyConverter<TestData>(dummyResultOfTestDataAndUnitType, moshi)

        // when
        val result = converter.convert(responseBody)

        // then
        assertTrue(result.isFailure)
    }

    @Test
    fun closes_responseBody_in_exception_case() {
        // given
        val responseBody = ResponseBodyWithObservableClosedState(emptyResponseBody())
        val converter = ResponseBodyConverter<TestData>(dummyResultOfTestDataAndUnitType, moshi)

        // when
        val result = converter.convert(responseBody)

        // then
        assertTrue(responseBody.isClosed)
    }

    @Test(expected = Throwable::class)
    fun closes_responseBody_in_unexpected_throwable_case() {
        // given
        val responseBody = NotImplementedResponseBodyWithObservableClosedState()
        val converter = ResponseBodyConverter<TestData>(dummyResultOfTestDataAndUnitType, moshi)

        // when
        val result = converter.convert(responseBody)

        // then
        assertTrue(responseBody.isClosed)
    }

    @Test
    fun closes_responseBody_in_normal_case() {
        // given
        val responseBody = ResponseBodyWithObservableClosedState(validResponseBody)
        val converter = ResponseBodyConverter<TestData>(dummyResultOfTestDataAndUnitType, moshi)

        // when
        val result = converter.convert(responseBody)

        // then
        assertTrue(responseBody.isClosed)
    }

    private fun jsonResponseBody(responseBodyContent: String) =
        ResponseBody.create(MediaType.parse("application/json"), responseBodyContent)

    private fun emptyResponseBody() =
        jsonResponseBody("")
}

private class ResponseBodyWithObservableClosedState(private val responseBody: ResponseBody): ResponseBody() {
    override fun contentLength() = responseBody.contentLength()
    override fun contentType() = responseBody.contentType()
    override fun source() = responseBody.source()

    var isClosed = false
    override fun close() {
        responseBody.close()
        isClosed = true
    }
}

private class NotImplementedResponseBodyWithObservableClosedState: ResponseBody() {
    override fun contentLength() = throw NotImplementedError()
    override fun contentType() = throw NotImplementedError()
    override fun source() = throw NotImplementedError()

    var isClosed = false
    override fun close() {
        isClosed = true
    }
}

private data class TestData(val n: Int, val s: String)