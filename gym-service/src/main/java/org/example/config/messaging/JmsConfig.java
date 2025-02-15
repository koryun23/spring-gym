package org.example.config.messaging;

import javax.jms.ConnectionFactory;
import org.apache.activemq.ActiveMQConnectionFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jms.annotation.EnableJms;
import org.springframework.jms.connection.SingleConnectionFactory;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.support.converter.MappingJackson2MessageConverter;
import org.springframework.jms.support.converter.MessageConverter;
import org.springframework.jms.support.converter.MessageType;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@EnableJms
@Configuration
@EnableTransactionManagement
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
     * Jms Template bean.
     */
    @Bean
    public JmsTemplate jmsTemplate(ConnectionFactory connectionFactory) {
        JmsTemplate jmsTemplate = new JmsTemplate(connectionFactory);
        jmsTemplate.setMessageConverter(jmsMessageConverter());
        return jmsTemplate;
    }
}
