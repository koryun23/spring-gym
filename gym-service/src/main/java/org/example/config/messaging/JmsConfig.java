package org.example.config.messaging;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jms.annotation.JmsListenerConfigurer;
import org.springframework.jms.config.DefaultJmsListenerContainerFactory;
import org.springframework.jms.config.JmsListenerEndpointRegistrar;
import org.springframework.jms.connection.SingleConnectionFactory;

@Configuration
public class JmsConfig implements JmsListenerConfigurer {

    /**
     * Single Connection Factory bean having ActiveMQ as its basis.
     *
     * @return SingleConnectionFactory
     */
    @Bean
    public SingleConnectionFactory connectionFactory() {

        ActiveMQConnectionFactory factory =
            new ActiveMQConnectionFactory("admin", "admin", "tcp://localhost:61616");
        SingleConnectionFactory connectionFactory = new SingleConnectionFactory(factory);
        return connectionFactory;
    }

    /**
     * Jms Listener Container Factory bean.
     *
     * @return DefaultJmsListenerContainerFactory
     */
    @Bean
    public DefaultJmsListenerContainerFactory jmsListenerContainerFactory() {
        DefaultJmsListenerContainerFactory factory = new DefaultJmsListenerContainerFactory();
        factory.setConnectionFactory(connectionFactory());
        return factory;
    }


    @Override
    public void configureJmsListeners(JmsListenerEndpointRegistrar jmsListenerEndpointRegistrar) {

    }
}
