package com.example.config.messaging;

import com.example.exception.handler.MessageErrorHandler;
import org.apache.activemq.ActiveMQConnectionFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jms.annotation.EnableJms;
import org.springframework.jms.config.DefaultJmsListenerContainerFactory;
import org.springframework.jms.connection.JmsTransactionManager;
import org.springframework.jms.connection.SingleConnectionFactory;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.support.converter.MappingJackson2MessageConverter;
import org.springframework.jms.support.converter.MessageConverter;
import org.springframework.jms.support.converter.MessageType;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.util.ErrorHandler;

@EnableTransactionManagement
@EnableJms
@Configuration
public class JmsConfig {
    /**
     * Single Connection Factory bean having ActiveMQ as its basis.
     *
     * @return SingleConnectionFactory
     */
    @Bean
    public SingleConnectionFactory connectionFactory() {

        ActiveMQConnectionFactory factory =
            new ActiveMQConnectionFactory("admin", "admin", "tcp://localhost:61616");
        return new SingleConnectionFactory(factory);
    }

    /**
     * Error handler bean for handling message exceptions.
     */
    @Bean
    public ErrorHandler errorHandler() {
        return new MessageErrorHandler();
    }
    /**
     * Jms Listener Container Factory bean.
     *
     * @return DefaultJmsListenerContainerFactory
     */
    @Bean
    public DefaultJmsListenerContainerFactory jmsListenerContainerFactory(ErrorHandler errorHandler) {
        DefaultJmsListenerContainerFactory factory = new DefaultJmsListenerContainerFactory();
        factory.setConnectionFactory(connectionFactory());
        factory.setMessageConverter(jmsMessageConverter());
        factory.setErrorHandler(errorHandler);
        factory.setTransactionManager(transactionManager());
        return factory;
    }

    /**
     * Message Converter bean.
     *
     * @return MessageConverter
     */
    @Bean
    public MessageConverter jmsMessageConverter() {
        MappingJackson2MessageConverter mappingJackson2MessageConverter = new MappingJackson2MessageConverter();
        mappingJackson2MessageConverter.setTargetType(MessageType.TEXT);
        mappingJackson2MessageConverter.setTypeIdPropertyName("_type");
        return mappingJackson2MessageConverter;
    }

    /**
     * Jms Transaction Manager bean.
     */
    @Bean
    public PlatformTransactionManager transactionManager() {
        return new JmsTransactionManager(connectionFactory());
    }

    /**
     * Jms template bean.
     */
    @Bean
    public JmsTemplate jmsTemplate() {
        JmsTemplate jmsTemplate = new JmsTemplate(connectionFactory());
        jmsTemplate.setMessageConverter(jmsMessageConverter());
        jmsTemplate.setDeliveryPersistent(true);
        jmsTemplate.setSessionTransacted(true);
        return jmsTemplate;
    }
}
