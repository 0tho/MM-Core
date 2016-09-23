package me.otho.metamods.core.api;

public interface IMetaTypeRegister {

	public void register (Object data);
	
	public int getPriority();

	public Class<?> getReaderClass();
}
