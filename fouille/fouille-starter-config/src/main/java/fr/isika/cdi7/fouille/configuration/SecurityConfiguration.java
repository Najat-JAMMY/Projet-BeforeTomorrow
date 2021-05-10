package fr.isika.cdi7.fouille.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import fr.isika.cdi7.fouille.services.UserService;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

	@Autowired
	private UserService userService;

	@Bean
	public BCryptPasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	public DaoAuthenticationProvider authenticationProvider() {
		DaoAuthenticationProvider auth = new DaoAuthenticationProvider();
		auth.setUserDetailsService(userService);
		auth.setPasswordEncoder(passwordEncoder());
		return auth;
	}

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.authenticationProvider(authenticationProvider());
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {

		http.authorizeRequests().antMatchers("/registration**", "/js/**", "/css/**", "/img/**", "/users/inscription")
				.permitAll()
				// .anyRequest().authenticated()
				// .antMatchers("/auth/*").hasAnyRole("ADMIN","USER")
				// .antMatchers("/don/**").hasRole("GESTIONNAIRE_PROJET")
				// .antMatchers("/don/*").hasRole("GESTIONNAIRE_PROJET")
				// .antMatchers("/don/*").access("hasRole('GESTIONNAIRE_PROJET')")
				// .antMatchers("/lancerUnProjet/*").access("hasRole('USER')")
				// .antMatchers("/lancerUnProjet/*").hasAuthority("UTILISATEUR")
				.antMatchers("/projet/ajouterAuxFavoris").hasRole("UTILISATEUR") 
				.antMatchers("/projet/retirerDesFavoris").hasRole("UTILISATEUR")
				.antMatchers("/compte/*").hasRole("UTILISATEUR") 
				.antMatchers("/don/*").hasRole("UTILISATEUR") 
				.antMatchers("/lancerUnProjet/*").hasRole("UTILISATEUR") 
				.antMatchers("/admins/*").hasAnyRole("GESTIONNAIRE_PROJET","DIRECTEUR","CHARGE_COM") 
				.and() 
				.formLogin() 
				.loginPage("/users/login") 
				.defaultSuccessUrl("/users/succes", true) 
				.permitAll().and().logout().invalidateHttpSession(true).clearAuthentication(true)
				.logoutRequestMatcher(new AntPathRequestMatcher("/logout")).logoutSuccessUrl("/") //login?logout
				.permitAll();
	}

}
