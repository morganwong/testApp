package com.testapp.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
	
	@Override
	protected void configure(AuthenticationManagerBuilder auth)
			throws Exception {
		auth.inMemoryAuthentication().withUser("user").password("userpassword").roles("USER");
		auth.inMemoryAuthentication().withUser("admin").password("adminpassword").roles("ADMIN");
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		
		
		http	
				.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).sessionFixation().none().
			and()
				.httpBasic()
			.and()
				.authorizeRequests().antMatchers("/**").permitAll()
			.and()
				.csrf().disable();
	}


}