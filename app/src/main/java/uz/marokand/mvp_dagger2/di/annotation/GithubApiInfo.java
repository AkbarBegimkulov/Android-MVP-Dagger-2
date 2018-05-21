package uz.marokand.mvp_dagger2.di.annotation;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import javax.inject.Qualifier;

/**
 * @author akbar
 * @since 2018, May 21
 */

@Qualifier
@Retention(RetentionPolicy.RUNTIME)
public @interface GithubApiInfo {
}
