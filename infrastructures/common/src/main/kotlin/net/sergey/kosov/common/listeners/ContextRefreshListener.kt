package net.sergey.kosov.common.listeners

import net.sergey.kosov.common.annotations.PostRefresh
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory
import org.springframework.context.event.ContextRefreshedEvent
import org.springframework.context.event.EventListener
import org.springframework.util.ClassUtils

//@Component
class ContextRefreshListener constructor(private val beanFactory: ConfigurableListableBeanFactory) {

    @EventListener
    fun handleContextRefresh(event: ContextRefreshedEvent) {
        val context = event.applicationContext
        for (beanDefinitionName in context.beanDefinitionNames) {
            beanFactory.getBeanDefinition(beanDefinitionName).beanClassName?.let {
                val beanClass = ClassUtils.resolveClassName(it, ClassLoader.getSystemClassLoader())
                beanClass.methods.forEach {
                    if (it.isAnnotationPresent(PostRefresh::class.java)) {
                        it.invoke(context.getBean(beanDefinitionName))
                    }
                }

            }
        }
    }

}