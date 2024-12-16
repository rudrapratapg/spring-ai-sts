package com.projectai.springai.listeners;

import org.springframework.context.ApplicationEvent;
import org.springframework.context.event.ContextClosedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;

@Component
public class ApplicationListener {


	@PostConstruct
	public void init() {
	    // Code to be executed after the bean has been constructed
		System.out.println("Application initialized!!!");
	}

	@PreDestroy
	public void destroy() {
	    // Code to be executed before the bean is destroyed
		System.out.println("Application is terminating!!!");
	}

	@EventListener
	public void onApplicationEvent(ApplicationEvent event) {
	    // Code to be executed in response to a specific event
		System.out.println("Application event: " + event);
		if (event instanceof ContextClosedEvent) {
            // Application is shutting down
            System.out.println("Application is shutting down...");
        }
	}

}
