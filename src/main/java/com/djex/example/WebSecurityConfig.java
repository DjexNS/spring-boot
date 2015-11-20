package com.djex.example;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

import com.google.common.base.Splitter;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity
@PropertySource(value = "application.properties")
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

	public String key, value;

	@Value("${roleUser}")
	private String roleData;

	@Value("${map}")
	private String readMap;

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.authorizeRequests().antMatchers("/css/**").permitAll().anyRequest().authenticated().and().formLogin()
				.loginPage("/login").permitAll().and().logout().permitAll();
	}

	@Autowired
	public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {

		Map<String, String> mapa = parseMap(readMap);

		for (Map.Entry<String, String> entry : mapa.entrySet()) {
			String key = entry.getKey();
			String value = entry.getValue();
			auth.inMemoryAuthentication().withUser(key).password(value).roles(roleData);
		}
	}

	protected Map<String, String> parseMap(String readMap) {
		return Splitter.on(",").withKeyValueSeparator("=").split(readMap);
	}

}