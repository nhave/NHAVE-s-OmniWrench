package com.nhave.nhwrench.api;

public interface IIntegrationRegister
{
	public void registerHandler(IWrenchHandler handler);
	
	public void registerHandler(IWrenchHandler handler, String name);
	
	public void registerStatic(String name);
}