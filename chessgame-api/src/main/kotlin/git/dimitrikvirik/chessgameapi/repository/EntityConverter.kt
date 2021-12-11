package git.dimitrikvirik.chessgameapi.repository

import java.lang.reflect.Field
import java.time.LocalDateTime
import java.util.*
import javax.ws.rs.NotSupportedException

enum class ColumnType(val type: String) {
    SHORT("smallint"),
    INT("integer"),
    LONG("bigint"),
    DOUBLE("decimal"),
    TIMESTAMP("timestamp"),
    VARCHAR("varchar(250)"),
    UUID("uuid");

    companion object {

        fun isSupportedType(type: Class<*>): Boolean {
            return when (type) {
                Short::class.java -> true
                Int::class.java -> true
                Long::class.java -> true
                Double::class.java -> true
                LocalDateTime::class.java -> true
                java.util.UUID::class.java -> true
                else -> {
                    false
                }
            }
        }

        fun typeToColumn(type: Class<*>): ColumnType {
            return when (type) {
                Short::class.java -> SHORT
                Int::class.java -> INT
                Long::class.java -> LONG
                Double::class.java -> DOUBLE
                LocalDateTime::class.java -> TIMESTAMP
                java.util.UUID::class.java -> UUID
                else -> {
                    throw NotSupportedException()
                }
            }
        }
    }
}

data class Column(
    val name: String,
    val type: ColumnType
)


class EntityConverter {

    companion object {
        fun fieldToColumn(field: Field): Column {
            return Column(field.name, ColumnType.typeToColumn(field.type))
        }
    }

}