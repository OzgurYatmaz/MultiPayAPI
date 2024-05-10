/**
 * This package is for data transfer objects (DTO) to transfer data over web while communicating with this API
 */
package com.multipay.dto;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * 
 * Objects of this class are used to transfer data over web. Response body of
 * make payment operation
 * 
 * @see com.multipay.controller.MultiPayRestService.startPayment(ProcessPaymentRequestDTO)
 * 
 * @author Ozgur Yatmaz
 * @version 1.0.0
 * @since 2024-05-10
 * 
 */
@Schema(description = "Start Payment Response Model Information")
public class ProcessPaymentResponseDTO extends Response {

}
