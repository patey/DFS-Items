package com.gmail.Patey07;

import com.google.common.base.Function;

public class utils {

	Function<Object,String> stringTransformer = new Function<Object,String>() {
	    
	    public String apply(Object input) {
	        if (input instanceof String) {
	            return (String) input;
	        } else {
	            return null;
	        }
	    }
	};
}
