package net.sergey.kosov.market.infrastracture

import net.sergey.kosov.market.api.CommunicationApi
import net.sergey.kosov.market.domains.entity.Message
import net.sergey.kosov.market.domains.entity.Order
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.config.BeanPostProcessor
import org.springframework.scheduling.annotation.Async
import org.springframework.scheduling.annotation.EnableAsync
import org.springframework.stereotype.Component
import org.springframework.stereotype.Service
import java.lang.reflect.Proxy

@Component
class MarketEventInvokerBeanPostProcessor constructor(var internalNotificationInvoker: InternalNotificationInvoker) : BeanPostProcessor {
    private val log = LoggerFactory.getLogger(javaClass)
    val map = HashMap<String, Class<Any>>()


    override fun postProcessBeforeInitialization(bean: Any, beanName: String): Any {
        val annotatedMethods = bean.javaClass.methods.any { it.isAnnotationPresent(NotifyEvent::class.java) }
        if (annotatedMethods) {
            map[beanName] = bean.javaClass
        }
        return bean
    }

    override fun postProcessAfterInitialization(bean: Any, beanName: String): Any {
        val beanClass = map[beanName]
        beanClass?.let {
            return Proxy.newProxyInstance(it.classLoader, it.interfaces) { _, method, args ->
                val annotation = beanClass.getMethod(method.name, *method.parameterTypes).getAnnotation(NotifyEvent::class.java)
                annotation?.let {
                    val retVal = method.invoke(bean, *args)
                    try {
                        internalNotificationInvoker.sendEvent(retVal, method.name, annotation)
                    } catch (e: Exception) {
                        log.warn("Can not send notification", e)
                    }
                    retVal
                } ?: method.invoke(bean, *args)

            }
        }
        return bean
    }


}

@Service
@EnableAsync
class InternalNotificationInvoker @Autowired constructor(val communicationApi: CommunicationApi) {
    val methodName2Message: Map<String, String> = HashMap() //todo заиндектить сюда мапу из пропертей

    @Async//todo посмотреть на каком пуле потоков это все работает
    fun sendEvent(retVal: Any, methodName: String, annotation: NotifyEvent) {
        if (retVal is Order) {
            val message = when (annotation.eventTo) {
                EventTo.SELLER -> {
                    val message = "Customer ${retVal.customer.fullName} ${getMessage(methodName)} \"${retVal.title}\""
                    Message(mess = message, to = retVal.customer.email, entityId = retVal.id.toString())
                }
                EventTo.CUSTOMER -> {
                    val message = "Seller ${retVal.customer.fullName} ${getMessage(methodName)} \"${retVal.title}\""
                    Message(mess = message, to = retVal.customer.email, entityId = retVal.id.toString())
                }
            }
            communicationApi.sendInternalMessage(message)
        }
    }

    private fun getMessage(methodName: String) =
            methodName2Message.getOrDefault(methodName, "executed method $methodName")
}