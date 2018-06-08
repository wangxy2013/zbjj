package com.zb.wyd.http;

public interface IRequestListener {

	/**
	 * notify
	 */
	public void notify(String action, String resultCode, String resultMsg, Object obj);
}
