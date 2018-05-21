package com.nagarro.pos.constant;

import java.lang.annotation.Documented;

/**
 * interface annotation for Javadoc
 */
@MyDoc(author = Constant.AUTHOR, date = Constant.CREATION_DATE, currentRevision = 1)
@Documented
public @interface MyDoc {
	String author();

	String date();

	int currentRevision() default 1;

}
