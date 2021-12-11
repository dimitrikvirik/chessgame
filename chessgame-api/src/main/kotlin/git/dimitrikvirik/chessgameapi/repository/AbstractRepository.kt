package git.dimitrikvirik.chessgameapi.repository

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.repository.CrudRepository
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.stereotype.Repository
import java.util.*

@Repository
abstract class AbstractRepository<T, ID> : CrudRepository<T, ID> {

    @Autowired
    lateinit var jdbcTemplate: JdbcTemplate


    override fun <S : T> save(entity: S): S {

        TODO("Not yet implemented")
    }

    override fun <S : T> saveAll(entities: MutableIterable<S>): MutableIterable<S> {
        TODO("Not yet implemented")
    }

    override fun findById(id: ID): Optional<T> {
        TODO("Not yet implemented")
    }

    override fun existsById(id: ID): Boolean {
        TODO("Not yet implemented")
    }

    override fun findAll(): MutableIterable<T> {
        TODO("Not yet implemented")
    }

    override fun findAllById(ids: MutableIterable<ID>): MutableIterable<T> {
        TODO("Not yet implemented")
    }

    override fun count(): Long {
        TODO("Not yet implemented")
    }

    override fun deleteById(id: ID) {
        TODO("Not yet implemented")
    }

    override fun delete(entity: T) {
        TODO("Not yet implemented")
    }

    override fun deleteAllById(ids: MutableIterable<ID>) {
        TODO("Not yet implemented")
    }

    override fun deleteAll(entities: MutableIterable<T>) {
        TODO("Not yet implemented")
    }

    override fun deleteAll() {
        TODO("Not yet implemented")
    }
}