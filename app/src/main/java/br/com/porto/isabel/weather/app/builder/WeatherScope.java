package br.com.porto.isabel.weather.app.builder;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import javax.inject.Scope;

@Scope
@Retention(RetentionPolicy.SOURCE)
public @interface WeatherScope {
}