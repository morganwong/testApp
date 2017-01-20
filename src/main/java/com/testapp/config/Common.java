package com.testapp.config;

import java.util.UUID;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.ResourceProcessor;

@Configuration
public class Common {
	
	public Common() {
	}
	
	@Bean(name="cacheThreadPool")
	public	ExecutorService		cacheThreadPool() {
		return Executors.newCachedThreadPool();
	}
	
	@Bean(name="singleThreadWorker")
	public	ExecutorService		singleThreadWorker() {
		return Executors.newSingleThreadExecutor();
	}
	
	
	static	protected		class	BlockLinksResourceProcessor<T> implements ResourceProcessor<Resource<T>> {

		@Override
		public Resource<T> process(
				Resource<T> resource) {
			resource.removeLinks();
			resource.add(new Link("http://localhost:8080/" + UUID.randomUUID().toString(), "self"));
			return resource;
		}
		
		
	}
}
