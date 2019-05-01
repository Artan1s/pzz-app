package by.mikemladinskiy.pzz.core.unit.backend

import by.mikemladinskiy.pzz.core.backend.internal.dto.PzzInnerResponseDto
import by.mikemladinskiy.pzz.core.backend.internal.dto.PzzResponseDto
import com.squareup.moshi.Moshi
import com.squareup.moshi.Types
import org.junit.Assert.assertEquals
import org.junit.Test

class PzzResponseDtoAdapterTest {

    @Test
    fun can_serialize_and_deserialize() {

        val pzzResponseDto = PzzResponseDto(PzzInnerResponseDto(TestData(20, "abc")))

        val moshi = Moshi.Builder()
            .build()

        val jsonAdapter = moshi.adapter<PzzResponseDto<TestData>>(
            Types.newParameterizedType(
                PzzResponseDto::class.java,
                TestData::class.java
            )
        )

        val json = jsonAdapter
            .toJson(pzzResponseDto)

        assertEquals("""{"response":{"data":{"n":20,"s":"abc"}}}""", json)

        val deserialized = jsonAdapter.fromJson(json)!!
        assertEquals(pzzResponseDto.response.data, deserialized.response.data)
    }


}

private data class TestData(val n: Int, val s: String)