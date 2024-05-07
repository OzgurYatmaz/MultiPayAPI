package com.multipay.routing.dispatchers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;

import com.multipay.beans.Response;
import com.multipay.beans.ResponseHeader;
import com.multipay.model.StatusMessageConstants;

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