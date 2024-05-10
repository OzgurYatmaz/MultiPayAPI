/**
 * This package is for all main services of the integrated payment service providers
 */
package com.multipay.routing.dispatchers;

import org.springframework.stereotype.Component;


@Component
public abstract class AbstractDispatcher implements MultiPayDispatcher {

	/**
	 * Used for identification of external payment service providers. Vital for internal routing
	 */
	public int dispatcherId;

	@Override
	public int getDispatcherId() {
		return dispatcherId;
	}

	@Override
	public void setDispatcherId(int dispatcherId) {
		this.dispatcherId = dispatcherId;
	}

}