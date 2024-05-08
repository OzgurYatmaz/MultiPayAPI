package com.multipay.routing.dispatchers;

import org.springframework.stereotype.Component;

@Component
public abstract class AbstractDispatcher implements MultiPayDispatcher {

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