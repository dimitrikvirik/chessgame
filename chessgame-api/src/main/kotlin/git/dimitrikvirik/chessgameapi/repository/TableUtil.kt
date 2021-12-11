package git.dimitrikvirik.chessgameapi.repository

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.stereotype.Component


@Component
class TableUtil {

    @Autowired
    lateinit var jdbcTemplate: JdbcTemplate


    fun create( tableName: String, columns: String){
        val sql = """
           CREATE TABLE IF NOT EXISTS $tableName(
                id uuid
                $columns
            );
            """
        jdbcTemplate.execute(sql)
    }

}