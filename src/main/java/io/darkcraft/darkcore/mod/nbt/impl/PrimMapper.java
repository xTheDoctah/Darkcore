package io.darkcraft.darkcore.mod.nbt.impl;

import net.minecraft.nbt.NBTTagCompound;

import io.darkcraft.darkcore.mod.nbt.Mapper;

public abstract class PrimMapper<T> extends Mapper<T>
{
	@Override
	public void writeToNBT(NBTTagCompound nbt, T o)
	{
		writeToNBT(nbt, "value", o);
	}

	@Override
	public abstract void writeToNBT(NBTTagCompound nbt, String id, T t);

	@Override
	public T fillFromNBT(NBTTagCompound nbt, T t){return t;}

	@Override
	public abstract T readFromNBT(NBTTagCompound nbt, String id);

	@Override
	public T fillFromNBT(NBTTagCompound nbt, String id, T t)
	{
		return readFromNBT(nbt, id);
	}

	@Override
	public T createFromNBT(NBTTagCompound nbt, Object... arguments)
	{
		return readFromNBT(nbt, "value");
	}

	@Override
	public boolean shouldCreateNew() { return true; }
}
