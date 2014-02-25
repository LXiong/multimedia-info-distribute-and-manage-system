package com.yunling.mediacenter.web;

public @interface FuncDeclare {
	Functions[] value() default {Functions.Basic};

}
