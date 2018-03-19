package com.shepard.client.config

import org.springframework.boot.autoconfigure.security.oauth2.client.EnableOAuth2Sso
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import org.springframework.web.context.request.RequestContextListener
import org.springframework.web.servlet.config.annotation.*

@Configuration
@EnableOAuth2Sso
class OAuthConfig : WebSecurityConfigurerAdapter() {
    override fun configure(http: HttpSecurity) {
        http.antMatcher("/**")
                .authorizeRequests()
                .antMatchers("/", "login**")
                .permitAll()
                .anyRequest()
                .authenticated()
    }
}

@Configuration
@EnableWebMvc
class WebConfig : WebMvcConfigurer {
    override fun configureDefaultServletHandling(configurer: DefaultServletHandlerConfigurer) {
        configurer.enable()
    }

    override fun addViewControllers(registry: ViewControllerRegistry) {
        super.addViewControllers(registry)
        registry.apply {
            addViewController("/").setViewName("forward:/index")
            addViewController("/index")
            addViewController("/secure")
        }
    }

    override fun addResourceHandlers(registry: ResourceHandlerRegistry) {
        registry.addResourceHandler("/resources/**")
                .addResourceLocations("/resources/")
    }

    @Bean
    fun propertySourcesPlaceholderConfigurer() =
            PropertySourcesPlaceholderConfigurer()

    @Bean
    fun requestContextListener() = RequestContextListener()
}