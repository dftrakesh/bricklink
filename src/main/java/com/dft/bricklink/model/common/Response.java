package com.dft.bricklink.model.common;

import lombok.Data;

@Data
public class Response<T extends DataInterface> {
	private Meta meta;
	private T data;
}