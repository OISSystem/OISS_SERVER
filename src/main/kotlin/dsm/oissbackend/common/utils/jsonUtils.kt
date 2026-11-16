package dsm.oissbackend.common.utils

import org.springframework.stereotype.Component
import tools.jackson.core.type.TypeReference
import tools.jackson.databind.ObjectMapper



@Component
class jsonUtils {

    private val objectMapper = ObjectMapper()

    fun jsonToMap(json: String): MutableMap<String?, Any?>? {
        val typeReference: TypeReference<MutableMap<String?, Any?>?> =
            object : TypeReference<MutableMap<String?, Any?>?>() {}

        return objectMapper.readValue(json, typeReference)
    }

    fun mapToJson(map: Map<String, String>): String {
        return objectMapper.writeValueAsString(map)
    }
}
