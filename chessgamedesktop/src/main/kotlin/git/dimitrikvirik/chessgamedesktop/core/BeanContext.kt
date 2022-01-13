package git.dimitrikvirik.chessgamedesktop.core

import org.springframework.beans.BeansException
import org.springframework.context.ApplicationContext
import org.springframework.context.ApplicationContextAware
import org.springframework.stereotype.Component

@Component
class BeanContext : ApplicationContextAware {

    @Throws(BeansException::class)
    override fun setApplicationContext(context: ApplicationContext) {
        Companion.context = context
    }

    companion object {
        lateinit var context: ApplicationContext
        fun <T> getBean(beanClass: Class<T>): T {
            return context.getBean(beanClass)
        }
    }
}