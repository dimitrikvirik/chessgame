package git.dimitrikvirik.chessgameapi.repository

import git.dimitrikvirik.chessgameapi.repository.annotation.Entity
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.config.BeanDefinition
import org.springframework.context.annotation.ClassPathScanningCandidateComponentProvider
import org.springframework.core.type.filter.AnnotationTypeFilter
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.stereotype.Component

@Component
class EntityScanner {

    @Autowired
    lateinit var tableUtil: TableUtil

    fun scan(scanPackage: String) {
        val provider: ClassPathScanningCandidateComponentProvider = createComponentScanner()
        for (entity in provider.findCandidateComponents(scanPackage)) {
            process(entity)
        }
    }

    private fun createComponentScanner(): ClassPathScanningCandidateComponentProvider {
        val provider = ClassPathScanningCandidateComponentProvider(false)
        provider.addIncludeFilter(AnnotationTypeFilter(Entity::class.java))
        return provider
    }

    private fun process(entity: BeanDefinition) {

        val clazz = Class.forName(entity.beanClassName)
        val tableName = clazz.simpleName
        val fields = clazz.declaredFields.filter {
            ColumnType.isSupportedType(it.type)
        }
        val columns = fields.map {
            EntityConverter.fieldToColumn(it)
        }.filter {
            it.name != "id"
        }.toMutableList()
        val enums = fields.filter {
            it.type.isEnum
        }.map { Column(it.name, ColumnType.VARCHAR) }
        columns.addAll(enums)

        val foreign = clazz.declaredFields.filter {
            it.type.annotations.any { an ->
                an.annotationClass.simpleName == "Entity"
            }
        }.map {
            it.name + "_id uuid"
        }


        var columnsAsString = columns.joinToString(", ") {
            it.name + " " + it.type.type
        }
        val joinedForeign = foreign.joinToString(", ") {
            it
        }
        columnsAsString += joinedForeign

        if (columnsAsString != "") {
            columnsAsString = ", $columnsAsString"

        }
        tableUtil.create(tableName, columnsAsString)

    }
}